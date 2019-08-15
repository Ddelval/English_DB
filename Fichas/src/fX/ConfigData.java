package fX;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class ConfigData {
	public String backupPath;
	public int s_delay,s_speed;
	public boolean s_readauto,s_showall;
	public String voice;
	public double scaling;
	public String getWord_1,getWord_2;
	public String autoco_1,autoco_2;
	public boolean convertUses;
	
	public final static int C_s_delay=1000,C_s_speed=170;
	public final static boolean C_s_readauto = false, C_s_showall=false;
	public final static String C_voice="Alex";
	public final static double C_scaling=1;
	
	public final static String C_getWord_1="curl 'https://www.wordreference.com/es/translation.asp?tranword=";
	public final static String C_getWord_2="' -H 'authority: www.wordreference.com' -H 'cache-control: max-age=0' -H 'upgrade-insecure-requests: 1' -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36' -H 'sec-fetch-mode: navigate' -H 'sec-fetch-user: ?1' -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3' -H 'sec-fetch-site: none' -H 'accept-encoding: gzip, deflate, br' -H 'accept-language: es-ES,es;q=0.9,en;q=0.8' -H 'cookie: _ga=GA1.2.704726212.1532023789; per_Mc_x=cae8e901f6c944e48ad7f084342f1e42; euconsent=BORKE6BORKE6BABABAESBS-AAAAeZ7_______9______9uz_Gv_v_f__33e8__9v_l_7_-___u_-33d4-_1vX99yfm1-7ftr1tp386ues2LDiCA; llang=enesi; per24h=1; _gid=GA1.2.428927050.1565467301; per24c=2; per_M_08=2' --compressed  >web.txt";
	
	public final static String C_autoco_1="curl 'https://www.wordreference.com/2012/autocomplete/autocomplete.aspx?dict=enes&query=";
	public final static String C_autoco_2="' -H 'authority: www.wordreference.com' -H 'cache-control: max-age=0' -H 'upgrade-insecure-requests: 1' -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36' -H 'sec-fetch-mode: navigate' -H 'sec-fetch-user: ?1' -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3' -H 'sec-fetch-site: none' -H 'accept-encoding: gzip, deflate, br' -H 'accept-language: es-ES,es;q=0.9,en;q=0.8' -H 'cookie: _ga=GA1.2.704726212.1532023789; per_Mc_x=cae8e901f6c944e48ad7f084342f1e42; euconsent=BORKE6BORKE6BABABAESBS-AAAAeZ7_______9______9uz_Gv_v_f__33e8__9v_l_7_-___u_-33d4-_1vX99yfm1-7ftr1tp386ues2LDiCA; llang=enesi; per24h=1; _gid=GA1.2.428927050.1565467301; per24c=2; per_M_08=2' --compressed  >com.txt";
	
	public final static boolean C_convertUses=false;
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		aux=Facade.getConfig(scalname);
		MainWindow.mssgWindow();
		if(aux!="")scaling=Double.parseDouble(aux);
		else scaling=C_scaling;
		
		aux=Facade.getConfig("getWord_1");
		MainWindow.mssgWindow();
		if(aux!="")getWord_1=aux;
		else getWord_1=C_getWord_1;
		
		aux=Facade.getConfig("getWord_2");
		MainWindow.mssgWindow();
		if(aux!="")getWord_2=aux;
		else getWord_2=C_getWord_2;
		
		aux=Facade.getConfig("autoco_1");
		MainWindow.mssgWindow();
		if(aux!="")getWord_1=aux;
		else autoco_1=C_autoco_1;
		
		aux=Facade.getConfig("autoco_2");
		MainWindow.mssgWindow();
		if(aux!="")getWord_2=aux;
		else autoco_2=C_autoco_2;
		
		aux=Facade.getConfig("convertUses");
		MainWindow.mssgWindow();
		if(aux!="")convertUses=(aux.equals("0"))? false:true;
		else convertUses=C_convertUses;
		
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
	public void update_autoco(String s1,String s2){
		autoco_1=s1;
		autoco_2=s2;
		Facade.updateConfig("autoco_1", autoco_1);
		MainWindow.mssgWindow();
		Facade.updateConfig("autoco_2", autoco_2);
		MainWindow.mssgWindow();
	}
	public void update_getWord(String s1,String s2){
		getWord_1=s1;
		getWord_2=s2;
		Facade.updateConfig("getWord_1", getWord_1);
		MainWindow.mssgWindow();
		Facade.updateConfig("getWord_2", getWord_2);
		MainWindow.mssgWindow();
	}
	public void update_convertUses (boolean b) {
		convertUses=b;
		Facade.updateConfig("convertUses", b? "1":"0");
		MainWindow.mssgWindow();
	}
}
