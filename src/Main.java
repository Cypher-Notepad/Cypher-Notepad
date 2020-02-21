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
		long start = System.currentTimeMillis();
		
		FileManager.getInstance().loadProperties();
		/*
		Thread threadInit = new TrInitializeUI();
		threadInit.start();
		ThreadManager.getInstance().setInitThead(threadInit);
		*/
		UIManager.getInstance().setDefaultUI();
		
		long end = System.currentTimeMillis(); 
		System.out.println( "werwerwertotal time : " + ( end - start )/1000.0 +"sec");
	}
}
