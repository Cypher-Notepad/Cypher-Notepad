package thread;

import config.Property;

public class TrKsetInitialize extends Thread{

	@Override
	public void run() {
		Property.setDefaultProperties();
	}
}
