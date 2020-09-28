import crypto.AESImpl;
import file.FileManager;
import ui.UIManager;

/**
 * @author LeeDongGeon1996
 * @release 2020.09.28 Cypher Notepad 3.0
 * 
 */

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
		System.out.println( "[Main] Loading MainUI time : " + ( end - start )/1000.0 +"sec");
		threadInit.start();
		
	}
}
