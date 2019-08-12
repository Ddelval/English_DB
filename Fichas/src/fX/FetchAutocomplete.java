package fX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FetchAutocomplete extends Thread {
	private String text;
	private ArrayList<String> elements;
	public FetchAutocomplete(String a) {
		text=a;
	}
	
	public void run() {
		ArrayList<String> elements= new ArrayList<String>();
		try {
			String comm="curl 'https://www.wordreference.com/2012/autocomplete/autocomplete.aspx?dict=enes&query="+text+"' -H 'authority: www.wordreference.com' -H 'cache-control: max-age=0' -H 'upgrade-insecure-requests: 1' -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36' -H 'sec-fetch-mode: navigate' -H 'sec-fetch-user: ?1' -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3' -H 'sec-fetch-site: none' -H 'accept-encoding: gzip, deflate, br' -H 'accept-language: es-ES,es;q=0.9,en;q=0.8' -H 'cookie: _ga=GA1.2.704726212.1532023789; per_Mc_x=cae8e901f6c944e48ad7f084342f1e42; euconsent=BORKE6BORKE6BABABAESBS-AAAAeZ7_______9______9uz_Gv_v_f__33e8__9v_l_7_-___u_-33d4-_1vX99yfm1-7ftr1tp386ues2LDiCA; llang=enesi; per24h=1; _gid=GA1.2.428927050.1565467301; per24c=2; per_M_08=2' --compressed  >com.txt";
			
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
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					MainWindow.setWRsuggestions(elements);
					//MainWindow.scrp.minHeightProperty().bind(MainWindow.buttonsautocomplete.heightProperty().add(20));
				}
				
			});
			
			
			
		} catch (Exception e) {
			// TODO Add exception handling
			e.printStackTrace();
		}
		
	}
	
}
