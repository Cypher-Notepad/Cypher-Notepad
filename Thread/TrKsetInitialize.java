package Thread;

import Config.Property;

public class TrKsetInitialize extends Thread{

	@Override
	public void run() {
		Property.setDefaultProperties();
	}
}
