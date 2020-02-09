package thread;

import file.FileManager;
import ui.NotepadUI;

public class TrKsetInvalidateFile extends Thread{
	
	@Override
	public void run() {
		FileManager.getInstance().invalidateKeys();
	}
}
