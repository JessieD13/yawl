package service;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

import dao.Specification;
import dao.SpecificationDAO;

public class SpecXMLService{
	
	SpecificationDAO specDao = new SpecificationDAO();
	LogService logServ = new LogService();
	
	SAXBuilder sb = new SAXBuilder();
	
	public Specification bulidSpec(String xml) {
		
		try {
			//Document doc = sb.build("CreditApplicationProcess.yawl");
			Document doc = sb.build(new StringReader(xml));
			Element root = doc.getRootElement(); //获取根元素 
			Namespace ns = root.getNamespace();
			
			Element spec = root.getChild("specification", ns);
			Element meta = spec.getChild("metaData", ns);
			
			Specification instance= new Specification(
					meta.getChildText("identifier", ns), 
					xml);
			
			return instance;
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Map<String, Map<String, String>> getIOVertex(String identifier, String caseid) {
		
		Map<String, Map<String, String>> ioVertex = new HashMap<String, Map<String, String>>();
		
		String xml = this.getXML(identifier);
		
		List<?> nets = this.getNet(xml);
		
		Element net = (Element)nets.get(0);//目前只获取第一个net
		
		Namespace ns = this.getNS(xml);
		
		List<?> vertex = net.getChildren("vertex", ns);
			
		for(Object o: vertex) {
			Map<String, String> vContent = new HashMap<String, String>();
			
			Element v = (Element)o;
			String id = this.split(v.getAttributeValue("id"));
			
			Element att = v.getChild("attributes", ns);
			Element bounds = att.getChild("bounds", ns);
			
			
			vContent.put("x", bounds.getAttributeValue("x"));
			vContent.put("y", bounds.getAttributeValue("y"));
			
			Map<String, String> m = this.getTaskStatus(caseid).get(id);
			if(m!=null) {
				vContent.put("itemid", m.get("itemid"));
				vContent.put("resourceid", m.get("resourceid"));
				vContent.put("eventtype", m.get("eventtype"));
				vContent.put("timestamp", m.get("timestamp"));	
			}
			
			ioVertex.put(id, vContent);
		}

		return ioVertex;

	}
	
	public Map<String, Map<String, String>> getTaskVertex(String identifier, String caseid) {
		
		Map<String, Map<String, String>> taskVertex = new HashMap<String, Map<String, String>>();
		
		String xml = this.getXML(identifier);
		
		List<?> nets = this.getNet(xml);
		
		Element net = (Element)nets.get(0);//目前只获取第一个net
		
		Namespace ns = this.getNS(xml);
		
		List<?> container = net.getChildren("container", ns);
		
		for(Object o: container) {
			
			Map<String, String> vContent = new HashMap<String, String>();
			
			Element c = (Element)o;
			String id = this.split(c.getAttributeValue("id"));
			
			Element v = c.getChild("vertex", ns);			
			Element att = v.getChild("attributes", ns);
			Element bounds = att.getChild("bounds", ns);
			
			
			vContent.put("x", bounds.getAttributeValue("x"));
			vContent.put("y", bounds.getAttributeValue("y"));
			
			Map<String, String> m = this.getTaskStatus(caseid).get(id);
			if(m!=null) {
				vContent.put("itemid", m.get("itemid"));
				vContent.put("resourceid", m.get("resourceid"));
				vContent.put("eventtype", m.get("eventtype"));
				vContent.put("timestamp", m.get("timestamp"));
			}
			
			taskVertex.put(id, vContent);
		}
		
		return taskVertex;
		
	}
	
	public Map<String, Map<String, String>> getFLowVertex(String identifier) {
		
		Map<String, Map<String, String>> flowVertex = new HashMap<String, Map<String, String>>();
		
		String xml = this.getXML(identifier);
		
		List<?> nets = this.getNet(xml);
		
		Element net = (Element)nets.get(0);//目前只获取第一个net
		
		Namespace ns = this.getNS(xml);
		
		List<?> flow = net.getChildren("flow", ns);
		
		for(Object o: flow) {
			
			Map<String, String> fContent = new HashMap<String, String>();
			
			Element f = (Element)o;
			String id = f.getAttributeValue("source") + "," + f.getAttributeValue("target");
			
			Element att = f.getChild("attributes", ns);
			Element points = att.getChild("points", ns);
			
			List<?> value = points.getChildren("value", ns);
			
			fContent.put("x1", ((Element)value.get(0)).getAttributeValue("x") );
			fContent.put("y1", ((Element)value.get(0)).getAttributeValue("y") );
			fContent.put("x2", ((Element)value.get(1)).getAttributeValue("x") );
			fContent.put("y2", ((Element)value.get(1)).getAttributeValue("y") );
			
			flowVertex.put(id, fContent);
		}
		
		return flowVertex;
	}
	
	public String getXML(String identifier) {
		
		Specification spec = (Specification) specDao.findByIdentifier(identifier).get(0);

		return spec.getSpecXml();
	}
	
	public List<?> getNet(String xml) {
		
		try {
			Document doc = sb.build(new StringReader(xml));
			Element root = doc.getRootElement(); //获取根元素 
			Namespace ns = root.getNamespace();
			
			Element layout = root.getChild("layout", ns);
			Element spec = layout.getChild("specification", ns);
			List<?> net = spec.getChildren("net", ns);
			
			return net;
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Namespace getNS(String xml) {
		
		try {
			Document doc = sb.build(new StringReader(xml));
			Element root = doc.getRootElement(); //获取根元素 
			Namespace ns = root.getNamespace();
			
			return ns;
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Map<String, Map<String, String>> getTaskStatus(String caseid) {
		
		Map<String, Map<String, String>> status = new HashMap<String, Map<String, String>>();
		
		String eventlog = logServ.getCaseEvents(caseid);
		
		try {
			Document doc = sb.build(new StringReader(eventlog));
			Element root = doc.getRootElement(); //获取根元素 

			List<?> event = root.getChildren("event");
			
			for(Object o : event) {
				Element e = (Element)o;
				
				if( e.getChildText("taskid").equals("")) continue;
				
				Map<String, String> task = new HashMap<String, String>();
				
				task.put("itemid", e.getChildText("itemid"));				
				task.put("eventtype", e.getChildText("eventtype"));
				task.put("resourceid", e.getChildText("resourceid"));
				task.put("timestamp", e.getChildText("timestamp"));
				
				status.put(e.getChildText("taskid"), task);
			}	
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
		
	}
	
	public String split(String s) {
		
		String[] ss = s.split("_");
		
		return ss[0];
	}

}