package Config;

import java.util.ArrayList;
import java.util.Properties;

import VO.PropertiesVO;

public class Property {
	
	public static String language = "LANGUAGE";
	public static String isEncrypted = "ENCRYPTED";
	public static String nOfRcntFiles = "NUMBER OF RECENT FILES";
	public static String rcntFile = "Recent File";
	private static Property property = null;
	private Properties prop;
	
	private Property() {
		prop = new Properties();
	}
	
	public static Properties getProperties() {
		if(property == null) {
			property = new Property();
		}
		
		return property.prop;
	} 
	
	
	public static void setDefaultProperties() {
		Properties prop = Property.getProperties();
		prop.setProperty(language, "KOREAN");
		prop.setProperty(isEncrypted, "TRUE");
		prop.setProperty(nOfRcntFiles, "5");
	}
	
	public static void addRecentFiles(String newFilePath) {
		Properties prop = Property.getProperties();
		
		int i = 0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))-1) {
			String moveDown = prop.getProperty(rcntFile + i, null);
			if(moveDown != null) {
				prop.setProperty(rcntFile + (i+1), moveDown);
			}
			i++;
		}
		prop.setProperty(rcntFile + "0", newFilePath);
	}
	
	public static void removeRecentFiles() {
		Properties prop = Property.getProperties();
		
		int i = 0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			prop.remove(rcntFile + i);
			i++;
		}
	}
	
	public static ArrayList<String> getRecentFiles() {
		ArrayList<String> rcntFiles = new ArrayList<String>();
		Properties prop = Property.getProperties();
		
		int i = 0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))){
			rcntFiles.add(prop.getProperty(rcntFile + i));
			i++;
		}
		
		return rcntFiles;
	}
}
