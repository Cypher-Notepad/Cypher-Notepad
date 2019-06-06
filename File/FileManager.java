package File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Properties;

import javax.crypto.BadPaddingException;

import Config.Property;
import Crypto.CryptoFacade;
import Crypto.RSAImpl;
import VO.MemoVO;
import VO.PropertiesVO;

public class FileManager {

	private static final String FILE_NAME_PROP = "crypto-notepad.properties";
	private static final String FILE_NAME_KEYS = "crypto-notepad.keys";
	private static final String EXT_MEMO = ".txt";
	private static FileManager instance = null;
	private ArrayList<String> keys;
	private Properties property;

	private FileManager() {
		keys = new ArrayList<String>();
		property = new Properties();
		loadKeys();
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

	private void loadKeys() {
		try {
			File keyFile = new File(FILE_NAME_KEYS);
			if (!keyFile.exists()) {
				System.out.println("Create crypto-notepad.keys");
				keyFile.createNewFile();
			}

			BufferedReader keyReader = new BufferedReader(new FileReader(FILE_NAME_KEYS));
			//keys = new ArrayList<String>();
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

	public void loadProperties() {

		try {
			File propFile = new File(FILE_NAME_PROP);
			if (!propFile.exists()) {
				System.out.println("Create crypto-notepad.properties");
				Property.setDefaultProperties(property);
			} else {
				InputStream inStream = getClass().getResourceAsStream(FILE_NAME_PROP);
				property.load(inStream);
				inStream.close();
			}
		} catch (Exception e) {

		}
	}

	private void createProperties() {

	}

	public void saveProperties() {
		OutputStream outStream;
		try {
			outStream = new FileOutputStream(FILE_NAME_PROP);
			try {
				property.store(outStream, "Crypto-notepad User Properties");
			} catch (IOException e) {
			} finally {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void saveMemo(String filename, MemoVO memo) {
		filename = filename + EXT_MEMO;
		PrintWriter memoWriter;
		try {
			memoWriter = new PrintWriter(new FileWriter(filename));
			memoWriter.println(String.valueOf(keys.size() - 1));

			// Encrypt.
			CryptoFacade crypto = new CryptoFacade();
			crypto.encrypt(memo);
			memoWriter.println(memo.getKey());
			memoWriter.println(memo.getContent());
			memoWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public MemoVO loadMemo(String filename) {
		filename = filename + EXT_MEMO;
		File memo = new File(filename);
		MemoVO readMemo = new MemoVO();
		if (memo.exists()) {
			try {
				BufferedReader memoReader = new BufferedReader(new FileReader(filename));
				int idx = Integer.parseInt(memoReader.readLine());

				readMemo.setKey(memoReader.readLine());
				readMemo.setContent(memoReader.readLine());
				memoReader.close();
				// Decrypt.
				new CryptoFacade().decrypt(readMemo, getKey(idx));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IndexOutOfBoundsException | BadPaddingException e) {
				readMemo.setContent("[ERROR]" + "\nUnable to decrypt the file." + "\nThe crypto.conf, may be corrupt.");
			}

		} else {
			readMemo.setContent("[ERROR]" + "\nThe file does not exist." + "\nPlease check your file name.");
		}

		return readMemo;
	}

}
