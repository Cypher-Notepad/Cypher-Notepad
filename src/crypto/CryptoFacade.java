package crypto;
import javax.crypto.BadPaddingException;

import vo.MemoVO;

public class CryptoFacade {

	private RSA rsa;
	private AES aes;

	public CryptoFacade() {
		this.rsa = RSAImpl.getInstance();
		this.aes = new AESImpl();
	}
	
	public CryptoFacade(boolean decryptMode) {
		if(decryptMode) {
			long start = System.currentTimeMillis();
			this.rsa = RSAImpl.getDecryptInstance();
			long end = System.currentTimeMillis(); 
			System.out.println( "FACAde time11 : " + ( end - start )/1000.0 +"sec");
			start = System.currentTimeMillis();
			this.aes = new AESImpl(true);
			end = System.currentTimeMillis(); 
			System.out.println( "FACAde time22 : " + ( end - start )/1000.0 +"sec");
		} else {
			this.rsa = RSAImpl.getInstance();
			this.aes = new AESImpl();
		}
	}
	
	public CryptoFacade(RSA rsa, AES aes) {
		this.rsa = rsa;
		this.aes = aes;
	}

	public void encrypt(MemoVO memo) {
		encrypt(memo, null);
	}
	
	public void encrypt(MemoVO memo, String privateKey) {
		String secretKey = this.aes.generateSecretKey();
		Encryptor aesEncryptor = aes.getEncryptor(secretKey);
		memo.setContent(aesEncryptor.encrypt(memo.getContent()));

		Encryptor rsaEncryptor;
		if(privateKey != null) {
			rsaEncryptor = rsa.getEncryptor(privateKey);			
		}else {
			rsaEncryptor = rsa.getEncryptor();
		}
		memo.setKey(rsaEncryptor.encrypt(secretKey));
	}
	
	public void decrypt(MemoVO memo, String privateKey) throws BadPaddingException {
		long start = System.currentTimeMillis();
		
		Decryptor rsaDecryptor = rsa.getDecryptor(privateKey);
		long end = System.currentTimeMillis(); 
		System.out.println( "CF time1 : " + ( end - start )/1000.0 +"sec");
		start = System.currentTimeMillis();
		
		String secretKey = rsaDecryptor.decrypt(memo.getKey());
		memo.setKey(secretKey);
		end = System.currentTimeMillis(); 
		System.out.println( "CF time2 : " + ( end - start )/1000.0 +"sec");
		start = System.currentTimeMillis();
		
		Decryptor aesDecryptor = aes.getDecryptor(secretKey);
		end = System.currentTimeMillis(); 
		System.out.println( "CF time3 : " + ( end - start )/1000.0 +"sec");
		start = System.currentTimeMillis();
		
		memo.setContent(aesDecryptor.decrypt(memo.getContent()));
		end = System.currentTimeMillis(); 
		System.out.println( "CF time4 : " + ( end - start )/1000.0 +"sec");
		start = System.currentTimeMillis();
		
	}

}
