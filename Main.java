import Crypto.CryptoFacade;
import File.FileManager;
import UI.UIManager;

public class Main {

	public static void main(String[] args) {
		/*TODO reduce initializing time */
		initialize();
		UIManager.getInstance().setDefaultUI();

	}
	
	private static void initialize() {
		//to reduce the first file saving time.
		FileManager.getInstance();
		new CryptoFacade();
	}
}
