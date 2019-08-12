package fX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AutoselectWindow extends Stage {
	private Label eng,pronun;
	private Button finish;
	private ScrollPane mid;
	private VBox examples;
	public static Font res_tit, res_head, res_text, res_extext, res_tra,res_et;
	public Queue<Integer> selected;
	public HashMap<String,Integer> dic;
	public AutoselectWindow() {
		double scale=MainWindow.scale;
		res_tit = 	Font.font("Helvetica",FontWeight.BOLD,20*MainWindow.scale);
		res_text = 	Font.font("Helvetica",FontWeight.LIGHT, 14*MainWindow.scale);
		res_head = 	Font.font("Helvetica Neue",FontWeight.MEDIUM, 18*MainWindow.scale);
		res_extext= Font.font("Helvetica Neue", FontWeight.BOLD,18*MainWindow.scale);
		res_et= Font.font("Arial", FontWeight.NORMAL,18*MainWindow.scale);
		res_tra = 	Font.font("Arial",FontWeight.NORMAL,15*MainWindow.scale);
		
		eng= new Label("COMPANION");
		eng.setAlignment(Pos.TOP_LEFT);
		eng.setFont(res_extext);
		eng.minWidthProperty().bind(widthProperty().divide(2).subtract(30*scale));
		eng.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				Facade.Speak(eng.getText());
				MainWindow.mssgWindow();
				
			}
		});
		pronun = new Label("[kəmˈpænjən]");
		pronun.setAlignment(Pos.TOP_RIGHT);
		pronun.minWidthProperty().bind(widthProperty().divide(2).subtract(30*MainWindow.scale));
		pronun.setFont(res_et);
		pronun.setWrapText(true);
		pronun.setTextAlignment(TextAlignment.RIGHT);
		pronun.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				Facade.Speak(eng.getText());
				MainWindow.mssgWindow();
				
			}
		});
		HBox h= new HBox();
		h.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale) );
		h.getChildren().addAll(eng,pronun);
		
		String word="Car";
		
		ArrayList<Block> dat=Testing.getWord(word);
		pronun.setText(dat.get(0).eng);
		eng.setText(word);
		dat.remove(0);
		VBox wrap=new VBox();
		wrap.setPadding(new Insets(0,10*scale,10*scale,10*scale));
		double mw=0;
		Text t= new Text();
		t.setFont(res_text);
		
		DropShadow d= new DropShadow();
		d.setBlurType(BlurType.GAUSSIAN);
		d.setColor(Color.GRAY);
		d.setHeight(5);
		d.setWidth(5);
		d.setRadius(10);
		d.setSpread(0.5);
		
		DropShadow d2= new DropShadow();
		d2.setBlurType(BlurType.GAUSSIAN);
		d2.setColor(Color.GRAY);
		d2.setHeight(5);
		d2.setWidth(0);
		d2.setRadius(0);
		d2.setSpread(0.5);
		ScrollPane scrll= new ScrollPane(wrap);
		ArrayList<VBox> mem= new ArrayList<VBox>();
		for(int i=0;i<dat.size();++i) {
			Block abc=dat.get(i);
			for(String a:abc.ex_eng) {
				t.setText(a);
				mw=Math.max(mw, t.getLayoutBounds().getWidth());
				
			}
			for(String a:abc.ex_spa) {
				t.setText(a);
				mw=Math.max(mw, t.getLayoutBounds().getWidth());
				
			}
			VBox c=Testing.render(abc,res_text);
			c.setEffect(d);
			mem.add(c);
			wrap.getChildren().add(c);
			VBox.setMargin(c, new Insets(20*scale,0,0,0));
		}
		
		dic= new HashMap<String,Integer>();
		selected= new LinkedList<Integer>();
		for(int i=0;i<mem.size();++i) {
			System.out.println(mem.get(i).getId()+"||||||"+mem.get(i).toString());
			dic.put(mem.get(i).toString(), i);
		}
		if(mem.size()>0) {
			selected.add(0);
			mem.get(0).setEffect(d2);
			if(mem.size()>1) {
				selected.add(1);
				mem.get(1).setEffect(d2);
				if(mem.size()>2) {
					selected.add(2);
					mem.get(2).setEffect(d2);
					
				}
			}
		}
		for(VBox c:mem) {
			c.setOnMouseClicked(e->{
				if(c.getEffect().equals(d)) {
					//Not selected;
					c.setEffect(d2);
					selected.add(dic.get(c.toString()));
					System.out.println("Adding: "+Integer.toString(dic.get(c.toString())));
					if(selected.size()>3) {
						int i=selected.poll();
						mem.get(i).setEffect(d);
					}
				}
				else {
					//Already selected
					c.setEffect(d);
					System.out.println("Removing: "+Integer.toString(dic.get(c.toString())));
					selected.remove(dic.get(c.toString()));
				}
				
				
				
			});
		}
		
		
		//wrap.setFillWidth(true);
		
		scrll.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		
		
		finish= new Button("Finish");
		VBox.setMargin(finish,new Insets(10*scale,0,0,0));
		finish.minWidthProperty().bind(widthProperty().subtract(30));
		VBox tot = new VBox();
		tot.setAlignment(Pos.CENTER);
		tot.setFillWidth(true);
		tot.getChildren().addAll(h,scrll,finish);
		tot.setPadding(new Insets(10*MainWindow.scale,10*MainWindow.scale,10*MainWindow.scale,10*MainWindow.scale));
		double wid,hei;
		wid=Math.max(Math.min(mw+130*scale,900*scale),300*scale);
		for(VBox s:mem) {
			s.setMaxWidth(wid-80*scale);
			s.setMinWidth(wid-80*scale);
		}
		scrll.setMaxWidth(wid-20*scale);
		wrap.setMaxWidth(wid-20*scale);
		hei=500+50*scale;
		Scene sc= new Scene(tot,wid,hei);
		sc.getStylesheets().add("Data.css");
		this.setScene(sc);
		this.show();
		
		
		
	}
	
}
class Ev implements EventHandler {

	@Override
	public void handle(Event event) {
		// TODO Auto-generated method stub
		
	}
	
}








