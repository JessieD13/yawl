package service;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONArray;
import org.yawlfoundation.yawl.elements.YSpecVersion;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;
import org.yawlfoundation.yawl.resourcing.resource.Participant;
import org.yawlfoundation.yawl.resourcing.rsInterface.ResourceLogGatewayClient;
import org.yawlfoundation.yawl.resourcing.rsInterface.WorkQueueGatewayClientAdapter;

public class LogService{
	String _handle;
	String _userName = "admin";
	String _password = "YAWL";
	String _defURI = "http://localhost:8080/resourceService/logGateway";
	String _defURI2 = "http://localhost:8080/resourceService/workqueuegateway";
	
	ResourceLogGatewayClient rsLogClient = new ResourceLogGatewayClient(_defURI);
	WorkQueueGatewayClientAdapter wqAdapter = new WorkQueueGatewayClientAdapter(_defURI2);
	
	WorkQueueService workqueueServ = new WorkQueueService();
	
    private boolean connected() {
    	try {
    		String check = rsLogClient.checkConnection(_handle);
			if (check.equals("false")) {
				_handle = rsLogClient.connect(_userName, _password);
				return rsLogClient.successful(_handle);
			} else {
				return true;
			}
		} catch (IOException e) {
			return false;
		}
    }
    
    public String getCaseEvents(String caseID) {
    	String result = null;
    	if (connected()) {
    		try {
				result = rsLogClient.getCaseEvents(caseID, _handle);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return result;
    }

	public Map<String, Map<String, String>> getCaseParticipate(String userid) {
		Participant p = workqueueServ.getParticipantFromUserid(userid);
		
		Map<String, Map<String, String>> mm = new HashMap<String, Map<String, String>>();
    	if (connected()) {
    		try {
    			String xml = rsLogClient.getParticipantHistory(p.getID(), _handle);
				
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(new StringReader(xml));
				Element root = doc.getRootElement(); //获取根元素 
				
				List<?> events = root.getChildren("event");
				
				for(Object o : events) {
								
					Element event = (Element) o;

					String caseid = event.getChildText("caseid");
					String itemid = event.getChildText("itemid");
					String taskid = event.getChildText("taskid");
					String eventtype = event.getChildText("eventtype");
					
					YSpecificationID specID = this.getYSpecificationIDFromKey(event.getChildText("speckey"));
					SpecificationData specData = this.getSpecData(specID);

					String specname = specData.getName();

					Long timeLong = new Long(event.getChildText("timestamp"));
					SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String time = sdf.format(timeLong);
					
					if( !eventtype.equals("launch_case") && !eventtype.equals("cancel_case") && !eventtype.equals("cancelled_by_case"))
						if( mm.get(caseid) == null || this.compare_date(mm.get(caseid).get("time"), time) == -1) {
							
							Map<String, String> m = new HashMap<String, String>();
	
							m.put("specname", specname);
							m.put("itemid", itemid);
							m.put("taskid", taskid);
							m.put("type",  eventtype);
							m.put("time", time);
							
							mm.put(caseid, m);
						}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
    	}
    	return mm;
		
	}
	
	
	public Map<String, Map<String, String>> getCaseParticipateOfComplete(String userid) {
		Participant p = workqueueServ.getParticipantFromUserid(userid);
		Map<String, Map<String, String>> mm = new HashMap<String, Map<String, String>>();
    	if (connected()) {
    		try {
    			String xml = rsLogClient.getParticipantHistory(p.getID(), _handle);
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(new StringReader(xml));
				Element root = doc.getRootElement(); //获取根元素 
				
				List<?> events = root.getChildren("event");
				
				for(Object o : events) {
								
					Element event = (Element) o;

					String caseid = event.getChildText("caseid");
					String itemid = event.getChildText("itemid");
					String taskid = event.getChildText("taskid");
					String eventtype = event.getChildText("eventtype");
					
					YSpecificationID specID = this.getYSpecificationIDFromKey(event.getChildText("speckey"));
					SpecificationData specData = this.getSpecData(specID);

					String specname = specData.getName();

					Long timeLong = new Long(event.getChildText("timestamp"));
					SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String time = sdf.format(timeLong);
					
					if( !eventtype.equals("launch_case") && !eventtype.equals("cancel_case") && !eventtype.equals("cancelled_by_case"))
						if( mm.get(caseid) == null || this.compare_date(mm.get(caseid).get("time"), time) == -1) {
							
							if(eventtype.equalsIgnoreCase("complete")){
								Map<String, String> m = new HashMap<String, String>();
								m.put("specname", specname);
								m.put("itemid", itemid);
								m.put("taskid", taskid);
								m.put("type",  eventtype);
								m.put("time", time);
								
								mm.put(caseid, m);
							}
						}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
    	}
    	return mm;
	}
	
	/*public Map<String, Map<String, String>> getCaseLaunchByMe(String userid) {
		Participant p = workqueueServ.getParticipantFromUserid(userid);
		
		System.out.print("p.getID():"+p.getID());
		
		Map<String, Map<String, String>> mm = new HashMap<String, Map<String, String>>();
		
		if (connected()) {
    		try {
    			String xml = rsLogClient.getParticipantHistory(p.getID(), _handle);
    			System.out.print("xml:"+xml);
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(new StringReader(xml));
				Element root = doc.getRootElement(); //获取根元素 
				
				List<?> events = root.getChildren("event");
				System.out.print("events："+events.size());
				for(Object o : events) {
								
					Element event = (Element) o;
					
					String caseid = event.getChildText("caseid");
					String eventtype = event.getChildText("eventtype");
					
					YSpecificationID specID = this.getYSpecificationIDFromKey(event.getChildText("speckey"));
					SpecificationData specData = this.getSpecData(specID);

					String specname = specData.getName();

					Long timeLong = new Long(event.getChildText("timestamp"));
					SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String time = sdf.format(timeLong);
					
					if( eventtype.equals("launch_case") || eventtype.equals("cancel_case")) {
						Map<String, String> m = new HashMap<String, String>();

						m.put("specname", specname);
						m.put("type",  eventtype);
						m.put("time", time);
						
						mm.put(caseid, m);
					}
				}
				
    		} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
		}
		return mm;
	}*/
	
public String getCaseLaunchByMe(String userid) {
		Participant p = workqueueServ.getParticipantFromUserid(userid);
		System.out.print("p.getID():"+p.getID());
		JSONArray mm = new JSONArray();
		if (connected()) {
    		try {
    			String xml = rsLogClient.getParticipantHistory(p.getID(), _handle);
    			System.out.print("xml:"+xml);
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(new StringReader(xml));
				Element root = doc.getRootElement(); //获取根元素 
				
				List<?> events = root.getChildren("event");
				System.out.print("events："+events.size());
				for(Object o : events) {
								
					Element event = (Element) o;
					String caseid = event.getChildText("caseid");
					String eventtype = event.getChildText("eventtype");
					YSpecificationID specID = this.getYSpecificationIDFromKey(event.getChildText("speckey"));
					SpecificationData specData = this.getSpecData(specID);
					String specname = specData.getName();
					Long timeLong = new Long(event.getChildText("timestamp"));
					SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String time = sdf.format(timeLong);
					if( eventtype.equals("launch_case") || eventtype.equals("cancel_case") || eventtype.equals("cancelled_by_case")) {
						Map<String, String> m = new HashMap<String, String>();
						m.put("specname", specname);
						m.put("type",  eventtype);
						m.put("time", time);
						m.put("caseid", caseid);
						mm.put(m);
					}
				}
    		} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
		}
		return mm.toString();
	}
	

	public List<Map<String, String>> getCasehistory(String caseid) {
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    	if (connected()) {
    		try {
    			String xml = rsLogClient.getCaseEvents(caseid, _handle);
    			
    			//System.out.println(caseid+": "+xml);
				
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(new StringReader(xml));
				Element root = doc.getRootElement(); //获取根元素 
				
				List<?> events = root.getChildren("event");
				
				for(Object o : events) {
								
					Element event = (Element) o;

					String itemid = event.getChildText("itemid");
					String taskid = event.getChildText("taskid");					
					String eventtype = event.getChildText("eventtype");

					Long timeLong = new Long(event.getChildText("timestamp"));
					SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String time = sdf.format(timeLong);
							
					Map<String, String> m = new HashMap<String, String>();
					
					m.put("itemid", itemid);
					m.put("taskid", taskid);
					m.put("type",  eventtype);
					m.put("time", time);

					String pid = event.getChildText("resourceid");
					if(pid.equals("system") || pid.equals("admin")){
						m.put("resource", pid);
					}
					else if(!pid.equals("")){
						String resource = workqueueServ.getParticipant(pid).getName();
						m.put("resource", resource);
					}	
					
					list.add(m);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
    	}		
		
		return list;
	}
	
	public YSpecificationID getYSpecificationIDFromKey(String speckey) {
		YSpecificationID yid = null;
		if (connected()) {
			try {
				String xmlYSpecificationID = rsLogClient.getSpecificationIdentifiers(Long.parseLong(speckey), _handle);
				
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(new StringReader(xmlYSpecificationID));
				Element root = doc.getRootElement(); //获取根元素<specificationid>
				
				String identifier = root.getChildText("identifier");
				YSpecVersion version = new YSpecVersion(root.getChildText("version"));
				String uri = root.getChildText("uri");
				
				yid = new YSpecificationID(identifier, version, uri);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
		}
		
		return yid;
	}
	
	public int compare_date(String DATE1, String DATE2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = sdf.parse(DATE1);
			Date dt2 = sdf.parse(DATE2);
			
			if (dt1.getTime() > dt2.getTime()) { return 1; }  //dt1 在dt2前
			else if (dt1.getTime() < dt2.getTime()) { return -1; }	//dt1在dt2后
			else { return 0; }
		
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		
		return 0;
	}
	

	/**
	 * 获取一个SpecificationData实例
	 * @param specID 要获取的SpecificationData实例对应的YSpecificationID
	 * @return 请求的SpecificationData实例
	 * 
	 * specService中也有同样的方法
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
	
}