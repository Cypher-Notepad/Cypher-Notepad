package crypto;

import java.security.Key;

import javax.crypto.BadPaddingException;

abstract public class Decryptor {
	
	private Key key = null;

	protected Key getKey() {
		return key;
	}
	protected void setKey(Key key) {
		this.key = key;
	}

	public abstract String decrypt(String encrypted) throws BadPaddingException;
}
