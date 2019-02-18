package Crypto;
public class RSAImpl extends RSA{

	private static RSAImpl instance = null;
	
	private RSAImpl() {
		//do nothing.
	}
	
	public static RSAImpl getInstance() {
		if(instance == null) {
			instance = new RSAImpl();
		}
		
		return instance;
	}
	
	@Override
	protected String getPublicKey() {
		return this.generatePublicKey();
	}
	
	public String getPrivateKey() {
		return super.getPrivateKey();
	}

}
