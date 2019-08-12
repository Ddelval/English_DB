package fX;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Mssgwindow extends Stage {
	public Mssgwindow(String s) {
		double scale=MainWindow.scale;
		Label l = new Label(s);
		l.setTextAlignment(TextAlignment.CENTER);
		l.minWidthProperty().bind(this.widthProperty());
		l.setPadding(new Insets(10*scale,10*scale,10*scale,10*scale));
		l.setWrapText(true);
		l.setFont(MainWindow.text);
		l.requestFocus();
		l.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				close();
				
			}
			
		});
		
		double wid,hei;
		wid=Math.max(Math.min(l.getPrefWidth()+30*scale,800*scale),300*scale);
		hei=l.getBoundsInLocal().getHeight()+50*scale;
		Scene sc = new Scene(l,wid,hei);
		this.setScene(sc);
		setX(MainWindow.primaryStage.getX()-(wid-MainWindow.primaryStage.getWidth())/2);
		setY(MainWindow.primaryStage.getY()-(hei+12-MainWindow.primaryStage.getHeight())/2);
		this.show();
		
	}
}
