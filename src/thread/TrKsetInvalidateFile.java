package thread;

import file.FileManager;

public class TrKsetInvalidateFile extends Thread{
	
	@Override
	public void run() {
		FileManager.getInstance().invalidateKeys();
	}
}
