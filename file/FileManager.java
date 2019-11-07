package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.Property;
import crypto.CryptoFacade;
import crypto.RSAImpl;
import ui.NotepadUI;
import vo.MemoVO;

public class FileManager {

	private static final String FILE_NAME_PROP = "crypto-notepad.properties";
	private static final String FILE_NAME_KEYS = "crypto-notepad.keys";
	private static final String EXT_MEMO = ".txt";
	private static final int NUM_HEADER_LINE = 9;
	private static final String HEADER_WARNING = "####################################################\r\n"
			+ "## 		      *Warning*			  ##\r\n" + "## This file has been encrypted. By using Windows ##\r\n"
			+ "## Notepad, you can not access this file anymore. ##\r\n"
			+ "## Please open it with Crypto-Notepad. Do not	  ##\r\n"
			+ "## modify anything in this file including this    ##\r\n"
			+ "## message or you will never recover its original ##\r\n"
			+ "## content.		_LEEDONGGEON1996_	  ##\r\n"
			+ "####################################################\r\n";
	private static FileManager instance = null;
	private ArrayList<String> keys;
	// private Properties property;

	private FileManager() {
		// keys = new ArrayList<String>();
		// property = new Properties();
		// property = Property.getProperties();
		// loadKeys();
	}

	public static FileManager getInstance() {

		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}

	private String getKey(int idx) {
		return this.keys.get(idx);
	}

