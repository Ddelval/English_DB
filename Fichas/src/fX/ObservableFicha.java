package fX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ObservableFicha {
	public SimpleIntegerProperty DBkey;
	public SimpleStringProperty english, pronunciation, use, ex1,ex2,ex3, KnownEtoS, KnownStoE;
	public Ficha f;
	public ObservableFicha(Ficha f) {
		this.f=f;
		DBkey = new SimpleIntegerProperty(f.getDBkey());
		english = new SimpleStringProperty(f.getEnglish());
		pronunciation = new SimpleStringProperty(f.getPronunciation());
		use = new SimpleStringProperty(f.getUse());
		
		if(f.getKnownEtoS()) {
			KnownEtoS= new SimpleStringProperty("All");
		}
		else {
			if(f.getExampleKnownEtoS(0)) {
				if(f.getExampleKnownEtoS(1)) {
					KnownEtoS = new SimpleStringProperty("Example 1 and 2");
				}
				else {
					KnownEtoS = new SimpleStringProperty("Example 1");
					if(f.getExampleKnownEtoS(2)) {
						KnownEtoS = new SimpleStringProperty("Example 1 and 3");
					}
				}
			}
			else {
				if(f.getExampleKnownEtoS(1)) {
					if(f.getExampleKnownEtoS(2)){
						KnownEtoS = new SimpleStringProperty("Example 2 and 3");
					}
					else {
						KnownEtoS = new SimpleStringProperty("Example 2");
					}
				}
				else {
					if(f.getExampleKnownEtoS(2)) {
						KnownEtoS = new SimpleStringProperty("Example 3");
					}
					else {
						KnownEtoS = new SimpleStringProperty("None");
					}
				}
				
			}
		}
		
		if(f.getKnownStoE()) {
			KnownStoE= new SimpleStringProperty("All");
		}
		else {
			if(f.getExampleKnownEtoS(0)) {
				if(f.getExampleKnownStoE(1)) {
					KnownStoE = new SimpleStringProperty("Example 1 and 2");
				}
				else {
					KnownStoE = new SimpleStringProperty("Example 1");
					if(f.getExampleKnownStoE(2)) {
						KnownStoE = new SimpleStringProperty("Example 1 and 3");
					}
				}
			}
			else {
				if(f.getExampleKnownStoE(1)) {
					if(f.getExampleKnownStoE(2)){
						KnownStoE = new SimpleStringProperty("Example 2 and 3");
					}
					else {
						KnownStoE = new SimpleStringProperty("Example 2");
					}
				}
				else {
					if(f.getExampleKnownStoE(2)) {
						KnownStoE = new SimpleStringProperty("Example 3");
					}
					else {
						KnownStoE = new SimpleStringProperty("None");
					}
				}
				
			}
		}
		
		int siz = f.getExampVec().size();
		if(siz>0) {
			ex1 = new SimpleStringProperty(f.getExample(0).toBasicString());
			if(siz>1) {
				ex2 = new SimpleStringProperty(f.getExample(1).toBasicString());
				if(siz>2) {
					ex3 = new SimpleStringProperty(f.getExample(2).toBasicString());

				}
				else {
					ex3=null;
				}
			}
			else {
				ex2=null;
				ex3=null;
			}
		}
		else {
			ex1=null;
			ex2=null;
			ex3=null;
		}
	}
	public int getDBkey() {
		return DBkey.get();
	}
	public String getEnglish() {
		return english.get();
	}
	public String getPronunciation() {
		return pronunciation.get();
	}
	public String getUse() {
		return use.get();
	}
	public String getEx1() {
		if(ex1==null){
			return " ";
		}
		else {
			return ex1.get();
		}
	}
	public String getEx2() {
		if(ex2==null){
			return " ";
		}
		else {
			return ex2.get();
		}
	}
	public String getEx3() {
		if(ex3==null){
			return " ";
		}
		else {
			return ex3.get();
		}
	}
	public String getKnownstoe() {
		return KnownStoE.get();
	}
	public String getKnownetos() {
		return KnownEtoS.get();
	}

	public SimpleStringProperty Knownstoe() {
		return KnownStoE;
	}
	
}
