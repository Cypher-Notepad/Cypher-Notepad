import crypto.AESImpl;
import file.FileManager;
import ui.UIManager;

public class Main {

	private static Thread threadInit = new Thread() {
		public void run() {
			new AESImpl();
			
		}
	};
	
	public static void main(String[] args) throws OutOfMemoryError{
		long start = System.currentTimeMillis();
		
		FileManager.getInstance().loadProperties();
		UIManager.getInstance().setDefaultUI();
		
		long end = System.currentTimeMillis(); 
		System.out.println( "[Main]Loading MainUI time : " + ( end - start )/1000.0 +"sec");
		threadInit.start();
		
	}
}
