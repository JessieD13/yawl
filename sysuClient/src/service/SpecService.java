package service;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;
import org.json.JSONArray;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;
import org.yawlfoundation.yawl.resourcing.datastore.eventlog.LogMiner;
import org.yawlfoundation.yawl.resourcing.rsInterface.WorkQueueGatewayClientAdapter;
import org.yawlfoundation.yawl.util.JDOMUtil;

import com.opensymphony.xwork2.ActionContext;

import dao.Specification;
import dao.SpecificationDAO;
import dao.User;
import dao.UserDAO;
import dao.UserHasSpecification;
import dao.UserHasSpecificationDAO;
import dao.UserHasSpecificationId;

public class SpecService{
	String _handle;
	String _userName = "admin";
	String _password = "YAWL";
	String _defURI = "http://localhost:8080/resourceService/workqueuegateway";
	
	WorkQueueGatewayClientAdapter wqAdapter = new WorkQueueGatewayClientAdapter(_defURI);
	Map<String, Object> session = ActionContext.getContext().getSession();
	
	SpecificationDAO specDao = new SpecificationDAO();
	SpecXMLService xmlServ = new SpecXMLService();
	
	UserDAO userDao = new UserDAO();
	UserHasSpecificationDAO uhsDao = new UserHasSpecificationDAO();
	
    public SpecificationDAO getSpecDao() {
		return specDao;
	}

	public void setSpecDao(SpecificationDAO specDao) {
		this.specDao = specDao;
	}

	public SpecXMLService getXmlServ() {
		return xmlServ;
	}

