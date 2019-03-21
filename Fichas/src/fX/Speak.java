package fX;

public class Speak extends Thread {
	private String text;
	private String voice;
	public Speak(String t,String v) {
		text=t;
		voice=v;
	}
	public void run() {
		try {
			Process p=Runtime.getRuntime().exec("say -v "+voice+" "+text);
			int exitVal =p.waitFor();
			if(exitVal==0) {
				//Success
			}
			else {
				throw new Exception("Unknown command line error");
			}
		}
		catch (Exception ex) {
			MainWindow.exceptions=ex.getMessage();
			MainWindow.mssgWindow(MainWindow.primaryStage.getWidth(), MainWindow.primaryStage.getHeight());
		}
	}
}
