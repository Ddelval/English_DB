package fX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ConfirmWindow extends Stage {
	private Label l;
	private Button b1,b2;
	private GridPane grid;
	private Scene sc1;
	public ConfirmWindow(String s) {
		double sc=MainWindow.scale;
		l = new Label(s);
		l.setTextAlignment(TextAlignment.CENTER);
		l.minWidthProperty().bind(widthProperty().subtract(30*sc));
		l.setPadding(new Insets(10*sc,10*sc,10*sc,10*sc));
		l.setWrapText(true);
		l.setFont(MainWindow.text);
		b2 = new Button("Confirm");
		b2.setFont(MainWindow.text);
		b2.minWidthProperty().bind(widthProperty().divide(2).subtract(30*sc));
		b1 = new Button ("Cancel");
		b1.setFont(MainWindow.text);
		b1.minWidthProperty().bind(widthProperty().divide(2).subtract(30*sc));
		grid = new GridPane();
		grid.setPadding(new Insets(10*sc,10*sc,10*sc,10*sc));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10*sc);
		grid.setVgap(10*sc);
		grid.add(l, 0, 0, 2, 1);
		grid.add(b1, 0, 1);
		grid.add(b2, 1, 1);
		double wid,hei;
		wid=Math.max(Math.min(grid.getPrefWidth()+30*sc,800*sc),300*sc);
		hei=grid.getPrefHeight()+120*sc;
		sc1 = new Scene(grid,wid,hei);
		setScene(sc1);
		setX(MainWindow.getX()-(wid-MainWindow.getW())/2);
		setY(MainWindow.getY()-(hei-MainWindow.getH())/2);
		show();
		b1.requestFocus();
	}
	public void setCancelEvent(EventHandler<ActionEvent> e) {
		b1.setOnAction(e);
		b1.setOnKeyPressed(ev->{
			if(ev.getCode()==KeyCode.ENTER)b1.getOnAction().handle(new ActionEvent());
		});
	}
	public void setOkEvent(EventHandler<ActionEvent> e) {
		b2.setOnAction(e);
		b2.setOnKeyPressed(ev->{
			if(ev.getCode()==KeyCode.ENTER)b2.getOnAction().handle(new ActionEvent());
		});
	}
}
