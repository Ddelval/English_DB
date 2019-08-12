package fX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.sun.prism.paint.Color;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.scene.Group; 
import javafx.scene.paint.*; 
import javafx.geometry.*;
public class Testing {
	
	
	public static String cleanLabel (String in, String label) {
		String end="</"+label;
		StringBuilder s = new StringBuilder(label);
		s.reverse();
		s.append('<');
		String beg=s.toString();
		
		StringBuilder sb= new StringBuilder(in);
		while(sb.toString().contains(end)) {
			int i=sb.indexOf(end)+end.length();
			StringBuilder sa=new StringBuilder(sb.toString());
			sa.reverse();
			int b=sb.length()-sa.indexOf(beg,sa.length()-i-1)-beg.length();
			sb=sb.delete(b, i);
			//System.out.println(sb.toString());
		}
		return sb.toString();
	}
	public static ArrayList<Element> processEl(String s){
		String s1=cleanLabel(s,"a");
		s1=cleanLabel(s1,"em");
		s1=cleanLabel(s1,"i");
		
		s1=s1.replace("</td","");
		s1=s1.replace("&nbsp;","");
		s1=s1.replace("<strong>","");
		s1=s1.replace("</strong>","");
		s1=s1.replace("</tr>","");
		s1=s1.replace("</i>","");
		s1=s1.replace("<br>","");
		
		
		s1=s1.replace("<span title='somebody'>[sb]</span>","[sb]");
		s1=s1.replace("<span title='something'>[sth]</span>","[sth]");
		s1=cleanLabel(s1,"span");
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
		//System.out.print("\n");System.out.print("\n");
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
		return el;
	}
	public static void prepData(String word) {
		try {
			String comm="curl 'https://www.wordreference.com/es/translation.asp?tranword="+word+"' -H 'authority: www.wordreference.com' -H 'cache-control: max-age=0' -H 'upgrade-insecure-requests: 1' -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36' -H 'sec-fetch-mode: navigate' -H 'sec-fetch-user: ?1' -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3' -H 'sec-fetch-site: none' -H 'accept-encoding: gzip, deflate, br' -H 'accept-language: es-ES,es;q=0.9,en;q=0.8' -H 'cookie: _ga=GA1.2.704726212.1532023789; per_Mc_x=cae8e901f6c944e48ad7f084342f1e42; euconsent=BORKE6BORKE6BABABAESBS-AAAAeZ7_______9______9uz_Gv_v_f__33e8__9v_l_7_-___u_-33d4-_1vX99yfm1-7ftr1tp386ues2LDiCA; llang=enesi; per24h=1; _gid=GA1.2.428927050.1565467301; per24c=2; per_M_08=2' --compressed  >web.txt";
			
			BufferedWriter writ = new BufferedWriter(new FileWriter("ex"));
			writ.write("#!/bin/zsh\n");
			writ.write(comm);
			writ.close();
//			
//			writ = new BufferedWriter(new FileWriter("web.txt"));
//			writ.write("");
//			writ.close();
//			
			Process p=Runtime.getRuntime().exec("./ex");
			p.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static ArrayList<String> getAutocomplete(String s){
		ArrayList<String> elements= new ArrayList<String>();
		try {
			String comm="curl 'https://www.wordreference.com/2012/autocomplete/autocomplete.aspx?dict=enes&query="+s+"' -H 'authority: www.wordreference.com' -H 'cache-control: max-age=0' -H 'upgrade-insecure-requests: 1' -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36' -H 'sec-fetch-mode: navigate' -H 'sec-fetch-user: ?1' -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3' -H 'sec-fetch-site: none' -H 'accept-encoding: gzip, deflate, br' -H 'accept-language: es-ES,es;q=0.9,en;q=0.8' -H 'cookie: _ga=GA1.2.704726212.1532023789; per_Mc_x=cae8e901f6c944e48ad7f084342f1e42; euconsent=BORKE6BORKE6BABABAESBS-AAAAeZ7_______9______9uz_Gv_v_f__33e8__9v_l_7_-___u_-33d4-_1vX99yfm1-7ftr1tp386ues2LDiCA; llang=enesi; per24h=1; _gid=GA1.2.428927050.1565467301; per24c=2; per_M_08=2' --compressed  >com.txt";
			
			BufferedWriter writ = new BufferedWriter(new FileWriter("ex"));
			writ.write("#!/bin/zsh\n");
			writ.write(comm);
			writ.close();
			
			Process p=Runtime.getRuntime().exec("./ex");
			p.waitFor();
			
			File f= new File("com.txt");
			BufferedReader br= new BufferedReader(new FileReader(f));

		    String line=br.readLine();
		    for(int i=0;i<15;++i) {
		    	if(line==null)break;
		    	if(line.substring(line.indexOf('\t')).contains("es"))break;
		    	String w=line.substring(0,line.indexOf('\t'));
		    	elements.add(w);
		    	line=br.readLine();
		    	
		    }
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return elements;
	}

	public static ArrayList<String> getData(){
		ArrayList<String> arrl= new ArrayList<String>();
		try {
			File f= new File("web.txt");
			 BufferedReader br= new BufferedReader(new FileReader(f));

		    String line=br.readLine();
		    
		    while(line!=null&&!line.contains("'pronWR'")) {
		    	line=br.readLine();
		    }
		    String p=line.substring(line.indexOf('['));
		    p=p.replace("<sup>r</sup>", "ʳ");
		    p=p.substring(0, p.indexOf(']')+1);
		    arrl.add(p);
		    System.out.println("Pronunciation: "+p+"\n");
		    
		    while(line!=null&&!line.contains("<div id=\"articleWRD\">")) {
		    	line=br.readLine();
		    }

		    line=br.readLine();
		    line=br.readLine();
		    String[] str= new String[2];
		    str[0]="<tr class=\'even\'";
		    str[1]="<tr class=\'odd\'";
		    String tmp="";
		    int a=0;
		    while(line.startsWith(str[0])||line.startsWith(str[1])) {
		    	if(line.startsWith(str[a]))tmp+=line;
		    	else {
		    		arrl.add(tmp);
		    		tmp=line;
		    		if(a==0)a=1;
		    		else a=0;
		    	}
		    	line=br.readLine();
		    	
		    }
		    if(tmp!=null &&tmp!="")arrl.add(tmp);
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		return arrl;
	}
	public static ArrayList<Block> getWord(String s){
		prepData(s);
		ArrayList<Block> b= new ArrayList<Block>();
		ArrayList<String> dat=getData();
		b.add(new Block(dat.get(0)));
		dat.remove(0);
		for(String a:dat) {
			Block blo=new Block(processEl(a));
			if(blo.eng==null||blo.ex_eng.size()<1)continue;
			b.add(blo);
		}
		return b;
	}
	public static VBox render(Block b,Font f) {
		VBox whole= new VBox();
		Node a;
		double scale=MainWindow.scale;
		if(b.tr.size()>1) {
			a= new ComboBox<String>();
			
			((ComboBoxBase<String>) a).setEditable(true);
			for(String ab:b.tr)((ComboBox<String>) a).getItems().add(ab);
			((ComboBox<String>) a).getSelectionModel().select(0);
			((ComboBox<String>)a).minWidthProperty().bind(whole.widthProperty().subtract(20*scale));
		}
		else {
			a= new TextField();
			if(b.tr.size()>0)((TextField) a).setText(b.tr.get(0));
		}
		
		Label l= new Label();
		if(b.ex_eng.size()>0)l.setText(b.ex_eng.get(0));
		Node esp;
		if(b.ex_spa.size()<=1) {
			esp=new Label();
			if(b.ex_spa.size()>0)((Labeled) esp).setText(b.ex_spa.get(0));
		}
		else {
			esp= new ChoiceBox<String>();
			for(String ab:b.ex_spa)((ChoiceBox<String>) esp).getItems().add(ab);
			((ChoiceBox<String>) esp).getSelectionModel().select(0);
		}
		
		
		//whole.setFillWidth(true);
		String fon="-fx-font: "+f.getSize()+"px"+" \""+f.getName()+"\" ;";
		whole.setStyle(fon);
		Label l2= new Label("Use: "+b.use);
		whole.getChildren().addAll(a,l,esp,l2);
		
		whole.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		whole.getStyleClass().add("custom-border");
		javafx.scene.paint.Color c=javafx.scene.paint.Color.WHITE;
		whole.setBackground(new Background(new BackgroundFill(c,new CornerRadii(5), Insets.EMPTY)));
		
		return whole;
		
	}
	
	/*
	public static void main(String[]args) {
		//String s="<tr class='even' id='enes:9181'><td class='FrWrd' ><strong><a href='/EnglishUsage/pay #1' target='_blank' class='engusg' title='English Usage information available'></a>pay<a title='conjugate pay' class='conjugate' href='/conj/EnVerbs.aspx?v=pay'>⇒</a></strong> <em class='tooltip POS2'>vi<span><i>intransitive verb</i>: Verb not taking a direct object--for example, \"She <b>jokes</b>.\" \"He <b>has arrived</b>.\" </span></em></td><td> (make a payment)</td><td class='ToWrd' >pagar<a title=\"conjugate pagar\" class='conjugate' href=\"/conj/EsVerbs.aspx?v=pagar\">⇒</a> <em class='tooltip POS2'>vtr<span><i>verbo transitivo</i>: Verbo que requiere de un objeto directo (\"<b>di</b> la verdad\", \"<b>encontré</b> una moneda\").</span></em></td></tr>";
		
		prepData("companion");
		ArrayList<Block> b= new ArrayList<Block>();
		ArrayList<String> dat=getData();
		for(String a:dat) {
			b.add(new Block(processEl(a)));
		}
		//System.out.println("\n\n\n *********************Results******************** \n\n\n");
		b.removeIf(ef->{return ef.eng.contains("Ingles");});
		for(Block c:b)System.out.println(c.toString()+"\n");
		
		
	}*/
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
class Block{
	String eng;
	ArrayList<String> tr;
	ArrayList<String> ex_eng;
	ArrayList<String> ex_spa;
	String use;
	public Block(ArrayList<Element> arr) {
		tr=new ArrayList<String>();
		ex_eng=new ArrayList<String>();
		ex_spa=new ArrayList<String>();
		for(Element e:arr) {
			if(e.text.contains("Español")||e.text.contains("Ingles")||e.text.contains("Inglés"))continue;
			while(e.text.length()>0&&e.text.charAt(0)==' ')e.text=e.text.substring(1);
			if(e.type.equals("FrWrd"))eng=e.text;
			else if(e.type.equals("ToWrd"))tr.add(e.text.substring(0,1).toUpperCase()+e.text.substring(1).toLowerCase());
			else if(e.type.equals("FrEx")) ex_eng.add(e.text);
			else if(e.type.equals("ToEx")) ex_spa.add(e.text);
			else if(e.type.equals("use")) use="("+e.text+")";
		}
	}
	public Block(String pronun) {
		eng=pronun;
	}
	public String toString() {
		
		return "English: "+eng+"\n"+"Translate: "+tr.toString()+"\nEx_eng: "+ex_eng.toString()+"\nEx_spa: "+ex_spa.toString();
	}
}
