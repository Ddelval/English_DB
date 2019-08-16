package fX;

public class Example {
	private String translation;
	private String eng_example, esp_example;
	private String use;
	private String english;
	private boolean knownEtoS=false;
	private boolean knownStoE=false;
	public Example(String eng_example, String esp_example, String translation) {
		this.translation=translation.toLowerCase();
		this.eng_example=eng_example;
		this.esp_example=esp_example;
		
	}
	public Example(String eng_example, String esp_example, String translation, boolean EtoS,boolean StoE) {
		this.translation=translation.toLowerCase();
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
		translation= str.substring(0, str.indexOf('|')).toLowerCase();
		str=str.substring(str.indexOf('|')+1);
		if(str.contains("|")) {
			//New one
			knownEtoS=Boolean.parseBoolean(str.substring(0,str.indexOf('|')));
			str=str.substring(str.indexOf('|')+1);
			if(str.contains("|")) {
				knownStoE=Boolean.parseBoolean(str.substring(0,str.indexOf('|')));
				str=str.substring(str.indexOf('|')+1);
				if(str.contains("|")) {
					use=str.substring(0,str.indexOf('|'));
					english=str.substring(str.indexOf('|'));
				}
				else {
					use=str;
				}
				
				
			}
			else {
				knownStoE=Boolean.parseBoolean(str);
			}
			
		}
		else {
			//Old one
			knownEtoS=Boolean.parseBoolean(str);
		}
		if (use==null) {
			use="";
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
		if(use!=null&&!use.equals("")) {
			s.append("|");
			s.append(use);
		}
		if(english!=null&&!english.equals("")) {
			s.append("|");
			s.append(english);
		}
		
		
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
	public String getUse() {
		return use;
	}
	public String getEnglish() {
		return english;
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
	public void setUse(String us) {
		this.use=us;
	}
	public void setEnglish(String en) {
		this.english=en;
	}
	
}
