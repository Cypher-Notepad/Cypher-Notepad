import crypto.CryptoFacade;
import file.FileManager;
import ui.UIManager;

public class Main {

	private static Thread threadInit = new Thread() {
		
		public void run() {
			initialize();
		}
		
		private void initialize() {
			//to reduce the first file saving time.
			FileManager.getInstance().loadKeys();
			new CryptoFacade();
		}
	};
	
	public static void main(String[] args) {
		FileManager.getInstance().loadProperties();
		UIManager.getInstance().setDefaultUI();
		threadInit.start();
	}
}
