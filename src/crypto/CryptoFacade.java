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
	
	/* not used */
	public CryptoFacade(boolean decryptMode) {
		if(decryptMode) {
			this.rsa = RSAImpl.getDecryptInstance();
			this.aes = new AESImpl(true);
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
		Decryptor rsaDecryptor = rsa.getDecryptor(privateKey);
		String secretKey = rsaDecryptor.decrypt(memo.getKey());
		memo.setKey(secretKey);
		
		Decryptor aesDecryptor = aes.getDecryptor(secretKey);
		memo.setContent(aesDecryptor.decrypt(memo.getContent()));
		
	}

}
