package fX;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConfigData {
	public String backupPath;
	public int s_delay,s_speed;
	public boolean s_readauto,s_showall;
	public String voice;
	public double scaling;
	
	public final static int C_s_delay=1000,C_s_speed=170;
	public final static boolean C_s_readauto = false, C_s_showall=false;
	public final static String C_voice="Alex";
	public final static double C_scaling=1;
	
	public static String scalname;
	public ConfigData() {
		String aux;
		backupPath=Facade.getConfig("backupPath");
		MainWindow.mssgWindow();
		
		aux=Facade.getConfig("s_delay");
		MainWindow.mssgWindow();
		if(aux!="")s_delay=Integer.parseInt(aux);
		else s_delay=C_s_delay;
		
		aux=Facade.getConfig("s_speed");
		MainWindow.mssgWindow();
		if(aux!="")s_speed=Integer.parseInt(aux);
		else s_speed=C_s_speed;
		
		aux=Facade.getConfig("s_readauto");
		MainWindow.mssgWindow();
		if(aux!="")s_readauto=(aux.equals("0"))? false:true;
		else s_readauto=C_s_readauto;
		
		aux=Facade.getConfig("s_showall");
		MainWindow.mssgWindow();
		if(aux!="")s_showall=(aux.equals("0"))? false:true;
		else s_showall=C_s_showall;
		
		aux=Facade.getConfig("s_speed");
		MainWindow.mssgWindow();
		if(aux!="")s_speed = Integer.parseInt(aux);
		else s_speed= C_s_speed;
		
		voice=Facade.getConfig("voice");
		MainWindow.mssgWindow();
		
		scalname="scaling";
		try {
			byte[] mac=NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			for(int i=0;i<mac.length;i++) {
				scalname+=String.format("%02X%s", mac[i],(i< mac.length-1)? "-":" ");
			}
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		aux=Facade.getConfig(scalname);
		MainWindow.mssgWindow();
		if(aux!="")scaling=Double.parseDouble(aux);
		else scaling=C_scaling;
		
	}
	public void update_backupPath (String s) {
		backupPath=s;
		Facade.updateConfig("backupPath", s);
		MainWindow.mssgWindow();
	}
	public void update_s_delay (int i) {
		s_delay=i;
		Facade.updateConfig("s_delay", Integer.toString(i));
		MainWindow.mssgWindow();
	}
	public void update_s_speed (int i) {
		s_speed=i;
		Facade.updateConfig("s_speed", Integer.toString(i));
		MainWindow.mssgWindow();
	}
	public void update_s_readauto (boolean b) {
		s_readauto=b;
		Facade.updateConfig("s_readauto", b? "1":"0");
		MainWindow.mssgWindow();
	}
	public void update_s_showall (boolean b) {
		s_showall=b;
		Facade.updateConfig("s_showall", b? "1":"0");
		MainWindow.mssgWindow();
	}
	public void update_voice (String s) {
		voice=s;
		Facade.updateConfig("voice", s);
		MainWindow.mssgWindow();
	}
	public void update_scaling (Double d) {
		scaling=d;
		Facade.updateConfig(scalname, Double.toString(d));
		MainWindow.mssgWindow();
	}
}
