package crypto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class AES {

	private static final int KEY_SIZE = 256; //bits
	private static final int NONCE_SIZE = 12; // bytes
	private static final int AUTH_TAG_SIZE = 16; // bytes, it is 128 in bits.
	private static final String ENCRYPT_ALGO = "AES";
	private static final String TRANSFORMATION = ENCRYPT_ALGO + "/GCM/NoPadding";
	private KeyGenerator keyGenerator;
	
	public AES() {
		System.out.println("[AES] AES initialized.");
		initialize();	
	}
	
	public AES(boolean decryptMode) {
		if(decryptMode) {
			System.out.println("[AES] AES initialized. - decrypt mode");
		} else {
			System.out.println("[AES] AES initialized.");
			initialize();
		}
	}
	
	private void initialize() {
		try {
			keyGenerator = KeyGenerator.getInstance(ENCRYPT_ALGO);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyGenerator.init(KEY_SIZE);
	}

	public String generateSecretKey() {

		/*
		byte[] secretKey = null;
		secretKey = keyGenerator.generateKey().getEncoded();	
		String secretKeyStr = Base64.getEncoder().encodeToString(secretKey);
		return secretKeyStr;
		*/
		
		return null;
	}

	abstract protected String getSecretKey();

	public Encryptor getEncryptor() {
		return new AES.AESEncryptor();
	}

	
	public Decryptor getDecryptor() {
		return new AES.AESDecryptor(getSecretKey());
	}
	
	public Decryptor getDecryptor(String secretKey) {
		return new AES.AESDecryptor(secretKey);
	}

	public class AESEncryptor extends Encryptor {
		
		private AESEncryptor() {			
			// Generate key with highest priority RNG algorithm.
			keyGenerator.init(KEY_SIZE, new SecureRandom());
	        SecretKey key = keyGenerator.generateKey();
			setKey(key);
		}
		
		@Override
		public String encrypt(String rawValue) {
			String complexMessage = null;
			
			try {
				// Create initialization vector with highest priority RNG algorithm.
				byte[] initVector = new byte[NONCE_SIZE];
				SecureRandom secureRandom = new SecureRandom();
				secureRandom.nextBytes(initVector);
				
				// Encrypt
				Cipher cipher = Cipher.getInstance(TRANSFORMATION);
				GCMParameterSpec parameterSpec = new GCMParameterSpec(AUTH_TAG_SIZE * 8 , initVector); //128 bit authentication tag length
				cipher.init(Cipher.ENCRYPT_MODE, (SecretKey) getKey(), parameterSpec);
				byte[] encrypted = cipher.doFinal(rawValue.getBytes(StandardCharsets.UTF_8));
				
				// Make it one (size of IV + IV + encrypted value).
				ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES + initVector.length + encrypted.length);
				byteBuffer.putInt(initVector.length);
				byteBuffer.put(initVector);
				byteBuffer.put(encrypted);
				complexMessage = Base64.getEncoder().encodeToString(byteBuffer.array());

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
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}

			return complexMessage;
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
				byte[] encryptedComplexMsg = Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8));
		        ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedComplexMsg);

		        // Get Nonce(IV) size, it should be 12 in my implementation.
		        int nonceSize = byteBuffer.getInt();
		        if(nonceSize != NONCE_SIZE) {
		        	throw new IllegalArgumentException("[AES] Nonce size is incorrect. Make sure that input file is not modified improperly.");
		        }
		        
		        byte[] iv = new byte[NONCE_SIZE];
		        byteBuffer.get(iv);
		        
		        byte[] encryptedContent = new byte[byteBuffer.remaining()];
		        byteBuffer.get(encryptedContent);
		        
		        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		        GCMParameterSpec parameterSpec = new GCMParameterSpec(AUTH_TAG_SIZE * 8, iv);

		        
		        cipher.init(Cipher.DECRYPT_MODE, (SecretKey) getKey(), parameterSpec);
		        decoded = new String(
		        		cipher.doFinal(encryptedContent),StandardCharsets.UTF_8);
		        
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				//e.printStackTrace();
			} catch (InvalidKeyException e) {
				//e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				//e.printStackTrace();
			} catch (BadPaddingException e) {
				//e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				//e.printStackTrace();
			}

			return decoded;
		}

	}

}
