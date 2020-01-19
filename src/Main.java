import crypto.CryptoFacade;
import file.FileManager;
import thread.ThreadManager;
import ui.UIManager;

public class Main {

	private static Thread threadInit = new Thread() {
		public void run() {
			initialize();
		}
		
		private void initialize() {
			//to reduce the first file saving time.
		}
	};
	
	public static void main(String[] args) {
		FileManager.getInstance().loadProperties();
		FileManager.getInstance().loadKeys();
		UIManager.getInstance().setDefaultUI();
		new CryptoFacade();
		
	}
}