	public void setXmlServ(SpecXMLService xmlServ) {
		this.xmlServ = xmlServ;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public UserHasSpecificationDAO getUhsDao() {
		return uhsDao;
	}

	public void setUhsDao(UserHasSpecificationDAO uhsDao) {
		this.uhsDao = uhsDao;
	}

	private boolean connected() {
        if (!wqAdapter.checkConnection(_handle)) {
        	
           _handle = wqAdapter.connect(_userName, _password) ;
            //_handle = wqAdapter.connect("zhang san", "1234") ;
            
            System.out.print("connect _handle:"+_handle);
            return wqAdapter.successful(_handle) ;
        }
        else return true ;
    }
    
	/**
	 * 上载一个新过程
	 * @param filePath 过程文件在本地硬盘的绝对路径
	 * @param fileName 过程文件文件名
	 * @param handle 客户端连接
	 * @return 成功则返回<success/>否则<failure>...</failure>
	 */
	public String upload(File file, String fileName){
		String result = null;
		if (this.connected()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				StringBuffer contents = new StringBuffer();
				
				String buf;
				while((buf=reader.readLine()) != null){
					if(buf != null)
						contents.append(buf);
				}
				
				String xml = contents.toString();
				result = wqAdapter.uploadSpecification(xml, fileName, _handle);

				if(result.startsWith("<failure") == false){
					/*
					 * 上传到yawl服务器成功同时，备份到client数据库中。
					 */
					
					Specification s = xmlServ.bulidSpec(xml);
					
					if( s != null) {
						specDao.save(s);
					}
					
					/*
					 * 同时注册到服务管理中心
					 */
					String servicename = fileName.substring(0, fileName.lastIndexOf("."));
					String userid = (String)session.get("userid");
					String password = (String)session.get("password");
					GetService gs = new GetService();
					boolean register = gs.registerSpecToSSH(userid, password, servicename, xml);
					if(register == false){
						result = "<failure> Register to SSH failed! </failure>";
					}
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				result = "<failure> File Not Found! </failure>";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * 启动一个特定的流程的一个案例实例
	 * @param specID 要启动的流程的ID
	 * @return 启动的案例ID，否则返回<failure>
	 */
	public String launchCase(YSpecificationID specID) {
		SpecificationData specData = this.getSpecData(specID);
		if (specData != null) {
			List<YParameter> params = specData.getInputParams();
			StringBuffer caseData = new StringBuffer();
			for (YParameter p : params) {
				caseData.append(p.toXML());
			}
			caseData.insert(0, "<"+specData.getRootNetID()+">");
			caseData.append("</"+specData.getRootNetID()+">");
			if (this.connected()) {
				try {
					
					System.out.print("_handle:"+_handle);
					return wqAdapter.launchCase(specID, caseData.toString(), _handle);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "<failure>";
	}
	
	/**
	 * 获取指定流程的所有运行中的案例
	 * @param specID 指定流程的ID
	 * @return 所有运行中的案例的ID
	 */
	public List<String> getRunningCases(YSpecificationID specID){
		List<String> result = null;
		if (this.connected()) {
			try {
				String temp = wqAdapter.getRunningCases(specID, _handle);
				if(temp.startsWith("<response><caseID>")){
					result = new ArrayList<String>();
					List<Element> es = JDOMUtil.stringToElement(temp).getChildren();
					
					for(Element e : es){
						result.add(e.getText());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String cancelCase(String caseID) {
		if (this.connected()) {
			try {
				return wqAdapter.cancelCase(caseID, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "<failure>";
	}
	
	public String unload(YSpecificationID specID){
		if (this.connected()) {
			try {
				
				String result = "";
				result = wqAdapter.unloadSpecification(specID, _handle);
				
				if(result.startsWith("<failure") == false){
					String specidentifier = specID.getIdentifier();
					
					/*
					 * 同步到client数据库中。
					 */
					
					List<Specification> s = specDao.findByIdentifier(specidentifier);
					if( s.size() > 0) {
						specDao.delete(s.get(0));
					}
					
					/**
					 * 同步到服务管理中心的流程移除
					 */
					
					//String userid = (String)session.get("userid");
					GetService gs = new GetService();
					boolean remove = gs.removeSpecFromSSH(specidentifier);
					if(remove == false){
						return "<failure>";
					}
				}
				
				return result; //wqAdapter.unloadSpecification(specID, _handle);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "<failure>";
	}

	/*public String getSpecName(String caseid) {
		if (this.connected()) {
			try {
				String caseData = wqAdapter.getCaseData(caseid, _handle);
				System.out.println(caseData);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}*/
	

	/**
	 * 所有已上传的过程
	 * @param handle 客户端应用连接获得的handle
	 * @return 已上传的所有过程
	 * @throws IOException 
	 */
	public Set<SpecificationData> getLoadedSpecList() {
		Set<SpecificationData> result = null;
		
		try {
			if(this.connected()) result = wqAdapter.getLoadedSpecs(_handle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取一个SpecificationData实例
	 * @param specID 要获取的SpecificationData实例对应的YSpecificationID
	 * @return 请求的SpecificationData实例
	 */
	public SpecificationData getSpecData(YSpecificationID specID) {
		SpecificationData result = null;
		
		if (this.connected()) {
			try {
				result = wqAdapter.getSpecData(specID, _handle);
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println(specID.getUri()+": This spec is not loaded.");
			}
		}
		return result;
	}
	
	
	/**
	 * 以caseID获取SpecificationData
	 */

	public SpecificationData getSpecData(String caseID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 获取spec name
	 */
	
	public String getSpecName(String identifier, String version, String uri) {
		
		YSpecificationID spenid = new YSpecificationID(identifier, version, uri);
		
		if (this.connected()) {
			try {
				return wqAdapter.getSpecData(spenid, _handle).getName();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 获取spec info
	 */
	
	public String getSpecInfo(String identifier, String version, String uri) {
		
		YSpecificationID spenid = new YSpecificationID(identifier, version, uri);
		
		String specsJson ="";
		
		if (this.connected()) {
			try {
				SpecificationData spec = wqAdapter.getSpecData(spenid, _handle);
				JSONArray ja = new JSONArray();
				
				if(spec != null){
					Map<String, String> m = new HashMap<String, String>();
					m.put("URI", spec.getSpecURI());
					m.put("VERSION", spec.getSpecVersion());
					m.put("DESCRIPTION", spec.getDocumentation());
					m.put("IDENTIFIER", spec.getSpecIdentifier());
					m.put("METATITLE", spec.getMetaTitle());
					m.put("SCHEMAVERSION", spec.getSchemaVersion().toString());
					m.put("AUTHOR", spec.getAuthors());
					m.put("ROOTNET", spec.getRootNetID());
					m.put("ENGINESTATUS", spec.getStatus());
					m.put("EXTERNALDATAGATEWAY", spec.getExternalDataGateway());
					ja.put(m);
				}
				specsJson = ja.toString();
				//return wqAdapter.getSpecData(spenid, _handle).getName();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return specsJson;
	}
	
	
	/**
	 * 下载spec log
	 */
	
	public String downloadLog(String identifier, String version, String uri) {
		
		YSpecificationID spenid = new YSpecificationID(identifier, version, uri);
		
		String specsJson ="success";
		
		if (this.connected()) {
			try {
				SpecificationData spec = wqAdapter.getSpecData(spenid, _handle);
				
				 if (spec != null) {
					 System.out.print(spec.getID()+";;;;");
		                String log = LogMiner.getInstance().getMergedXESLog(spec.getID(), true);
		                System.out.print("log;;;;"+log); 
					 	//String log = wqAdapter.getLog(spec);
		                if (log != null) {
		                    String filename = String.format("%s%s.xes", spec.getSpecURI(),
		                            spec.getSpecVersion());
		                    FacesContext context = FacesContext.getCurrentInstance();
		                    HttpServletResponse response =
		                            ( HttpServletResponse ) context.getExternalContext().getResponse();
		                    response.setContentType("text/xml");
		                    response.setCharacterEncoding("UTF-8");
		                    response.setHeader("Content-Disposition",
		                            "attachment;filename=\"" + filename + "\"");
		                    OutputStream os = response.getOutputStream();
		                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		                    osw.write(log);
		                    osw.flush();
		                    osw.close();
		                    FacesContext.getCurrentInstance().responseComplete();
		               //     msgPanel.success("Data successfully exported to file '" + filename + "'.");
		                }
		                else specsJson = "Unable to create export file: malformed xml.";
		            }
		        }
		        catch (IOException ioe) {
		        	specsJson = "Unable to create export file. Please see the log for details.";
		        }
		        catch (NumberFormatException nfe) {
		        	specsJson = "Please select a specification to download the log for.";
		        }
		}
		return specsJson;
	}
	
	/**
	 * 
	 * 添加关注的流程
	 */

	public void addFollow(String identifier, String userid) {
		User user = userDao.findById(userid);
		
		if(user == null) {
			User ins = new User();
			ins.setId(userid);
			userDao.save(ins);
			user = userDao.findById(userid);
		}
		
		Specification spec;
		
		List<?> list = specDao.findByIdentifier(identifier);
		
		if(list.size() == 0) {
			Specification ins = new Specification(identifier);
			specDao.save(ins);
			spec = ins;
		} else {
			spec = (Specification) list.get(0);
		}
		
		UserHasSpecificationId uhsId = new UserHasSpecificationId(user, spec);
		
		uhsDao.save(new UserHasSpecification(uhsId));
	}
	
	/**
	 * 
	 * 删除关注的流程
	 */

	public void deleteFollow(String identifier, String userid) {
		User user = userDao.findById(userid);
		
		if(user == null) {
			User ins = new User();
			ins.setId(userid);
			userDao.save(ins);
			user = userDao.findById(userid);
		}
		
		Specification spec;
		
		List<?> list = specDao.findByIdentifier(identifier);
		System.out.print("list.size():"+list.size());
		if(list.size() == 0) {
			Specification ins = new Specification(identifier);
			specDao.save(ins);
			spec = ins;
			System.out.print("userid:"+userid+"  identifier:"+identifier+"  spec.getId():"+spec.getId());
		} else {
			spec = (Specification) list.get(0);
		}
		System.out.print("userid:"+userid+"  identifier:"+identifier+"  spec.getId():"+spec.getId());
		UserHasSpecificationId uhsId = new UserHasSpecificationId(user, spec);
		
		uhsDao.delete(new UserHasSpecification(uhsId));
	}

	public boolean isFollowed(String identifier, String userid) {
		
		User user = userDao.findById(userid);
		
		if(user == null) {
			return false;
		}
		
		//User user = new User(userid);
		
		List<?> list = specDao.findByIdentifier(identifier);
		
		if(list.size() == 0) {
			return false;
		}
		
		UserHasSpecificationId ins = new UserHasSpecificationId(user, (Specification) list.get(0));
		
		if( uhsDao.findById(ins) != null ) {
			return true;
		} else {
			return false;
		}

	}
	
}