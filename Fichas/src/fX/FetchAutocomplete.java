package fX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;

public class FetchAutocomplete extends Thread {
	private String text;
	public FetchAutocomplete(String a) {
		text=a;
	}
	
	public void run() {
		ArrayList<String> elements= new ArrayList<String>();
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
		    	elements.add(w);
		    	line=br.readLine();
		    	
		    }
		    br.close();
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					MainWindow.setWRsuggestions(elements);
					//MainWindow.scrp.minHeightProperty().bind(MainWindow.buttonsautocomplete.heightProperty().add(20));
				}
				
			});
			
			
			
		} catch (InterruptedException e) {
			// TODO Add exception handling
			MainWindow.exceptions=e.getMessage();
			e.printStackTrace();
			
		}
		catch(IOException e) {
			MainWindow.exceptions=e.getMessage();
			e.printStackTrace();
		}
		
	}
	
}
