package fX;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javafx.application.Platform;

public class FindLocal_run extends Thread {
	private String text;
	public static volatile boolean refresh=false;
	private final int N=10; //Number of elements
	public FindLocal_run(String a) {
		text=a.toLowerCase();
	}
	public void run() {
		if (FindLocal.his==null)FindLocal.his=new Stack<LocalHistory>();
		if(refresh)FindLocal.his.clear();
		
		if (FindLocal.his==null||FindLocal.his.empty()) {
			if(FindLocal.his==null)FindLocal.his=new Stack<LocalHistory>();
			ArrayList<Ficha> f= Facade.getAll();
			String q="";
			FindLocal.his.push(new LocalHistory(f,q));
		}
		while(!FindLocal.his.peek().contains(text))FindLocal.his.pop();
		
		ArrayList<Ficha> candidates= new ArrayList<Ficha>();
		if(text!="") {
			boolean found=false;
			
			Iterator<Ficha> it=FindLocal.his.peek().res.iterator();
			Ficha tmp;
			while(it.hasNext()) {
				tmp=it.next();
				if(tmp.getEnglish().toLowerCase().equals(text))found=true;
				if(tmp.getEnglish().toLowerCase().startsWith(text)) {
					if(candidates.size()>N)break;
					candidates.add(tmp);
					
				}
			}
			MainWindow.updateStatus(found);
			if(candidates.size()<N) {
				it=FindLocal.his.peek().res.iterator();
				while(it.hasNext()) {
					tmp=it.next();
					if(tmp.getEnglish().toLowerCase().endsWith(text)&&!candidates.contains(tmp)) {
						if(candidates.size()>N)break;
						candidates.add(tmp);
						
					}
				}
			}
			if(candidates.size()<N) {
				it=FindLocal.his.peek().res.iterator();
				while(it.hasNext()) {
					tmp=it.next();
					if(tmp.getEnglish().toLowerCase().contains(text)&&!candidates.contains(tmp)) {
						if(candidates.size()>N)break;
						candidates.add(tmp);
						
					}
				}
			}
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				MainWindow.setLocalsuggestions(candidates);
				
			}
			
		});
		
		
		
	}
}
