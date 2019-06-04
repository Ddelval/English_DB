package fX;
import java.util.StringTokenizer;
import java.util.Vector;

public class Ficha {
	private String english;
	private String pronunciation;
	private Vector <Example> examp;
	private String use;
	private int DBkey;
	private boolean knownEtoS=false;
	private boolean knownStoE=false;
	
	
	public Ficha(String english, String pronunciation, String use) {
		this.english=Facade.autotrim(english).replace("%&/%", "'");
		this.pronunciation=Facade.autotrim(pronunciation).replace("%&/%", "'"); 
		this.use = Facade.autotrim(use).replace("%&/%", "'");
	}
	public Ficha(ObservableFicha o) {
		this.english=o.f.getEnglish();
		this.pronunciation=o.f.getPronunciation();
		this.examp=o.f.getExampVec();
		this.use=o.f.getUse();
		this.DBkey=o.f.getDBkey();
		this.knownEtoS=o.f.getKnownEtoS();
		this.knownStoE=o.f.getKnownEtoS();
		
	}
	public Ficha(String s) {
		this.english=(s.substring(0, s.indexOf("%%"))).replace("%&/%", "'");
		s=s.substring(s.indexOf("%%")+2);
		this.setSQLExamples((s.substring(0,s.indexOf("%%"))).replace("%&/%", "'"));
		s=s.substring(s.indexOf("%%")+2);
		this.use=(s.substring(0,s.indexOf("%%"))).replace("%&/%", "'");
		s=s.substring(s.indexOf("%%")+2);
		this.pronunciation=(s.substring(0,s.indexOf("%%"))).replace("%&/%", "'");
	}
	public void addExample(Example ex) {
		if(examp==null) {
			examp=new Vector<Example>(3);
		}
		examp.add(ex);
		
	}
	public void addExample(String engExamp, String espExamp, String translate) {
		if(examp==null) {
			examp=new Vector<Example>(3);
		}
		examp.add(new Example(Facade.autotrim(engExamp), Facade.autotrim(espExamp),Facade.autotrim(translate)));
	}
	public String toSQLString() {
		StringBuilder sb = new StringBuilder("'");
		String eng= this.english;
		String examps;
		eng=eng.replace("'", "%&/%");
		sb.append(eng);
		sb.append("','");
		if(examp!=null) {
			for (int i=0; i<examp.size();i++) {
				examps =examp.elementAt(i).toString();
				examps=examps.replace("'", "%&/%");
				sb.append(examps);
				if(examp.size()-i>1){
					sb.append("\n");
				}
				
			}
		}
		sb.append("','");
		String use = this.use;
		use=use.replace("'", "%&/%");
		sb.append(use);
		sb.append("','");
		String pronunciation = this.pronunciation;
		pronunciation=pronunciation.replace("'", "%&/%");
		sb.append(pronunciation);
		sb.append("'");
		return sb.toString();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(english);
		sb.append("%%");
		for (int i=0; i<examp.size();i++) {
			sb.append(examp.elementAt(i).toString());
			if(examp.size()-i>1){
				sb.append("\n");
			}
			
		}
		
		sb.append("%%");
		sb.append(use);
		sb.append("%%");
		sb.append(pronunciation);
		sb.append("%%");
		sb.append(Boolean.toString(knownEtoS));
		sb.append("%%");
		sb.append(Boolean.toString(knownEtoS));
		sb.append("%%");
		return sb.toString();
	}
	public boolean allKnownEtoS() {
			boolean b = true;
			for(int i=0; i<examp.size();i++) {
				if(examp.elementAt(i).getEtoSKnown()==false) {
					b=false;
				}
			}
			return b;
		
	}
	public boolean allKnownStoE() {
		boolean b = true;
		for(int i=0; i<examp.size();i++) {
			if(examp.elementAt(i).getStoEKnown()==false) {
				b=false;
			}
		}
		return b;
	
}
	public void setUnknownEtoS() {
		for (int i=0; i<examp.size();i++) {
			examp.elementAt(i).setEtoSKnown(false);
		}
		
	}
	public void setUnknownStoE() {
		for (int i=0; i<examp.size();i++) {
			examp.elementAt(i).setStoEKnown(false);
		}
		
	}
	public void setExKnownEtoS(int index) {
		if(index<examp.size()) {
			examp.elementAt(index).setEtoSKnown(true);
			if(allKnownEtoS()) {
				setKnownEtoS(true);
			}
		}
	}
	public void setExKnownStoE(int index) {
		if(index<examp.size()) {
			examp.elementAt(index).setStoEKnown(true);
			if(allKnownStoE()) {
				setKnownStoE(true);
			}
		}
	}
	public String getExamplesString() {
		if(examp==null) {
			return "";
		}
		else {
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<examp.size();i++) {
				sb.append(examp.elementAt(i).toString());
				if((examp.size()-i)>1){
					sb.append("\n");
				}
				
			}
			return sb.toString();
		}
	}
	
	public void setSQLExamples(String str) {
		StringTokenizer st = new StringTokenizer(str, "\n");
		Vector <String> vec = new Vector<String>();
		while(st.hasMoreTokens()) {
			vec.add(st.nextToken());
		}
		examp = new Vector<Example>(3);
		for(int i = 0; i<vec.size();i++){
			examp.add(new Example(vec.elementAt(i).replace("%&/%", "'")));
		}
	}
	
	public boolean getExampleKnownEtoS(int i) {
		if(i<examp.size()) {
			if( examp.elementAt(i).getEtoSKnown()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	public boolean getExampleKnownStoE(int i) {
		if(i<examp.size()) {
			if( examp.elementAt(i).getStoEKnown()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	public void clearExamples() {
		this.examp=new Vector<Example>(3);
	}
	public void deleteExample(int i) {
		if(i<examp.size()) {
			this.examp.remove(i);
		}
	}
	public boolean getKnownEtoS() {
		return knownEtoS;
	}
	public boolean getKnownStoE() {
		return knownStoE;
	}
	public String getEnglish() {
		return english;
	}
	public String getPronunciation() {
		return pronunciation;
	}
	public Vector<Example> getExampVec() {
		return examp;
	}
	public Example getExample(int i) {
		if(i<examp.size()) {
			return examp.elementAt(i);
		}
		else {
			return new Example("","","");
		}
	}
	public int getDBkey() {
		return DBkey;
	}
	public String getUse() {
		return use;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}
	public void setExampVec(Vector<Example> examp) {
		this.examp = examp;
	}
	public void setExamp(Example example, int i) {
		this.examp.set(i, example);
	}
	public void setDBkey(int dBkey) {
		DBkey = dBkey;
	}
	public void setUse (String use) {
		this.use=use;
	}
	public void setKnownEtoS(boolean b) {
		knownEtoS = b;
	}
	public void setKnownStoE(boolean b) {
		knownStoE = b;
	}
	
}
