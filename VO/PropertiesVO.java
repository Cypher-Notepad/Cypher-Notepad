package VO;

import lombok.Data;

@Data
public class PropertiesVO {
	
	private static PropertiesVO instance;
	private String language;
	private boolean isEncryted;
	private int nOfRcntFile;
	private String[] rcntFileame;
	private String[] rcntFilePath;
	private String[] rcntFileDate;
	
	private PropertiesVO() {
		
		
	}
	
	public static PropertiesVO getInstance() {
		if(instance == null) {
			instance = new PropertiesVO();
		}
		
		return instance;
	}
	
	/*
	private setDefaultProperties() {
		
	}
	*/
	
}
