package fX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;

public class CommandProcessor extends Thread {
	private String in;
	private String out;
	public CommandProcessor(String s) {
		in=s;
	}
	
	public String getOutput() {
		return out;
	}
	public void run() {
		in=Facade.basicfilter(in);
		if(in.startsWith("help")){
			help();
		}
		else if(in.startsWith("addAll")) {
			addAll();
		}
		else if(in.startsWith("add")) {
			add();
		}
		MainWindow.addCommandout(out);
		
	}
	private void help() {
		out="The commands supported are:\n" + 
				"--> add\n" + 
				"	Syntax: \"add word\" where word is the word to be added to the database\n" + 
				"--> addAll\n" + 
				"	Syntax: \"add path\" where path is the path to a text file \n" + 
				"                           containing a list of words, one per line.";
	}
	private void add() {
		String arg= in.substring(3);
		arg=Facade.basicfilter(arg);
		int ret=addWord(arg);
		if(ret==0) {
			out="Success";
		}
		else if(ret==-1) {
			out="The word could not be found";
		}
		else {
			out="There was an error during the introduction of the word";
		}
		
	}
	private void addAll() {
		String arg= in.substring(6);
		arg=Facade.basicfilter(arg);
		ArrayList<String> failures= new ArrayList<String>();
		BufferedReader br=null;
		int counter=0;
		try {
			br= new BufferedReader(new FileReader(arg));
		} catch (FileNotFoundException e) {
			out=e.getMessage();
			e.printStackTrace();
			return;
		}
		try {
			String word=br.readLine();
			while(word!=null) {
				int retval=addWord(word);
				if(retval==1) {
					failures.add(word+": Error");
				}
				else if(retval==-1) {
					failures.add(word+": Word not found");
				}
				else if(retval==-2) {
					failures.add(word+": No new examples");
				}
				else {
					counter++;
					MainWindow.addCommandout("Added: "+word);
				}
				word=br.readLine();
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out=e.getMessage();
			e.printStackTrace();
			return;
		}
		if(failures.size()==0) {
			out="\nResult:\nAll "+Integer.toString(counter)+" words have been introduced";
		}
		else {
			out="\nResult:\nThe following words could not be introduced\n";
			for(String s:failures) {
				
				out+=s;
				out+="\n";
			}
		}
		
		
	}
	private int addWord(String word) {
		//if (checkWord(word)) {
			ArrayList<Block> b=Block.getWord(word);
			if(b.size()<1)return -1;
			if(b.get(0).eng.equalsIgnoreCase("not found")&&b.size()==1)return -1;
			Ficha f= new Ficha(word.toLowerCase(),b.get(0).eng,"");
			b.remove(0);
			b=Block.filter(word, b);
			if(b.size()<1)return -2;
			
			for(int i=1;i<Math.min(4,b.size());++i) {
				Example ex= new Example(b.get(i).ex_eng.get(0),b.get(i).ex_spa.get(0),b.get(i).tr.get(0));
				ex.setEnglish(b.get(i).eng);
				ex.setUse(b.get(i).use);
				f.addExample(ex);
			}
			Facade.addDB(f);
			if(MainWindow.exceptions.isEmpty())return 0;
			else return 1;
		//}
		//return -1;
	}
	private boolean checkWord(String text) {
		try {
			try {
				text=URLEncoder.encode(text,StandardCharsets.UTF_8.toString());
			}catch(Exception e) {
				MainWindow.exceptions=e.getMessage();
				e.printStackTrace();
			}
			String comm=MainWindow.cfd.autoco_1+text+MainWindow.cfd.autoco_2;
			
			BufferedWriter writ = new BufferedWriter(new FileWriter("ex"));
			writ.write("#!/bin/bash\n");
			writ.write(comm);
			writ.close();
			
			Process p=Runtime.getRuntime().exec("chmod +x ./ex ");
			p.waitFor();
			p=Runtime.getRuntime().exec("./ex");
			p.waitFor();
			
			File f= new File("com.txt");
			BufferedReader br= new BufferedReader(new FileReader(f));

		    String line=br.readLine();
		    for(int i=0;i<15;++i) {
		    	if(line==null)break;
		    	if(line.substring(line.indexOf('\t')).contains("es"))break;
		    	String w=line.substring(0,line.indexOf('\t'));
		    	if(w.equals(text))return true;
		    	line=br.readLine();
		    	
		    }
						
		} catch (InterruptedException e) {
			// TODO Add exception handling
			MainWindow.exceptions=e.getMessage();
			e.printStackTrace();
			return false;
			
		}
		catch(IOException e) {
			MainWindow.exceptions=e.getMessage();
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
