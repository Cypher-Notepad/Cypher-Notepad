package thread;

import config.Property;
import file.FileManager;

public class TrKsetLanguage extends Thread{

	@Override
	public void run() {
		String current = Property.getProperties().getProperty(Property.language);
		if (current.equals("KOREAN")) {
			Property.getProperties().setProperty(Property.language, "ENGLISH");
		} else {
			Property.getProperties().setProperty(Property.language, "KOREAN");
		}
		
		FileManager.getInstance().saveProperties();
	}
}
