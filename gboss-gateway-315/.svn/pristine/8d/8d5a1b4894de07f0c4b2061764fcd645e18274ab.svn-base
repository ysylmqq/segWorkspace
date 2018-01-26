package cc.chinagps.gateway.unit.kelong.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KeLongVehicleManager {
	private static final String XMLPATH = "config/kelong.xml";
	public static Map<Integer, Integer> brandMap = new HashMap<Integer, Integer>();
	public static Map<Integer, Integer> powerTypeMap = new HashMap<Integer, Integer>();
	private static long lastModified = 0L;
	private static File xmlFile = null;

	public static Integer getBrandCode(Integer segId) {
		return brandMap.get(segId);
	}
	
	public static Integer getPowerType(Integer segId) {
		return powerTypeMap.get(segId);
	}

	public static void start() {
		parseXml();
		(new ReloadThread()).start();
	}

	private synchronized static void parseXml() {
		try {
			File f = new File(XMLPATH);
			lastModified = f.lastModified();
			xmlFile = f;
			brandMap.clear();
			powerTypeMap.clear();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList brands = doc.getElementsByTagName("brand");
			for (int i = 0; i < brands.getLength(); i++) {
				// brand下的子节点
				NodeList childNodes = brands.item(i).getChildNodes();
				Integer segId = null;
				Integer code = null;
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node node = childNodes.item(j);
					String name = node.getNodeName();
					String text = node.getTextContent();
					if ("segBrandId".equals(name)) {
						segId = Integer.valueOf(node.getTextContent());
					} else if ("code".equals(name))
						code = Integer.valueOf(text.substring(2), 16);
				}
				brandMap.put(segId, code);
				
			}
			NodeList powerTypes = doc.getElementsByTagName("powerType");
			for (int i = 0; i < powerTypes.getLength(); i++) {
				// powerType下的子节点
				NodeList childNodes = powerTypes.item(i).getChildNodes();
				Integer segId = null;
				Integer value = null;
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node node = childNodes.item(j);
					String name = node.getNodeName();
					String text = node.getTextContent();
					if ("segPowerTypeId".equals(name)) {
						segId = Integer.valueOf(node.getTextContent());
					} else if ("value".equals(name))
						value = Integer.valueOf(text.substring(2), 16);
				}
				powerTypeMap.put(segId, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class ReloadThread extends Thread {
		public void run() {
			while (true) {
				if (xmlFile != null) {
					long m = xmlFile.lastModified();
					if (m != lastModified) {
						lastModified = m;
						parseXml();
					}
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else
					break;
			}
		}
	}
	public static void main(String[] args) {
		start();
	}
}
