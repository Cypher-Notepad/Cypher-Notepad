package file;

import java.io.BufferedReader;
import java.io.Closeable;
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
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.Language;
import config.Property;
import crypto.CryptoFacade;
import crypto.RSAImpl;
import ui.NotepadUI;
import vo.MemoVO;

public class FileManager {

	public static final String SEPARATOR = File.separator;
	public static final String HOME_DIR = System.getProperty("user.home") + SEPARATOR;
	public static final String DIR_NAME = HOME_DIR + "Crypto-Notepad" + SEPARATOR;
	public static final String FILE_NAME_PROP = "crypto-notepad.properties";
	public static final String FILE_NAME_KEYS = "crypto-notepad.keys";
	private static final int NUM_HEADER_LINE = 9;
	private static final String HEADER_WARNING = "####################################################\r\n" + 
			"##		     *Warning*			  ##\r\n" + 
			"## This file has been encrypted. By using Windows ##\r\n" +
			"## Notepad, you can not access this file anymore. ##\r\n" +
			"## Please open it with valid key. Do not modify	  ##\r\n" +
			"## anything in this file including this message	  ##\r\n" +
			"## or you will never be able to recover its	  ##\r\n" +
			"## original content.       _LEEDONGGEON1996_      ##\r\n" +
			"####################################################\r\n";

	private static FileManager instance = null;
	private ArrayList<String> keys = new ArrayList<String>();
	private Language lang;

	// Index of Keys.
	private int processID = 0;
	private int maxKey = 8192;
	private boolean recycleKey = false;

	private FileManager() {
		// do nothing.
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
		loadKeys(false);
	}

