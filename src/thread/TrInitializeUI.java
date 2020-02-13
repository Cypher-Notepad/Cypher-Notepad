package thread;

import crypto.CryptoFacade;
import file.FileManager;
import ui.MainUI;
import ui.NotepadUI;

public class TrInitializeUI extends Thread {

	Thread prepareNotepad = new Thread() {
		@Override
		public void run() {
			System.out.println("[MainUI]Preload NotepadUI for reducing the time for loading");
			MainUI.notepadUI = new NotepadUI();
			MainUI.notepadUI.initializeUI();
		}
	};
	
	@Override
	public void run() {
		// to reduce the first file saving time.
		prepareNotepad.start();
		ThreadManager.getInstance().addThread(prepareNotepad);

		FileManager.getInstance().loadKeys();
		new CryptoFacade();
	}

}
