package fX;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
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
import javafx.util.Callback;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
public class MainWindow extends Application{

public static Stage primaryStage;
public static Font tit, head;
public static Font text;
private Font extext;
private Font extext2;
private HBox menu;
private Button b_intro, b_exam, b_consul, b_options, b_StoE;
private final double M_w=500;
private final double M_h=110;
//Introduction scene
private Font textf;
private TextField eng, engExamp1,engExamp2, engExamp3, espExamp1, espExamp2, espExamp3, esp1, esp2, esp3, pronun;
private TextArea indications;
private Button add;
TextField ex_eng;
private final double I_w=550;
private final double I_h=800;

Stack<ArrayList<Ficha>> mem;
private final int s_amount=10;

// Exam scene
private Label ex_pronun;
private TextArea  ex_examp;
private TextField ex_spa;
private Ficha ex_f;
private Example ex_e;
private int ex_index;
private final double E_w=500;
private final double E_h=220;
public static final int waitt=1000;
//Result
private Font res_tit;
private VBox v,vv;
public static Stage resStage;
//Consult
public static TableView<ObservableFicha> table;
public static ObservableList <ObservableFicha> data;
private Button eliminate, edit;
//Update
private TextField up_eng, up_engExamp1,up_engExamp2, up_engExamp3, up_espExamp1, up_espExamp2, up_espExamp3, up_esp1, up_esp2, up_esp3, up_pronun;
private TextArea up_indications;
private Button update;
private CheckBox up_known_E_1, up_known_E_2, up_known_E_3;
private CheckBox up_known_S_1, up_known_S_2, up_known_S_3;
private Ficha up_f;
//SQLConsole
private TextField sql_query;
private CheckBox sql_w_result;
private Button execute;
private TextArea sql_result;
//Adjust
private Button resetEtoS;
private final double A_w=500;
private final double A_h=400;
ArrayList<Voice> arrv;
//Backup	
private Button b_backup,b_restore;
private TextField tf_backup, tf_restore;
//Exceptions
public static String exceptions="";
public static double scale=1;

public static ConfigData cfd;
	@Override
	public void start(Stage primaryStage) throws Exception {
		cfd= new ConfigData();
		scale=cfd.scaling;
		/***  Fonts definition  ***/
		MainWindow.exceptions="";
		tit = Font.font("Helvetica",FontWeight.BOLD,20*scale);
		text = Font.font("Helvetica",FontWeight.LIGHT, 14*scale);
		head = Font.font("Helvetica Neue",FontWeight.MEDIUM, 15*scale);
		extext= Font.font("Helvetica Neue", FontWeight.BOLD,17*scale);
		extext2= Font.font("Arial", FontWeight.NORMAL,18*scale);
		textf= Font.font("Helvetica",FontWeight.NORMAL,12*scale);
		
		
		/***  Initialization ***/
		MainWindow.primaryStage=primaryStage;
		MainWindow.primaryStage.setTitle("Vocabulary Archive");
		MainWindow.primaryStage.setScene(mainMenu());
		MainWindow.primaryStage.show();
		
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
		b_exam = new Button("Test E to S");
		b_exam.setFont(text);
		b_exam.setOnAction((ActionEvent e)->{
			if(Facade.checkAvailabilityEtoS()) {
				double width=primaryStage.getWidth();
				double height = primaryStage.getHeight();
				primaryStage.setScene(examEtoS(width,height));
				System.out.println(primaryStage.getWidth());
				System.out.println(primaryStage.getHeight());
				ex_spa.requestFocus();
			}
			else {
				mssgWindow();
			}
			
		});
		b_StoE=new Button("Test S to E");
		b_StoE.setFont(text);
		b_StoE.setOnAction((ActionEvent e)->{
			if(Facade.checkAvailabilityStoE()) {
				double width=primaryStage.getWidth();
				double height = primaryStage.getHeight();
				primaryStage.setScene(examStoE(width,height));
				System.out.println(primaryStage.getWidth());
				System.out.println(primaryStage.getHeight());
				ex_spa.requestFocus();
			}
			else {
				mssgWindow();
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
		
		HBox.setMargin(b_intro, new Insets(0,0,0,10*scale));
		HBox.setMargin(b_exam, new Insets(0,0,0,10*scale));
		HBox.setMargin(b_StoE, new Insets(0,0,0,10*scale));
		HBox.setMargin(b_consul, new Insets(0,0,0,10*scale));
		HBox.setMargin(b_options, new Insets(0,0,0,10*scale));
		menu = new HBox();
		menu.setAlignment(Pos.TOP_CENTER);
		menu.getChildren().addAll(b_intro,b_exam,b_StoE,b_consul,b_options);
		VBox v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);
		v.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		v.getChildren().addAll(title,menu);
		VBox.setMargin(menu, new Insets(15*scale,0,0,0));
		Scene scene = new Scene(v,M_w*scale,M_h*scale);
		return scene;
	}
	private Scene introduction(double width,double height) {
		
		Text tit = new Text("Addition of words");
		tit.setFont(MainWindow.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		Text ing = new Text("English:");
		ing.setFont(MainWindow.text);
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
		trad1.setFont(MainWindow.text);
		trad1.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad2 = new Text("Translation:");
		trad2.setFont(MainWindow.text);
		trad2.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad3 = new Text("Translation:");
		trad3.setFont(MainWindow.text);
		trad3.setFontSmoothingType(FontSmoothingType.LCD);
		Text indicate = new Text("Use notes");
		indicate.setFont(MainWindow.text);
		indicate.setFontSmoothingType(FontSmoothingType.LCD);
		Text pronunciation = new Text("Pronunciation");
		pronunciation.setFont(MainWindow.text);
		pronunciation.setFontSmoothingType(FontSmoothingType.LCD);
		
		Text state = new Text("New");
		state.setFont(MainWindow.tit);
		state.setFontSmoothingType(FontSmoothingType.LCD);
		Text suggestions = new Text();
		suggestions.setTextAlignment(TextAlignment.CENTER);
		suggestions.setFont(textf);
		eng = new TextField();
		eng.setFont(textf);
		mem=null;
		eng.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if(Facade.selectSome(Facade.autotrim(eng.getText())).size()!=0) {
					mssgWindow("This word is already in the system.");
				}
				else {
					mssgWindow("This word is NOT in the system.");
				}
			}
			
		});
		eng.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
			@Override
			public void handle(KeyEvent event) {
				String s="";
				
					if(event.getCode()==KeyCode.BACK_SPACE) {
						if(eng.getText().length()==0) {
							state.setText("New");
							return;
						}
						s=eng.getText().substring(0, eng.getText().length()-1);
						boolean found=false;
						Iterator<Ficha> it;
						Ficha f;
						if(mem.size()<=1) {
							state.setText("New");
							suggestions.setText("");
							return;
						}
						mem.pop();
						it=mem.peek().iterator();
						while(it.hasNext()) {
							f=it.next();
							if(f.getEnglish().toLowerCase().equals(s.toLowerCase())) {
								found=true;
								break;
							}
						}
						if(found) {
							state.setText("Not new");
						}
						else state.setText("New");
					}
					else if(!event.getText().equals("")){
						s=eng.getText()+event.getText();
						boolean found=false;
						ArrayList<Ficha> nal= new ArrayList<Ficha>();
						Iterator<Ficha> it;
						Ficha f;
						if(mem==null||mem.isEmpty()) {
							state.setText("New");
							mem=new Stack<ArrayList<Ficha>>();
							mem.push(Facade.getAll());
						}
						it=mem.peek().iterator();
						while(it.hasNext()) {
							f=it.next();
							if(f.getEnglish().toLowerCase().equals(s.toLowerCase())) {
								found=true;
								nal.add(f);
							}
							else if(f.getEnglish().toLowerCase().contains(s.toLowerCase())) {
								nal.add(f);
							}
						}
						mem.push(nal);
						if(found) {
							state.setText("Not new");
						}
						else state.setText("New");
					}
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
					if(mem!=null&&mem.size()>1&&!s.equals("")) {
						String str="";
						Ficha f;
						int i;
						Iterator<Ficha> it=mem.peek().iterator();
						for(i=0;i<s_amount;++i) {
							if(!it.hasNext()) break;
							while(it.hasNext()) {
								f=it.next();
								if(f.getEnglish().toLowerCase().startsWith(s.toLowerCase())) {
									str+=f.getEnglish();
									str+="   ";
									break;
								}
								
							}
							if(!it.hasNext()) break;
						}
						if(i==s_amount) {
							suggestions.setText(str);
							return;
						}
						it=mem.peek().iterator();
						for(;i<s_amount;++i) {
							if(!it.hasNext()) break;
							while(it.hasNext()) {
								f=it.next();
								if(f.getEnglish().toLowerCase().endsWith(s.toLowerCase())) {
									str+=f.getEnglish();
									str+="   ";
									break;
								}
								
							}
							if(!it.hasNext()) break;
						}
						if(i==s_amount) {
							suggestions.setText(str);
							return;
						}
						it=mem.peek().iterator();
						for(;i<s_amount;++i) {
							if(!it.hasNext()) break;
							while(it.hasNext()) {
								f=it.next();
								if(f.getEnglish().toLowerCase().contains(s.toLowerCase())&&!(f.getEnglish().toLowerCase().startsWith(s.toLowerCase()))&&!(f.getEnglish().toLowerCase().endsWith(s.toLowerCase()))) {
									str+=f.getEnglish();
									str+="   ";
									break;
								}
								
							}
							if(!it.hasNext()) break;
						}
						suggestions.setText(str);
						return;
					}
					else {
						suggestions.setText("");
					}
					
				
			}
			
		});
		engExamp1 = new TextField();
		engExamp2 = new TextField();
		engExamp3 = new TextField();
		espExamp1 = new TextField();
		espExamp2 = new TextField();
		espExamp3 = new TextField();
		
		engExamp1.setFont(textf);
		engExamp2.setFont(textf);
		engExamp3.setFont(textf);
		espExamp1.setFont(textf);
		espExamp2.setFont(textf);
		espExamp3.setFont(textf);
		
		indications = new TextArea();
		indications.setFont(textf);
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
		
		esp1.setFont(textf);
		esp2.setFont(textf);
		esp3.setFont(textf);
		
		pronun = new TextField();
		pronun.setFont(textf);
		add= new Button("Add");
		add.maxWidthProperty().bind(primaryStage.widthProperty());
		add.setFont(MainWindow.text);
		add.setOnAction((ActionEvent e)->{
			if(Facade.selectSome(Facade.autotrim(eng.getText())).size()!=0) {
				confirmWindowintro("This word is already in the system. Are you sure you wan to include it?");
			}
			else {
				add();
			}
			
		});
		engExamp1.minWidthProperty().bind(primaryStage.widthProperty().subtract(30));
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
		HBox h= new HBox();
		h.setAlignment(Pos.CENTER);
		HBox.setMargin(eng, new Insets(0,10*scale,0,10*scale));
		HBox.setHgrow(eng, Priority.ALWAYS);
		h.getChildren().addAll(ing,eng,state);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10*scale);
		grid.setVgap(10*scale);
		grid.add(menu, 0, 0, 2, 1);
		grid.add(tit, 0, 1, 2, 1);
		grid.add(h, 0, 2,2,1);
		grid.add(suggestions, 0, 3,2,1);
		grid.add(pronunciation, 0, 4, 2, 1);
		grid.add(pronun, 0, 5, 2, 1);
		
		grid.add(ex1, 0, 6, 2, 1);
		grid.add(engExamp1, 0, 7, 2, 1);
		grid.add(espExamp1, 0, 8, 2, 1);
		grid.add(trad1, 0, 9);
		grid.add(esp1, 1, 9);
		grid.add(ex2, 0, 10, 2, 1);
		grid.add(engExamp2, 0, 11, 2, 1);
		grid.add(espExamp2, 0, 12, 2, 1);
		grid.add(trad2, 0, 13);
		grid.add(esp2, 1, 13);
		grid.add(ex3, 0, 14, 2, 1);
		grid.add(engExamp3, 0, 15, 2, 1);
		grid.add(espExamp3, 0, 16, 2, 1);
		grid.add(trad3, 0, 17);
		grid.add(esp3, 1, 17);
		grid.add(indicate, 0, 18,2,1);
		grid.add(indications, 0, 19, 2, 1);
		
		grid.add(add, 0, 21, 2, 1);
		Scene scene = new Scene(grid,I_w*scale,I_h*scale);
		primaryStage.setX(primaryStage.getX()-(I_w*scale-width)/2);
		primaryStage.setY(primaryStage.getY()-(I_h*scale+22-height)/2);
		return scene;
		
	}
	private Scene examEtoS (double width,double height) {
		ex_f = Facade.getRndFichaEtoS();
		mssgWindow();
		Vector <Example> v = ex_f.getExampVec();
		//Reduce the number of examples to 1
		if(v.size()>1) {
			Random r = new Random();
			ex_index = r.nextInt(v.size()-1)+1;
			while(v.elementAt(ex_index).getEtoSKnown()) {
				ex_index = r.nextInt(v.size()-1);
			}
			ex_e=v.elementAt(ex_index);
		}
		else if(v.size()==0) {
			ex_e=null;
		}
		else {
			ex_index=0;
			ex_e=v.elementAt(0);
		}
		ex_eng = new TextField(ex_f.getEnglish());
		ex_eng.setAlignment(Pos.CENTER_LEFT);
		ex_eng.setFont(extext);
		ex_eng.setPadding(new Insets(0,0,0,10*scale));
		ex_eng.setEditable(false);
		ex_eng.setBackground(Background.EMPTY);
		ex_eng.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30*scale));
		ex_eng.setOnMouseClicked((Event e)->{
			Facade.Speak(ex_eng.getText());
			mssgWindow();
		});
		ex_pronun = new Label(ex_f.getPronunciation());
		ex_pronun.setAlignment(Pos.TOP_RIGHT);
		ex_pronun.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30*scale));
		ex_pronun.setFont(extext2);
		ex_pronun.setOnMouseClicked((Event e)->{
			Facade.Speak(ex_eng.getText());
			mssgWindow();
		});
		if(ex_e!=null) {
		ex_examp= new TextArea(ex_e.getEng_example());
		ex_examp.setWrapText(true);
		ex_examp.setEditable(false);
		ex_examp.setFont(extext);
		ex_examp.setBackground(null);
		ex_examp.setPadding(new Insets(0,0,0,0));
		ex_examp.setCenterShape(true);
		ex_examp.setOnMouseClicked((Event e)->{
			Facade.Speak(ex_examp.getText());
			mssgWindow();
		});
		}
		else {
			ex_examp= new TextArea("");
			ex_examp.setWrapText(true);
			ex_examp.setEditable(false);
			ex_examp.setFont(extext);
			ex_examp.setBackground(null);
			ex_examp.setPadding(new Insets(0,0,0,0));
			ex_examp.setCenterShape(true);
		}
		ex_spa = new TextField();
		ex_spa.setFont(textf);
		ex_spa.setOnAction((ActionEvent e)->{
			if(ex_e.getTranslation().toUpperCase().equals(Facade.autotrim(ex_spa.getText().toUpperCase()))) {
				double d_width = primaryStage.getWidth();
				double d_height=primaryStage.getHeight();
				primaryStage.setScene(resultEtoS(1,d_width,d_height));
				
				//setPositionOnScreen(width, height);
				this.v.requestFocus();
				ex_f.setExKnownEtoS(ex_index);
				Facade.updateFicha(ex_f);
				ex_f.getExampVec().elementAt(ex_index).setEtoSKnown(true);
				mssgWindow();
			}
			else {
				double d_width=primaryStage.getWidth();
				double d_height = primaryStage.getHeight();primaryStage.setScene(resultEtoS(0,d_width,d_height));
				//setPositionOnScreen(width,height);
				this.v.requestFocus();
			}
		});
		GridPane.setFillWidth(ex_eng, true);
		GridPane.setFillWidth(ex_pronun, true);
		GridPane.setFillWidth(ex_examp, false);
		GridPane.setFillWidth(ex_spa, true);
		GridPane.setMargin(ex_pronun, new Insets(0,0,0,10*scale));
		GridPane.setMargin(ex_eng, new Insets(0,10*scale,0,0));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(menu, 0, 0, 2, 1);
		grid.add(ex_eng, 0, 2);
		grid.add(ex_pronun, 1, 2);
		grid.add(ex_examp, 0, 3, 2, 1);
		grid.add(ex_spa, 0, 4, 2, 1);
		ex_spa.requestFocus();
		Scene scene = new Scene(grid,E_w*scale,E_h*scale);
		scene.getStylesheets().add("/Data.css");
		primaryStage.setX(primaryStage.getX()-(E_w*scale-width)/2);
		primaryStage.setY(primaryStage.getY()-(E_h*scale+22-height)/2);
		if(cfd.s_readauto) {
			Facade.SpeakwDelay(ex_examp.getText());
		}
		
		
		return scene;
		
	}
	
	
	private Scene examStoE (double width,double height) {
		ex_f = Facade.getRndFichaStoE();
		mssgWindow();
		Vector <Example> v = ex_f.getExampVec();
		//Reduce the number of examples to 1
		if(v.size()>1) {
			Random r = new Random();
			ex_index = r.nextInt(v.size()-1)+1;
			while(v.elementAt(ex_index).getStoEKnown()) {
				ex_index = r.nextInt(v.size()-1);
			}
			ex_e=v.elementAt(ex_index);
		}
		else {
			ex_index=0;
			ex_e=v.elementAt(0);
		}
		ex_eng = new TextField(ex_e.getTranslation());
		ex_eng.setAlignment(Pos.CENTER_LEFT);
		ex_eng.setFont(extext);
		ex_eng.setPadding(new Insets(0,0,0,10*scale));
		ex_eng.setEditable(false);
		ex_eng.setBackground(Background.EMPTY);
		ex_eng.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30*scale));
		ex_eng.setOnMouseClicked((Event e)->{
			Facade.Speak(ex_eng.getText());
			mssgWindow();
		});
		ex_examp= new TextArea(ex_e.getEsp_example());
		ex_examp.setWrapText(true);
		ex_examp.setEditable(false);
		ex_examp.setFont(extext);
		ex_examp.setBackground(null);
		ex_examp.setPadding(new Insets(0,0,0,0));
		ex_examp.setCenterShape(true);
		ex_examp.setOnMouseClicked((Event e)->{
			Facade.Speak(ex_examp.getText());
			mssgWindow();
		});
		ex_spa = new TextField();
		ex_spa.setFont(textf);
		ex_spa.setOnAction((ActionEvent e)->{
			if(ex_f.getEnglish().toUpperCase().equals(Facade.autotrim(ex_spa.getText().toUpperCase()))) {
				double d_width = primaryStage.getWidth();
				double d_height=primaryStage.getHeight();
				primaryStage.setScene(resultStoE(1,d_width,d_height));
				this.vv.requestFocus();
				ex_f.setExKnownStoE(ex_index);
				Facade.updateFicha(ex_f);
				ex_f.getExampVec().elementAt(ex_index).setStoEKnown(true);
				mssgWindow();
			}
			else {
				double d_width=primaryStage.getWidth();
				double d_height = primaryStage.getHeight();primaryStage.setScene(resultStoE(0,d_width,d_height));
				//setPositionOnScreen(width,height);
				this.vv.requestFocus();
			}
		});
		GridPane.setFillWidth(ex_eng, true);
		GridPane.setFillWidth(ex_examp, false);
		GridPane.setFillWidth(ex_spa, true);
		GridPane.setMargin(ex_eng, new Insets(0,10*scale,0,0));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(menu, 0, 0, 2, 1);
		grid.add(ex_eng, 0, 2);
		grid.add(ex_examp, 0, 3, 2, 1);
		grid.add(ex_spa, 0, 4, 2, 1);
		ex_spa.requestFocus();
		Scene scene = new Scene(grid,E_w*scale,E_h*scale);
		scene.getStylesheets().add("/Data.css");
		primaryStage.setX(primaryStage.getX()-(E_w*scale-width)/2);
		primaryStage.setY(primaryStage.getY()-(E_h*scale+22-height)/2);
		
		
		return scene;
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param i A value of 0 equals failure whereas a 1 equals success
	 * @return
	 */
	private Scene resultEtoS(int i,double width,double height) {
		Text tit;
		res_tit = Font.font("Helvetica",FontWeight.BOLD,20*scale);
		if(i==1) {
			tit= new Text("Correct:");
		}
		else {
			tit = new Text("The answer does not seem correct");
		}
		tit.setFont(res_tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		
		v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);
		v.setFillWidth(true);
		v.getChildren().addAll(menu,tit);
		VBox.setMargin(tit, new Insets(10*scale,0,0,0));
		Scene s = AuxiliarBuilder.displayFicha(ex_f, primaryStage);
		HBox b=new HBox();
		b.getChildren().add(s.getRoot());
		v.getChildren().add(b);
		//VBox.setMargin(grid, new Insets(20,0,20,0));
		
		if(i==0) {
			
			Text res = new Text("Your answer has been: \""+ex_spa.getText()+"\". It was expected: "+ex_e.getTranslation());
			res.wrappingWidthProperty().bind(primaryStage.widthProperty().subtract(30*scale));
			res.setFont(text);
			res.setFontSmoothingType(FontSmoothingType.LCD);
			res.setTextAlignment(TextAlignment.LEFT);
			GridPane.setFillWidth(res, true);
			v.getChildren().add(res);
			
			Button but =new Button("Mark as correct");
			but.maxWidthProperty().bind(primaryStage.widthProperty().subtract(30*scale));
			v.getChildren().add(but);
			but.setFont(text);
			VBox.setMargin(but, new Insets(10*scale,0,15*scale,0));
			but.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ex_f.setExKnownEtoS(ex_index);
					Facade.updateFicha(ex_f);
					mssgWindow();
					if(Facade.checkAvailabilityEtoS()) {
						double width=primaryStage.getWidth();
						double height = primaryStage.getHeight();
						primaryStage.setScene(examEtoS(width,height));
						ex_spa.requestFocus();
					}
					else {
						
					}
					mssgWindow();
				}
				
				
				
			});
		}
		v.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode()==KeyCode.ENTER) {
					if(Facade.checkAvailabilityEtoS()) {
						double width=primaryStage.getWidth();
						double height = primaryStage.getHeight();
						primaryStage.setScene(examEtoS(width,height));
						ex_spa.requestFocus();
					}
					else {
						
					}
					mssgWindow();
				}
				else if (event.getCode()==KeyCode.CONTROL) {
					if(i==0) {
						ex_f.setExKnownEtoS(ex_index);
						Facade.updateFicha(ex_f);
					}
					else {
						ex_f.setExUnKnownEtoS(ex_index);
						Facade.updateFicha(ex_f);
					}
					mssgWindow();
					if(Facade.checkAvailabilityEtoS()) {
						double width=primaryStage.getWidth();
						double height = primaryStage.getHeight();
						primaryStage.setScene(examEtoS(width,height));
						ex_spa.requestFocus();
					}
					else {
						
					}
					mssgWindow();
				}
				
			}
			
		});
		int height1=(int)AuxiliarBuilder.expHeight+ (int)(150*MainWindow.scale);
		if(i!=0)height1-=70*scale;
		double wid=Math.max(500*scale,AuxiliarBuilder.expWidth);
		wid+=20;
		Scene scene = new Scene(v,wid,height1);
		primaryStage.setX(primaryStage.getX()-(wid-width)/2);
		primaryStage.setY(primaryStage.getY()-((height1+20*MainWindow.scale)-height)/2);
		return scene;
		
	}
	private Scene resultStoE(int i,double width,double height) {
		Text tit;
		res_tit = Font.font("Helvetica",FontWeight.BOLD,20*scale);
		if(i==1) {
			tit= new Text("Correct:");
		}
		else {
			tit = new Text("The answer does not seem correct");
		}
		tit.setFont(res_tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		
		vv = new VBox();
		vv.setAlignment(Pos.TOP_CENTER);
		vv.setFillWidth(true);
		vv.getChildren().addAll(menu,tit);
		VBox.setMargin(tit, new Insets(10*scale,0,0,0));
		Scene s = AuxiliarBuilder.displayFicha(ex_f, primaryStage);
		HBox b=new HBox();
		b.getChildren().add(s.getRoot());
		vv.getChildren().add(b);
		//VBox.setMargin(grid, new Insets(20,0,20,0));
		
		if(i==0) {
			
			Text res = new Text("Your answer has been: \""+ex_spa.getText()+"\". It was expected: "+ex_f.getEnglish());
			res.wrappingWidthProperty().bind(primaryStage.widthProperty().subtract(30*scale));
			res.setFont(text);
			res.setFontSmoothingType(FontSmoothingType.LCD);
			res.setTextAlignment(TextAlignment.LEFT);
			GridPane.setFillWidth(res, true);
			vv.getChildren().add(res);
			
			Button but =new Button("Mark as correct");
			but.setFont(text);
			but.maxWidthProperty().bind(primaryStage.widthProperty().subtract(30*scale));
			vv.getChildren().add(but);
			VBox.setMargin(but, new Insets(10*scale,0,15*scale,0));
			but.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ex_f.setExKnownStoE(ex_index);
					Facade.updateFicha(ex_f);
					mssgWindow();
					if(Facade.checkAvailabilityStoE()) {
						double width=primaryStage.getWidth();
						double height = primaryStage.getHeight();
						primaryStage.setScene(examStoE(width,height));
						ex_spa.requestFocus();
					}
					else {
						
					}
					mssgWindow();
				}
				
				
				
			});
		}
		vv.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode()==KeyCode.ENTER) {
					if(Facade.checkAvailabilityStoE()) {
						double width=primaryStage.getWidth();
						double height = primaryStage.getHeight();
						primaryStage.setScene(examStoE(width,height));
						ex_spa.requestFocus();
					}
					else {
						
					}
					mssgWindow();
				}
				else if (event.getCode()==KeyCode.CONTROL) {
					if(i==0) {
						ex_f.setExKnownStoE(ex_index);
						Facade.updateFicha(ex_f);
					}
					else {
						ex_f.setExUnKnownStoE(ex_index);
						Facade.updateFicha(ex_f);
					}
					mssgWindow();
					if(Facade.checkAvailabilityStoE()) {
						double width=primaryStage.getWidth();
						double height = primaryStage.getHeight();
						primaryStage.setScene(examStoE(width,height));
						ex_spa.requestFocus();
					}
					else {
						
					}
					mssgWindow();
				}
				
			}
			
		});
		int height1=(int)AuxiliarBuilder.expHeight+ (int)(150*MainWindow.scale);
		if(i!=0)height1-=70*scale;
		double wid=Math.max(500*scale,AuxiliarBuilder.expWidth);
		wid+=20;
		Scene scene = new Scene(vv,wid,height1);
		primaryStage.setX(primaryStage.getX()-(wid-width)/2);
		primaryStage.setY(primaryStage.getY()-((height1+20*MainWindow.scale)-height)/2);
		return scene;
		
	}
	private Scene consult(double width,double height) {
		Text tit =new Text("Database");
		tit.setFont(MainWindow.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		TextField tfs= new TextField();
		tfs.setFont(MainWindow.text);
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
		mssgWindow();
		TableColumn<ObservableFicha, Integer> key = new TableColumn<ObservableFicha, Integer>("Index");
		key.setMinWidth(30*scale);
		key.setCellValueFactory(new PropertyValueFactory<ObservableFicha, Integer> ("DBkey"));
		key.setCellFactory(new Callback<TableColumn<ObservableFicha, Integer>,TableCell<ObservableFicha, Integer>>(){

			@Override
			public TableCell<ObservableFicha, Integer> call(TableColumn<ObservableFicha, Integer> param) {
				return new TableCell<ObservableFicha,Integer>(){
					@Override
					public void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(Integer.toString(item));
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> english = new TableColumn<ObservableFicha, String>("English");
		english.setMinWidth(30*scale);
		english.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("english"));
		english.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> ex1 = new TableColumn<ObservableFicha, String>("Ex.1");
		ex1.setMinWidth(30*scale);
		ex1.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("ex1"));
		ex1.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> ex2 = new TableColumn<ObservableFicha, String>("Ex.2");
		ex2.setMinWidth(30*scale);
		ex2.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("ex2"));
		ex2.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> ex3 = new TableColumn<ObservableFicha, String>("Ex.3");
		ex3.setMinWidth(30*scale);
		ex3.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("ex3"));
		ex3.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> pronunciation = new TableColumn<ObservableFicha, String>("Pronunciation");
		pronunciation.setMinWidth(30*scale);
		pronunciation.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("pronunciation"));
		pronunciation.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> use = new TableColumn<ObservableFicha, String>("Use");
		use.setMinWidth(30*scale);
		use.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("use"));
		use.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> knownEtoS = new TableColumn<ObservableFicha, String>("Known EtoS");
		knownEtoS.setMinWidth(130*scale);
		knownEtoS.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("knownetos"));
		knownEtoS.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		TableColumn<ObservableFicha, String> knownStoE = new TableColumn<ObservableFicha, String>("Known StoE");
		knownStoE.setMinWidth(130*scale);
		knownStoE.setCellValueFactory(new PropertyValueFactory<ObservableFicha, String> ("knownstoe"));
		knownStoE.setCellFactory(new Callback<TableColumn<ObservableFicha, String>,TableCell<ObservableFicha, String>>(){

			@Override
			public TableCell<ObservableFicha, String> call(TableColumn<ObservableFicha, String> param) {
				return new TableCell<ObservableFicha,String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(isEmpty()) {
							setText("");
						}
						else {
							setFont(text);
							setText(item);
						}
					}
				};
			}
			
		});
		table.setItems(data);
		
		table.getColumns().addAll(key,english, ex1, ex2, ex3, pronunciation, use,knownEtoS,knownStoE);
		
		table.minHeightProperty().bind(primaryStage.heightProperty().subtract(150*scale));
		table.getSortOrder().add(english);
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount()==2) {
				Ficha f=new Ficha(table.getSelectionModel().getSelectedItem());
				detail(f,primaryStage.getWidth(),primaryStage.getHeight());
				}
			}
			
		});
		table.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ENTER) {
					Ficha f=new Ficha(table.getSelectionModel().getSelectedItem());
					detail(f,primaryStage.getWidth(),primaryStage.getHeight());
				}
				
			}
			
		});
		eliminate = new Button("Delete");
		eliminate.setFont(text);
		eliminate.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(20*scale));
		eliminate.setOnAction((ActionEvent e)->{
			if(table.getSelectionModel().getSelectedIndex()!=-1) {
				ObservableList<Integer> i = table.getSelectionModel().getSelectedIndices();
				if(i.size()>1) {
					//There are several rows selected
					Vector<ObservableFicha> v = new Vector<ObservableFicha>();
					for(int j=0;j<i.size();j++) {
						v.add(table.getSelectionModel().getSelectedItems().get(j));
					}
					
					confirmWindow("¿Desea eliminar "+i.size()+" filas?", v);
					data=Facade.selectAll();
					table.setItems(data);
					table.refresh();
					table.getSortOrder().clear();
					table.getSortOrder().add(english);
					mssgWindow();
				}
				else {
					//There is only one row selected
					confirmWindow("¿Desea eliminar "+(data.get(table.getSelectionModel().getSelectedIndex()).getEnglish())+"?",table.getSelectionModel().getSelectedItem().getDBkey());
					data=Facade.selectAll();
					table.setItems(data);
					table.refresh();
					table.getSortOrder().clear();
					table.getSortOrder().add(english);
					mssgWindow();
				}
			}
			
		});
		edit = new Button("Edit");
		edit.setFont(text);
		edit.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(20*scale));
		edit.setOnAction((ActionEvent e)->{
			if(table.getSelectionModel().getSelectedIndex()!=-1) {
				if( table.getSelectionModel().getSelectedIndices().size()<2) {
					//There is only one row selected
					editWindow(new Ficha(table.getSelectionModel().getSelectedItem()),primaryStage.getWidth(),primaryStage.getHeight());
					
					
				}
				else {
					mssgWindow("Select only one one row");
				}
			}
			else {
				mssgWindow("Select a row");
			}
		});
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(menu, 0, 0, 2, 1);
		grid.add(tit, 0, 1, 1, 1);
		grid.add(tfs, 1, 1,1 ,1);
		grid.add(table, 0, 2, 2, 1);
		grid.add(eliminate, 0, 3);
		grid.add(edit, 1, 3);
		double w = 800*scale;
		double h= 600*scale;
		Scene scene = new Scene(grid,w,h);
		primaryStage.setX(primaryStage.getX()-(w-width)/2);
		primaryStage.setY(primaryStage.getY()-(h-height)/2);
		return scene;
		
	}
	private Scene options(double width,double height) {
		Font up_head = head = Font.font("Helvetica Neue",FontWeight.MEDIUM, 17*scale);
		Text tit = new Text("Settings");
		tit.setFont(MainWindow.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.LEFT);
		
		//SQL console
		
		
		sql_query= new TextField();
		sql_query.setFont(textf);
		sql_query.minWidthProperty().bind(primaryStage.widthProperty().subtract(150*scale));
		sql_query.setPromptText("Enter the command");
		sql_w_result= new CheckBox("Result");
		sql_w_result.setFont(MainWindow.text);
		execute = new Button("Execute");
		execute.setFont(MainWindow.text);
		execute.setOnAction( (ActionEvent e)->{
			System.out.println(sql_query.getText());
			System.out.println(Facade.execute(sql_query.getText(), sql_w_result.isSelected()));
			sql_result.setText(Facade.execute(sql_query.getText(), sql_w_result.isSelected()));
		});
		sql_result = new TextArea("");
		sql_result.setFont(textf);
		sql_result.minWidthProperty().bind(primaryStage.widthProperty().subtract(30*scale));
		sql_result.minHeightProperty().bind(primaryStage.heightProperty().subtract(250*scale));
		GridPane.setFillWidth(menu, true);
		GridPane.setFillWidth(sql_query, true);
		GridPane.setFillWidth(execute, true);
		GridPane.setFillWidth(sql_result, true);
		TabPane tp = new TabPane();
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10*scale);
		grid.setVgap(10*scale);
		grid.add   (sql_query, 0, 0);
		grid.add(sql_w_result, 1, 0);
		grid.add     (execute, 0, 1, 2, 1);
		grid.add  (sql_result, 0, 2, 2, 1);
		Tab consol = new Tab("SQL console");
		consol.setClosable(false);
		consol.setStyle("-fx-font-size:"+text.getSize()*0.8+"px;");
		consol.setContent(grid);
		
		//Settings
		Tab adjust = new Tab ("Other");
		adjust.setStyle("-fx-font-size:"+text.getSize()*0.8+"px;");
		
		Text scal = new Text("Select the scale for the graphical interface:");
		scal.setFont(text);
		TextField ts = new TextField(Double.toString(cfd.scaling));
		ts.setFont(textf);
		ts.setOnAction((ActionEvent e)->{
			cfd.update_scaling(Double.parseDouble(ts.getText()));
		});
		HBox sb = new HBox();
		sb.getChildren().addAll(scal,ts);
		sb.setPadding(new Insets(10,0,10,0));
		HBox.setHgrow(ts, Priority.ALWAYS);
		HBox.setMargin(ts, new Insets(0,0,0,10));
		
		Text t = new Text ("Set as unknown");
		t.setFont(head);
		t.setTextAlignment(TextAlignment.LEFT);
		resetEtoS = new Button ("English to Spanish");
		resetEtoS.setFont(text);
		resetEtoS.setOnAction((ActionEvent e)->{
			confirmWindowEtoS("Are you sure you want to make every register as unknown from English to Spanish?");
		});
		Button resetStoE = new Button("Spanish to English");
		resetStoE.setFont(text);
		resetStoE.setOnAction((ActionEvent e)->{
			confirmWindowStoE("Are you sure you want to make every register as unknown from Spanish to English?");
		});
		
		GridPane g = new GridPane();
		GridPane.setFillWidth(resetEtoS, true);
		GridPane.setFillWidth(resetStoE, true);
		GridPane.setHgrow(resetEtoS,Priority.ALWAYS);
		resetEtoS.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30*scale));
		GridPane.setHgrow(resetStoE,Priority.ALWAYS);
		resetStoE.minWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(30*scale));
		g.setHgap(10*scale);
		g.setVgap(10*scale);
		g.add(scal, 0, 0,2,1);
		g.add(ts, 0, 1,2,1);
		g.add(t, 0, 2,2,1);
		g.add(resetEtoS, 0, 3);
		g.add(resetStoE, 1, 3);
		g.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		adjust.setContent(g);
		adjust.setClosable(false);
		
		//Backup
		Tab backup = new Tab("Backup");
		backup.setStyle("-fx-font-size:"+text.getSize()*0.8+"px;");
		Text back =new Text("Save backup");
		back.setFont(up_head);
		back.setFontSmoothingType(FontSmoothingType.LCD);
		Text rest =new Text("Restore from backup");
		rest.setFont(up_head);
		rest.setFontSmoothingType(FontSmoothingType.LCD);
		tf_backup = new TextField(cfd.backupPath);
		tf_backup.setFont(textf);
		tf_restore = new TextField("");
		tf_restore.setFont(textf);
		tf_restore.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(b_restore.isDisabled()) {
					b_restore.setDisable(false);
				}
				
			}
			
		});
		b_backup = new Button("Backup");
		b_backup.setFont(text);
		b_backup.minWidthProperty().bind(primaryStage.widthProperty().subtract(20));
		b_backup.setOnAction((ActionEvent e)->{
			String s =Facade.basicfilter(tf_backup.getText());
			cfd.update_backupPath(s);
			Facade.backup(s);
			mssgWindow();
		});
		b_restore = new Button ("Restore");
		b_restore.setDisable(true);
		b_restore.setFont(text);
		b_restore.setOnAction((ActionEvent e)->{
			if(!tf_restore.getText().equals("")) {
				Facade.restore(tf_restore.getText());
				mssgWindow();
			}
		});
		b_restore.minWidthProperty().bind(primaryStage.widthProperty().subtract(20));
		VBox vb2 = new VBox();
		HBox h1 = new HBox();
		h1.setAlignment(Pos.TOP_LEFT);
		h1.setPadding(new Insets(10*scale,0,5*scale,0));
		h1.getChildren().add(back);
		HBox h2 = new HBox();
		h2.setAlignment(Pos.TOP_CENTER);
		h2.setPadding(new Insets(5*scale,0,5*scale,0));
		h2.getChildren().add(tf_backup);
		HBox h3 = new HBox();
		h3.setAlignment(Pos.TOP_CENTER);
		h3.setPadding(new Insets(5*scale,0,5*scale,0));
		h3.getChildren().add(b_backup);
		HBox h4 = new HBox();
		h4.setAlignment(Pos.TOP_LEFT);
		h4.setPadding(new Insets(5*scale,0,5*scale,0));
		h4.getChildren().add(rest);
		HBox h5 = new HBox();
		h5.setAlignment(Pos.TOP_CENTER);
		h5.setPadding(new Insets(5*scale,0,5*scale,0));
		h5.getChildren().add(tf_restore);
		HBox h6 = new HBox();
		h6.setAlignment(Pos.TOP_CENTER);
		h6.setPadding(new Insets(5*scale,0,5*scale,0));
		h6.getChildren().add(b_restore);
		HBox.setHgrow(tf_backup, Priority.ALWAYS);
		HBox.setHgrow(b_backup, Priority.ALWAYS);
		HBox.setHgrow(tf_restore, Priority.ALWAYS);
		HBox.setHgrow(b_backup, Priority.ALWAYS);
		vb2.setAlignment(Pos.TOP_CENTER);
		vb2.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		vb2.getChildren().addAll(h1, h2, h3, h4, h5, h6);
		backup.setContent(vb2);
		backup.setClosable(false);
		
		//Voice
		

		Tab voice = new Tab ("Voice");
		voice.setStyle("-fx-font-size:"+text.getSize()*0.8+"px;");
		Label la = new Label("Select the voice:");
		la.setFont(text);
		ComboBox<String> cb = new ComboBox<String>();
		cb.setStyle("-fx-font-size:"+text.getSize()*0.8+"px;");
		if(arrv==null)arrv=Facade.getVoices();
		mssgWindow();
		if(cfd.s_showall) {
			for(Voice v: arrv) {
				cb.getItems().add(v.toString());
			}
		}
		else {
			for(Voice v: arrv) {
				if(v.getLanguage().equals("en")) {
					cb.getItems().add(v.toString());
				}
			}
		}
		String s=cfd.voice;
		
		for(Voice v:arrv) {
			if(v.getName().equals(s)) {
				cb.getSelectionModel().select(cb.getItems().indexOf(v.toString()));
				break;
			}
		}
		
		cb.setOnAction((ActionEvent e)->{
			String strg=cb.getSelectionModel().getSelectedItem();
			if(strg!=null) {
				cfd.update_voice(strg.substring(0, strg.indexOf('(')-1));
			}
			
		});
		
		
		Label ls=new Label("Select the speed (WPM)");
		ls.setFont(text);
		TextField tf= new TextField(Integer.toString(cfd.s_speed));
		tf.setFont(textf);
		tf.setOnKeyTyped((KeyEvent e)->{
			try {
				tf.appendText(e.getCharacter());
				e.consume();
				cfd.update_s_speed(Integer.parseInt(tf.getText()));
			}
			catch(Exception ex) {}
			mssgWindow();
		});
		CheckBox ceeb= new CheckBox();
		ceeb.setSelected(false);
		ceeb.setFont(text);
		ceeb.setAllowIndeterminate(false);
		if(cfd.s_readauto) {
			ceeb.setSelected(true);
		}
		ceeb.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cfd.update_s_readauto(ceeb.isSelected());
				
			}
			
		});
		
		Label ww = new Label("Automatic exam example reading");
		ww.setFont(text);
		Label aa= new Label("Delay:");
		aa.setFont(text);
		Label aab= new Label("Enable:");
		aab.setFont(text);
		TextField dl=new TextField(Integer.toString(cfd.s_delay));
		dl.setFont(textf);
		dl.setOnKeyTyped((KeyEvent e)->{
			try {
				dl.appendText(e.getCharacter());
				e.consume();
				cfd.update_s_delay(Integer.parseInt(dl.getText()));
			}
			catch(Exception ex) {}
			mssgWindow();
		});
		CheckBox cbb= new CheckBox("Show all installed voices");
		cbb.setFont(text);
		cbb.setAllowIndeterminate(false);
		if(cfd.s_showall) {
			cbb.setSelected(true);
		}
		else {
			cbb.setSelected(false);
		}
		cbb.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				cfd.update_s_showall(cbb.isSelected());
				
				cb.getItems().clear();
				if(s.equals("1")) {
					for(Voice v: arrv) {
						cb.getItems().add(v.toString());
					}
				}
				else {
					for(Voice v: arrv) {
						if(v.getLanguage().equals("en")) {
							cb.getItems().add(v.toString());
						}
					}
				}
				String str=cfd.voice;
				
				for(Voice v:arrv) {
					if(v.getName().equals(str)) {
						cb.getSelectionModel().select(cb.getItems().indexOf(v.toString()));
						break;
					}
				}
				
				
				
				
				
			}
			
		});
		
		VBox va = new VBox();
		HBox hh= new HBox();
		hh.getChildren().addAll(aab,ceeb,aa,dl);
		hh.setAlignment(Pos.CENTER);
		va.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		VBox.setMargin(cb, new Insets(10*scale,0,5*scale,0));
		VBox.setMargin(ls, new Insets(10*scale,0,5*scale,0));
		VBox.setMargin(ww, new Insets(10*scale,0,5*scale,0));
		VBox.setMargin(cbb, new Insets(20*scale,0,5*scale,0));
		
		HBox.setMargin(aab, new Insets(0,0,0,10*scale));
		HBox.setMargin(ceeb, new Insets(0,0,0,10*scale));
		HBox.setMargin(aa, new Insets(0,0,0,10*scale));
		HBox.setMargin(dl, new Insets(0,0,0,10*scale));
		HBox.setHgrow(dl, Priority.ALWAYS);
		va.getChildren().addAll(la,cb,ls,tf,ww,hh,cbb);
		va.setAlignment(Pos.TOP_CENTER);
		voice.setContent(va);
		voice.setClosable(false);
		tp.getTabs().addAll(consol,backup,voice,adjust);
		VBox vb= new VBox();
		vb.setAlignment(Pos.TOP_CENTER);
		VBox.setVgrow(tp, Priority.ALWAYS);
		VBox.setVgrow(menu, Priority.NEVER);
		VBox.setMargin(menu, new Insets(10*scale,10*scale,10*scale,10*scale));
		HBox hh1 = new HBox(tit);
		hh1.setPadding(new Insets(0,0,10*scale,10*scale));
		hh1.setAlignment(Pos.CENTER_LEFT);
		vb.getChildren().addAll(menu, hh1,tp);
		Scene scene = new Scene(vb,A_w*scale,A_h*scale);
		primaryStage.setX(primaryStage.getX()-(A_w*scale-width)/2);
		primaryStage.setY(primaryStage.getY()-(A_h*scale+22-height)/2);
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
				mem=null;
				eng.setPromptText("Enter successfully");
			}
			mssgWindow();
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
	static public void mssgWindow() {
		if(exceptions==null) exceptions="";
		if(!exceptions.equals("")) {
			mssgWindow(exceptions);
			exceptions="";
		}
		
		
	}
	public static void mssgWindow(String s) {
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	new Mssgwindow(s);
		    }
		});
			
		
	}
	public void confirmWindow(String s, Object list) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	ConfirmWindow m= new ConfirmWindow(s);
		    	EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if(list instanceof Integer) {
							Facade.eliminateFicha((Integer)list);
						}
						else if(list instanceof Vector){
							Facade.eliminateFicha((Vector<ObservableFicha>)list);
						}
						
						m.close();
						MainWindow.data=Facade.selectAll();
						MainWindow.table.setItems(MainWindow.data);
						MainWindow.table.refresh();
						MainWindow.table.getSortOrder().add(MainWindow.table.getColumns().get(1));
						MainWindow.mssgWindow();
						
					}
		    		
		    	};
		    	m.setOkEvent(e1);
		    	EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						m.close();	
					}
		    	};
		    	m.setCancelEvent(e2);
		    }
		});
	}
	public void confirmWindowEtoS(String s) {
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	ConfirmWindow m= new ConfirmWindow(s);
		    	EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Facade.updateAllKnownEtoS(false);
						m.close();
						mssgWindow();
					}
		    	};
		    	m.setOkEvent(e1);
		    	EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						m.close();	
					}
		    	};
		    	m.setCancelEvent(e2);
		    }
		});
		
	}
	
	public void confirmWindowStoE(String s) {
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	ConfirmWindow m= new ConfirmWindow(s);
		    	EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Facade.updateAllKnownStoE(false);
						m.close();
						mssgWindow();
					}
		    	};
		    	m.setOkEvent(e1);
		    	EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						m.close();	
					}
		    	};
		    	m.setCancelEvent(e2);
		    }
		});
		
	}
	public void confirmWindowintro(String s) {
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	ConfirmWindow m= new ConfirmWindow(s);
		    	EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						add();
						m.close();
					}
		    	};
		    	m.setOkEvent(e1);
		    	EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						m.close();	
					}
		    	};
		    	m.setCancelEvent(e2);
		    }
		});
	}
	public void editWindow(Ficha f, double width, double height) {
		up_f=f;
		Stage stage = new Stage();
		Text tit = new Text("Update");
		tit.setFont(MainWindow.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		Text ing = new Text("English:");
		ing.setFont(MainWindow.text);
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
		trad1.setFont(MainWindow.text);
		trad1.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad2 = new Text("Translation:");
		trad2.setFont(MainWindow.text);
		trad2.setFontSmoothingType(FontSmoothingType.LCD);
		Text trad3 = new Text("Translation:");
		trad3.setFont(MainWindow.text);
		trad3.setFontSmoothingType(FontSmoothingType.LCD);
		Text indicate = new Text("Use instructions");
		indicate.setFont(MainWindow.text);
		indicate.setFontSmoothingType(FontSmoothingType.LCD);
		Text pronunciation = new Text("Pronunciation");
		pronunciation.setFont(MainWindow.text);
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
		update.setFont(MainWindow.text);
		
		update.setOnAction((ActionEvent e)->{
			if(!up_eng.getText().equals("")) {
				try {
					up_f.setEnglish(Facade.autotrim(up_eng.getText()));
					up_f.setPronunciation(up_pronun.getText());
					up_f.setUse(up_indications.getText());
					up_f.clearExamples();
					if(!up_engExamp1.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp1.getText(), up_espExamp1.getText(), up_esp1.getText())));
						up_f.getExample(0).setEtoSKnown(up_known_E_1.isSelected());
						up_f.getExample(0).setStoEKnown(up_known_S_1.isSelected());
					}
					
					if(!up_engExamp2.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp2.getText(), up_espExamp2.getText(), up_esp2.getText())));
						up_f.getExample(1).setEtoSKnown(up_known_E_2.isSelected());
						up_f.getExample(1).setStoEKnown(up_known_S_2.isSelected());

					}
					
					if(!up_engExamp3.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp3.getText(), up_espExamp3.getText(), up_esp3.getText())));
						up_f.getExample(2).setEtoSKnown(up_known_E_3.isSelected());
						up_f.getExample(1).setStoEKnown(up_known_S_2.isSelected());

					}
					if(up_f.allKnownEtoS()){
						up_f.setKnownEtoS(true);
					}
					else {
						up_f.setKnownEtoS(false);
					}
					if(up_f.allKnownStoE()) {
						up_f.setKnownStoE(true);
					}
					else {
						up_f.setKnownStoE(false);
					}
					Facade.updateFicha(up_f);
					mssgWindow("Success");
				}
				catch(Exception ex) {
					
				}
				mssgWindow();
				stage.close();
				data=Facade.selectAll();
				table.setItems(data);
				table.getSortOrder().add(table.getColumns().get(1));
				//table.refresh();
				
				
			}
			
		});
		Tooltip t= new Tooltip();
		t.setText("English to Spanish");
		up_known_E_1 = new CheckBox();
		up_known_E_2 = new CheckBox();
		up_known_E_3 = new CheckBox();
		up_known_E_1.setSelected(f.getExampleKnownEtoS(0));
		up_known_E_2.setSelected(f.getExampleKnownEtoS(1));
		up_known_E_3.setSelected(f.getExampleKnownEtoS(2));
		
		up_known_E_1.setOnMouseEntered(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t.show(up_known_E_1,up_known_E_1.localToScreen(up_known_E_1.getBoundsInLocal()).getMaxX(),up_known_E_1.localToScreen(up_known_E_1.getBoundsInLocal()).getMaxY());
				
			}
			
		});
		up_known_E_1.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t.hide();
				
			}
			
		});
		
		up_known_E_2.setOnMouseEntered(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t.show(up_known_E_2,up_known_E_2.localToScreen(up_known_E_2.getBoundsInLocal()).getMaxX(),up_known_E_2.localToScreen(up_known_E_2.getBoundsInLocal()).getMaxY());
				
			}
			
		});
		up_known_E_2.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t.hide();
				
			}
			
		});
		
		up_known_E_3.setOnMouseEntered(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t.show(up_known_E_3,up_known_E_3.localToScreen(up_known_E_3.getBoundsInLocal()).getMaxX(),up_known_E_3.localToScreen(up_known_E_3.getBoundsInLocal()).getMaxY());
				
			}
			
		});
		up_known_E_3.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t.hide();
				
			}
			
		});
		
		Tooltip t2= new Tooltip();
		t2.setText("Spanish to English");
		
		up_known_S_1 = new CheckBox();
		up_known_S_2 = new CheckBox();
		up_known_S_3 = new CheckBox();
		
		up_known_S_1.setSelected(f.getExampleKnownStoE(0));
		up_known_S_2.setSelected(f.getExampleKnownStoE(1));
		up_known_S_3.setSelected(f.getExampleKnownStoE(2));
		
		up_known_S_1.setOnMouseEntered(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t2.show(up_known_S_1,up_known_S_1.localToScreen(up_known_S_1.getBoundsInLocal()).getMaxX(),up_known_S_1.localToScreen(up_known_S_1.getBoundsInLocal()).getMaxY());
				
			}
			
		});
		up_known_S_1.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t2.hide();
				
			}
			
		});
		up_known_S_2.setOnMouseEntered(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t2.show(up_known_S_2,up_known_S_2.localToScreen(up_known_S_2.getBoundsInLocal()).getMaxX(),up_known_S_2.localToScreen(up_known_S_2.getBoundsInLocal()).getMaxY());
				
			}
			
		});
		up_known_S_2.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t2.hide();
				
			}
			
		});
		up_known_S_3.setOnMouseEntered(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t2.show(up_known_S_3,up_known_S_3.localToScreen(up_known_S_3.getBoundsInLocal()).getMaxX(),up_known_S_3.localToScreen(up_known_S_3.getBoundsInLocal()).getMaxY());
				
			}
			
		});
		up_known_S_3.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t2.hide();
				
			}
			
		});
		
		
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
		GridPane.setHgrow(up_eng, Priority.ALWAYS);
		GridPane.setHgrow(up_engExamp1, Priority.ALWAYS);
		GridPane.setHgrow(up_engExamp2, Priority.ALWAYS);
		GridPane.setHgrow(up_engExamp3, Priority.ALWAYS);
		GridPane.setHgrow(up_espExamp1, Priority.ALWAYS);
		GridPane.setHgrow(up_espExamp2, Priority.ALWAYS);
		GridPane.setHgrow(up_espExamp3, Priority.ALWAYS);
		GridPane.setHgrow(up_esp1, Priority.ALWAYS);
		GridPane.setHgrow(up_esp2, Priority.ALWAYS);
		GridPane.setHgrow(up_esp3, Priority.ALWAYS);
		GridPane.setHgrow(up_pronun, Priority.ALWAYS);
		GridPane.setHgrow(update, Priority.ALWAYS);
		GridPane.setHgrow(up_indications, Priority.ALWAYS);
		GridPane.setHgrow(up_known_E_1, Priority.NEVER);
		up_engExamp3.minWidthProperty().bind(stage.widthProperty().subtract(30).subtract(up_known_E_1.widthProperty()));
		GridPane grid = new GridPane();
		grid.minWidthProperty().bind(stage.widthProperty().subtract(30));
		grid.maxWidthProperty().bind(stage.maxWidthProperty());
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add           (tit, 0, 0, 3, 1);
		grid.add           (ing, 0, 1);
		grid.add        (up_eng, 1, 1, 2, 1);
		grid.add           (ex1, 0, 2, 3, 1);
		grid.add  (up_engExamp1, 0, 3, 2, 1);
		grid.add  (up_known_E_1, 2, 3, 1, 1);
		grid.add  (up_espExamp1, 0, 4, 2, 1);
		grid.add  (up_known_S_1, 2, 4, 1, 1);
		grid.add         (trad1, 0, 5);
		grid.add       (up_esp1, 1, 5, 2, 1);
		grid.add           (ex2, 0, 6, 3, 1);
		grid.add  (up_engExamp2, 0, 7, 2, 1);
		grid.add  (up_known_E_2, 2, 7, 1, 1);
		grid.add  (up_espExamp2, 0, 8, 2, 1);
		grid.add  (up_known_S_2, 2, 8, 1, 1);
		grid.add         (trad2, 0, 9);
		grid.add       (up_esp2, 1, 9, 2, 1);
		grid.add           (ex3, 0, 10, 3, 1);
		grid.add  (up_engExamp3, 0, 11, 2, 1);
		grid.add  (up_known_E_3, 2, 11, 1, 1);
		grid.add  (up_espExamp3, 0, 12, 2, 1);
		grid.add  (up_known_S_3, 2, 12, 1, 1);
		grid.add         (trad3, 0, 13);
		grid.add       (up_esp3, 1, 13, 2, 1);
		grid.add      (indicate, 0, 14, 3, 1);
		grid.add(up_indications, 0, 15, 3, 1);
		grid.add (pronunciation, 0, 16, 3, 1);
		grid.add     (up_pronun, 0, 17, 3, 1);
		grid.add        (update, 0, 18, 3, 1);
		Scene scene = new Scene(grid,510,750);
		stage.setX(primaryStage.getX()-(420-width)/2);
		stage.setY(primaryStage.getY()-(772-height)/2);
		stage.setScene(scene);
		stage.show();
	}
	public void detail(Ficha f,double width,double height) {
		resStage = new Stage();
		
		resStage.setScene(AuxiliarBuilder.displayFicha(f,resStage));
		resStage.setX(primaryStage.getX()-(AuxiliarBuilder.expWidth-width)/2);
		resStage.setY(primaryStage.getY()-(AuxiliarBuilder.expHeight+20-height)/2);
		resStage.show();
		
	}
	public static void main (String[]args) {
		launch(args);
	}
}
