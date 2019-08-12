package fX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class EditWindow extends Stage {
	private TextField up_eng, up_engExamp1,up_engExamp2, up_engExamp3, up_espExamp1, up_espExamp2, up_espExamp3, up_esp1, up_esp2, up_esp3, up_pronun;
	private TextArea up_indications;
	private Button update;
	private CheckBox up_known_E_1, up_known_E_2, up_known_E_3;
	private CheckBox up_known_S_1, up_known_S_2, up_known_S_3;
	private Ficha up_f;
	public EditWindow(Ficha f) {
		up_f=f;
		//TODO Add fields
		
		Text tit = new Text("Update");
		tit.setFont(MainWindow.tit);
		tit.setFontSmoothingType(FontSmoothingType.LCD);
		tit.setTextAlignment(TextAlignment.CENTER);
		Text ing = new Text("English:");
		ing.setFont(MainWindow.text);
		ing.setFontSmoothingType(FontSmoothingType.LCD);
		Text ex1 = new Text("Example 1");
		ex1.setFont(MainWindow.head);
		ex1.setFontSmoothingType(FontSmoothingType.LCD);
		ex1.setTextAlignment(TextAlignment.CENTER);
		Text ex2 = new Text("Example 2");
		ex2.setFont(MainWindow.head);
		ex2.setFontSmoothingType(FontSmoothingType.LCD);
		ex2.setTextAlignment(TextAlignment.CENTER);
		Text ex3 = new Text("Example 3");
		ex3.setFont(MainWindow.head);
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
		Text use_1 = new Text("Use:");
		use_1.setFont(MainWindow.text);
		use_1.setFontSmoothingType(FontSmoothingType.LCD);
		Text use_2 = new Text("Use:");
		use_2.setFont(MainWindow.text);
		use_2.setFontSmoothingType(FontSmoothingType.LCD);
		Text use_3 = new Text("Use:");
		use_3.setFont(MainWindow.text);
		use_3.setFontSmoothingType(FontSmoothingType.LCD);
		
		
		if(ex1!=null)GridPane.setHalignment(ex1, HPos.CENTER);
		if(ex2!=null)GridPane.setHalignment(ex2, HPos.CENTER);
		if(ex3!=null)GridPane.setHalignment(ex3, HPos.CENTER);
		
		up_eng = new TextField(f.getEnglish());
		up_engExamp1 = new TextField(f.getExample(0).getEng_example());
		up_engExamp2 = new TextField(f.getExample(1).getEng_example());
		up_engExamp3 = new TextField(f.getExample(2).getEng_example());
		up_espExamp1 = new TextField(f.getExample(0).getEsp_example());
		up_espExamp2 = new TextField(f.getExample(1).getEsp_example());
		up_espExamp3 = new TextField(f.getExample(2).getEsp_example());
		up_indications = new TextArea(f.getUse());
		up_indications.maxWidthProperty().bind(this.widthProperty());
		
		up_eng.setFont(MainWindow.textf);
		up_engExamp1.setFont(MainWindow.textf);
		up_engExamp2.setFont(MainWindow.textf);
		up_engExamp3.setFont(MainWindow.textf);
		up_espExamp1.setFont(MainWindow.textf);
		up_espExamp2.setFont(MainWindow.textf);
		up_espExamp3.setFont(MainWindow.textf);
		up_indications.setFont(MainWindow.textf);
		
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
		
		up_esp1.setFont(MainWindow.textf);
		up_esp2.setFont(MainWindow.textf);
		up_esp3.setFont(MainWindow.textf);
		
		TextField use1 = new TextField(f.getExample(0).getUse());
		TextField use2 = new TextField(f.getExample(1).getUse());
		TextField use3 = new TextField(f.getExample(2).getUse());
		
		use1.setFont(MainWindow.textf);
		use2.setFont(MainWindow.textf);
		use3.setFont(MainWindow.textf);
		
		up_pronun = new TextField(f.getPronunciation());
		update= new Button("Update");
		update.maxWidthProperty().bind(this.widthProperty());
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
						up_f.getExample(up_f.getExampVec().size()-1).setEtoSKnown(up_known_E_1.isSelected());
						up_f.getExample(up_f.getExampVec().size()-1).setStoEKnown(up_known_S_1.isSelected());
						up_f.getExample(up_f.getExampVec().size()-1).setUse(use1.getText());
					}
					
					if(!up_engExamp2.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp2.getText(), up_espExamp2.getText(), up_esp2.getText())));
						up_f.getExample(up_f.getExampVec().size()-1).setEtoSKnown(up_known_E_2.isSelected());
						up_f.getExample(up_f.getExampVec().size()-1).setStoEKnown(up_known_S_2.isSelected());
						up_f.getExample(up_f.getExampVec().size()-1).setUse(use2.getText());

					}
					
					if(!up_engExamp3.getText().equals("")) {
						up_f.addExample((new Example(up_engExamp3.getText(), up_espExamp3.getText(), up_esp3.getText())));
						up_f.getExample(up_f.getExampVec().size()-1).setEtoSKnown(up_known_E_3.isSelected());
						up_f.getExample(up_f.getExampVec().size()-1).setStoEKnown(up_known_S_3.isSelected());
						up_f.getExample(up_f.getExampVec().size()-1).setUse(use3.getText());

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
					MainWindow.mssgWindow("Success");
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				MainWindow.mssgWindow();
				this.close();
				MainWindow.data=Facade.selectAll();
				MainWindow.table.setItems(MainWindow.data);
				MainWindow.table.getSortOrder().add(MainWindow.table.getColumns().get(1));
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
		up_engExamp3.prefWidthProperty().bind(this.widthProperty().subtract(30).subtract(up_known_E_1.widthProperty()));
		GridPane grid = new GridPane();
		grid.minWidthProperty().bind(this.widthProperty().subtract(30));
		grid.maxWidthProperty().bind(this.maxWidthProperty());
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		int row=0;
		grid.add           (tit, 0, row, 3, 1);row++;
		grid.add           (ing, 0, row);
		grid.add        (up_eng, 1, row, 2, 1);row++;
		grid.add           (ex1, 0, row, 3, 1);row++;
		grid.add  (up_engExamp1, 0, row, 2, 1);
		grid.add  (up_known_E_1, 2, row, 1, 1);row++;
		grid.add  (up_espExamp1, 0, row, 2, 1);
		grid.add  (up_known_S_1, 2, row, 1, 1);row++;
		grid.add         (trad1, 0, row);
		grid.add       (up_esp1, 1, row, 2, 1);row++;
		grid.add         (use_1, 0, row, 1, 1);
		grid.add          (use1, 1, row, 2, 1);row++;
		grid.add           (ex2, 0, row, 3, 1);row++;
		grid.add  (up_engExamp2, 0, row, 2, 1);
		grid.add  (up_known_E_2, 2, row, 1, 1);row++;
		grid.add  (up_espExamp2, 0, row, 2, 1);
		grid.add  (up_known_S_2, 2, row, 1, 1);row++;
		grid.add         (trad2, 0, row);
		grid.add       (up_esp2, 1, row, 2, 1);row++;
		grid.add         (use_2, 0, row, 1, 1);
		grid.add          (use2, 1, row, 2, 1);row++;
		grid.add           (ex3, 0, row, 3, 1);row++;
		grid.add  (up_engExamp3, 0, row, 2, 1);
		grid.add  (up_known_E_3, 2, row, 1, 1);row++;
		grid.add  (up_espExamp3, 0, row, 2, 1);
		grid.add  (up_known_S_3, 2, row, 1, 1);row++;
		grid.add         (trad3, 0, row);
		grid.add       (up_esp3, 1, row, 2, 1);row++;
		grid.add         (use_3, 0, row, 1, 1);
		grid.add          (use3, 1, row, 2, 1);row++;
		
		grid.add      (indicate, 0, row, 3, 1);row++;
		grid.add(up_indications, 0, row, 3, 1);row++;
		grid.add (pronunciation, 0, row, 3, 1);row++;
		grid.add     (up_pronun, 0, row, 3, 1);row++;
		grid.add        (update, 0, row, 3, 1);row++;
		Scene scene = new Scene(grid,510,750);
		this.setScene(scene);
	}
}
