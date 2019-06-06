package Config;

import java.util.Properties;

import VO.PropertiesVO;

public class Property {
	
	public static String language = "LANGUAGE";
	public static String isEncrypted = "ENCRYPTED";
	public static String nOfRcntFiles = "NUMBER OF RECENT FILES";
	public static String rcntFile = "Recent File";
	
	public static void setDefaultProperties(Properties prop) {
		prop.setProperty(language, "KOREAN");
		prop.setProperty(isEncrypted, "TRUE");
		prop.setProperty(nOfRcntFiles, "5");
	}
	
	public static void addRecentFiles(Properties prop, String newFilePath) {
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
	
	public static void removeRecentFiles(Properties prop) {
		int i = 0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			prop.remove(rcntFile + i);
			i++;
		}
	}
}
