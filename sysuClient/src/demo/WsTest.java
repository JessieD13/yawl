package demo;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;
import org.apache.axis.encoding.XMLType;
import javax.xml.rpc.ParameterMode;

public class WsTest {
	
   public String invokeRemoteFuc(){
      String endpoint = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
      String result = null;
      
      try {
    	  Service service = new Service();
          Call call = (Call)service.createCall();
          call.setTargetEndpointAddress(endpoint);//远程调用路径 
          call.setOperationName(new QName("http://WebXml.com.cn/", "getMobileCodeInfo") );//调用的方法名
          
          //设置参数名:       
          call.addParameter(new QName("http://WebXml.com.cn/", "mobileCode"), //参数名
        		  XMLType.XSD_STRING,//参数类型:String
        		  ParameterMode.IN);//参数模式：'IN' or 'OUT'
          
          call.addParameter(new QName("http://WebXml.com.cn/", "userID"), //参数名
        		  XMLType.XSD_STRING,//参数类型:String
        		  ParameterMode.IN);//参数模式：'IN' or 'OUT'
          
          //设置返回值类型：
          call.setReturnType(XMLType.XSD_STRING);//返回值类型：UnsignedByte
          
          call.setSOAPActionURI("http://WebXml.com.cn/getMobileCodeInfo"); //不加上会报soapAction的错
          
          result = (String) call.invoke(new Object[]{"15018409629", ""});//远程调用
          
      } catch (ServiceException e) {
    	  e.printStackTrace();
    	  
      } catch (RemoteException e) {
    	  e.printStackTrace();

	}
      
      return result;
      
   }
   
   //测试：
   
   public static void main(String[] args){
	   
	   WsTest test = new WsTest();
	   String result = test.invokeRemoteFuc();
	   
	   System.out.println(result);
	   
   }
   
}
