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
	
	Thread keyLoadThread = new Thread() {
		@Override
		public void run() {
			FileManager.getInstance().loadKeys();
		}
	};
	
	@Override
	public void run() {
		// to reduce the first file saving time.
		prepareNotepad.start();
		ThreadManager.getInstance().addThread(prepareNotepad);

		keyLoadThread.start();
		ThreadManager.getInstance().setKeyLoadingThead(keyLoadThread);

		new CryptoFacade();

	}

}
