package crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

abstract public class RSA {
	private static final int KEY_SIZE = 1024;
	private static final String ENCRYPT_ALGO = "RSA";
	private static final String TRANSFORMATION = ENCRYPT_ALGO + "/ECB/OAEPWithSHA-1AndMGF1Padding";
	private byte[] publicKey = null;
	private byte[] privateKey = null;

	public RSA() {
		System.out.println("[RSA]RSA initialize.");
		initialize();
	}
	
	public RSA(boolean decryptMode) {
		if(decryptMode) {
			System.out.println("[RSA]RSA initialize. - decrypt mode");
		} else {
			System.out.println("[RSA]RSA initialize.");
			initialize();
		}
	}

	private void initialize() {

		// create a pair of key
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(ENCRYPT_ALGO);
			keyGenerator.initialize(KEY_SIZE);

			KeyPair keyPair = keyGenerator.generateKeyPair();
			this.publicKey = keyPair.getPublic().getEncoded();
			this.privateKey = keyPair.getPrivate().getEncoded();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String generatePublicKey() {

		// transfer public key to client
		String publicKeyStr = Base64.getEncoder().encodeToString(this.publicKey);
		return publicKeyStr;
	}

	// get public key from server
	abstract protected String getPublicKey();

	protected String getPrivateKey() {

		String privateKeyStr = Base64.getEncoder().encodeToString(this.privateKey);
		return privateKeyStr;
	}

	public Encryptor getEncryptor() {
		return new RSA.RSAEncryptor(getPublicKey());
	}

	public Encryptor getEncryptor(String privateKey) {
		PublicKey publicKey= derivePublicKey(privateKey);
		String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		
		return new RSA.RSAEncryptor(publicKeyStr);
	}

	public Decryptor getDecryptor() {
		return new RSA.RSADecryptor(getPrivateKey());
	}

	public Decryptor getDecryptor(String privateKey) {
		return new RSA.RSADecryptor(privateKey);
	}

	private PublicKey derivePublicKey(String privateKey) {
		PublicKey derivedPublicKey = null;
		RSAPrivateCrtKey privk = (RSAPrivateCrtKey) getDecryptor(privateKey).getKey();
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privk.getModulus(), privk.getPublicExponent());

		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance(ENCRYPT_ALGO);
			derivedPublicKey = keyFactory.generatePublic(publicKeySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		

		return derivedPublicKey;
	}

	private class RSAEncryptor extends Encryptor {

		private RSAEncryptor(String publicKeyStr) {

			// convert string as parameter into public key.
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
			try {
				KeyFactory factory = KeyFactory.getInstance(ENCRYPT_ALGO);
				setKey(factory.generatePublic(keySpec));

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String encrypt(String rawValue) {
			String encrypted = null;

			try {
				Cipher cipher = Cipher.getInstance(TRANSFORMATION);
				cipher.init(Cipher.ENCRYPT_MODE, (RSAPublicKey) getKey());

				// be careful about encoding, charset, flag of base64
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

	private static class RSADecryptor extends Decryptor {

		private RSADecryptor(String privateKeyStr) {

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
			try {
				KeyFactory factory = KeyFactory.getInstance(ENCRYPT_ALGO);
				setKey(factory.generatePrivate(keySpec));
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String decrypt(String encrypted) throws BadPaddingException {
			String decoded = null;

			try {
				Cipher cipher = Cipher.getInstance(TRANSFORMATION);
				cipher.init(Cipher.DECRYPT_MODE, (RSAPrivateKey) getKey());

				// make sure to specify charset when creating string.
				decoded = new String(
						cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8))),
						StandardCharsets.UTF_8);

			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			}

			return decoded;
		}

	}

}
