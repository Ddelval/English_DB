package fX;

public class Example {
	private String translation;
	private String eng_example, esp_example;
	private boolean known=false;
	public Example(String eng_example, String esp_example, String translation) {
		this.translation=translation;
		this.eng_example=eng_example;
		this.esp_example=esp_example;
		
	}
	public Example(String eng_example, String esp_example, String translation, boolean b) {
		this.translation=translation;
		this.eng_example=eng_example;
		this.esp_example=esp_example;
		this.known=b;
	}
	public Example (String str) {
		eng_example= str.substring(0, str.indexOf('|'));
		str=str.substring(str.indexOf('|')+1);
		esp_example= str.substring(0, str.indexOf('|'));
		str=str.substring(str.indexOf('|')+1);
		translation= str.substring(0, str.indexOf('|'));
		str=str.substring(str.indexOf('|')+1);
		known=Boolean.parseBoolean(str);
	}
	public String toString() {
		StringBuilder s = new StringBuilder(eng_example);
		s.append("|");
		s.append(esp_example);
		s.append("|");
		s.append(translation);
		s.append("|");
		s.append(Boolean.toString(known));
		return s.toString();
	}
	public String toBasicString() {
		StringBuilder s = new StringBuilder(eng_example);
		s.append(" | ");
		s.append(esp_example);
		s.append(" | ");
		s.append(translation);
		return s.toString();
	}
	
	public boolean getKnown () {
		return known;
	}
	public String getTranslation() {
		return translation;
	}
	public String getEng_example() {
		return eng_example;
	}
	public String getEsp_example() {
		return esp_example;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public void setEng_example(String eng_example) {
		this.eng_example = eng_example;
	}
	public void setEsp_example(String esp_example) {
		this.esp_example = esp_example;
	}
	public void setKnown(boolean b) {
		this.known=b;
	}
	
}
