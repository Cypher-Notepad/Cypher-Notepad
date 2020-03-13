package thread;

import crypto.AESImpl;
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
		//new AESImpl();
		
		keyLoadThread.start();
		ThreadManager.getInstance().setKeyLoadingThead(keyLoadThread);

		prepareNotepad.start();
		ThreadManager.getInstance().addThread(prepareNotepad);
		
		ThreadManager.getInstance().joinKeyLoadingThread();
		long start = System.currentTimeMillis();
		new CryptoFacade();
		long end = System.currentTimeMillis(); 
		System.out.println( "crypto time : " + ( end - start )/1000.0 +"sec");

	}

}
