package fX;

public class Example {
	private String translation;
	private String eng_example, esp_example;
	private boolean knownEtoS=false;
	private boolean knownStoE=false;
	public Example(String eng_example, String esp_example, String translation) {
		this.translation=translation;
		this.eng_example=eng_example;
		this.esp_example=esp_example;
		
	}
	public Example(String eng_example, String esp_example, String translation, boolean EtoS,boolean StoE) {
		this.translation=translation;
		this.eng_example=eng_example;
		this.esp_example=esp_example;
		this.knownEtoS=EtoS;
		this.knownStoE=StoE;
	}
	public Example (String str) {
		eng_example= str.substring(0, str.indexOf('|'));
		str=str.substring(str.indexOf('|')+1);
		esp_example= str.substring(0, str.indexOf('|'));
		str=str.substring(str.indexOf('|')+1);
		translation= str.substring(0, str.indexOf('|'));
		str=str.substring(str.indexOf('|')+1);
		if(str.contains("|")) {
			//New one
			knownEtoS=Boolean.parseBoolean(str.substring(0,str.indexOf('|')));
			str=str.substring(str.indexOf('|')+1);
			knownStoE=Boolean.parseBoolean(str);
		}
		else {
			//Old one
			knownEtoS=Boolean.parseBoolean(str);
		}
		
	}
	public String toString() {
		StringBuilder s = new StringBuilder(eng_example);
		s.append("|");
		s.append(esp_example);
		s.append("|");
		s.append(translation);
		s.append("|");
		s.append(Boolean.toString(knownEtoS));
		s.append("|");
		s.append(Boolean.toString((knownStoE)));
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
	
	public boolean getEtoSKnown () {
		return knownEtoS;
	}
	public boolean getStoEKnown () {
		return knownStoE;
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
	public void setEtoSKnown(boolean b) {
		this.knownEtoS=b;
	}
	public void setStoEKnown (boolean b) {
		this.knownStoE=b;
	}
	
}
