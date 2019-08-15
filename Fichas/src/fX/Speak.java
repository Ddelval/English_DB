package fX;

public class Speak extends Thread {
	private String text;
	private String voice;
	private int tim,speed; 
	public Speak(String t,String v,int s) {
		text=t;
		voice=v;
		speed=s;
		tim=0;
	}
	public Speak(String t,String v,int ti,int s) {
		text=t;
		voice=v;
		speed=s;
		tim=ti;
	}
	public void run() {
		try {
			if(tim!=0)sleep(tim);
			String s="say -v "+voice+" -r "+Integer.toString(speed)+" "+text;
			Process p=Runtime.getRuntime().exec(s);
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
		}
	}
}
