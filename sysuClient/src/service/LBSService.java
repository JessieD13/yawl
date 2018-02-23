package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class LBSService{
	
	public String getResult(double origin_latitude, double origin_longitude, String origin_region, 
			double destination_latitude, double destination_longitude, String destination_region) {
		
		StringBuffer document = new StringBuffer();
		
		try {
			StringBuffer str = new StringBuffer();
			str.append("http://api.map.baidu.com/direction/v1?mode=driving");
			str.append("&origin="+origin_latitude+","+origin_longitude);
			str.append("&destination="+destination_latitude+","+destination_longitude);
			str.append("&origin_region="+origin_region);
			str.append("&destination_region="+destination_region);
			str.append("&output=xml");
			str.append("&ak=B358350d9d657429d0cad77a78038c10");
			
			URL U = new URL(str.toString());
			URLConnection connection = U.openConnection();
			connection.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")); //demo中不加utf-8编制可以运行，但是web工程中没有家则会出现乱码，导致xml无法解析

			String line;
			while ((line = in.readLine())!= null){
				document.append(line);
			}
			
			in.close();			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println(document.toString());
		
		return document.toString();
	}
	
	public double getDuration(String xml) {
		Double duration = null;
		
		SAXBuilder sb = new SAXBuilder();

		try {
			Document doc = sb.build(new StringReader(xml));
			Element root = doc.getRootElement(); //获取根元素 
			Element result = root.getChild("result");
			
			duration = Double.parseDouble(result.getChild("routes").getChildText("duration"));

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return duration;
	
	}
	
	public static void main(String[] args) {
		LBSService l = new LBSService();
		
		String str = l.getResult(40.056878, 116.30815, "北京", 39.915285, 116.403857, "北京");
		//http://api.map.baidu.com/direction/v1?mode=driving&origin=40.056878,116.30815&destination=39.915285,116.403857&origin_region=北京&destination_region=北京&output=json&ak=B358350d9d657429d0cad77a78038c10
		
		System.out.println(l.getDuration(str) );
	}
	
}