	public void loadKeys(boolean reload) {
		String keyFilePath = DIR_NAME + FILE_NAME_KEYS;
		BufferedReader keyReader = null;
		PrintWriter keyWriter = null;
		String keyLine = null;

		try {
			File keyFile = new File(keyFilePath);
			if (!keyFile.exists()) {
				System.out.println("Create crypto-notepad.keys");
				File parentDir = keyFile.getParentFile();
				if (!parentDir.exists()) {
					parentDir.mkdir();
				}
				keyFile.createNewFile();
			}

			keyReader = new BufferedReader(new FileReader(keyFilePath));
			if (reload) {
				ArrayList<String> reloaded = new ArrayList<String>();
				while ((keyLine = keyReader.readLine()) != null) {
					reloaded.add(keyLine);
				}

				/*
				 * The program uses the key below the maximum index to encrypt. But the number
				 * of key in the key file may be exceed max idx.
				 */
				if (reloaded.size() < maxKey) {
					/*
					 * The case of key file invalidation. In the case of key invalidation, the RSA
					 * must be re-created to make new key-pair.
					 **/
					if (reloaded.size() < keys.size()) {
						addToKeyFile(true, RSAImpl.getInstance(true).getPrivateKey());
						reloaded.add(RSAImpl.getInstance().getPrivateKey());
						NotepadUI.setInvalidationFlag(true);
					}
					processID = reloaded.indexOf(RSAImpl.getInstance().getPrivateKey());
					recycleKey = false;
				} else {
					processID = new Random().nextInt(maxKey);
					recycleKey = true;
				}
				keys = reloaded;

			} else {
				while ((keyLine = keyReader.readLine()) != null) {
					keys.add(keyLine);
				}
				if (keys.size() < maxKey) {
					addToKeyFile(true, RSAImpl.getInstance().getPrivateKey());
					processID = keys.size();
					keys.add(RSAImpl.getInstance().getPrivateKey());
					recycleKey = false;
				} else {
					processID = new Random().nextInt(maxKey);
					recycleKey = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(keyReader, keyWriter);
		}

		System.out.println("[KEY] procID : " + processID + ", #ofkeys : " + keys.size());
	}

	private void closeIO(Closeable... IOs) {
		for (Closeable io : IOs) {
			try {
				if (io != null)
					io.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addToKeyFile(boolean append, String... contents) {
		String keyFilePath = DIR_NAME + FILE_NAME_KEYS;
		FileOutputStream fout = null;
		PrintWriter keyWriter = null;

		try {
			fout = new FileOutputStream(keyFilePath, append);
			try {
				FileLock lock = fout.getChannel().lock();

				try {
					keyWriter = new PrintWriter(fout);
					for (String content : contents) {
						keyWriter.println(content);
					}
				} catch (Exception e) {
				} finally {
					lock.release();
					closeIO(keyWriter);
					fout = null;
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeIO(fout);
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			closeIO(fout);
		}
	}

	public void saveKeys() {
		String[] a = new String[] {};
		addToKeyFile(false, keys.toArray(a));
	}
	
	public void invalidateKeys() {
		PrintWriter keyWriter = null;
		File keyFile = new File(DIR_NAME + FILE_NAME_KEYS);
		if (keyFile.exists()) {
			try {
				System.out.println("Clear crypto-notepad.keys");

				// Important. This code clears keyFile.====================================
				keyWriter = new PrintWriter(new FileWriter(keyFile, false));
				// ========================================================================

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (keyWriter != null) {
					keyWriter.close();
				}
			}
		}
	}

	public void loadProperties() {
		loadProperties(false);
	}

	public void loadProperties(boolean reload) {
		String propFilePath = DIR_NAME + FILE_NAME_PROP;

		try {
			File propFile = new File(propFilePath);
			if (!propFile.exists()) {
				System.out.println("Create crypto-notepad.properties");
				File parentDir = propFile.getParentFile();
				if (!parentDir.exists()) {
					parentDir.mkdir();
				}
				propFile.createNewFile();
				Property.initialize();
				saveProperties();
			}
			InputStream inStream = new FileInputStream(propFilePath);
			if (reload) {
				Property.reload(inStream);
			} else {
				Property.load(inStream);
				lang = Property.getLanguagePack();
			}
			inStream.close();

			maxKey = Integer.valueOf(Property.getProperties().getProperty(Property.nOfKeys, "8192"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveProperties() {
		OutputStream outStream;
		try {
			outStream = new FileOutputStream(DIR_NAME + FILE_NAME_PROP);
			Property.store(outStream, "Crypto-notepad User Properties");
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	public void saveMemo(String filename, MemoVO memo) {
		saveMemo(String filename, MemoVO memo, boolean isEncrypted
	}
	*/
	
	public void saveMemo(String filename, MemoVO memo, boolean isEncrypted) {
		PrintWriter memoWriter = null;
		try {
			memoWriter = new PrintWriter(new FileWriter(filename));

			if (isEncrypted) {
				// Encrypt.
				CryptoFacade crypto = new CryptoFacade();
				if (recycleKey) {
					crypto.encrypt(memo, keys.get(processID));
				} else {
					crypto.encrypt(memo);
				}
				memoWriter.println(HEADER_WARNING);
				memoWriter.println(
						String.valueOf(Base64.getEncoder().encodeToString(String.valueOf(processID).getBytes())));
				memoWriter.println(memo.getKey());
				memoWriter.println(memo.getContent());
			} else {
				memoWriter.println(memo.getContent());
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(memoWriter != null) {
				memoWriter.close();
			}
		}

	}

	public MemoVO loadMemo(JFrame frame, String filename) {
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

				// Return null not to show up notepadUI(= stay MainUI).
			} catch (FileNotFoundException e) {
				JOptionPane
						.showMessageDialog(
								frame, "[ERROR]" + "\nThe file does not exist." + "\nPlease check your file name."
										+ "\n(" + e.getClass().getName() + ")",
								"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (NumberFormatException e) {
				JOptionPane
						.showMessageDialog(frame,
								"[ERROR]" + "\nUnable to decrypt this file." + "\nThis encrypted file may be modified."
										+ "\n(" + e.getClass().getName() + ")",
								"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalArgumentException e) {
				JOptionPane
						.showMessageDialog(frame,
								"[ERROR]" + "\nUnable to decrypt this file." + "\nThis encrypted file may be modified."
										+ "\n(" + e.getClass().getName() + ")",
								"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane
						.showMessageDialog(frame,
								"[ERROR]" + "\nUnable to decrypt this file." + "\nThis encrypted file may be modified."
										+ "\n(" + e.getClass().getName() + ")",
								"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (IndexOutOfBoundsException | BadPaddingException e) {
				JOptionPane.showMessageDialog(
						frame, "[ERROR]" + "\nUnable to decrypt this file."
								+ "\nThe configuration file may be corrupted." + "\n(" + e.getClass().getName() + ")",
						"Crypto Notepad", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame,
						"[ERROR]" + "\nUnable to decrypt this file because of unhandled error."
								+ "\nPlease contact developer via email with error name below."
								+ "\n*E-mail : matth1996@hanmail.net" + "\n**Error Name : " + e.getClass().getName(),
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
			ampm = lang.am;
		} else {
			ampm = lang.pm;
		}

		if (Property.getProperties().get(Property.language).equals("KOREAN")) {
			return String.format("%4d/%02d/%02d " + ampm + "%02d:%02d ", year, month, day, hour, minute);
		} else {
			return String.format("%02d/%02d/%4d " + "%02d:%02d" + ampm, day, month, year, hour, minute);
		}
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
