package fX;

public class Autocompletator {
	private static FetchAutocomplete e;
	public static String tmp;
	public Autocompletator(){
		tmp="";
	}
	public static void search(String s){
		System.out.println("Interrupting-1");
		if(e!=null&&e.isAlive()) {
			e.interrupt();
			try {
				e.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
			e = new FetchAutocomplete(s);
			e.start();
		
		
	}
	
}
