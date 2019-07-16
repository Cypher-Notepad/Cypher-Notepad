package Thread;

import Config.Property;

public class TrKsetLanguage extends Thread{

	@Override
	public void run() {
		String current = Property.getProperties().getProperty(Property.language);
		if (current.equals("KOREAN")) {
			Property.getProperties().setProperty(Property.language, "ENGLISH");
		} else {
			Property.getProperties().setProperty(Property.language, "KOREAN");
		}
	}
}
