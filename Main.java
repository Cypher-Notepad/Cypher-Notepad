import Crypto.CryptoFacade;
import File.FileManager;
import UI.UIManager;

public class Main {

	private static Thread threadInit = new Thread() {
		
		public void run() {
			initialize();
		}
		
		private void initialize() {
			//to reduce the first file saving time.
			FileManager.getInstance();
			new CryptoFacade();
		}
	};
	
	public static void main(String[] args) {
		/*TODO reduce initializing time */
		UIManager.getInstance().setDefaultUI();
		threadInit.start();
	}
}
