package fX;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class FindLocal_run extends Thread {
	private String text;
	private final int N=10; //Number of elements
	public FindLocal_run(String a) {
		text=a;
	}
	public void run() {
		if (FindLocal.his==null||FindLocal.his.empty()) {
			if(FindLocal.his==null)FindLocal.his=new Stack<LocalHistory>();
			ArrayList<Ficha> f= Facade.getAll();
			String q="";
			FindLocal.his.push(new LocalHistory(f,q));
		}
		while(!FindLocal.his.peek().contains(text))FindLocal.his.pop();
		
		ArrayList<Ficha> candidates= new ArrayList<Ficha>();
		boolean found=false;
		
		Iterator<Ficha> it=FindLocal.his.peek().res.iterator();
		Ficha tmp;
		while(it.hasNext()) {
			tmp=it.next();
			if(tmp.getEnglish().equals(text))found=true;
			if(tmp.getEnglish().startsWith(text)) {
				if(candidates.size()>N)break;
				candidates.add(tmp);
				
			}
		}
		//TODO Change implementation of this code
		if  (found) MainWindow.state.setText("Not new");
		else 		MainWindow.state.setText("New"); 
			
		if(candidates.size()<N) {
			it=FindLocal.his.peek().res.iterator();
			while(it.hasNext()) {
				tmp=it.next();
				if(tmp.getEnglish().endsWith(text)&&!candidates.contains(tmp)) {
					if(candidates.size()>N)break;
					candidates.add(tmp);
					
				}
			}
		}
		if(candidates.size()<N) {
			it=FindLocal.his.peek().res.iterator();
			while(it.hasNext()) {
				tmp=it.next();
				if(tmp.getEnglish().contains(text)&&!candidates.contains(tmp)) {
					if(candidates.size()>N)break;
					candidates.add(tmp);
					
				}
			}
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				double scale= MainWindow.scale;
				FlowPane res= new FlowPane();
				res.setVgap(10);
				res.setHgap(10);
				Button[] autoc= new Button[candidates.size()];
				for(int i=0;i<candidates.size();++i) {
					autoc[i]= new Button(candidates.get(i).getEnglish());
					final Ficha s=candidates.get(i);
					autoc[i].setOnAction(e->{
						MainWindow.detail(s,MainWindow.primaryStage.getWidth(),MainWindow.primaryStage.getHeight());
					});
					HBox.setMargin(autoc[i], new Insets(0,10*scale,0,0));
					res.getChildren().add(autoc[i]);
				}
				res.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
				//TODO  Send this to the MainWindow file
				//res.minWidth(100*scale);
				res.prefWidthProperty().bind(MainWindow.tot.widthProperty().subtract(MainWindow.grid.widthProperty()));
				MainWindow.s.setDividerPosition(0, 0.65);
				MainWindow.scrplocal.setContent(res);
				//MainWindow.scrp.minHeightProperty().bind(MainWindow.buttonsautocomplete.heightProperty().add(20));
				
			}
			
		});
		this.stop();
		
		
	}
}
