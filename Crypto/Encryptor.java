package Crypto;

import java.security.Key;

abstract public class Encryptor{

	private Key key = null;

	protected Key getKey() {
		return key;
	}
	protected void setKey(Key key) {
		this.key = key; 
	}

	abstract public String encrypt(String rawValue);
}