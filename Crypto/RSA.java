package Crypto;

/**
 * Created by COMSO on 2018-03-27.
 * <p>
 * 앱이 실행 되어 한번 퍼블릭키값을 받아오면 종료될때까지 같은 퍼블릭 키값이 앱상에 존재합니다
 * 서버에서는 서버가 재실행 되지않는 이상 키값이 변할 일은 없습니다.
 * <p>
 * 다만 서버가 재시작할 경우가 발생한다면 서버상에 키값이 변하기 때문에
 * 앱이 켜진산태로 서버를 재시작한다면 퍼블릭키값이 무의미해집니다
 * 서버를 재시작하였는데 퍼블릭키값이 필요하다면 앱도 재시작하여 서버로부터 새로 키값을 받아와야합니다.
 * <p>
 * Update : 암호화 할 때 즉시 퍼블릭키 값을 받기 때문에 서버가 재시작 되어도
 * 앱을 재시작할 필요가 없음. (18.03.27)
 *
 * Update : DTO가 추가 되면 추상클래스 Encryptor와 Decryptor에
 * 메소드를 구현하면 됨 (18.03.30)
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

		// 키생성은 이부분에서만 이루어져야 함.
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

		// Client에게 publicKey를 전달해주는 메소드.

		// For Spring Server.
		//String publicKeyStr = Base64.getEncoder().encodeToString(RSA.getInstance().publicKey);
		String publicKeyStr = Base64.getEncoder().encodeToString(this.publicKey);
		// For Android.
		// String publicKeyStr = Base64.encodeToString(RSA.getInstance().publicKey,
		// Base64.NO_WRAP);

		return publicKeyStr;
	}

	// Server로부터 publicKey를 받아오는 메소드.
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

			// 전달 받은 string을 공개키로 변환.
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

				// 인코딩 주의!! - charset설정주의 / base64플래그 중요!
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

				// 문자열 생성시 반드시 charset지정 할 것.
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
