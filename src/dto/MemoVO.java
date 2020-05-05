package dto;
import crypto.Encryptable;

public class MemoVO implements Encryptable{
	private String content;
	private String key;
	
	public void setContent(String content) {
		this.content = content;
	} 
	public void setKey(String key) {
		this.key = key;
	}
	
	public String  getContent() {
		if(content == null) {
			content = "";
		}
		return content;
	}
	
	@Override
	public String getKey() {
		return key;
	}
}
