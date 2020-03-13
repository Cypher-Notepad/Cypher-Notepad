package crypto;
public class AESImpl extends AES{
	
	public AESImpl() {}
	
	public AESImpl(boolean decryptMode) {
		super(decryptMode);
	}
	
	@Override
	protected String getSecretKey() {
		return null;
	}

}
