package esercizio1;

public class KeyPlainText {
	private String key;
	private String plainText;
	
	public KeyPlainText(String key, String plainText) {
		this.key = key;
		this.plainText = plainText;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPlainText() {
		return plainText;
	}
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	
}
