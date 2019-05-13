package File;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;

import Crypto.CryptoFacade;
import Crypto.RSAImpl;
import VO.MemoVO;

public class FileManager {

	private static final String FILE_NAME_CONF = "crypto.conf";
	private static final String EXT_MEMO = ".txt";
	private static FileManager instance = null;
	ArrayList<String> config;

	private FileManager() {
		try {
			File confFile = new File(FILE_NAME_CONF);
			if (!confFile.exists()) {
				System.out.println("Create crypto.conf");
				confFile.createNewFile();
			}

			BufferedReader confReader = new BufferedReader(new FileReader(FILE_NAME_CONF));
			config = new ArrayList<String>();
			String confLine = null;
			while ((confLine = confReader.readLine()) != null) {
				config.add(confLine);
			}
			confReader.close();

			PrintWriter confWriter = new PrintWriter(new FileWriter(FILE_NAME_CONF, true));
			confWriter.println(RSAImpl.getInstance().getPrivateKey());
			confWriter.close();
			config.add(RSAImpl.getInstance().getPrivateKey());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FileManager getInstance() {

		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}
	
	private String getConfig(int idx) {
		return this.config.get(idx);
	}
	
	public void saveMemo(String filename, MemoVO memo) {
		filename = filename + EXT_MEMO;
		PrintWriter memoWriter;
		try {
			memoWriter = new PrintWriter(new FileWriter(filename));
			memoWriter.println(String.valueOf(config.size()-1));
			
			//Encrypt.
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
		if(memo.exists()) {
			try {
				BufferedReader memoReader = new BufferedReader(new FileReader(filename));
				int idx = Integer.parseInt(memoReader.readLine());
				
				readMemo.setKey(memoReader.readLine());
				readMemo.setContent(memoReader.readLine());
				memoReader.close();
				//Decrypt.
				new CryptoFacade().decrypt(readMemo, getConfig(idx));
				
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
				readMemo.setContent("[ERROR]"
						+ "Unable to decrypt the password. "
						+ "\nThe file, crypto.conf, may be corrupt.");
			}
			
		}
		else {
			readMemo.setContent("[ERROR]"
					+ "The file does not exist."
					+ "Please check your file name.");
		}
		
		return readMemo;
	}

}
