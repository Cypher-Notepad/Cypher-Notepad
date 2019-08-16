package Config;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import File.FileManager;
import UI.Custom.KFontChooser;

public class Property {
	
	public static String version = "VERSION";
	public static String language = "LANGUAGE";
	public static String isEncrypted = "ENCRYPTED";
	public static String nOfRcntFiles = "NUMBER_OF_RECENT_FILES";
	public static String rcntFile = "Recent File";
	public static String fontFamily = "FONT_FAMILY";
	public static String fontStyle = "FONT_STYLE";
	public static String fontSize = "FONT_SIZE";
	public static String fontColor = "FONT_COLOR";
			
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
		prop.setProperty(isEncrypted, "TRUE");
		prop.setProperty(nOfRcntFiles, "5");
		prop.setProperty(fontFamily, "Consolas");
		prop.setProperty(fontStyle, String.valueOf(Font.PLAIN));
		prop.setProperty(fontSize, String.valueOf(11));
		prop.setProperty(fontColor, "0");
	}
	
	public static void initialize() {
		Properties prop = Property.getProperties();
		prop.setProperty(version, "1.0");
		prop.setProperty(language, "KOREAN");
		prop.setProperty(isEncrypted, "TRUE");
		prop.setProperty(nOfRcntFiles, "5");
		prop.setProperty(fontFamily, "Consolas");
		prop.setProperty(fontStyle, String.valueOf(Font.PLAIN));
		prop.setProperty(fontSize, String.valueOf(11));
		prop.setProperty(fontColor, "0");
	}
	
	public static void setFont(Font font, Color color) {
		Properties prop = Property.getProperties();
		prop.setProperty(fontFamily, font.getFamily());
		prop.setProperty(fontStyle, String.valueOf(font.getStyle()));
		prop.setProperty(fontSize, String.valueOf(font.getSize() - KFontChooser.FONT_SIZE_CORRECTION));
		prop.setProperty(fontColor, String.valueOf(color.getRGB()));
	}
	
	public static void addRecentFiles(String newFilePath) {
		Properties prop = Property.getProperties();
		int i;
		
		i = 0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			String path = prop.getProperty(rcntFile + i, null);
			if(newFilePath.equals(path)) break;
			i++;
		}
		removeRecentFile(i);
		
		i = Integer.parseInt(prop.getProperty(nOfRcntFiles))-2;
		while(i >= 0) {
			String moveDown = prop.getProperty(rcntFile + i, null);
			if(moveDown != null) {
				prop.setProperty(rcntFile + (i+1), moveDown);
			}
			i--;
		}
		prop.setProperty(rcntFile + "0", newFilePath);
		
		//debug
		i=0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			String path = prop.getProperty(rcntFile + i, null);
			System.out.println("rcnt File  : " + path);
			i++;
		}

		FileManager.getInstance().saveProperties();
	}
	
	public static void removeAllRecentFiles() {
		Properties prop = Property.getProperties();
		
		int i = 0;
		while(i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			prop.remove(rcntFile + i);
			i++;
		}

		FileManager.getInstance().saveProperties();
	}
	
	public static void removeRecentFile(int idx) {
		Properties prop = Property.getProperties();
		
		int max = Integer.parseInt(prop.getProperty(nOfRcntFiles))-1;
		if(idx < 0 || idx > max) return;
		prop.remove(rcntFile + idx);
		while(idx < max) {
			String moveUp = prop.getProperty(rcntFile + (idx+1), null);
			if(moveUp != null) {
				prop.setProperty(rcntFile + idx, moveUp);
			} 
			else {
				prop.remove(rcntFile + idx);
				break;
			}
			idx++;
		}

		FileManager.getInstance().saveProperties();
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
