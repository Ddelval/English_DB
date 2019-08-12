package fX;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

class Block{
	String eng;
	ArrayList<String> tr;
	ArrayList<String> ex_eng;
	ArrayList<String> ex_spa;
	String use;
	public Block(String s) {
		if(s.startsWith("[")&&s.endsWith("]")||s.equalsIgnoreCase("Not found")){
			eng=s;
			return;
		}
		tr=new ArrayList<String>();
		ex_eng=new ArrayList<String>();
		ex_spa=new ArrayList<String>();
		
		
		String s1=Facade.cleanLabel(s,"a");
		s1=Facade.cleanLabel(s1,"em");
		s1=Facade.cleanLabel(s1,"i");
		
		s1=s1.replace("</td","");
		s1=s1.replace("&nbsp;","");
		s1=s1.replace("<strong>","");
		s1=s1.replace("</strong>","");
		s1=s1.replace("</tr>","");
		s1=s1.replace("</i>","");
		s1=s1.replace("<br>","");
		
		
		s1=s1.replace("<span title='somebody'>[sb]</span>","[sb]");
		s1=s1.replace("<span title='something'>[sth]</span>","[sth]");
		s1=Facade.cleanLabel(s1,"span");
		while(s1.contains(">>"))s1=s1.replace(">>",">");
		
		String[]tex=s1.split("<td");
		ArrayList<String> ar= new ArrayList<String>();
		for(String ss:tex) {
			String[]t2=ss.split("\n");
			for(String a:t2) {
				String[]t3=a.split("<tr");
				for(String t:t3)if(!t.equals("<"))ar.add(t);
			}
			
		}
		ArrayList<Element> el= new ArrayList<Element>();
		for(String ss:ar) {
			if(ss.contains("class=\'even\'")||ss.contains("class=\'odd\'"))continue;
			if(!ss.contains("class")&&ss.indexOf('>')==ss.lastIndexOf('>'))continue;
			try {
			while(ss.startsWith(">")||ss.startsWith(" "))ss=ss.substring(1);
			if(!ss.contains("class")&&ss.endsWith(">")) {
				while(ss.startsWith("("))ss=ss.substring(1);
				ss=ss.substring(0,ss.lastIndexOf(')'));
				el.add(new Element("use",ss));
				continue;
			}
			ss=ss.substring(ss.indexOf("class")+7);
			String c=ss.substring(0, ss.indexOf("\'"));
			ss=ss.substring(ss.indexOf(">")+1);
			String t=ss.substring(0,ss.indexOf(">"));
			while(t.endsWith(" "))t=t.substring(0,t.length()-1);
			el.add(new Element(c,t));
			//System.out.println(ss);
			}catch(Exception e) {}
			
		}
		
		
		for(Element e:el) {
			if(e.text.contains("Español")||e.text.contains("Ingles")||e.text.contains("Inglés"))continue;
			while(e.text.length()>0&&e.text.charAt(0)==' ')e.text=e.text.substring(1);
			if(e.type.equals("FrWrd"))eng=e.text;
			else if(e.type.equals("ToWrd"))tr.add(e.text.substring(0,1).toUpperCase()+e.text.substring(1).toLowerCase());
			else if(e.type.equals("FrEx")) ex_eng.add(e.text);
			else if(e.type.equals("ToEx")) ex_spa.add(e.text);
			else if(e.type.equals("use")) use="("+e.text+")";
		}
	}
	
	public static ArrayList<Block> filter(String word,ArrayList<Block> arr){
		ArrayList<Ficha> dat= Facade.selectSomeRaw(word);
		ArrayList<Block> res= new ArrayList<Block>();
		Set<String> s= new TreeSet<String>();
		dat.forEach(e->e.getExampVec().forEach(a->s.add(a.getEng_example())));
		for(int i=0;i<arr.size();++i) {
			if(arr.get(i).tr.size()>0&&arr.get(i).ex_eng.size()>0&&arr.get(i).ex_spa.size()>0&&!s.contains(arr.get(i).ex_eng.get(0))) {
				res.add(arr.get(i));
			}
		}
		return res;
		
	}
	public static ArrayList<Block> getWord(String s){
		ArrayList<Block> b= new ArrayList<Block>();
		ArrayList<String> dat=Facade.getData(s);
		b.add(new Block(dat.get(0)));
		dat.remove(0);
		for(String a:dat) {
			Block blo=new Block(a);
			if(blo.eng==null||blo.ex_eng.size()<1)continue;
			b.add(blo);
		}
		return b;
	}
	public String toString() {
		
		return "English: "+eng+"\n"+"Translate: "+tr.toString()+"\nEx_eng: "+ex_eng.toString()+"\nEx_spa: "+ex_spa.toString();
	}
	
	
	
}

class Element{
	String type;
	String text;
	public Element(String a, String b) {
		type=a;
		text=b;
	}
	public String toString() {
		return type+" -> "+text;
	}
}