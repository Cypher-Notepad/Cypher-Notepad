package Crypto;

/**
 * Created by COMSO on 2018-03-27.
 * <p>
 * ���� ���� �Ǿ� �ѹ� �ۺ�Ű���� �޾ƿ��� ����ɶ����� ���� �ۺ� Ű���� �ۻ� �����մϴ�
 * ���������� ������ ����� �����ʴ� �̻� Ű���� ���� ���� �����ϴ�.
 * <p>
 * �ٸ� ������ ������� ��찡 �߻��Ѵٸ� ������ Ű���� ���ϱ� ������
 * ���� �������·� ������ ������Ѵٸ� �ۺ�Ű���� ���ǹ������ϴ�
 * ������ ������Ͽ��µ� �ۺ�Ű���� �ʿ��ϴٸ� �۵� ������Ͽ� �����κ��� ���� Ű���� �޾ƿ;��մϴ�.
 * <p>
 * Update : ��ȣȭ �� �� ��� �ۺ�Ű ���� �ޱ� ������ ������ ����� �Ǿ
 * ���� ������� �ʿ䰡 ����. (18.03.27)
 *
 * Update : DTO�� �߰� �Ǹ� �߻�Ŭ���� Encryptor�� Decryptor��
 * �޼ҵ带 �����ϸ� �� (18.03.30)
 */

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
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
	//private static String receivedPublicKey = null;

	//private static RSA instance = null;

	public RSA() {
		System.out.println("RSA initialize.");
		initialize();
	}

	private void initialize() {

		// Ű������ �̺κп����� �̷������ ��.
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(ENCRYPT_ALGO);
			keyGenerator.initialize(KEY_SIZE);

			KeyPair keyPair = keyGenerator.generateKeyPair();
			this.publicKey = keyPair.getPublic().getEncoded();
			this.privateKey = keyPair.getPrivate().getEncoded();

			/*
			 * KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_ALGO);
			 * this.publicKeySpec = keyFactory.getKeySpec(this.publicKey.,
			 * RSAPublicKeySpec.class); this.privateKeySpec =
			 * keyFactory.getKeySpec(this.privateKey, RSAPrivateKeySpec.class);
			 */
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//return instance;
	}
	

	public String generatePublicKey() {

		// Client���� publicKey�� �������ִ� �޼ҵ�.

		// For Spring Server.
		//String publicKeyStr = Base64.getEncoder().encodeToString(RSA.getInstance().publicKey);
		String publicKeyStr = Base64.getEncoder().encodeToString(this.publicKey);
		// For Android.
		// String publicKeyStr = Base64.encodeToString(RSA.getInstance().publicKey,
		// Base64.NO_WRAP);

		return publicKeyStr;
	}

	// Server�κ��� publicKey�� �޾ƿ��� �޼ҵ�.
	abstract protected String getPublicKey();


	protected String getPrivateKey() {

		// For Spring Server.
		String privateKeyStr = Base64.getEncoder().encodeToString(this.privateKey);
		// For Android.
		// String privateKeyStr = Base64.encodeToString(RSA.getInstance().privateKey,
		// Base64.NO_WRAP);

		return privateKeyStr;
	}

	public Encryptor getEncryptor() {
		return new RSA.RSAEncryptor(getPublicKey());
	}

	public Decryptor getDecryptor() {
		return new RSA.RSADecryptor(getPrivateKey());
	}
	public Decryptor getDecryptor(String privateKey) {
		return new RSA.RSADecryptor(privateKey);
	}

	private class RSAEncryptor extends Encryptor {
		
		private RSAEncryptor(String publicKeyStr) {

			// ���� ���� string�� ����Ű�� ��ȯ.
			// ***************** For Spring Server. *****************
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
			// ***************** For Android. *****************
			// X509EncodedKeySpec keySpec = new
			// X509EncodedKeySpec(Base64.decode(publicKeyStr, Base64.NO_WRAP));

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

				// ���ڵ� ����!! - charset�������� / base64�÷��� �߿�!
				// ***************** For Spring Server. *****************
				encrypted = Base64.getEncoder()
						.encodeToString(cipher.doFinal(rawValue.getBytes(StandardCharsets.UTF_8)));
				// ***************** For Android. *****************
				// encrypted =
				// Base64.encodeToString(cipher.doFinal(rawValue.getBytes(StandardCharsets.UTF_8)),
				// Base64.NO_WRAP);

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

			// ***************** For Spring Server. *****************
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
			// ***************** For Android. *****************
			// PKCS8EncodedKeySpec keySpec = new
			// PKCS8EncodedKeySpec(Base64.decode(privateKeyStr, Base64.NO_WRAP));

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

				// ���ڿ� ������ �ݵ�� charset���� �� ��.
				// ***************** For Spring Server. *****************
				decoded = new String(
						cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8))),
						StandardCharsets.UTF_8);
				// ***************** For Android. *****************
				// decoded = new
				// String(cipher.doFinal(Base64.decode(encrypted.getBytes(StandardCharsets.UTF_8),
				// Base64.NO_WRAP)), StandardCharsets.UTF_8);

			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			}
			
			System.out.println(decoded);
			return decoded;
		}

	}

}
