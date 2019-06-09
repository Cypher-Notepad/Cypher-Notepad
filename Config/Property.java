package Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

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
	
	private static class LazyHolder {
	    private static final Property INSTANCE = new Property();  
	}
	
	public static Properties getProperties() {
		return LazyHolder.INSTANCE.prop;
	} 
	
	public static void load(InputStream inStream) {
		try {
			Property.getProperties().load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public static void store(OutputStream out, String comments) {
		try {
			Property.getProperties().store(out, comments);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public static ArrayList<String> getRecentFilePaths() {
		ArrayList<String> rcntFiles = new ArrayList<String>();
		Properties prop = Property.getProperties();
		System.out.println(prop.getProperty(nOfRcntFiles));
		
		int i = 0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))){
			String path = prop.getProperty(rcntFile + i);
			if(path != null) {
				rcntFiles.add(prop.getProperty(rcntFile + i));
			}
			i++;
		}
		
		return rcntFiles;
	}
}
