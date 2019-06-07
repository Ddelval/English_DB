package fX;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AuxiliarBuilder {
	/* Result window */
	public static Font res_tit, res_head, res_text, res_extext, res_tra,res_et;
	public static Label res_eng, res_pronun;
	public static Label res_ex_eng_1, res_ex_eng_2, res_ex_eng_3, res_ex_esp_1, res_ex_esp_2, res_ex_esp_3;
	public static Label res_esp_1, res_esp_2, res_esp_3;
	public static TextArea TAuse;
	public static VBox v;
	private static final double MAXWID=600;
	private static final double MINWID=400;
	public static double expWidth;
	public static double expHeight;
	/**********************/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	public static Scene displayFicha (Ficha f, Stage st) {
		Text ex2=null,ex1=null,ex3=null, use = null;
		Label tr1,tr2,tr3;
		HBox h1,h2,h3;
		h1=h2=h3=null;
		tr1=tr2=tr3=null;
		res_tit = 	Font.font("Helvetica",FontWeight.BOLD,20*MainWindow.scale);
		res_text = 	Font.font("Helvetica",FontWeight.LIGHT, 14*MainWindow.scale);
		res_head = 	Font.font("Helvetica Neue",FontWeight.MEDIUM, 18*MainWindow.scale);
		res_extext= Font.font("Helvetica Neue", FontWeight.BOLD,18*MainWindow.scale);
		res_et= Font.font("Arial", FontWeight.NORMAL,18*MainWindow.scale);
		res_tra = 	Font.font("Arial",FontWeight.NORMAL,15*MainWindow.scale);
		int len = f.getExampVec().size();
		res_eng = new Label(f.getEnglish().toUpperCase());
		res_eng.setAlignment(Pos.TOP_LEFT);
		res_eng.setFont(res_extext);
		res_eng.minWidthProperty().bind(st.widthProperty().divide(2).subtract(30*MainWindow.scale));
		res_eng.setOnMouseClicked(new EventHandler(){
			@Override
			public void handle(Event event) {
				Facade.Speak(res_eng.getText());
				MainWindow.mssgWindow();
				
			}
		});
		
		
		res_pronun = new Label(f.getPronunciation());
		res_pronun.setAlignment(Pos.TOP_RIGHT);
		res_pronun.minWidthProperty().bind(st.widthProperty().divide(2).subtract(30*MainWindow.scale));
		res_pronun.setFont(res_et);
		res_pronun.setWrapText(true);
		res_pronun.setTextAlignment(TextAlignment.RIGHT);
		res_pronun.setOnMouseClicked(new EventHandler(){
			@Override
			public void handle(Event event) {
				Facade.Speak(res_eng.getText());
				MainWindow.mssgWindow();
				
			}
		});
		if(len>0) {
			res_ex_eng_1 = new Label(f.getExample(0).getEng_example());
			res_ex_eng_1.setAlignment(Pos.TOP_CENTER);
			res_ex_eng_1.minWidthProperty().bind(st.widthProperty().subtract(30*MainWindow.scale));
			res_ex_eng_1.setFont(res_text);
			res_ex_eng_1.setWrapText(true);
			res_ex_eng_1.setTextAlignment(TextAlignment.CENTER);
			res_ex_eng_1.setOnMouseClicked(new EventHandler(){
				@Override
				public void handle(Event event) {
					Facade.Speak(res_ex_eng_1.getText());
					MainWindow.mssgWindow();
					
				}
			});
			
			res_ex_esp_1 = new Label(f.getExample(0).getEsp_example());
			res_ex_esp_1.setAlignment(Pos.TOP_CENTER);
			res_ex_esp_1.minWidthProperty().bind(st.widthProperty().subtract(30*MainWindow.scale));
			res_ex_esp_1.setFont(res_text);
			res_ex_esp_1.setWrapText(true);
			res_ex_esp_1.setTextAlignment(TextAlignment.CENTER);
			res_esp_1 = new Label(f.getExample(0).getTranslation());
			res_esp_1.setAlignment(Pos.TOP_CENTER);
			res_esp_1.setWrapText(true);
			res_esp_1.setFont(res_text);
			res_esp_1.setTextAlignment(TextAlignment.CENTER);
			ex1 = new Text("Example 1: ");
			ex1.setFont(res_head);
			//ex1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			ex1.setTextAlignment(TextAlignment.LEFT);
			
			GridPane.setFillWidth(res_ex_eng_1, true);
			GridPane.setFillWidth(res_ex_esp_1, true);
			GridPane.setFillWidth(res_esp_1, true);
			GridPane.setFillWidth(ex1, true);
			if(len>1) {
				res_ex_eng_2 = new Label(f.getExample(1).getEng_example());
				res_ex_eng_2.setAlignment(Pos.TOP_CENTER);
				res_ex_eng_2.minWidthProperty().bind(st.widthProperty().subtract(30*MainWindow.scale));
				res_ex_eng_2.setFont(res_text);
				res_ex_eng_2.setWrapText(true);
				res_ex_eng_2.setTextAlignment(TextAlignment.CENTER);
				res_ex_eng_2.setOnMouseClicked(new EventHandler(){
					@Override
					public void handle(Event event) {
						Facade.Speak(res_ex_eng_2.getText());
						MainWindow.mssgWindow();
						
					}
				});
				res_ex_esp_2 = new Label(f.getExample(1).getEsp_example());
				res_ex_esp_2.setAlignment(Pos.TOP_CENTER);
				res_ex_esp_2.minWidthProperty().bind(st.widthProperty().subtract(30*MainWindow.scale));
				res_ex_esp_2.setFont(res_text);
				res_ex_esp_2.setTextAlignment(TextAlignment.CENTER);
				res_ex_esp_2.setWrapText(true);
				res_esp_2 = new Label(f.getExample(1).getTranslation());
				res_esp_2.setAlignment(Pos.TOP_CENTER);
				res_esp_2.setWrapText(true);
				res_esp_2.setFont(res_text);
				res_esp_2.setTextAlignment(TextAlignment.CENTER);
				ex2 = new Text("Example 2: ");
				ex2.setFont(res_head);
				ex2.setFontSmoothingType(FontSmoothingType.LCD);
				ex2.setTextAlignment(TextAlignment.LEFT);
				
				GridPane.setFillWidth(res_ex_eng_2, true);
				GridPane.setFillWidth(res_ex_esp_2, true);
				GridPane.setFillWidth(res_esp_2, true);
				GridPane.setFillWidth(ex2, true);
				if(len>2) {
					res_ex_eng_3 = new Label(f.getExample(2).getEng_example());
					res_ex_eng_3.setAlignment(Pos.TOP_CENTER);
					res_ex_eng_3.minWidthProperty().bind(st.widthProperty().subtract(30*MainWindow.scale));
					res_ex_eng_3.setFont(res_text);
					res_ex_eng_3.setWrapText(true);
					res_ex_eng_3.setTextAlignment(TextAlignment.CENTER);
					res_ex_eng_3.setOnMouseClicked(new EventHandler(){
						@Override
						public void handle(Event event) {
							Facade.Speak(res_ex_eng_3.getText());
							MainWindow.mssgWindow();
							
						}
					});
					res_ex_esp_3 = new Label(f.getExample(2).getEsp_example());
					res_ex_esp_3.setAlignment(Pos.TOP_CENTER);
					res_ex_esp_3.minWidthProperty().bind(st.widthProperty().subtract(30*MainWindow.scale));
					res_ex_esp_3.setFont(res_text);
					res_ex_esp_3.setTextAlignment(TextAlignment.CENTER);
					res_ex_esp_3.setWrapText(true);
					res_esp_3 = new Label(f.getExample(2).getTranslation());
					res_esp_3.setAlignment(Pos.CENTER);
					res_esp_3.setWrapText(true);
					res_esp_3.setFont(res_text);
					res_esp_3.setTextAlignment(TextAlignment.CENTER);
					ex3 = new Text("Example 3: ");
					ex3.setFont(res_head);
					ex3.setFontSmoothingType(FontSmoothingType.LCD);
					ex3.setTextAlignment(TextAlignment.LEFT);
					
					GridPane.setFillWidth(res_ex_eng_3, true);
					GridPane.setFillWidth(res_ex_esp_3, true);
					GridPane.setFillWidth(res_esp_3, true);
					GridPane.setFillWidth(ex3, true);
					
				}
			}
		}
		if(!f.getUse().equals("")) {
			use = new Text("Use notes: ");
			use.setFont(res_head);
			use.setFontSmoothingType(FontSmoothingType.LCD);
			use.setTextAlignment(TextAlignment.CENTER);
			TAuse=new TextArea(f.getUse());
			TAuse.setEditable(false);
			TAuse.setFont(res_text);
		}
		Text tri;
		tri=new Text("Translation:");
		tri.setFont(res_tra);
		
		if(len>0) {
		h1=new HBox();
		tr1=new Label("Translation:");
		tr1.setTextAlignment(TextAlignment.RIGHT);
		tr1.setFont(res_tra);
		tr1.setMinWidth(tri.layoutBoundsProperty().get().getWidth());
		h1.getChildren().addAll(tr1,res_esp_1);
		h1.setAlignment(Pos.CENTER);
		HBox.setHgrow(tr1, Priority.ALWAYS);
		HBox.setHgrow(res_esp_1, Priority.NEVER);
		HBox.setMargin(tr1, new Insets(0,10*MainWindow.scale,0,0));
		if(len>1) {
		h2=new HBox();
		tr2=new Label("Translation:");
		tr2.setTextAlignment(TextAlignment.RIGHT);
		tr2.setFont(res_tra);
		tr2.setMinWidth(tri.layoutBoundsProperty().get().getWidth());
		h2.getChildren().addAll(tr2,res_esp_2);
		h2.setAlignment(Pos.CENTER); 
		HBox.setHgrow(tr2, Priority.ALWAYS);
		HBox.setHgrow(res_esp_2, Priority.NEVER);
		HBox.setMargin(tr2, new Insets(0,10*MainWindow.scale,0,0));
		if(len>2) {
		h3=new HBox();
		tr3=new Label("Translation:");
		tr3.setTextAlignment(TextAlignment.RIGHT);
		tr3.setFont(res_tra);
		tr3.setMinWidth(tri.layoutBoundsProperty().get().getWidth());
		h3.getChildren().addAll(tr3,res_esp_3);
		h3.setAlignment(Pos.CENTER); 
		HBox.setHgrow(tr3, Priority.ALWAYS);
		HBox.setHgrow(res_esp_3, Priority.NEVER);
		HBox.setMargin(tr3, new Insets(0,10*MainWindow.scale,0,0));
		}}}
		
		
		
		GridPane.setFillWidth(res_eng, true);
		GridPane.setFillWidth(res_pronun, true);
		
		GridPane grid = new GridPane();
		
		grid.setPadding(new Insets(10*MainWindow.scale,10*MainWindow.scale,10*MainWindow.scale,10*MainWindow.scale));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10*MainWindow.scale);
		grid.setVgap(10*MainWindow.scale);
		grid.add     (res_eng, 0, 0);
		grid.add  (res_pronun, 1, 0);
		
		if(len>0) {
			GridPane.setFillHeight(res_ex_eng_1, true);
			GridPane.setFillHeight(res_ex_esp_1, true);
			GridPane.setFillWidth(res_ex_eng_1, true);
			//GridPane.setFillHeight(h1, true);
			grid.add         (ex1, 0, 1, 2, 1);
			grid.add(res_ex_eng_1, 0, 2, 2, 1);
			grid.add(res_ex_esp_1, 0, 3, 2, 1);
			grid.add   (h1		 , 0, 4, 2, 1);
			if(len>1) {
				GridPane.setFillHeight(res_ex_eng_2, true);
				GridPane.setFillHeight(res_ex_esp_2, true);
				//GridPane.setFillHeight(h2, true);
				grid.add         (ex2, 0, 5, 2, 1);
				grid.add(res_ex_eng_2, 0, 6, 2, 1);
				grid.add(res_ex_esp_2, 0, 7, 2, 1);
				grid.add   (h2		 , 0, 8, 2, 1);
				if(len>2) {
					GridPane.setFillHeight(res_ex_eng_3, true);
					GridPane.setFillHeight(res_ex_esp_3, true);
					//GridPane.setFillHeight(h3, true);
					grid.add         (ex3, 0,  9, 2, 1);
					grid.add(res_ex_eng_3, 0, 10, 2, 1);
					grid.add(res_ex_esp_3, 0, 11, 2, 1);
					grid.add   (h3		 , 0, 12, 2, 1);
				}
			}
		}
		double extrause=0;
		double maxwidth=0;
		if(!f.getUse().equals("")) {
			//The switch is only for the row count
			GridPane.setFillHeight(TAuse, false);
			switch(len) {
				case 1:{
					grid.add  (use, 0, 5, 2, 1);
					grid.add(TAuse, 0, 6, 2, 1);
					break;
				}
				case 2:{
					grid.add  (use, 0,  9, 2, 1);
					grid.add(TAuse, 0, 10, 2, 1);
					break;
				}
				case 3:{
					grid.add  (use, 0, 13, 2, 1);
					grid.add(TAuse, 0, 14, 2, 1);
					break;
				}
			}
			tri.setFont(TAuse.getFont());
			String us=f.getUse();
			int count=0;
			double cwidth=0;
			
			int pstop=0;
			for(int i=0;i<us.length();++i) {
				if((us.charAt(i)=='\n'||us.charAt(i)=='\r')) {
					if(pstop!=i) {
						tri.setText(us.substring(pstop, i));
						cwidth=tri.layoutBoundsProperty().get().getWidth();
						if(cwidth>maxwidth)maxwidth=cwidth;
						count++;
						pstop=i;
					}
				}
			}
			if(pstop!=us.length()-1) {
				tri.setText(us.substring(pstop));
				cwidth=tri.layoutBoundsProperty().get().getWidth();
				if(cwidth>maxwidth)maxwidth=cwidth;
			}
			if(count==0)count++;
			extrause=(tri.layoutBoundsProperty().get().getHeight()-10*MainWindow.scale)*(count+1)+40*MainWindow.scale;
			TAuse.setMaxHeight(extrause);
			
		}
		grid.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(10*MainWindow.scale), BorderWidths.DEFAULT, new Insets(5*MainWindow.scale,5*MainWindow.scale,5*MainWindow.scale,5*MainWindow.scale))));
		
		
		
		/* Calculate the best width in this design */
		
		tri.setFont(res_text);
		ArrayList<Double> widths= new ArrayList<Double>();
		if(len>0) {
			tri=new Text(res_ex_eng_1.getText());
			widths.add(tri.layoutBoundsProperty().get().getWidth());
			tri=new Text(res_ex_esp_1.getText());
			widths.add(tri.layoutBoundsProperty().get().getWidth());
			tri=new Text(res_esp_1.getText());
			widths.add(20*MainWindow.scale+tr1.layoutBoundsProperty().get().getWidth()+tri.layoutBoundsProperty().get().getWidth());
			if(len>1) {
				tri=new Text(res_ex_eng_2.getText());
				widths.add(tri.layoutBoundsProperty().get().getWidth());
				tri=new Text(res_ex_esp_2.getText());
				widths.add(tri.layoutBoundsProperty().get().getWidth());
				tri=new Text(res_esp_2.getText());
				widths.add(20*MainWindow.scale+tr1.layoutBoundsProperty().get().getWidth()+tri.layoutBoundsProperty().get().getWidth());
				
				if(len>2) {
					tri=new Text(res_ex_eng_3.getText());
					widths.add(tri.layoutBoundsProperty().get().getWidth());
					tri=new Text(res_ex_esp_3.getText());
					widths.add(tri.layoutBoundsProperty().get().getWidth());
					tri=new Text(res_esp_3.getText());
					widths.add(20*MainWindow.scale+tr1.layoutBoundsProperty().get().getWidth()+tri.layoutBoundsProperty().get().getWidth());
					
				}
			}
		}
		// Find the minimum height that we will get
		int minlines=0;
		for(Double d:widths) {
			minlines+=Math.ceil((d.doubleValue()+45*MainWindow.scale)/MAXWID*MainWindow.scale);
		}
		int lines=minlines;
		double pw=MAXWID*MainWindow.scale;
		double cw=MAXWID*MainWindow.scale;
		while(lines==minlines&&cw>MINWID) {
			lines=0;
			for(Double d:widths) {
				lines+=Math.ceil((d.doubleValue()+45*MainWindow.scale)/cw);
			}
			pw=cw;
			cw-=5;
			//System.out.println(lines+"; w:"+cw);
		}
		tri.setText("Hello world");
		double heig=minlines*tri.layoutBoundsProperty().get().getHeight();
		tri.setFont(res_extext);
		heig+=tri.layoutBoundsProperty().get().getHeight()+10*MainWindow.scale;
		tri.setFont(res_head);
		heig+=len*(30*MainWindow.scale+tri.layoutBoundsProperty().get().getHeight());
		if(!f.getUse().equals("")) {
			tri.setFont(res_head);
			heig+=tri.layoutBoundsProperty().get().getHeight();
			heig+=extrause;
		}
		//Top and bottom margin
		heig+=120*MainWindow.scale;
		
		if(maxwidth>pw) pw=Math.min(maxwidth,MAXWID*MainWindow.scale);
		
		Scene scene = new Scene(grid,pw,heig);
		expWidth=pw;
		expHeight=heig;
		grid.requestFocus();
		grid.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				//System.out.println(grid.getWidth()+" "+grid.getHeight());
				if(event.getCode()==KeyCode.ENTER) {
					st.close();
				}
				
			}
			
		});
		return scene;
	}
}
