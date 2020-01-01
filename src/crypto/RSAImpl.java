package crypto;
public class RSAImpl extends RSA{
	
	private RSAImpl() {
		//do nothing.
	}
	
	private static final class LazyHolder{
		private static RSAImpl instance = new RSAImpl();
	}
	
	public static RSAImpl getInstance(){
		return getInstance(false);
	} 
	
	public static RSAImpl getInstance(boolean recreate) {
		if(recreate) {
			LazyHolder.instance = new RSAImpl();
		}
		return LazyHolder.instance;
	}
	
	@Override
	protected String getPublicKey() {
		return this.generatePublicKey();
	}
	
	public String getPrivateKey() {
		return super.getPrivateKey();
	}

}
