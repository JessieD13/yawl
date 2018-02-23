package service;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import dao.Parameter;

public class WebServService {
	
	ParamService paramServ = new ParamService();
	
	public String invokeRemoteFuc(String endpoint, String operationName, String SOAPActionURI, LinkedHashMap<Integer, String> params){
		
		//String endpoint = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
		
		String result = null;
		
		try {
			Service service = new Service();
			Call call = (Call)service.createCall();
			call.setTargetEndpointAddress(endpoint);//远程调用路径 
			call.setOperationName(new QName("http://WebXml.com.cn/", operationName) );//调用的方法名
			
			//设置输入参数名:
			Iterator<Integer> iter = params.keySet().iterator();
			Object[] query = new Object[]{};
			int count = 0;
			
			while (iter.hasNext()) {
				
				Parameter param = paramServ.getParam(iter.next());
				String paramType = param.getType();
				QName xmlType = null;
				
				if(paramType.equals("string")){
					xmlType = XMLType.XSD_STRING;//参数类型:String
				} else if(paramType.equals("int")) {
					xmlType = XMLType.XSD_INT;
				} else {
					System.out.println("type error");
				}
				
				call.addParameter(new QName("http://WebXml.com.cn/", param.getName()),xmlType,ParameterMode.IN);//参数模式：'IN' or 'OUT'
				query[count++] = params.get(iter);
			}
			
			//设置返回值类型：
			call.setReturnType(XMLType.XSD_STRING);//返回值类型：UnsignedByte
			
			call.setSOAPActionURI(SOAPActionURI); //不加上会报soapAction的错
			
			result = (String) call.invoke(query);//远程调用
          
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
