package fX;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class FindLocal {
	public static Stack<LocalHistory> his;
	private static FindLocal_run th;
	public static void find(String s) {
		if(th!=null&&th.isAlive()) {
			System.out.println("Interrupting-2");
			th.interrupt();
			try {
				th.join();
			}
			catch(InterruptedException e) {
				//TODO catch block
				e.printStackTrace();
			}
		}
		th=new FindLocal_run(s);
		th.start();
		
	}
}


class LocalHistory{
	public String querry;
	public ArrayList<Ficha> res;
	public LocalHistory(ArrayList<Ficha> a, String b) {
		res=a;
		querry=b;
	}
	public boolean contains(String s) {
		if(querry.equals("")||s.startsWith(querry))return true;
		return false;
	}
	
}