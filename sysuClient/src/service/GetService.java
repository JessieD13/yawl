package service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;



import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import util.specEntity;

import dao.Application;
import dao.Parameter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//从服务端获取服务
/**
 *从服务中心获取用户对应的服务
 * @author liaoyu
 *
 */
public class GetService {
		 private static  String result;
		 //调用的服务端的地址
	     private  String endpoint = "http://127.0.0.1:8080/SSH_Prototype_J2EE_5.0/GetServiceInfoPort?wsdl";
	     
	     public  String getAvailableServiceList(int userid ) {//my service list
		  // TODO Auto-generated method stub
		  
		  //String endpoint = "http://localhost:8080/ServiceInf/DemoPort?wsdl";
		 	  
        //直接引用远程的wsdl文件
       //以下都是套路 
		  try
		  {
	          Service service = new Service();
	          Call call = (Call) service.createCall();
	          call.setTargetEndpointAddress(endpoint);
	          call.setOperationName(new QName("http://server.com/", "getMyService")); //WSDL里面描述的接口名称
	          call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING,
	                        javax.xml.rpc.ParameterMode.IN);//接口的参数
	          call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
	          //String result2 = (String)call.invoke(new Object[]{temp, temp});
	          //List<Map<String,Object>> li=new ArrayList<Map<String,Object>>();
	        //  JSONArray js=new JSONArray();
	        
	          //result= (String)call.invoke(new Object[]{userid});
	         result = (String)call.invoke(new Object[]{userid});
	          //给方法传递参数，并且调用方法
	         // System.out.println("result is "+result2);
	       
	          System.out.println(result);
	          return result;
	         
		  }
		  catch (Exception e) 
		  {
			  //WebServiceService server = new WebServiceService();
			  //WebServiceDelegate dd = server.getWebServicePort();
			  
			  //String result = dd.sayHello("hewei");
			  
			  //System.out.println(result);
            System.err.println(e.toString());
            return null;
		  }
		 }
	
	public  Map<String,String>  getCallService(int appId, String userid){
		  JSONArray json = JSONArray.fromObject(result ); // 首先把字符串转成 JSONArray  对象
		  System.out.println(json.toString()+"="+result+"\n") ;
		  Map<String ,String> mp=new HashMap<String,String>();
	         if(json.size()>0){
	           for(int i=0;i<json.size();i++){// 遍历 jsonarray 数组，把每一个对象转成 json 对象
	             JSONObject job = json.getJSONObject(i); 
	             if(job.getInt("appid")==appId){
	            	// mp.put("appid", String.valueOf(appId));
	            	 mp.put("userid", userid);
	            	 mp.put("type", job.getString("appType"));
	            	 mp.put("name", job.getString("appName"));
	            	 mp.put("desc", job.getString("appDesc"));
	            	 mp.put("url", job.getString("appURL"));
	            	 mp.put("query", job.getString("query"));
	            	 if(job.containsKey("params")){
	            		 mp.put("params",job.getString("params"));
	            	 }
	            	 if(job.containsKey("vars")){
	            		 mp.put("vars",job.getString("vars"));
	            	 }
	            	 if(job.containsKey("subparams")){
	            		 mp.put("subparams",job.getString("subparams"));
	            	 }
	            	// mp.put("params",job.getString("params")); 	 
	             }
	            // System.out.println(job.get("appName")+"=") ;  // 得到 每个对象中的属性值
	           }
	         }
	    
	         return mp;
		
	}
	
	public  String getAllService(int userId){//all service means that exclude the already possess
		  
	        //直接引用远程的wsdl文件
	       //以下都是套路 
			  try
			  {
		          Service service = new Service();
		          Call call = (Call) service.createCall();
		          call.setTargetEndpointAddress(endpoint);
		          call.setOperationName(new QName("http://server.com/", "getAllService")); //WSDL里面描述的接口名称
		          call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING,
		                        javax.xml.rpc.ParameterMode.IN);//接口的参数
		          call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
		         
		          return (String)call.invoke(new Object[]{userId});
		          //给方法传递参数，并且调用方法
		         
			  }
			  catch (Exception e) 
			  {
	            System.err.println(e.toString());
	            return null;
			  }
	}
	
	public  String getMySpec(String userId){//all service means that exclude the already possess
		  
        //直接引用远程的wsdl文件
       //以下都是套路 
		  try
		  {
	          Service service = new Service();
	          Call call = (Call) service.createCall();
	          call.setTargetEndpointAddress(endpoint);
	          call.setOperationName(new QName("http://server.com/", "getMySpec")); //WSDL里面描述的接口名称
	          call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING,
	                        javax.xml.rpc.ParameterMode.IN);//接口的参数
	          call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
	          System.out.print("getMySpec:"+result);
	          return (String)call.invoke(new Object[]{userId});
	          //给方法传递参数，并且调用方法
	         
		  }
		  catch (Exception e) 
		  {
            System.err.println(e.toString());
            return null;
		  }
	}
	
	public void deleteService(int ServiceId,int userId) throws Exception {
			try
			{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","delete"));
				call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_INT, 
						javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg1",org.apache.axis.encoding.XMLType.XSD_INT,
						javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
				result = (String) call.invoke(new Object[]{ServiceId,userId});
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}
	
	public void applyService(int ServiceId,int userId) throws Exception{
		try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","add"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_INT, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg1", org.apache.axis.encoding.XMLType.XSD_INT, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
				result = (String) call.invoke(new Object[]{ServiceId,userId});
		}catch(Exception e){
			throw e;
		}
	}
	
	public String test(){
		 try
		  {
			   int id=12;
	          Service service = new Service();
	          Call call = (Call) service.createCall();
	          call.setTargetEndpointAddress("http://127.0.0.1:8020/demo/EmployeeServerInterfacePort?wsdl");
	          call.setOperationName(new QName("http://server.com/", "getEmpfromrole")); //WSDL里面描述的接口名称
	          call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING,
	                        javax.xml.rpc.ParameterMode.IN);//接口的参数
	          call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
	         result = (String)call.invoke(new Object[]{id});
	          //给方法传递参数，并且调用方法
	         // System.out.println("result is "+result2);
	       
	       //   System.out.println(result);
	          return result;
	         
		  }
		  catch (Exception e) 
		  {
			  //WebServiceService server = new WebServiceService();
			  //WebServiceDelegate dd = server.getWebServicePort();
			  
			  //String result = dd.sayHello("hewei");
			  
			  //System.out.println(result);
          System.err.println(e.toString());
          return null;
		  }
	}
	
	public void registerService(int userId, String servicename, String serviceaddress, String servicdesc) throws Exception{
		try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","registerService"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_INT, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
				result = (String) call.invoke(new Object[]{userId, servicename, serviceaddress, servicdesc});
				System.out.print("registerService:"+result);
		}catch(Exception e){
			throw e;
		}
	}
	
	public void removeService(int ServiceId) throws Exception {
		try
		{
			Service service=new Service();
			Call call=(Call)service.createCall();
			call.setTargetEndpointAddress(endpoint);
			call.setOperationName(new QName("http://server.com/","removeService"));
			call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_INT, 
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
			result = (String) call.invoke(new Object[]{ServiceId});
			System.out.print("removeService:"+result);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public String loginVerify(String userid, String password) throws Exception{
		//调用的服务端的地址
	    String endpoint2 = "http://127.0.0.1:8020/demo/EmployeeServerInterfacePort?wsdl";
	     
		try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint2);
				call.setOperationName(new QName("http://server.com/","loginVerify"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				//call.invoke(new Object[]{userid, password});
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
		        result = (String)call.invoke(new Object[]{userid, password});
		        System.out.print("loginVerify:"+result);
		        return result;
		}catch(Exception e){
			throw e;
		}
	}
	
	
	public List<Application> parseAppsJson(String appsJson){
		List<Application> apps = new ArrayList<Application>();
		apps.clear();
		 JSONArray json = JSONArray.fromObject(appsJson ); // 首先把字符串转成 JSONArray  对象
		  System.out.println(json.toString()+"="+appsJson+"\n");
	         if(json.size()>0){
	           for(int i=0;i<json.size();i++){// 遍历 jsonarray 数组，把每一个对象转成 json 对象
	             JSONObject job = json.getJSONObject(i);
	             Application app = new Application();
	             app.setId(Integer.parseInt(job.getString("appid")));
	             app.setAppName(job.getString("appName"));
	             app.setAppType(job.getString("appType"));
	             app.setAppUrl(job.getString("appURL"));
	             app.setAppDesc(job.getString("appDesc"));
	             if(job.containsKey("params")){
	            	 Set paramSet = new HashSet();
	            	 String paramString = job.getString("params");
	            	 JSONObject paramJson = JSONObject.fromObject(paramString);
	            	 if(paramJson.isEmpty() == false){
	            		 //for(int j = 0; j < paramJson.size(); j++){
	            		//	 JSONObject o = paramJson.getJSONObject(j);
	            			 Iterator it = paramJson.keys();  
	            			 while(it.hasNext()){
	            				 String paramName = it.next().toString(); 
	            				 String paramType = paramJson.getString(paramName);
	            				 Parameter p = new Parameter();
		            			 p.setApplication(app);
		            			 p.setName(paramName);
		            			 p.setType(paramType);
		            			 paramSet.add(p);
	            			 }
	            			
	            		 //}
	            	 }
	            	 app.setParameters(paramSet);
	             }
	             apps.add(app);
	           }
	         }
	     return apps;
	}
	
	public List<specEntity> parseSpecJson(String appsJson){
		List<specEntity> specs = new ArrayList<specEntity>();
		specs.clear();
		 JSONArray json = JSONArray.fromObject(appsJson ); // 首先把字符串转成 JSONArray  对象
		  System.out.println(json.toString()+"="+appsJson+"\n");
	         if(json.size()>0){
	           for(int i=0;i<json.size();i++){// 遍历 jsonarray 数组，把每一个对象转成 json 对象
	             JSONObject job = json.getJSONObject(i);
	             specEntity spec = new specEntity();
	             spec.setAppid(job.getString("appid"));
	             spec.setDocumentation(job.getString("documentation"));
	             spec.setIdentifier(job.getString("identifier"));
	             spec.setName(job.getString("name"));
	             spec.setUri(job.getString("uri"));
	             spec.setVersion(job.getString("version"));
	             specs.add(spec);
	           }
	         }
	     return specs;
	}
	
	public boolean registerSpecToSSH(String userid, String password,String servicename, String xml){
	    try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","registerSpec"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
		        result = (String)call.invoke(new Object[]{userid, password, servicename, xml});
		        
		        JSONArray json = JSONArray.fromObject(result); // 首先把字符串转成 JSONArray  对象
				System.out.println(json.toString()+"="+result+"\n");
			    if(json.size()>0){
			    	JSONObject job = json.getJSONObject(0);
			    	if(job.getString("registerSpec").equals("success")){
			          	return true;
			    	}
			    }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean removeSpecFromSSH(String specid){
	    try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","removeSpec"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
		        result = (String)call.invoke(new Object[]{specid});
		        
		        JSONArray json = JSONArray.fromObject(result); // 首先把字符串转成 JSONArray  对象
				System.out.println(json.toString()+"="+result+"\n");
			    if(json.size()>0){
			    	JSONObject job = json.getJSONObject(0);
			    	if(job.getString("removeSpec").equals("success")){
			          	return true;
			    	}
			    }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public String getAllSpec(String userid){
	    try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","getAllSpec"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
		        result = (String)call.invoke(new Object[]{userid});
		        System.out.print("getAllSpec:"+result);
		        return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String loadSpecFromSSH(){
	    try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","loadAllSpec"));
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
		        result = (String)call.invoke(new Object[]{});
		        System.out.print("loadSpecFromSSH:"+result);
		        return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 记录流程发起日志到服务管理中心
	 * @throws Exception
	 */
	public void launchSpec(String userid, String identifier, String version, String uri, String desc) throws Exception{
		try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","launchSpec"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg2",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg4", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
				result = (String) call.invoke(new Object[]{userid, identifier, version, uri, desc});
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 记录流程cancel日志到服务管理中心
	 * @throws Exception
	 */
	public void cancelSpec(String userid, String identifier, String version, String uri, String desc) throws Exception{
		try{
				Service service=new Service();
				Call call=(Call)service.createCall();
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName(new QName("http://server.com/","cancelSpec"));
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg2",org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("arg4", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
				result = (String) call.invoke(new Object[]{userid, identifier, version, uri, desc});
				System.out.print(result);
		}catch(Exception e){
			throw e;
		}
	}
}
