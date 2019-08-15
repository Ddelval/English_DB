package fX;


import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
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

private static Stage primaryStage;
public static Font tit, head;
public static Font text;
public static Font extext;
public static Font extext2;
private HBox menu;
private Button b_intro, b_exam, b_consul, b_options, b_StoE;
public static ScrollPane scrp,scrplocal;
private final double M_w=500;
private final double M_h=110;
//Introduction scene
public static Font textf;
public static Text state;
private static TextField eng, engExamp1,engExamp2, engExamp3, espExamp1, espExamp2, espExamp3, esp1, esp2, esp3, use1, use2, use3,pronun;
private static TextArea indications;
private static Button add;
TextField ex_eng;
public static VBox tot;
public static SplitPane s;
public static GridPane grid;
private final double I_w=650;
private final double I_h=860;


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
		Facade.initialCheck();
		cfd= new ConfigData();
		scale=cfd.scaling;
		/***  Fonts definition  ***/
		MainWindow.exceptions="";
		tit = Font.font("Helvetica",FontWeight.BOLD,20*scale);
		text = Font.font("Helvetica",FontWeight.LIGHT, 14*scale);
		head = Font.font("Gill Sans",FontWeight.NORMAL, 19*scale);
		extext= Font.font("Helvetica Neue", FontWeight.BOLD,18*scale);
		extext2= Font.font("Arial", FontWeight.NORMAL,18*scale);
		textf= Font.font("Helvetica",FontWeight.NORMAL,12*scale);
		
		
		/***  Initialization ***/
		exceptions= new String();
		MainWindow.primaryStage=primaryStage;
		MainWindow.primaryStage.setTitle("Vocabulary Archive");
		MainWindow.primaryStage.setScene(mainMenu());
		MainWindow.primaryStage.show();
		
		primaryStage.setOnCloseRequest(e->{
			exceptions="";
			Facade.backup(cfd.backupPath);
			if(exceptions!=null&&!exceptions.isEmpty()) {
				mssgWindow("Error in automated backup");
			}
		});
		
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
		grid = new GridPane();
		
		Text tit = new Text("Addition of words");
		tit.setFont(MainWindow.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		Text ing = new Text("English:");
		ing.setFont(MainWindow.text);
		ing.setFontSmoothingType(FontSmoothingType.LCD);
		Text ex1 = new Text("Example 1");
		ex1.setTextAlignment(TextAlignment.CENTER);
		ex1.setFont(head);
		ex1.setTextAlignment(TextAlignment.CENTER);
		Text ex2 = new Text("Example 2");
		ex2.setFont(head);
		ex2.setTextAlignment(TextAlignment.CENTER);
		Text ex3 = new Text("Example 3");
		ex3.setFont(head);
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
		Text use_1 = new Text("Use:");
		use_1.setFont(MainWindow.text);
		use_1.setFontSmoothingType(FontSmoothingType.LCD);
		Text use_2 = new Text("Use:");
		use_2.setFont(MainWindow.text);
		use_2.setFontSmoothingType(FontSmoothingType.LCD);
		Text use_3 = new Text("Use:");
		use_3.setFont(MainWindow.text);
		use_3.setFontSmoothingType(FontSmoothingType.LCD);
		Text indicate = new Text("Use notes");
		indicate.setFont(MainWindow.head);
		indicate.setFontSmoothingType(FontSmoothingType.LCD);
		Text pronunciation = new Text("Pronunciation");
		pronunciation.setFont(MainWindow.head);
		
		state = new Text("New");
		state.setFont(MainWindow.tit);
		state.setFontSmoothingType(FontSmoothingType.LCD);
		Text suggestions = new Text();
		suggestions.setTextAlignment(TextAlignment.CENTER);
		suggestions.setFont(textf);
		eng = new TextField();
		eng.setFont(textf);
		eng.textProperty().addListener((observable,oldvalue,newvalue)->{
			
			Autocompletator.search(newvalue);
			FindLocal.find(newvalue);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(newvalue), null);
			
		});
		EventHandler<KeyEvent> evhand=new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ENTER) {
					add.requestFocus();
				}
				
			}
			
		};
		eng.setOnKeyPressed(evhand);
		
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
		
		engExamp1.setOnKeyPressed(evhand);
		engExamp2.setOnKeyPressed(evhand);
		engExamp3.setOnKeyPressed(evhand);
		espExamp1.setOnKeyPressed(evhand);
		espExamp2.setOnKeyPressed(evhand);
		espExamp3.setOnKeyPressed(evhand);
		
		indications = new TextArea();
		indications.setFont(textf);
		indications.maxWidthProperty().bind(grid.widthProperty().subtract(20));
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
		
		esp1.setOnKeyPressed(evhand);
		esp2.setOnKeyPressed(evhand);
		esp3.setOnKeyPressed(evhand);
		
		use1 = new TextField();
		use2 = new TextField();
		use3 = new TextField();
		
		use1.setFont(textf);
		use2.setFont(textf);
		use3.setFont(textf);
		
		use1.setOnKeyPressed(evhand);
		use2.setOnKeyPressed(evhand);
		use3.setOnKeyPressed(evhand);
		
		pronun = new TextField();
		pronun.setFont(textf);
		add= new Button("Add");
		add.maxWidthProperty().bind(grid.widthProperty());
		add.setFont(MainWindow.text);
		add.setOnAction((ActionEvent e)->{
			if(Facade.selectSome(Facade.autotrim(eng.getText())).size()!=0) {
				
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	ConfirmWindow m= new ConfirmWindow("This word is already in the system. Are you sure you wan to include it?");
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
			else {
				add();
			}
			eng.requestFocus();
			
		});
		add.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) add.getOnAction().handle(new ActionEvent());
			
		});
		engExamp1.prefWidthProperty().bind(grid.widthProperty());
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
		GridPane.setFillWidth(use1, true);
		GridPane.setFillWidth(use2, true);
		GridPane.setFillWidth(use3, true);
		GridPane.setFillWidth(pronun, true);
		GridPane.setFillWidth(pronunciation,true);
		GridPane.setFillWidth(add,true);
		GridPane.setFillWidth(indications, true);
		
		GridPane.setHalignment(ex1, HPos.CENTER);
		GridPane.setHalignment(ex2, HPos.CENTER);
		GridPane.setHalignment(ex3, HPos.CENTER);
		GridPane.setHalignment(indicate, HPos.CENTER);
		GridPane.setHalignment(pronunciation, HPos.CENTER);
		
		HBox h= new HBox();
		h.setAlignment(Pos.CENTER);
		HBox.setMargin(eng, new Insets(0,10*scale,0,10*scale));
		HBox.setHgrow(eng, Priority.ALWAYS);
		h.getChildren().addAll(ing,eng,state);
		VBox top= new VBox();
		top.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		VBox.setMargin(menu, new Insets(0,0,10*scale,0));
		top.getChildren().addAll(menu,tit);
		
		grid.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10*scale);
		grid.setVgap(10*scale);
		int row=0;
		//grid.add(menu, 			0, row, 2, 1); row++;
		//grid.add(tit, 			0, row, 2, 1); row++;
		grid.add(h,            	0, row ,2, 1); row++;
		grid.add(pronunciation, 0, row ,2, 1); row++;
		grid.add(pronun,        0, row ,2, 1); row++;
		
		grid.add(ex1, 			0, row ,2, 1); row++;
		grid.add(engExamp1, 	0, row ,2, 1); row++;
		grid.add(espExamp1, 	0, row ,2, 1); row++;
		grid.add(trad1, 		0, row, 1, 1); 
		grid.add(esp1, 			1, row, 1, 1); row++;
		grid.add(use_1, 		0, row, 1, 1); 
		grid.add(use1, 			1, row, 1, 1); row++;
		
		grid.add(ex2, 			0, row ,2, 1); row++;
		grid.add(engExamp2, 	0, row ,2, 1); row++;
		grid.add(espExamp2, 	0, row ,2, 1); row++;
		grid.add(trad2, 		0, row, 1, 1); 
		grid.add(esp2, 			1, row, 1, 1); row++;
		grid.add(use_2, 		0, row, 1, 1); 
		grid.add(use2, 			1, row, 1, 1); row++;
		
		grid.add(ex3, 			0, row ,2, 1); row++;
		grid.add(engExamp3, 	0, row ,2, 1); row++;
		grid.add(espExamp3, 	0, row ,2, 1); row++;
		grid.add(trad3, 		0, row, 1, 1); 
		grid.add(esp3, 			1, row, 1, 1); row++;
		grid.add(use_3, 		0, row, 1, 1); 
		grid.add(use3, 			1, row, 1, 1); row++;
		
		grid.add(indicate, 		0, row ,2, 1); row++;
		grid.add(indications, 	0, row ,2, 1); row++;
		
		grid.add(add,           0, row ,2, 1); row++; 
		
		SplitPane ss= new SplitPane();
		tot= new VBox();
		s= new SplitPane();
		
		s.getItems().add(0,grid);
		s.getItems().add(1,ss);
		grid.prefWidthProperty().bind(s.widthProperty());
		
		s.setDividerPosition(0, 0.65);
		Text t2 = new Text("Local suggestions");
		Text t = new Text("WR suggestions");
		t.setFont(MainWindow.head);
		t2.setFont(MainWindow.head);
		FlowPane a= new FlowPane(t);
		a.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		FlowPane a2= new FlowPane(t2);
		a2.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		scrp=new ScrollPane(a);
		scrplocal= new ScrollPane(a2);
		ss.setOrientation(Orientation.VERTICAL);
		ss.getItems().addAll(scrp,scrplocal);
		
		tot.getChildren().addAll(top,s);
		Scene scene = new Scene(tot,I_w*scale,I_h*scale);
		primaryStage.setX(primaryStage.getX()-(I_w*scale-width)/2);
		primaryStage.setY(primaryStage.getY()-(I_h*scale+22-height)/2);
		return scene;
		
	}
	public static void finishInsert() {
		eng.clear();
		eng.setPromptText("Enter successfully");
		eng.requestFocus();
		
	}
	public static void setWRsuggestions(ArrayList<String> elements) {
		VBox whole= new VBox();
		Text t = new Text("WR suggestions");
		t.setFont(MainWindow.head);

		FlowPane res= new FlowPane();
		res.setVgap(10);
		res.setHgap(10);
		Button[] autoc= new Button[elements.size()];
		for(int i=0;i<elements.size();++i) {
			autoc[i]= new Button(elements.get(i));
			final String s=elements.get(i);
			autoc[i].setOnAction(e->{
				AutoselectWindow a = new AutoselectWindow(s);
			});
			HBox.setMargin(autoc[i], new Insets(0,10*scale,0,0));
			res.getChildren().add(autoc[i]);
		}
		whole.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		res.prefWidthProperty().bind(tot.widthProperty().subtract(grid.widthProperty()).subtract(30));
	    s.setDividerPosition(0, 0.65);
	    VBox.setMargin(res, new Insets(10*scale,0,0,0));
	    whole.getChildren().addAll(t,res);
		scrp.setContent(whole);
	}
	public static void setLocalsuggestions(ArrayList<Ficha>candidates) {
		VBox whole= new VBox();
		Text t = new Text("Local suggestions");
		t.setFont(MainWindow.head);
		FlowPane res= new FlowPane();
		res.setVgap(10);
		res.setHgap(10);
		
		Button[] autoc= new Button[candidates.size()];
		for(int i=0;i<candidates.size();++i) {
			autoc[i]= new Button(candidates.get(i).getEnglish());
			final Ficha s=candidates.get(i);
			autoc[i].setOnAction(e->{
				MainWindow.detail(s,primaryStage.getWidth(),primaryStage.getHeight());
			});
			HBox.setMargin(autoc[i], new Insets(0,10*scale,0,0));
			res.getChildren().add(autoc[i]);
		}
		whole.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		res.prefWidthProperty().bind(tot.widthProperty().subtract(grid.widthProperty()).subtract(30));
		s.setDividerPosition(0, 0.65);
		whole.getChildren().addAll(t,res);
		VBox.setMargin(res, new Insets(10*scale,0,0,0));
		scrplocal.setContent(whole);
	}
	public static void updateStatus(boolean alreadypresent) {
		if(alreadypresent)state.setText("Not new");
		else state.setText("New");
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
			mssgWindow();
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
		grid.add(ex_eng, 0, 2,2,1);
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
				MainWindow.detail(f,primaryStage.getWidth(),primaryStage.getHeight());
				}
			}
			
		});
		table.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ENTER) {
					Ficha f=new Ficha(table.getSelectionModel().getSelectedItem());
					MainWindow.detail(f,primaryStage.getWidth(),primaryStage.getHeight());
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
					
					MainWindow.confirmWindow("¿Desea eliminar "+i.size()+" filas?", v);
					data=Facade.selectAll();
					table.setItems(data);
					table.refresh();
					table.getSortOrder().clear();
					table.getSortOrder().add(english);
					mssgWindow();
				}
				else {
					//There is only one row selected
					MainWindow.confirmWindow("¿Desea eliminar "+(data.get(table.getSelectionModel().getSelectedIndex()).getEnglish())+"?",table.getSelectionModel().getSelectedItem().getDBkey());
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
					MainWindow.editWindow(new Ficha(table.getSelectionModel().getSelectedItem()),primaryStage.getWidth(),primaryStage.getHeight());
					
					
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
		Font up_head = head;
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
		scal.setFont(head);
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
			MainWindow.confirmWindowEtoS("Are you sure you want to make every register as unknown from English to Spanish?");
		});
		Button resetStoE = new Button("Spanish to English");
		resetStoE.setFont(text);
		resetStoE.setOnAction((ActionEvent e)->{
			MainWindow.confirmWindowStoE("Are you sure you want to make every register as unknown from Spanish to English?");
		});
		Text t2 = new Text ("Retrocompatibility");
		t2.setFont(head);
		t2.setTextAlignment(TextAlignment.LEFT);
		CheckBox cbb1= new CheckBox("Insert existing use data into the examples");
		cbb1.setSelected(MainWindow.cfd.convertUses);
		cbb1.setAllowIndeterminate(false);
		cbb1.setOnAction(e->{
			MainWindow.cfd.update_convertUses(cbb1.isSelected());
			MainWindow.cfd.convertUses=cbb1.isSelected();
		});
		cbb1.setFont(text);
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
		g.add(t2, 0, 4);
		g.add(cbb1, 0, 5,2,1);
		GridPane.setHalignment(cbb1, HPos.CENTER);
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
			catch(Exception ex) {exceptions=ex.getMessage();}
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
			catch(Exception ex) {exceptions=ex.getMessage(); }
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
		va.setId("tabborder");
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
		
		//Network
		
		Tab network = new Tab ("Network");
		network.setStyle("-fx-font-size:"+text.getSize()*0.8+"px;");
		
		Text autoc = new Text("Fetching autocomplete data:");
		autoc.setFont(head);
		TextField auto1 = new TextField(cfd.autoco_1);
		TextField auto2 = new TextField(cfd.autoco_2);
		auto1.setFont(textf);
		auto2.setFont(textf);
		auto1.setOnAction((ActionEvent e)->{
			cfd.update_autoco(auto1.getText(), auto2.getText());
		});
		auto2.setOnAction((ActionEvent e)->{
			cfd.update_autoco(auto1.getText(), auto2.getText());
		});
		
		Text sear = new Text("Fetching search data:");
		sear.setFont(head);
		TextField sear1 = new TextField(cfd.getWord_1);
		TextField sear2 = new TextField(cfd.getWord_2);
		sear1.setFont(textf);
		sear2.setFont(textf);
		sear1.setOnAction((ActionEvent e)->{
			cfd.update_getWord(auto1.getText(), auto2.getText());
		});
		sear2.setOnAction((ActionEvent e)->{
			cfd.update_getWord(auto1.getText(), auto2.getText());
		});
		
		GridPane g2 = new GridPane();

		auto1.prefWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(20*scale));
		auto2.prefWidthProperty().bind(primaryStage.widthProperty().divide(2).subtract(20*scale));
		g2.setHgap(10*scale);
		g2.setVgap(10*scale);
		g2.add(autoc, 0, 0,2,1);
		g2.add(auto1, 0, 1,1,1);
		g2.add(auto2, 1, 1,1,1);
		
		g2.add(sear, 0,  2,2,1);
		g2.add(sear1, 0, 3,1,1);
		g2.add(sear2, 1, 3,1,1);

		g2.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		network.setContent(g2);
		network.setClosable(false);
		
		
		
		
		consol.getContent().setId("tabborder");
		backup.getContent().setId("tabborder");
		voice.getContent().setId("tabborder");
		adjust.getContent().setId("tabborder");
		network.getContent().setId("tabborder");
		tp.getTabs().addAll(consol,backup,voice,network,adjust);
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
		scene.getStylesheets().add("Settings.css");
		primaryStage.setX(primaryStage.getX()-(A_w*scale-width)/2);
		primaryStage.setY(primaryStage.getY()-(A_h*scale+22-height)/2);
		return scene;
	}
	public void add() {
		if(!eng.getText().equals("")) {
			Ficha f = new Ficha(eng.getText(),pronun.getText(),indications.getText());
			if(!engExamp1.getText().equals("")) {
				Example e=new Example(engExamp1.getText(), espExamp1.getText(), esp1.getText());
				e.setUse(use1.getText());;
				f.addExample(e);
				
			}
			if(!engExamp2.getText().equals("")) {
				Example e=new Example(engExamp2.getText(), espExamp2.getText(), esp2.getText());
				e.setUse(use2.getText());;
				f.addExample(e);
			}
			if(!engExamp3.getText().equals("")) {
				Example e=new Example(engExamp3.getText(), espExamp3.getText(), esp3.getText());
				e.setUse(use3.getText());;
				f.addExample(e);
			}
			Facade.addDB(f);
			if(exceptions==null||exceptions.isEmpty()) {
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
				use1.clear();
				use2.clear();
				use3.clear();
				pronun.clear();
				indications.clear();
				eng.setPromptText("Enter successfully");
			}
			mssgWindow();
		}
	}
	
	
	
	public static void prepareInput(Ficha f) {
		eng.setText(f.getEnglish());
		pronun.setText(f.getPronunciation());
		int nex;
		if(f.getExampVec()!=null)nex=f.getExampVec().size();
		else nex=0;
		if(nex>0) {
			engExamp1.setText(f.getExample(0).getEng_example());
			espExamp1.setText(f.getExample(0).getEsp_example());
			esp1.setText(f.getExample(0).getTranslation());
			use1.setText(f.getExample(0).getUse());
			if(nex>1) {
				engExamp2.setText(f.getExample(1).getEng_example());
				espExamp2.setText(f.getExample(1).getEsp_example());
				esp2.setText(f.getExample(1).getTranslation());
				use2.setText(f.getExample(1).getUse());
				if(nex>2) {
					engExamp3.setText(f.getExample(2).getEng_example());
					espExamp3.setText(f.getExample(2).getEsp_example());
					esp3.setText(f.getExample(2).getTranslation());
					use3.setText(f.getExample(2).getUse());
				}
			}
		}
		indications.setText(f.getUse());
		add.requestFocus();
		
		
		
		
		
	}


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
	public static void confirmWindow(String s, Object list) {
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
	public static void confirmWindowEtoS(String s) {
		
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
	
	public static void confirmWindowStoE(String s) {
		
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
	public static void editWindow(Ficha f, double width, double height) {
		EditWindow e= new EditWindow(f);
		
		e.setX(getX()-(420-width)/2);
		e.setY(getY()-(772-height)/2);
		e.show();
	}
	public static void detail(Ficha f,double width,double height) {
		//TODO: Move to another place
		Stage resStage = new Stage();
		resStage.setScene(AuxiliarBuilder.displayFicha(f,resStage));
		resStage.setX(getX()-(AuxiliarBuilder.expWidth-width)/2);
		resStage.setY(getY()-(AuxiliarBuilder.expHeight+20-height)/2);
		resStage.show();
		
	}
	
	
	public static double getX() {
		return primaryStage.getX();
	}
	public static double getY() {
		return primaryStage.getY();
	}
	public static double getW() {
		return primaryStage.getWidth();
	}
	public static double getH() {
		return primaryStage.getHeight();
	}
	public static void main (String[]args) {
		
		launch(args);
	}
}
