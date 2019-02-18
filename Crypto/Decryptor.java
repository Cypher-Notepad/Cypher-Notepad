package Crypto;

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
    //public abstract String decrypt(Encrytable encrypted); 
    /*
    public User decrypt(User user) {
    	User decrypted = new User();
    	
    	decrypted.setEMail(decrypt(user.getEMail()));
    	decrypted.setName(decrypt(user.getName()));
    	decrypted.setPassword(decrypt(user.getPassword()));
    	decrypted.setPNum(decrypt(user.getPNum()));
        System.out.println(decrypted.toString());

        return decrypted;
    }
*/
}
