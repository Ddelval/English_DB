package fX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
	private String word;
	public static Font res_tit, res_head, res_text, res_extext, res_tra,res_et;
	public Set<Integer> selected;
	private ArrayList<VBox> mem;
	private ArrayList<Block> dat;
	public HashMap<String,Integer> dic;
	public AutoselectWindow(String word) {
		this.word=word;
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
		
		dat=Testing.getWord(word);
		pronun.setText(dat.get(0).eng);
		if(pronun.getText().equals("Not found"))pronun.setText("");
		eng.setText(word);
		dat.remove(0);
		VBox wrap=new VBox();
		wrap.setPadding(new Insets(0,10*scale,10*scale,10*scale));
		double mw=0;
		Text t= new Text();
		t.setFont(res_text);
		
		DropShadow d2= new DropShadow();
		d2.setBlurType(BlurType.GAUSSIAN);
		d2.setColor(Color.GREEN);
		d2.setHeight(5);
		d2.setWidth(5);
		d2.setRadius(10);
		d2.setSpread(0.5);
		
		DropShadow d= new DropShadow();
		d.setBlurType(BlurType.GAUSSIAN);
		d.setColor(Color.GRAY);
		d.setHeight(5);
		d.setWidth(5);
		d.setRadius(10);
		d.setSpread(0.5);
		
		
		ScrollPane scrll= new ScrollPane(wrap);
		mem= new ArrayList<VBox>();
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
			VBox c=render(abc,res_text);
			c.setEffect(d);
			mem.add(c);
			wrap.getChildren().add(c);
			VBox.setMargin(c, new Insets(20*scale,0,0,0));
		}
		
		dic= new HashMap<String,Integer>();
		selected= new TreeSet<Integer>();
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
		finish.setOnAction(e->{
			ArrayList<Ficha> arrl= new ArrayList<Ficha>();
			while(selected.size()>0) arrl.add(createFicha());
			Facade.insertAll(arrl);
			this.close();
		});
		Button fc= new Button("Finish and check");
		fc.setOnAction(e->{
			Ficha f=createFicha();
			MainWindow.prepareInput(f);
			this.close();
			
			
		});
		HBox.setMargin(finish, new Insets(0,0,0,10*MainWindow.scale));
		HBox bot= new HBox();
		bot.getChildren().addAll(fc,finish);
		VBox.setMargin(bot,new Insets(10*scale,0,0,0));
		finish.prefWidthProperty().bind(widthProperty().subtract(20).divide(2));
		fc.prefWidthProperty().bind(widthProperty().subtract(20).divide(2));
		
		VBox tot = new VBox();
		tot.setAlignment(Pos.CENTER);
		tot.setFillWidth(true);
		tot.getChildren().addAll(h,scrll,bot);
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
	private Ficha createFicha() {
		Ficha f= new Ficha(word,pronun.getText(),"");
		int added=0;
		ArrayList<Integer> tmp= new ArrayList<Integer>();
		for(int i:selected) {
			if(added==3)break;
			VBox v=mem.get(i);
			Block b= dat.get(i);
			String eng=b.ex_eng.get(0);
			String esp;
			if(b.ex_spa.size()>1) {
				Node n=v.getChildren().get(2);
				ChoiceBox<String> cb= (ChoiceBox<String>)n;
				esp=cb.getSelectionModel().getSelectedItem();
				
			}
			else {
				esp=b.ex_spa.get(0);
			}
			String use=b.use;
			String tra;
			if(b.tr.size()>1){
				Node n=v.getChildren().get(0);
				ComboBox<String> cb= (ComboBox<String>)n;
				tra=cb.getValue();
			}
			else {
				Node n=v.getChildren().get(0);
				TextField cb= (TextField)n;
				tra=cb.getText();
			}
		
			
			Example examp= new Example(eng,esp,tra);
			examp.setUse(use);
			examp.setEnglish(b.eng);
			f.addExample(examp);
			tmp.add(i);
			added++;
		}
		for(int i:tmp)selected.remove(i);
		return f;
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
		whole.getStyleClass().addAll("custom-border");
		javafx.scene.paint.Color col=javafx.scene.paint.Color.WHITE;
		Background bac=new Background(new BackgroundFill(col,new CornerRadii(5), Insets.EMPTY));
		whole.setBackground(bac);
		return whole;
		
	}
	
	
}










