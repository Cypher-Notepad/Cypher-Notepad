import crypto.CryptoFacade;
import file.FileManager;
import thread.ThreadManager;
import thread.TrInitializeUI;
import ui.UIManager;

public class Main {

	/*
	private static Thread threadInit = new Thread() {

	};
	*/
	
	public static void main(String[] args) {
		FileManager.getInstance().loadProperties();
		Thread threadInit = new TrInitializeUI();
		threadInit.start();
		ThreadManager.getInstance().setInitThead(threadInit);
		UIManager.getInstance().setDefaultUI();
	}
}