	public void loadKeys() {
		try {
			File keyFile = new File(FILE_NAME_KEYS);
			if (!keyFile.exists()) {
				System.out.println("Create crypto-notepad.keys");
				keyFile.createNewFile();
			}

			BufferedReader keyReader = new BufferedReader(new FileReader(FILE_NAME_KEYS));
			keys = new ArrayList<String>();
			String keyLine = null;
			while ((keyLine = keyReader.readLine()) != null) {
				keys.add(keyLine);
			}
			keyReader.close();

			PrintWriter keyWriter = new PrintWriter(new FileWriter(FILE_NAME_KEYS, true));
			keyWriter.println(RSAImpl.getInstance().getPrivateKey());
			keyWriter.close();
			keys.add(RSAImpl.getInstance().getPrivateKey());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void invalidateKeys() {
		File keyFile = new File(FILE_NAME_KEYS);
		if (keyFile.exists()) {
			System.out.println("Re-create crypto-notepad.keys");
			keyFile.delete();
			loadKeys();
		}
	}

	public void loadProperties() {

		try {
			File propFile = new File(FILE_NAME_PROP);
			if (!propFile.exists()) {
				System.out.println("Create crypto-notepad.properties");
				propFile.createNewFile();
				Property.initialize();
				saveProperties();
			}
			// InputStream inStream = getClass().getResourceAsStream(FILE_NAME_PROP);
			InputStream inStream = new FileInputStream(FILE_NAME_PROP);
			Property.load(inStream);
			inStream.close();
			
		} catch (IOException e) {

		}
	}

	public void saveProperties() {
		OutputStream outStream;
		try {
			outStream = new FileOutputStream(FILE_NAME_PROP);
			Property.store(outStream, "Crypto-notepad User Properties");
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveMemo(String filename, MemoVO memo) {
		PrintWriter memoWriter;
		try {
			memoWriter = new PrintWriter(new FileWriter(filename));

			// Encrypt.
			CryptoFacade crypto = new CryptoFacade();
			crypto.encrypt(memo);

			memoWriter.println(HEADER_WARNING);
			memoWriter.println(
					String.valueOf(Base64.getEncoder().encodeToString(String.valueOf(keys.size() - 1).getBytes())));
			memoWriter.println(memo.getKey());
			memoWriter.println(memo.getContent());
			memoWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public MemoVO loadMemo(JFrame frame, String filename) {
		// filename = filename + EXT_MEMO;
		File memo = new File(filename);
		MemoVO readMemo = new MemoVO();
		if (memo.exists()) {
			try {
				BufferedReader memoReader = new BufferedReader(new FileReader(filename));
				if (isEncrypted(memoReader)) {
					memoReader.readLine();
					String strIdx = memoReader.readLine();
					readMemo.setKey(memoReader.readLine());
					readMemo.setContent(memoReader.readLine());
					memoReader.close();
					// Decrypt.
					int idx = Integer.parseInt(new String(Base64.getDecoder().decode(strIdx)));
					new CryptoFacade().decrypt(readMemo, getKey(idx));
				} else {
					memoReader.close();
					memoReader = new BufferedReader(new FileReader(filename));
					String content = "";
					String read = memoReader.readLine();
					while (read != null) {
						content += read + "\r\n";
						read = memoReader.readLine();
					}
					readMemo.setContent(content);
				}
				
			//Return null not to show up notepadUI(= stay MainUI).
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(
						frame, "[ERROR]" + "\nThe file does not exist." + "\nPlease check your file name."
								+ "\n(Error Name : " + e.getClass().getName() + ")",
						"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame,
						"[ERROR]" + "\nUnable to decrypt this file." + "\nThis encrypted file may be modified."
								+ "\n(Error Name : " + e.getClass().getName() + ")",
						"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(frame,
						"[ERROR]" + "\nUnable to decrypt this file." + "\nThis encrypted file may be modified."
								+ "\n(Error Name : " + e.getClass().getName() + ")",
						"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
						"[ERROR]" + "\nUnable to decrypt this file." + "\nThis encrypted file may be modified."
								+ "\n(Error Name : " + e.getClass().getName() + ")",
						"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (IndexOutOfBoundsException | BadPaddingException e) {
				JOptionPane.showMessageDialog(frame,
						"[ERROR]" + "\nUnable to decrypt this file." + "\nThe configuration file may be corrupted."
								+ "\n(Error Name : " + e.getClass().getName() + ")",
						"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame,
						"[ERROR]" + "\nUnable to decrypt this file because of unhandled error."
								+ "\nPlease contact developer via email with error name below."
								+ "\n(E-mail : matth1996@hanmail.net)"
								+ "\n(Error Name : " + e.getClass().getName() + ")",
						"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
				return null;
			}

		} else {
			JOptionPane.showMessageDialog(frame,
					"[ERROR]" + "\nThe file does not exist." + "\nPlease check your file name.", "Crypto Notepad",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return readMemo;
	}

	private Boolean isEncrypted(BufferedReader reader) {
		String read = "";
		try {
			for (int i = 0; i < NUM_HEADER_LINE; i++) {
				String line = reader.readLine();
				if (line == null)
					return false;
				read += line + "\r\n";
			}
			System.out.println(read);
			if (read.equals(HEADER_WARNING))
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Object[][] loadRecentFiles() {
		ArrayList<String> paths = Property.getRecentFilePaths();
		ArrayList<File> files = new ArrayList<File>();
		for (String path : paths) {
			System.out.println(path);
			File f = new File(path);
			if (f.exists()) {
				files.add(f);
			}
		}
		String[][] rcntFiles = new String[files.size()][];
		for (int i = 0; i < files.size(); i++) {
			rcntFiles[i] = new String[4];
			File f = files.get(i);
			rcntFiles[i][0] = f.getName();
			rcntFiles[i][1] = getDate(f.lastModified());
			rcntFiles[i][2] = getFileSize(Integer.parseInt(String.valueOf(f.length())));
			try {
				rcntFiles[i][3] = f.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rcntFiles;
	}

	private String getDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time));
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		int amPm = cal.get(Calendar.AM_PM);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		String ampm = "";
		if (amPm == 0) {
			ampm = "AM";
		} else {
			ampm = "PM";
		}

		// int pos = textArea.getCaretPosition();
		return String.format("%2d:%2d " + ampm + "%2d/%2d/%4d", hour, minute, month, day, year);
	}

	private String getFileSize(int filesize) {
		Integer unit = 1024;
		if (filesize < unit) {
			return String.format("%d Byte", filesize);
		}
		int exp = (int) (Math.log(filesize) / Math.log(unit));

		return String.format("%.0f %s", filesize / Math.pow(unit, exp), "KMGTPE".charAt(exp - 1));
	}

}
