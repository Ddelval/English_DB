package fX;


import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.math.*;
public class MainWindow extends Application{

public static Stage primaryStage;
private Font tit, head, text, extext,extext2;
private HBox menu;
private Button b_intro, b_exam, b_consul, b_options;
private final double M_w=400;
private final double M_h=110;
//Introduction scene
private TextField eng, engExamp1,engExamp2, engExamp3, espExamp1, espExamp2, espExamp3, esp1, esp2, esp3, pronun;
private TextArea indications;
private Button add;
TextField ex_eng;
private final double I_w=550;
private final double I_h=770;
// Exam scene
private Label ex_pronun;
private TextArea  ex_examp;
private TextField ex_spa;
private Ficha ex_f;
private Example ex_e;
private int ex_index;
private final double E_w=500;
private final double E_h=220;
//Result
private Font res_tit, res_head, res_text, res_extext;
private Label res_eng, res_pronun;
private Label res_ex_eng_1, res_ex_eng_2, res_ex_eng_3, res_ex_esp_1, res_ex_esp_2, res_ex_esp_3;
private Label res_esp_1, res_esp_2, res_esp_3;
private TextArea use;
private VBox v;
//Consult
private TableView<ObservableFicha> table;
private ObservableList <ObservableFicha> data;
private Button eliminate, edit;
//Update
private TextField up_eng, up_engExamp1,up_engExamp2, up_engExamp3, up_espExamp1, up_espExamp2, up_espExamp3, up_esp1, up_esp2, up_esp3, up_pronun;
private TextArea up_indications;
private Button update;
private CheckBox up_known_1, up_known_2, up_known_3;
private Ficha up_f;
//SQLConsole
private TextField sql_query;
private CheckBox sql_w_result;
private Button execute;
private TextArea sql_result;
//Adjust
private Button reset;
private final double A_w=500;
private final double A_h=400;
//Backup	
private Button b_backup,b_restore;
private TextField tf_backup, tf_restore;
//Exceptions
public static String exceptions="";
	@Override
	public void start(Stage primaryStage) throws Exception {
		/***  Fonts definition  ***/
		tit = Font.font("Helvetica",FontWeight.BOLD,20);
		text = Font.font("Helvetica",FontWeight.LIGHT, 14);
		head = Font.font("Helvetica Neue",FontWeight.MEDIUM, 15);
		extext= Font.font("Helvetica Neue", FontWeight.BOLD,17);
		extext2= Font.font("Helvetica Neue", FontWeight.NORMAL,17);
		
		/***  Initialization ***/
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("Vocabulary Archive");
		this.primaryStage.setScene(mainMenu());
		this.primaryStage.show();
		detail(Facade.getRndFicha(),100,100);
	}
	private Scene mainMenu () {
		Text title;
		title = new Text("Main menu");
		title.setFont(tit);
		title.setFontSmoothingType(FontSmoothingType.LCD);
		title.setTextAlignment(TextAlignment.CENTER);
		b_intro = new Button("Add word");
		b_intro.setFont(text);
		b_intro.setOnAction((ActionEvent e)->{
			double width=primaryStage.getWidth();
			double height = primaryStage.getHeight();
			primaryStage.setScene(introduction(width,height));
			//setPositionOnScreen(width,height);
			eng.requestFocus();
		});
		b_exam = new Button("Test");
		b_exam.setFont(text);
		b_exam.setOnAction((ActionEvent e)->{
			if(Facade.checkAvailability()) {
				double width=primaryStage.getWidth();
				double height = primaryStage.getHeight();
				primaryStage.setScene(exam(width,height));
				System.out.println(primaryStage.getWidth());
				System.out.println(primaryStage.getHeight());
				ex_spa.requestFocus();
			}
			else {
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
			}
			
		});
		b_consul = new Button ("Database");
		b_consul.setFont(text);
		b_consul.setOnAction((ActionEvent e)->{
			double width=primaryStage.getWidth();
			double height = primaryStage.getHeight();
			primaryStage.setScene(consult(width,height));
			//setPositionOnScreen(width,height);
		});
		b_options = new Button ("Settings");
		b_options.setFont(text);
		b_options.setOnAction( (ActionEvent e)->{
			double width=primaryStage.getWidth();
			double height = primaryStage.getHeight();
			primaryStage.setScene(options(width,height));
			//setPositionOnScreen(width,height);
		});
		
		HBox.setMargin(b_intro, new Insets(0,0,0,10));
		HBox.setMargin(b_exam, new Insets(0,0,0,10));
		HBox.setMargin(b_consul, new Insets(0,0,0,10));
		HBox.setMargin(b_options, new Insets(0,0,0,10));
		menu = new HBox();
		menu.setAlignment(Pos.TOP_CENTER);
		menu.getChildren().addAll(b_intro,b_exam,b_consul,b_options);
		VBox v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);
		v.setPadding(new Insets(10,10,10,10));
		v.getChildren().addAll(title,menu);
		VBox.setMargin(menu, new Insets(15,0,0,0));
		Scene scene = new Scene(v,M_w,M_h);
		return scene;
	}
	private Scene introduction(double width,double height) {
		
		Text tit = new Text("Addition of words");
		tit.setFont(this.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		Text ing = new Text("English:");
		ing.setFont(this.text);
		ing.setFontSmoothingType(FontSmoothingType.LCD);
		Text ex1 = new Text("Example 1");
		ex1.setFont(head);
		ex1.setFontSmoothingType(FontSmoothingType.LCD);
		ex1.setTextAlignment(TextAlignment.CENTER);
		Text ex2 = new Text("Example 2");
		ex2.setFont(head);
		ex2.setFontSmoothingType(FontSmoothingType.LCD);
		ex2.setTextAlignment(TextAlignment.CENTER);
		Text ex3 = new Text("Example 3");
		ex3.setFont(head);
		ex3.setFontSmoothingType(FontSmoothingType.LCD);
		ex3.setTextAlignment(TextAlignment.CENTER);
		Text trad1 = new Text("Translation:");
		trad1.setFont(this.text);
		trad1.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad2 = new Text("Translation:");
		trad2.setFont(this.text);
		trad2.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad3 = new Text("Translation:");
		trad3.setFont(this.text);
		trad3.setFontSmoothingType(FontSmoothingType.LCD);
		Text indicate = new Text("Use notes");
		indicate.setFont(this.text);
		indicate.setFontSmoothingType(FontSmoothingType.LCD);
		Text pronunciation = new Text("Pronunciation");
		pronunciation.setFont(this.text);
		pronunciation.setFontSmoothingType(FontSmoothingType.LCD);
		
		
		eng = new TextField();
		eng.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if(Facade.selectSome(Facade.autotrim(eng.getText())).size()!=0) {
					mssgWindow("This word is already in the system.",primaryStage.getWidth(),primaryStage.getHeight());
				}
				else {
					mssgWindow("This word is not in the system.",primaryStage.getWidth(),primaryStage.getHeight());
				}
			}
			
		});
		engExamp1 = new TextField();
		engExamp2 = new TextField();
		engExamp3 = new TextField();
		espExamp1 = new TextField();
		espExamp2 = new TextField();
		espExamp3 = new TextField();
		indications = new TextArea();
		indications.maxWidthProperty().bind(primaryStage.widthProperty());
		indications.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode()==KeyCode.TAB) {
					pronun.requestFocus();
					event.consume();
					}
					else {
						
					}
					
				}
				
			});
			
		esp1 = new TextField();
		esp2 = new TextField();
		esp3 = new TextField();
		pronun = new TextField();
		add= new Button("Add");
		add.maxWidthProperty().bind(primaryStage.widthProperty());
		add.setFont(this.text);
		add.setOnAction((ActionEvent e)->{
			if(Facade.selectSome(Facade.autotrim(eng.getText())).size()!=0) {
				confirmWindowintro("This word is already in the system. Are you sure you wan to include it?",primaryStage.getWidth(),primaryStage.getHeight());
			}
			else {
				add();
			}
			
		});
		
		GridPane.setFillWidth(tit,true);
		GridPane.setFillWidth(menu, true);
		GridPane.setFillWidth(eng, true);
		GridPane.setFillWidth(ex1,true);
		GridPane.setFillWidth(ex2,true);
		GridPane.setFillWidth(ex3,true);
		GridPane.setFillWidth(engExamp1, true);
		GridPane.setFillWidth(engExamp2, true);
		GridPane.setFillWidth(engExamp3, true);
		GridPane.setFillWidth(espExamp1, true);
		GridPane.setFillWidth(espExamp2, true);
		GridPane.setFillWidth(espExamp3, true);
		GridPane.setFillWidth(esp1, true);
		GridPane.setFillWidth(esp2, true);
		GridPane.setFillWidth(esp3, true);
		GridPane.setFillWidth(pronun, true);
		GridPane.setFillWidth(pronunciation,true);
		GridPane.setFillWidth(add,true);
		GridPane.setFillWidth(indications, true);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(menu, 0, 0, 2, 1);
		grid.add(tit, 0, 1, 2, 1);
		grid.add(ing, 0, 2);
		grid.add(eng, 1, 2);
		grid.add(ex1, 0, 3, 2, 1);
		grid.add(engExamp1, 0, 4, 2, 1);
		grid.add(espExamp1, 0, 5, 2, 1);
		grid.add(trad1, 0, 6);
		grid.add(esp1, 1, 6);
		grid.add(ex2, 0, 7, 2, 1);
		grid.add(engExamp2, 0, 8, 2, 1);
		grid.add(espExamp2, 0, 9, 2, 1);
		grid.add(trad2, 0, 10);
		grid.add(esp2, 1, 10);
		grid.add(ex3, 0, 11, 2, 1);
		grid.add(engExamp3, 0, 12, 2, 1);
		grid.add(espExamp3, 0, 13, 2, 1);
		grid.add(trad3, 0, 14);
		grid.add(esp3, 1, 14);
		grid.add(indicate, 0, 15,2,1);
		grid.add(indications, 0, 16, 2, 1);
		grid.add(pronunciation, 0, 17, 2, 1);
		grid.add(pronun, 0, 18, 2, 1);
		grid.add(add, 0, 19, 2, 1);
		Scene scene = new Scene(grid,I_w,I_h);
		primaryStage.setX(primaryStage.getX()-(I_w-width)/2);
		primaryStage.setY(primaryStage.getY()-(I_h+22-height)/2);
		return scene;
		
	}
	private Scene exam (double width,double height) {
		ex_f = Facade.getRndFicha();
		mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		Vector <Example> v = ex_f.getExampVec();
		//Reduce the number of examples to 1
		if(v.size()>1) {
			Random r = new Random();
			ex_index = r.nextInt(v.size()-1)+1;
			while(v.elementAt(ex_index).getKnown()) {
				ex_index = r.nextInt(v.size()-1);
			}
			ex_e=v.elementAt(ex_index);
		}
		else {
			ex_index=0;
			ex_e=v.elementAt(0);
		}
		ex_eng = new TextField(ex_f.getEnglish());
		ex_eng.setAlignment(Pos.CENTER_LEFT);
		ex_eng.setFont(extext);
		ex_eng.setPadding(new Insets(0,0,0,10));
		ex_eng.setEditable(false);
		ex_eng.setBackground(Background.EMPTY);
		ex_eng.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30));
		ex_pronun = new Label(ex_f.getPronunciation());
		ex_pronun.setAlignment(Pos.TOP_RIGHT);
		ex_pronun.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30));
		ex_pronun.setFont(extext2);
		ex_examp= new TextArea(ex_e.getEng_example());
		ex_examp.setWrapText(true);
		ex_examp.setEditable(false);
		ex_examp.setFont(extext);
		ex_examp.setBackground(null);
		ex_examp.setPadding(new Insets(0,0,0,0));
		ex_examp.setCenterShape(true);
		ex_spa = new TextField();
		ex_spa.setOnAction((ActionEvent e)->{
			if(ex_e.getTranslation().toUpperCase().equals(Facade.autotrim(ex_spa.getText().toUpperCase()))) {
				double d_width = primaryStage.getWidth();
				double d_height=primaryStage.getHeight();
				primaryStage.setScene(result(1,d_width,d_height));
				//setPositionOnScreen(width, height);
				this.v.requestFocus();
				ex_f.setExKnown(ex_index);
				Facade.updateFicha(ex_f);
				ex_f.getExampVec().elementAt(ex_index).setKnown(true);
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
			}
			else {
				double d_width=primaryStage.getWidth();
				double d_height = primaryStage.getHeight();primaryStage.setScene(result(0,d_width,d_height));
				//setPositionOnScreen(width,height);
				this.v.requestFocus();
			}
		});
		GridPane.setFillWidth(ex_eng, true);
		GridPane.setFillWidth(ex_pronun, true);
		GridPane.setFillWidth(ex_examp, false);
		GridPane.setFillWidth(ex_spa, true);
		GridPane.setMargin(ex_pronun, new Insets(0,0,0,10));
		GridPane.setMargin(ex_eng, new Insets(0,10,0,0));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(menu, 0, 0, 2, 1);
		grid.add(ex_eng, 0, 2);
		grid.add(ex_pronun, 1, 2);
		grid.add(ex_examp, 0, 3, 2, 1);
		grid.add(ex_spa, 0, 4, 2, 1);
		ex_spa.requestFocus();
		Scene scene = new Scene(grid,E_w,E_h);
		scene.getStylesheets().add("/Data.css");
		primaryStage.setX(primaryStage.getX()-(E_w-width)/2);
		primaryStage.setY(primaryStage.getY()-(E_h+22-height)/2);
		
		
		return scene;
		
	}
	/**
	 * 
	 * @param i A value of 0 equals failure whereas a 1 equals success
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Scene result(int i,double width,double height) {
		Button pron_eng,pron_ex1, pron_ex2,pron_ex3;
		Text tit,ex2=null,ex1=null,ex3=null, use = null;
		res_tit = Font.font("Helvetica",FontWeight.BOLD,20);
		res_text = Font.font("Helvetica",FontWeight.LIGHT, 14);
		res_head = Font.font("Helvetica Neue",FontWeight.MEDIUM, 18);
		res_extext= Font.font("Helvetica Neue", FontWeight.BOLD,17);
		if(i==1) {
			tit= new Text("Correct:");
		}
		else {
			tit = new Text("The answer does not seem correct");
		}
		tit.setFont(res_tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		int len = ex_f.getExampVec().size();
		pron_eng= new Button("Say");
		res_eng = new Label(ex_f.getEnglish());
		res_eng.setAlignment(Pos.TOP_LEFT);
		res_eng.setFont(res_extext);
		res_eng.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30));
		
		res_eng.setOnMouseClicked(new EventHandler(){
			@Override
			public void handle(Event event) {
				Facade.Speak(res_eng.getText());
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				
			}
		});
		
		
		res_pronun = new Label(ex_f.getPronunciation());
		res_pronun.setAlignment(Pos.TOP_RIGHT);
		res_pronun.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30));
		res_pronun.setFont(res_extext);
		
		res_pronun.setOnMouseClicked(new EventHandler(){
			@Override
			public void handle(Event event) {
				Facade.Speak(res_eng.getText());
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				
			}
		});
		if(len>0) {
			res_ex_eng_1 = new Label(ex_f.getExample(0).getEng_example());
			res_ex_eng_1.setAlignment(Pos.TOP_CENTER);
			res_ex_eng_1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			res_ex_eng_1.setFont(res_text);
			res_ex_eng_1.setWrapText(true);
			res_ex_eng_1.setOnMouseClicked(new EventHandler(){
				@Override
				public void handle(Event event) {
					Facade.Speak(res_ex_eng_1.getText());
					mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
					
				}
			});
			res_ex_esp_1 = new Label(ex_f.getExample(0).getEsp_example());
			res_ex_esp_1.setAlignment(Pos.TOP_CENTER);
			res_ex_esp_1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			res_ex_esp_1.setFont(res_text);
			res_ex_esp_1.setWrapText(true);
			res_esp_1 = new Label("Translation: "+ex_f.getExample(0).getTranslation());
			res_esp_1.setAlignment(Pos.TOP_CENTER);
			res_esp_1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			res_esp_1.setFont(res_text);
			ex1 = new Text("Example 1: ");
			ex1.setFont(this.res_head);
			//ex1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			ex1.setTextAlignment(TextAlignment.LEFT);
			
			GridPane.setFillWidth(res_ex_eng_1, true);
			GridPane.setFillWidth(res_ex_esp_1, true);
			GridPane.setFillWidth(res_esp_1, true);
			GridPane.setFillWidth(ex1, true);
			if(len>1) {
				res_ex_eng_2 = new Label(ex_f.getExample(1).getEng_example());
				res_ex_eng_2.setAlignment(Pos.TOP_CENTER);
				res_ex_eng_2.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
				res_ex_eng_2.setFont(res_text);
				res_ex_eng_2.setWrapText(true);
				res_ex_eng_2.setOnMouseClicked(new EventHandler(){
					@Override
					public void handle(Event event) {
						Facade.Speak(res_ex_eng_2.getText());
						mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
						
					}
				});
				res_ex_esp_2 = new Label(ex_f.getExample(1).getEsp_example());
				res_ex_esp_2.setAlignment(Pos.TOP_CENTER);
				res_ex_esp_2.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
				res_ex_esp_2.setFont(res_text);
				res_ex_esp_2.setWrapText(true);
				res_esp_2 = new Label("Translation: "+ex_f.getExample(1).getTranslation());
				res_esp_2.setAlignment(Pos.CENTER);
				res_esp_2.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
				res_esp_2.setFont(res_text);
				ex2 = new Text("Example 2: ");
				ex2.setFont(this.res_head);
				ex2.setFontSmoothingType(FontSmoothingType.LCD);
				ex2.setTextAlignment(TextAlignment.LEFT);
				
				GridPane.setFillWidth(res_ex_eng_2, true);
				GridPane.setFillWidth(res_ex_esp_2, true);
				GridPane.setFillWidth(res_esp_2, true);
				GridPane.setFillWidth(ex2, true);
				if(len>2) {
					res_ex_eng_3 = new Label(ex_f.getExample(2).getEng_example());
					res_ex_eng_3.setAlignment(Pos.TOP_CENTER);
					res_ex_eng_3.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
					res_ex_eng_3.setFont(res_text);
					res_ex_eng_3.setWrapText(true);
					res_ex_eng_3.setOnMouseClicked(new EventHandler(){
						@Override
						public void handle(Event event) {
							Facade.Speak(res_ex_eng_3.getText());
							mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
							
						}
					});
					res_ex_esp_3 = new Label(ex_f.getExample(2).getEsp_example());
					res_ex_esp_3.setAlignment(Pos.TOP_CENTER);
					res_ex_esp_3.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
					res_ex_esp_3.setFont(res_text);
					res_ex_esp_3.setWrapText(true);
					res_esp_3 = new Label("Translation: "+ex_f.getExample(2).getTranslation());
					res_esp_3.setAlignment(Pos.CENTER);
					res_esp_3.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
					res_esp_3.setFont(res_text);
					
					ex3 = new Text("Example 3: ");
					ex3.setFont(this.res_head);
					ex3.setFontSmoothingType(FontSmoothingType.LCD);
					ex3.setTextAlignment(TextAlignment.LEFT);
					
					GridPane.setFillWidth(res_ex_eng_3, true);
					GridPane.setFillWidth(res_ex_esp_3, true);
					GridPane.setFillWidth(res_esp_3, true);
					GridPane.setFillWidth(ex3, true);
					
				}
			}
		}
		if(!ex_f.getUse().equals("")) {
			use = new Text("Use notes: ");
			use.setFont(this.res_head);
			use.setFontSmoothingType(FontSmoothingType.LCD);
			use.setTextAlignment(TextAlignment.CENTER);
			this.use=new TextArea(ex_f.getUse());
			this.use.setEditable(false);
		}
		GridPane.setFillWidth(res_eng, true);
		GridPane.setFillWidth(res_pronun, true);
		v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);
		v.setFillWidth(true);
		v.getChildren().addAll(menu,tit);
		VBox.setMargin(tit, new Insets(10,0,0,0));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add     (res_eng, 0, 0);
		grid.add  (res_pronun, 1, 0);
		if(len>0) {
			grid.add         (ex1, 0, 1, 2, 1);
			grid.add(res_ex_eng_1, 0, 2, 2, 1);
			grid.add(res_ex_esp_1, 0, 3, 2, 1);
			grid.add   (res_esp_1, 0, 4,2,1);
			if(len>1) {
				grid.add         (ex2, 0, 5, 2, 1);
				grid.add(res_ex_eng_2, 0, 6, 2, 1);
				grid.add(res_ex_esp_2, 0, 7, 2, 1);
				grid.add   (res_esp_2, 0, 8,2, 1);
				if(len>2) {
					grid.add         (ex3, 0, 9, 2, 1);
					grid.add(res_ex_eng_3, 0, 10, 2, 1);
					grid.add(res_ex_esp_3, 0, 11, 2, 1);
					grid.add   (res_esp_3, 0, 12, 2, 1);
				}
			}
		}
		if(!ex_f.getUse().equals("")) {
			//The switch is only for the row count
			switch(len) {
				case 1:{
					grid.add(use, 0, 5, 2, 1);
					grid.add(this.use, 0, 6, 2, 1);
					break;
				}
				case 2:{
					grid.add(use, 0, 9, 2, 1);
					grid.add(this.use, 0, 10, 2, 1);
					break;
				}
				case 3:{
					grid.add(use, 0, 13, 2, 1);
					grid.add(this.use, 0, 14, 2, 1);
					break;
				}
			}
		}
		grid.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT, new Insets(5,5,5,5))));
		
		v.getChildren().add(grid);
		VBox.setMargin(grid, new Insets(20,0,20,0));
		
		if(i==0) {
			
			Text res = new Text("Your answer has been: \""+ex_spa.getText()+"\". It was expected: "+ex_e.getTranslation());
			res.setFont(text);
			res.setFontSmoothingType(FontSmoothingType.LCD);
			res.setTextAlignment(TextAlignment.LEFT);
			GridPane.setFillWidth(res, true);
			v.getChildren().add(res);
			
			Button but =new Button("Mark as correct");
			but.maxWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			v.getChildren().add(but);
			VBox.setMargin(but, new Insets(10,0,15,0));
			
		}
		int height1 =345;
		switch(len) {
			case 2:{
				height1+=120;
				break;
			}
			case 3:{
				height1+=240;
				break;
			}
		}
		if(!ex_f.getUse().equals("")){
			height1+=120;
			
		}
		v.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode()==KeyCode.ENTER) {
					if(Facade.checkAvailability()) {
						double width=primaryStage.getWidth();
						double height = primaryStage.getHeight();
						primaryStage.setScene(exam(width,height));
						ex_spa.requestFocus();
					}
					else {
						
					}
					mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				}
				
			}
			
		});
		Scene scene = new Scene(v,500,height1);
		primaryStage.setX(primaryStage.getX()-(500-width)/2);
		primaryStage.setY(primaryStage.getY()-(height1+20-height)/2);
		return scene;
		
	}
	private Scene consult(double width,double height) {
		Text tit =new Text("Database");
		tit.setFont(this.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		TextField tfs= new TextField();
		tfs.setFont(this.text);
		tfs.setPromptText("Search");
		tfs.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(!tfs.getText().equals("")) {
					//data=Facade.selectAll();
					ObservableList <ObservableFicha> data2=FXCollections.observableList(new ArrayList<ObservableFicha>());
					ObservableList <ObservableFicha> data3=FXCollections.observableList(new ArrayList<ObservableFicha>());
					for(int i=0;i<data.size();i++) {
						if(data.get(i).f.getEnglish().toUpperCase().startsWith(tfs.getText().toUpperCase())) {
							data2.add(data.get(i));
						}
						else if(data.get(i).f.getEnglish().toUpperCase().contains(tfs.getText().toUpperCase())) {
							data3.add(data.get(i));
						}
					}
					data2.addAll(data3);
					table.setItems(data2);
				}
				else {
					table.setItems(data);
				}
				
			}
			
		});
		tfs.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!tfs.getText().equals("")) {
					//data=Facade.selectAll();
					ObservableList <ObservableFicha> data2=FXCollections.observableList(new ArrayList<ObservableFicha>());
					ObservableList <ObservableFicha> data3=FXCollections.observableList(new ArrayList<ObservableFicha>());
					for(int i=0;i<data.size();i++) {
						if(data.get(i).f.getEnglish().toUpperCase().startsWith(tfs.getText().toUpperCase())) {
							data2.add(data.get(i));
						}
						else if(data.get(i).f.getEnglish().toUpperCase().contains(tfs.getText().toUpperCase())) {
							data3.add(data.get(i));
						}
					}
					table.setItems(data2);
				}
				else {
					table.setItems(data);
				}
				
				
			}
			
		});
		table = new TableView<ObservableFicha> ();
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.getSelectionModel().setCellSelectionEnabled(false);
		data = Facade.selectAll();
		mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		TableColumn<ObservableFicha, Integer> key = new TableColumn<ObservableFicha, Integer>("Index");
		key.setMinWidth(30);
		key.setCellValueFactory(new PropertyValueFactory<ObservableFicha, Integer> ("DBkey"));
		TableColumn<ObservableFicha, String> english = new TableColumn<ObservableFicha, String>("English");
		english.setMinWidth(30);
		english.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("english"));
		TableColumn<ObservableFicha, String> ex1 = new TableColumn<ObservableFicha, String>("Ex.1");
		ex1.setMinWidth(30);
		ex1.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("ex1"));
		TableColumn<ObservableFicha, String> ex2 = new TableColumn<ObservableFicha, String>("Ex.2");
		ex2.setMinWidth(30);
		ex2.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("ex2"));
		TableColumn<ObservableFicha, String> ex3 = new TableColumn<ObservableFicha, String>("Ex.3");
		ex3.setMinWidth(30);
		ex3.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("ex3"));
		TableColumn<ObservableFicha, String> pronunciation = new TableColumn<ObservableFicha, String>("Pronunciation");
		pronunciation.setMinWidth(30);
		pronunciation.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("pronunciation"));
		TableColumn<ObservableFicha, String> use = new TableColumn<ObservableFicha, String>("Use");
		use.setMinWidth(30);
		use.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("use"));
		TableColumn<ObservableFicha, String> known = new TableColumn<ObservableFicha, String>("Known");
		known.setMinWidth(100);
		known.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("known"));
		table.setItems(data);
		table.getColumns().addAll(key,english, ex1, ex2, ex3, pronunciation, use,known);
		table.minHeightProperty().bind(primaryStage.heightProperty().subtract(150));
		table.getSortOrder().add(english);
		eliminate = new Button("Delete");
		eliminate.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(20));
		eliminate.setOnAction((ActionEvent e)->{
			if(table.getSelectionModel().getSelectedIndex()!=-1) {
				ObservableList<Integer> i = table.getSelectionModel().getSelectedIndices();
				if(i.size()>1) {
					//There are several rows selected
					Vector<ObservableFicha> v = new Vector<ObservableFicha>();
					for(int j=0;j<i.size();j++) {
						v.add(table.getSelectionModel().getSelectedItems().get(j));
					}
					
					confirmWindow("¿Desea eliminar "+i.size()+" filas?", v,primaryStage.getWidth(),primaryStage.getHeight());
					data=Facade.selectAll();
					table.setItems(data);
					table.refresh();
					mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				}
				else {
					//There is only one row selected
					confirmWindow("¿Desea eliminar "+(data.get(table.getSelectionModel().getSelectedIndex()).getEnglish())+"?",table.getSelectionModel().getSelectedItem().getDBkey(),primaryStage.getWidth(),primaryStage.getHeight() );
					data=Facade.selectAll();
					table.setItems(data);
					table.refresh();
					mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				}
			}
			
		});
		edit = new Button("Edit");
		edit.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(20));
		edit.setOnAction((ActionEvent e)->{
			if(table.getSelectionModel().getSelectedIndex()!=-1) {
				if( table.getSelectionModel().getSelectedIndices().size()<2) {
					//There is only one row selected
					editWindow(new Ficha(table.getSelectionModel().getSelectedItem()),primaryStage.getWidth(),primaryStage.getHeight());
					
					
				}
				else {
					mssgWindow("Select only one one row",primaryStage.getWidth(),primaryStage.getHeight());
				}
			}
			else {
				mssgWindow("Select a row", primaryStage.getWidth(),primaryStage.getHeight());
			}
		});
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(menu, 0, 0, 2, 1);
		grid.add(tit, 0, 1, 1, 1);
		grid.add(tfs, 1, 1,1 ,1);
		grid.add(table, 0, 2, 2, 1);
		grid.add(eliminate, 0, 3);
		grid.add(edit, 1, 3);
		Scene scene = new Scene(grid,800,600);
		primaryStage.setX(primaryStage.getX()-(800-width)/2);
		primaryStage.setY(primaryStage.getY()-(622-height)/2);
		return scene;
		
	}
	private Scene options(double width,double height) {
		Font up_head = head = Font.font("Helvetica Neue",FontWeight.MEDIUM, 17);
		Text tit = new Text("Settings");
		tit.setFont(this.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.LEFT);
		
		//SQL console
		
		
		sql_query= new TextField();
		sql_query.minWidthProperty().bind(primaryStage.widthProperty().subtract(150));
		sql_query.setPromptText("Enter the command");
		sql_w_result= new CheckBox("Result");
		sql_w_result.setFont(this.text);
		execute = new Button("Execute");
		execute.setFont(this.text);
		execute.setOnAction( (ActionEvent e)->{
			System.out.println(sql_query.getText());
			System.out.println(Facade.execute(sql_query.getText(), sql_w_result.isSelected()));
			sql_result.setText(Facade.execute(sql_query.getText(), sql_w_result.isSelected()));
		});
		sql_result = new TextArea("");
		sql_result.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
		sql_result.minHeightProperty().bind(primaryStage.heightProperty().subtract(250));
		GridPane.setFillWidth(menu, true);
		GridPane.setFillWidth(sql_query, true);
		GridPane.setFillWidth(execute, true);
		GridPane.setFillWidth(sql_result, true);
		TabPane tp = new TabPane();
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add   (sql_query, 0, 0);
		grid.add(sql_w_result, 1, 0);
		grid.add     (execute, 0, 1, 2, 1);
		grid.add  (sql_result, 0, 2, 2, 1);
		Tab consol = new Tab("SQL console");
		consol.setClosable(false);
		consol.setContent(grid);
		
		//Settings
		Tab adjust = new Tab ("Settings");
		reset = new Button ("Mark all as unknown");
		reset.setFont(text);
		reset.setOnAction((ActionEvent e)->{
			confirmWindow("Are you sure you want to make every register as unknown?", primaryStage.getWidth(),primaryStage.getHeight());
		});
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10,10,10,10));
		vbox.getChildren().add(reset);
		vbox.setAlignment(Pos.TOP_CENTER);
		adjust.setContent(vbox);
		adjust.setClosable(false);
		
		//Backup
		Tab backup = new Tab("Backup");
		Text back =new Text("Save backup");
		back.setFont(up_head);
		back.setFontSmoothingType(FontSmoothingType.LCD);
		Text rest =new Text("Restore from backup");
		rest.setFont(up_head);
		rest.setFontSmoothingType(FontSmoothingType.LCD);
		tf_backup = new TextField(Facade.getConfig("backupPath"));
		tf_restore = new TextField("");
		tf_restore.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(b_restore.isDisabled()) {
					b_restore.setDisable(false);
				}
				
			}
			
		});
		b_backup = new Button("Backup");
		b_backup.minWidthProperty().bind(primaryStage.widthProperty().subtract(20));
		b_backup.setOnAction((ActionEvent e)->{
			String s =Facade.basicfilter(tf_backup.getText());
			Facade.updateConfig("backupPath", s);
			Facade.backup(s);
			mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		});
		b_restore = new Button ("Restore");
		b_restore.setDisable(true);
		b_restore.setOnAction((ActionEvent e)->{
			if(!tf_restore.getText().equals("")) {
				Facade.restore(tf_restore.getText());
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
			}
		});
		b_restore.minWidthProperty().bind(primaryStage.widthProperty().subtract(20));
		VBox vb2 = new VBox();
		HBox h1 = new HBox();
		h1.setAlignment(Pos.TOP_LEFT);
		h1.setPadding(new Insets(10,0,5,0));
		h1.getChildren().add(back);
		HBox h2 = new HBox();
		h2.setAlignment(Pos.TOP_CENTER);
		h2.setPadding(new Insets(5,0,5,0));
		h2.getChildren().add(tf_backup);
		HBox h3 = new HBox();
		h3.setAlignment(Pos.TOP_CENTER);
		h3.setPadding(new Insets(5,0,5,0));
		h3.getChildren().add(b_backup);
		HBox h4 = new HBox();
		h4.setAlignment(Pos.TOP_LEFT);
		h4.setPadding(new Insets(5,0,5,0));
		h4.getChildren().add(rest);
		HBox h5 = new HBox();
		h5.setAlignment(Pos.TOP_CENTER);
		h5.setPadding(new Insets(5,0,5,0));
		h5.getChildren().add(tf_restore);
		HBox h6 = new HBox();
		h6.setAlignment(Pos.TOP_CENTER);
		h6.setPadding(new Insets(5,0,5,0));
		h6.getChildren().add(b_restore);
		HBox.setHgrow(tf_backup, Priority.ALWAYS);
		HBox.setHgrow(b_backup, Priority.ALWAYS);
		HBox.setHgrow(tf_restore, Priority.ALWAYS);
		HBox.setHgrow(b_backup, Priority.ALWAYS);
		vb2.setAlignment(Pos.TOP_CENTER);
		vb2.setPadding(new Insets(10,10,10,10));
		vb2.getChildren().addAll(h1, h2, h3, h4, h5, h6);
		backup.setContent(vb2);
		backup.setClosable(false);
		
		//Voice
		Tab voice = new Tab ("Voice");
		Label la = new Label("Select the voice:");
		ComboBox<String> cb = new ComboBox<String>();
		cb.getItems().addAll("Daniel (GB)","Alex (US)","Fred (US)", "Victoria (US)","Fiona (Scot)", "Veena (IN)", "Karen (AU)", "Moira (IE)", 
				"Tessa (ZA)");
		String s=Facade.getConfig("voice");
		mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		switch(s) {
			case "Daniel": cb.getSelectionModel().select(cb.getItems().indexOf("Daniel (GB)")); break;
			case "Alex": cb.getSelectionModel().select(cb.getItems().indexOf("Alex (US)")); break;
			case "Fred": cb.getSelectionModel().select(cb.getItems().indexOf("Fred (US)")); break;
			case "Victoria": cb.getSelectionModel().select(cb.getItems().indexOf("Victoria (US)")); break;
			case "Fiona": cb.getSelectionModel().select(cb.getItems().indexOf("Fiona (Scot)")); break;
			case "Veena": cb.getSelectionModel().select(cb.getItems().indexOf("Veena (IN)")); break;
			case "Tessa": cb.getSelectionModel().select(cb.getItems().indexOf("Tessa (ZA)")); break;
		}
		
		cb.setOnAction((ActionEvent e)->{
			Facade.updateConfig("voice", cb.getSelectionModel().getSelectedItem().substring(0, cb.getSelectionModel().getSelectedItem().indexOf('(')-1));
			mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		});
		VBox va = new VBox();
		va.setPadding(new Insets(10,10,10,10));
		va.getChildren().addAll(la,cb);
		va.setAlignment(Pos.TOP_CENTER);
		voice.setContent(va);
		voice.setClosable(false);
		
		tp.getTabs().addAll(consol,adjust,backup,voice);
		VBox vb= new VBox();
		vb.setAlignment(Pos.TOP_CENTER);
		VBox.setVgrow(tp, Priority.ALWAYS);
		VBox.setVgrow(menu, Priority.NEVER);
		VBox.setMargin(menu, new Insets(10,10,10,10));
		HBox h = new HBox(tit);
		h.setPadding(new Insets(0,0,10,10));
		h.setAlignment(Pos.CENTER_LEFT);
		vb.getChildren().addAll(menu, h,tp);
		Scene scene = new Scene(vb,A_w,A_h);
		primaryStage.setX(primaryStage.getX()-(A_w-width)/2);
		primaryStage.setY(primaryStage.getY()-(A_h+22-height)/2);
		return scene;
	}
	public void add() {
		if(!eng.getText().equals("")) {
			Ficha f = new Ficha(eng.getText(),pronun.getText(),indications.getText());
			if(!engExamp1.getText().equals("")) {
				f.addExample(new Example(engExamp1.getText(), espExamp1.getText(), esp1.getText()));
			}
			if(!engExamp2.getText().equals("")) {
				f.addExample(new Example(engExamp2.getText(), espExamp2.getText(), esp2.getText()));
			}
			if(!engExamp3.getText().equals("")) {
				f.addExample(new Example(engExamp3.getText(), espExamp3.getText(), esp3.getText()));
			}
			Facade.addDB(f);
			if(exceptions.equals("")) {
				eng.clear();
				engExamp1.clear();
				engExamp2.clear();
				engExamp3.clear();
				espExamp1.clear();
				espExamp2.clear();
				espExamp3.clear();
				esp1.clear();
				esp2.clear();
				esp3.clear();
				pronun.clear();
				indications.clear();
				eng.setPromptText("Enter successfully");
			}
			mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		}
	}
	/**
	 *
	 * @param width: The width of the previous component
	 * @param height: The height of the previous component
	 */
	/*private void setPositionOnScreen(double width, double height){
		primaryStage.setX(primaryStage.getX()-(primaryStage.getWidth()-width)/2);
		primaryStage.setY(primaryStage.getY()-(primaryStage.getHeight()-height)/2);
		
		
	}
	*/
	static public void mssgWindow(double width, double height) {
		if(!exceptions.equals("")) {
			Stage stage = new Stage();
			Label l = new Label(exceptions);
			l.setTextAlignment(TextAlignment.CENTER);
			l.minWidthProperty().bind(stage.widthProperty());
			l.setPadding(new Insets(10,10,10,10));
			l.setWrapText(true);
			Scene sc = new Scene(l,Math.max(Math.min(l.getPrefWidth()+30,800),300),l.getPrefHeight()+40);
			stage.setScene(sc);
			stage.setX(primaryStage.getX()-(Math.max(Math.min(l.getPrefWidth()+30,800),300)-width)/2);
			stage.setY(primaryStage.getY()-(l.getPrefHeight()+62-height)/2);
			stage.show();
			exceptions="";
		}
		
		
	}
	public void mssgWindow(String s, double width, double height) {
		
			Stage stage = new Stage();
			Label l = new Label(s);
			l.setTextAlignment(TextAlignment.CENTER);
			l.minWidthProperty().bind(stage.widthProperty());
			l.setPadding(new Insets(10,10,10,10));
			l.setWrapText(true);
			Scene sc = new Scene(l,Math.max(Math.min(l.getPrefWidth()+30,800),300),l.getPrefHeight()+40);
			stage.setScene(sc);
			stage.setX(primaryStage.getX()-(Math.max(Math.min(l.getPrefWidth()+30,800),300)-width)/2);
			stage.setY(primaryStage.getY()-(l.getPrefHeight()+62-height)/2);
			l.requestFocus();
			l.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					stage.close();
					
				}
				
			});
			stage.show();
			
		
	}
	public void confirmWindow(String s, Vector<ObservableFicha> list, double width, double height) {
		Stage stage = new Stage();
		Label l = new Label(s);
		l.setTextAlignment(TextAlignment.CENTER);
		l.minWidthProperty().bind(stage.widthProperty().subtract(30));
		l.setPadding(new Insets(10,10,10,10));
		l.setWrapText(true);
		Button b2 = new Button("Confirmar");
		b2.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(30));
		b2.setOnAction((ActionEvent e)->{
			Facade.eliminateFicha(list);
			stage.close();
			data=Facade.selectAll();
			table.setItems(data);
			table.refresh();
			mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		});
		Button b1 = new Button ("Cancelar");
		b1.setOnAction((ActionEvent e)->{
			stage.close();
		});
		b1.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(30));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(l, 0, 0, 2, 1);
		grid.add(b1, 0, 1);
		grid.add(b2, 1, 1);
		Scene sc = new Scene(grid,Math.max(Math.min(grid.getPrefWidth()+30,800),300),grid.getPrefHeight()+40);
		stage.setScene(sc);
		stage.setX(primaryStage.getX()-(Math.max(Math.min(grid.getPrefWidth()+30,800),300)-width)/2);
		stage.setY(primaryStage.getY()-(grid.getPrefHeight()+62-height)/2);
		stage.show();
		b1.requestFocus();
	}
	public void confirmWindow(String s, int list, double width, double height) {
		Stage stage = new Stage();
		Label l = new Label(s);
		l.setTextAlignment(TextAlignment.CENTER);
		l.minWidthProperty().bind(stage.widthProperty().subtract(30));
		l.setPadding(new Insets(10,10,10,10));
		l.setWrapText(true);
		Button b2 = new Button("Confirmar");
		b2.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(30));
		b2.setOnAction((ActionEvent e)->{
			Facade.eliminateFicha(list);
			stage.close();
			data=Facade.selectAll();
			table.setItems(data);
			table.refresh();
			mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		});
		Button b1 = new Button ("Cancelar");
		b1.setOnAction((ActionEvent e)->{
			stage.close();
		});
		b1.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(30));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(l, 0, 0, 2, 1);
		grid.add(b1, 0, 1);
		grid.add(b2, 1, 1);
		Scene sc = new Scene(grid,Math.max(Math.min(grid.getPrefWidth()+30,800),300),grid.getPrefHeight()+120);
		stage.setScene(sc);
		stage.setX(primaryStage.getX()-(Math.max(Math.min(grid.getPrefWidth()+30,800),300)-width)/2);
		stage.setY(primaryStage.getY()-(grid.getPrefHeight()+122-height)/2);;
		stage.show();
		b1.requestFocus();
	}
	public void confirmWindow(String s, double width, double height) {
		Stage stage = new Stage();
		Label l = new Label(s);
		l.setTextAlignment(TextAlignment.CENTER);
		l.minWidthProperty().bind(stage.widthProperty().subtract(30));
		l.setPadding(new Insets(10,10,10,10));
		l.setWrapText(true);
		Button b2 = new Button("Confirmar");
		b2.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(30));
		b2.setOnAction((ActionEvent e)->{
			Facade.updateAllKnown(false);
			stage.close();
			mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
		});
		Button b1 = new Button ("Cancelar");
		b1.setOnAction((ActionEvent e)->{
			stage.close();
		});
		b1.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(30));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(l, 0, 0, 2, 1);
		grid.add(b1, 0, 1);
		grid.add(b2, 1, 1);
		Scene sc = new Scene(grid,Math.max(Math.min(grid.getPrefWidth()+30,800),300),grid.getPrefHeight()+120);
		stage.setScene(sc);
		stage.setX(primaryStage.getX()-(Math.max(Math.min(grid.getPrefWidth()+30,800),300)-width)/2);
		stage.setY(primaryStage.getY()-(grid.getPrefHeight()+120-height)/2);;
		stage.show();
		b1.requestFocus();
	}
	public void confirmWindowintro(String s, double width, double height) {
		Stage stage = new Stage();
		Label l = new Label(s);
		l.setTextAlignment(TextAlignment.CENTER);
		l.minWidthProperty().bind(stage.widthProperty().subtract(30));
		l.setPadding(new Insets(10,10,10,10));
		l.setWrapText(true);
		Button b2 = new Button("Confirmar");
		b2.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(10));
		b2.setOnAction((ActionEvent e)->{
			add();
			stage.close();
			
		});
		Button b1 = new Button ("Cancelar");
		b1.setOnAction((ActionEvent e)->{
			stage.close();
		});
		b1.minWidthProperty().bind(stage.widthProperty().divide(2).subtract(10));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(l, 0, 0, 2, 1);
		grid.add(b1, 0, 1);
		grid.add(b2, 1, 1);
		Scene sc = new Scene(grid,Math.max(Math.min(grid.getPrefWidth()+30,900),300),grid.getPrefHeight()+120);
		stage.setScene(sc);
		stage.setX(primaryStage.getX()-(Math.max(Math.min(grid.getPrefWidth()+30,800),300)-width)/2);
		stage.setY(primaryStage.getY()-(grid.getPrefHeight()+120-height)/2);;
		stage.show();
		b1.requestFocus();
	}
	public void editWindow(Ficha f, double width, double height) {
		up_f=f;
		Stage stage = new Stage();
		Text tit = new Text("Update");
		tit.setFont(this.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		Text ing = new Text("English:");
		ing.setFont(this.text);
		ing.setFontSmoothingType(FontSmoothingType.LCD);
		Text ex1 = new Text("Example 1");
		ex1.setFont(head);
		ex1.setFontSmoothingType(FontSmoothingType.LCD);
		ex1.setTextAlignment(TextAlignment.CENTER);
		Text ex2 = new Text("Example 2");
		ex2.setFont(head);
		ex2.setFontSmoothingType(FontSmoothingType.LCD);
		ex2.setTextAlignment(TextAlignment.CENTER);
		Text ex3 = new Text("Example 3");
		ex3.setFont(head);
		ex3.setFontSmoothingType(FontSmoothingType.LCD);
		ex3.setTextAlignment(TextAlignment.CENTER);
		Text trad1 = new Text("Translation:");
		trad1.setFont(this.text);
		trad1.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad2 = new Text("Translation:");
		trad2.setFont(this.text);
		trad2.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad3 = new Text("Translation:");
		trad3.setFont(this.text);
		trad3.setFontSmoothingType(FontSmoothingType.LCD);
		Text indicate = new Text("Use instructions");
		indicate.setFont(this.text);
		indicate.setFontSmoothingType(FontSmoothingType.LCD);
		Text pronunciation = new Text("Pronunciation");
		pronunciation.setFont(this.text);
		pronunciation.setFontSmoothingType(FontSmoothingType.LCD);
		
		
		up_eng = new TextField(f.getEnglish());
		up_engExamp1 = new TextField(f.getExample(0).getEng_example());
		up_engExamp2 = new TextField(f.getExample(1).getEng_example());
		up_engExamp3 = new TextField(f.getExample(2).getEng_example());
		up_espExamp1 = new TextField(f.getExample(0).getEsp_example());
		up_espExamp2 = new TextField(f.getExample(1).getEsp_example());
		up_espExamp3 = new TextField(f.getExample(2).getEsp_example());
		up_indications = new TextArea(f.getUse());
		up_indications.maxWidthProperty().bind(stage.widthProperty());
		up_indications.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode()==KeyCode.TAB) {
					up_pronun.requestFocus();
					event.consume();
					}
					else {
						
					}
					
				}
				
			});
		up_esp1 = new TextField(f.getExample(0).getTranslation());
		up_esp2 = new TextField(f.getExample(1).getTranslation());
		up_esp3 = new TextField(f.getExample(2).getTranslation());
		up_pronun = new TextField(f.getPronunciation());
		update= new Button("Update");
		update.maxWidthProperty().bind(stage.widthProperty());
		update.setFont(this.text);
		update.setOnAction((ActionEvent e)->{
			if(!up_eng.getText().equals("")) {
				try {
					up_f.setEnglish(Facade.autotrim(up_eng.getText()));
					up_f.setPronunciation(up_pronun.getText());
					up_f.setUse(up_indications.getText());
					up_f.clearExamples();
					if(!up_engExamp1.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp1.getText(), up_espExamp1.getText(), up_esp1.getText())));
						up_f.getExample(0).setKnown(up_known_1.isSelected());
					}
					
					if(!up_engExamp2.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp2.getText(), up_espExamp2.getText(), up_esp2.getText())));
						up_f.getExample(1).setKnown(up_known_2.isSelected());
					}
					
					if(!up_engExamp3.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp3.getText(), up_espExamp3.getText(), up_esp3.getText())));
						up_f.getExample(2).setKnown(up_known_3.isSelected());
					}
					if(up_f.allKnown()){
						up_f.setKnown(true);
					}
					else {
						up_f.setKnown(false);
					}
					Facade.updateFicha(up_f);
					mssgWindow("Success", primaryStage.getWidth(),primaryStage.getHeight());
				}
				catch(Exception ex) {
					
				}
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				stage.close();
				data=Facade.selectAll();
				table.setItems(data);
				//table.refresh();
				
				
			}
			
		});
		up_known_1= new CheckBox("Known");
		if(f.getExampleKnown(0)) {
			up_known_1.setSelected(true);
		}
		up_known_2= new CheckBox("Known");
		if(f.getExampleKnown(1)) {
			up_known_2.setSelected(true);
		}
		up_known_3= new CheckBox("Known");
		if(f.getExampleKnown(2)) {
			up_known_3.setSelected(true);
		}
		
		
		GridPane.setFillWidth(tit,true);
		GridPane.setFillWidth(up_eng, true);
		GridPane.setFillWidth(ex1,true);
		GridPane.setFillWidth(ex2,true);
		GridPane.setFillWidth(ex3,true);
		GridPane.setFillWidth(up_engExamp1, true);
		GridPane.setFillWidth(up_engExamp2, true);
		GridPane.setFillWidth(up_engExamp3, true);
		GridPane.setFillWidth(up_espExamp1, true);
		GridPane.setFillWidth(up_espExamp2, true);
		GridPane.setFillWidth(up_espExamp3, true);
		GridPane.setFillWidth(up_esp1, true);
		GridPane.setFillWidth(up_esp2, true);
		GridPane.setFillWidth(up_esp3, true);
		GridPane.setFillWidth(up_pronun, true);
		GridPane.setFillWidth(pronunciation,true);
		GridPane.setFillWidth(update,true);
		GridPane.setFillWidth(up_indications, true);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add           (tit, 0, 0, 3, 1);
		grid.add           (ing, 0, 1);
		grid.add        (up_eng, 1, 1, 2, 1);
		grid.add           (ex1, 0, 2, 3, 1);
		grid.add  (up_engExamp1, 0, 3, 3, 1);
		grid.add  (up_espExamp1, 0, 4, 3, 1);
		grid.add         (trad1, 0, 5);
		grid.add       (up_esp1, 1, 5);
		grid.add    (up_known_1, 2, 5);
		grid.add           (ex2, 0, 6, 3, 1);
		grid.add  (up_engExamp2, 0, 7, 3, 1);
		grid.add  (up_espExamp2, 0, 8, 3, 1);
		grid.add         (trad2, 0, 9);
		grid.add       (up_esp2, 1, 9);
		grid.add    (up_known_2, 2, 9);
		grid.add           (ex3, 0, 10, 3, 1);
		grid.add  (up_engExamp3, 0, 11, 3, 1);
		grid.add  (up_espExamp3, 0, 12, 3, 1);
		grid.add         (trad3, 0, 13);
		grid.add       (up_esp3, 1, 13);
		grid.add    (up_known_3, 2, 13);
		grid.add      (indicate, 0, 14, 3, 1);
		grid.add(up_indications, 0, 15, 3, 1);
		grid.add (pronunciation, 0, 16, 3, 1);
		grid.add     (up_pronun, 0, 17, 3, 1);
		grid.add        (update, 0, 18, 3, 1);
		Scene scene = new Scene(grid,420,750);
		stage.setX(primaryStage.getX()-(420-width)/2);
		stage.setY(primaryStage.getY()-(772-height)/2);;
		
		stage.setScene(scene);
		stage.show();
	}
	public void detail(Ficha f,double width,double height) {
		Stage stage = new Stage();
		Text ex2=null,ex1=null,ex3=null, use = null;
		res_tit = Font.font("Helvetica",FontWeight.BOLD,20);
		res_text = Font.font("Helvetica",FontWeight.LIGHT, 14);
		res_head = Font.font("Helvetica Neue",FontWeight.MEDIUM, 18);
		res_extext= Font.font("Helvetica Neue", FontWeight.BOLD,17);
		int len = f.getExampVec().size();
		res_eng = new Label(f.getEnglish());
		res_eng.setAlignment(Pos.TOP_LEFT);
		res_eng.setFont(res_extext);
		res_eng.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30));
		
		res_eng.setOnMouseClicked(new EventHandler(){
			@Override
			public void handle(Event event) {
				Facade.Speak(res_eng.getText());
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				
			}
		});
		
		
		res_pronun = new Label(f.getPronunciation());
		res_pronun.setAlignment(Pos.TOP_RIGHT);
		res_pronun.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30));
		res_pronun.setFont(res_extext);
		res_pronun.setTextAlignment(TextAlignment.RIGHT);
		res_pronun.setOnMouseClicked(new EventHandler(){
			@Override
			public void handle(Event event) {
				Facade.Speak(res_eng.getText());
				mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
				
			}
		});
		if(len>0) {
			res_ex_eng_1 = new Label(f.getExample(0).getEng_example());
			res_ex_eng_1.setAlignment(Pos.TOP_CENTER);
			res_ex_eng_1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			res_ex_eng_1.setFont(res_text);
			res_ex_eng_1.setWrapText(true);
			res_ex_eng_1.setTextAlignment(TextAlignment.CENTER);
			res_ex_eng_1.setOnMouseClicked(new EventHandler(){
				@Override
				public void handle(Event event) {
					Facade.Speak(res_ex_eng_1.getText());
					mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
					
				}
			});
			
			res_ex_esp_1 = new Label(f.getExample(0).getEsp_example());
			res_ex_esp_1.setAlignment(Pos.TOP_CENTER);
			res_ex_esp_1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			res_ex_esp_1.setFont(res_text);
			res_ex_esp_1.setWrapText(true);
			res_ex_esp_1.setTextAlignment(TextAlignment.CENTER);
			res_esp_1 = new Label("Translation: "+f.getExample(0).getTranslation());
			res_esp_1.setAlignment(Pos.TOP_CENTER);
			res_esp_1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			res_esp_1.setFont(res_text);
			res_esp_1.setTextAlignment(TextAlignment.CENTER);
			ex1 = new Text("Example 1: ");
			ex1.setFont(this.res_head);
			//ex1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
			ex1.setTextAlignment(TextAlignment.LEFT);
			
			GridPane.setFillWidth(res_ex_eng_1, true);
			GridPane.setFillWidth(res_ex_esp_1, true);
			GridPane.setFillWidth(res_esp_1, true);
			GridPane.setFillWidth(ex1, true);
			if(len>1) {
				res_ex_eng_2 = new Label(f.getExample(1).getEng_example());
				res_ex_eng_2.setAlignment(Pos.TOP_CENTER);
				res_ex_eng_2.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
				res_ex_eng_2.setFont(res_text);
				res_ex_eng_2.setWrapText(true);
				res_ex_eng_2.setTextAlignment(TextAlignment.CENTER);
				res_ex_eng_2.setOnMouseClicked(new EventHandler(){
					@Override
					public void handle(Event event) {
						Facade.Speak(res_ex_eng_2.getText());
						mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
						
					}
				});
				res_ex_esp_2 = new Label(f.getExample(1).getEsp_example());
				res_ex_esp_2.setAlignment(Pos.TOP_CENTER);
				res_ex_esp_2.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
				res_ex_esp_2.setFont(res_text);
				res_ex_esp_2.setTextAlignment(TextAlignment.CENTER);
				res_ex_esp_2.setWrapText(true);
				res_esp_2 = new Label("Translation: "+f.getExample(1).getTranslation());
				res_esp_2.setAlignment(Pos.CENTER);
				res_esp_2.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
				res_esp_2.setFont(res_text);
				res_esp_2.setTextAlignment(TextAlignment.CENTER);
				ex2 = new Text("Example 2: ");
				ex2.setFont(this.res_head);
				ex2.setFontSmoothingType(FontSmoothingType.LCD);
				ex2.setTextAlignment(TextAlignment.LEFT);
				
				GridPane.setFillWidth(res_ex_eng_2, true);
				GridPane.setFillWidth(res_ex_esp_2, true);
				GridPane.setFillWidth(res_esp_2, true);
				GridPane.setFillWidth(ex2, true);
				if(len>2) {
					res_ex_eng_3 = new Label(f.getExample(2).getEng_example());
					res_ex_eng_3.setAlignment(Pos.TOP_CENTER);
					res_ex_eng_3.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
					res_ex_eng_3.setFont(res_text);
					res_ex_eng_3.setWrapText(true);
					res_ex_eng_3.setTextAlignment(TextAlignment.CENTER);
					res_ex_eng_3.setOnMouseClicked(new EventHandler(){
						@Override
						public void handle(Event event) {
							Facade.Speak(res_ex_eng_3.getText());
							mssgWindow(primaryStage.getWidth(),primaryStage.getHeight());
							
						}
					});
					res_ex_esp_3 = new Label(f.getExample(2).getEsp_example());
					res_ex_esp_3.setAlignment(Pos.TOP_CENTER);
					res_ex_esp_3.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
					res_ex_esp_3.setFont(res_text);
					res_ex_esp_3.setTextAlignment(TextAlignment.CENTER);
					res_ex_esp_3.setWrapText(true);
					res_esp_3 = new Label("Translation: "+f.getExample(2).getTranslation());
					res_esp_3.setAlignment(Pos.CENTER);
					res_esp_3.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
					res_esp_3.setFont(res_text);
					res_esp_3.setTextAlignment(TextAlignment.CENTER);
					ex3 = new Text("Example 3: ");
					ex3.setFont(this.res_head);
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
			use.setFont(this.res_head);
			use.setFontSmoothingType(FontSmoothingType.LCD);
			use.setTextAlignment(TextAlignment.CENTER);
			this.use=new TextArea(f.getUse());
			this.use.setEditable(false);
		}
		GridPane.setFillWidth(res_eng, true);
		GridPane.setFillWidth(res_pronun, true);
		GridPane.setFillWidth(res_ex_eng_1, true);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add     (res_eng, 0, 0);
		grid.add  (res_pronun, 1, 0);
		if(len>0) {
			grid.add         (ex1, 0, 1, 2, 1);
			grid.add(res_ex_eng_1, 0, 2, 2, 1);
			grid.add(res_ex_esp_1, 0, 3, 2, 1);
			grid.add   (res_esp_1, 0, 4,2,1);
			if(len>1) {
				grid.add         (ex2, 0, 5, 2, 1);
				grid.add(res_ex_eng_2, 0, 6, 2, 1);
				grid.add(res_ex_esp_2, 0, 7, 2, 1);
				grid.add   (res_esp_2, 0, 8,2, 1);
				if(len>2) {
					grid.add         (ex3, 0, 9, 2, 1);
					grid.add(res_ex_eng_3, 0, 10, 2, 1);
					grid.add(res_ex_esp_3, 0, 11, 2, 1);
					grid.add   (res_esp_3, 0, 12, 2, 1);
				}
			}
		}
		if(!f.getUse().equals("")) {
			//The switch is only for the row count
			switch(len) {
				case 1:{
					grid.add(use, 0, 5, 2, 1);
					grid.add(this.use, 0, 6, 2, 1);
					break;
				}
				case 2:{
					grid.add(use, 0, 9, 2, 1);
					grid.add(this.use, 0, 10, 2, 1);
					break;
				}
				case 3:{
					grid.add(use, 0, 13, 2, 1);
					grid.add(this.use, 0, 14, 2, 1);
					break;
				}
			}
		}
		grid.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT, new Insets(5,5,5,5))));
		v=new VBox();
		/*v.getChildren().add(grid);
		VBox.setMargin(grid, new Insets(20,0,20,0));
		*/
		int height1 =245;
		switch(len) {
			case 2:{
				height1+=120;
				break;
			}
			case 3:{
				height1+=240;
				break;
			}
		}
		if(!f.getUse().equals("")){
			height1+=120;
			
		}
		Scene scene = new Scene(grid,400,grid.getPrefHeight());
		stage.setX(primaryStage.getX()-(500-width)/2);
		stage.setY(primaryStage.getY()-(height1+20-height)/2);
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main (String[]args) {
		launch(args);
	}
}
