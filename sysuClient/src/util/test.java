package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.httpclient.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair; 



public class test{
	 public void postForm() {  
	        // 创建默认的httpClient实例.    
	        CloseableHttpClient httpclient = HttpClients.createDefault();  
	        // 创建httppost    
	        HttpPost httppost = new HttpPost("http://localhost:8080/SSH_Prototype_J2EE_5.0/login.action");  
	        // 创建参数队列    
	        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
	        formparams.add(new BasicNameValuePair("userId", "1"));  
	        formparams.add(new BasicNameValuePair("password", "1234"));  
	        UrlEncodedFormEntity uefEntity;  
	        try {  
	            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
	            httppost.setEntity(uefEntity);  
	            System.out.println("executing request " + httppost.getURI());
	            CloseableHttpResponse response = httpclient.execute(httppost);  
	            try {  
	                HttpEntity entity = (HttpEntity) response.getEntity();  
	                if (entity != null) {  
	                    System.out.println("--------------------------------------");  
	                    System.out.println("Response content: " + EntityUtils.toString((org.apache.http.HttpEntity) entity, "UTF-8"));  
	                    System.out.println("--------------------------------------");  
	                }  
	            } finally {  
	                response.close();  
	            }  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (UnsupportedEncodingException e1) {  
	            e1.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            // 关闭连接,释放资源    
	            try {  
	                httpclient.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	       
	    }  
	 
}