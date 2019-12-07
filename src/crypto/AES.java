package crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public abstract class AES {

	private static final int KEY_SIZE = 256;
	private static final String ENCRYPT_ALGO = "AES";
	private static final String TRANSFORMATION = ENCRYPT_ALGO + "/ECB/PKCS5Padding";
	private KeyGenerator keyGenerator;
	
	public AES() {
		try {
			System.out.println("AES initialize.");
			keyGenerator = KeyGenerator.getInstance(ENCRYPT_ALGO);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyGenerator.init(KEY_SIZE);
	}

	public String generateSecretKey() {

		byte[] secretKey = null;

		secretKey = keyGenerator.generateKey().getEncoded();	
		String secretKeyStr = Base64.getEncoder().encodeToString(secretKey);
		return secretKeyStr;
	}

	abstract protected String getSecretKey();

	public Encryptor getEncryptor(String secretKey) {

		return new AES.AESEncryptor(secretKey);
	}

	public Decryptor getDecryptor() {
		return new AES.AESDecryptor(getSecretKey());
	}
	public Decryptor getDecryptor(String secretKey) {
		return new AES.AESDecryptor(secretKey);
	}
	
	

	private static class AESEncryptor extends Encryptor {
		
		private AESEncryptor(String secretKeyStr) {
			
			//convert string as parameter into public key
			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKeyStr), ENCRYPT_ALGO);
			setKey(keySpec);
		}
		
		@Override
		public String encrypt(String rawValue) {
			String encrypted = null;

			try {
				Cipher cipher = Cipher.getInstance(TRANSFORMATION);
				cipher.init(Cipher.ENCRYPT_MODE, (SecretKey) getKey());

				//be careful about encoding, charset, flag of base64
				encrypted = Base64.getEncoder()
						.encodeToString(cipher.doFinal(rawValue.getBytes(StandardCharsets.UTF_8)));

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}

			return encrypted;
		}
	}

	private static class AESDecryptor extends Decryptor {

		private AESDecryptor(String secretKeyStr) {

			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKeyStr), ENCRYPT_ALGO);
			setKey(keySpec);
		}

		@Override
		public String decrypt(String encrypted) {
			String decoded = null;

			try {
				Cipher cipher = Cipher.getInstance(TRANSFORMATION);
				cipher.init(Cipher.DECRYPT_MODE, (SecretKey) getKey());

				//make sure to specify charset when creating string.
				decoded = new String(
						cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8))),
						StandardCharsets.UTF_8);

			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}

			System.out.println(decoded);
			return decoded;
		}

	}

}
