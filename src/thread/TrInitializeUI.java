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
		long start = System.currentTimeMillis();
		
		prepareNotepad.start();
		ThreadManager.getInstance().addThread(prepareNotepad);

		keyLoadThread.start();
		ThreadManager.getInstance().setKeyLoadingThead(keyLoadThread);
		
		new CryptoFacade();
		
		long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
		System.out.println( "init UI 실행 시간 : " + ( end - start )/1000.0 +"초");

	}

}
