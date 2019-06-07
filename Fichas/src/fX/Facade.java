package fX;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Facade {
	public final static int s_speed=170;
	static Speak sp;
	public static String basicfilter(String s) {
		if(s.equals("")) {
			return s;
		}
		else {
			while(s.charAt(0)==' ') {
				s=s.substring(1);
			}
			while(s.charAt(s.length()-1)==' ') {
				s=s.substring(0, s.length()-1);
			}
			return s;
		}
		
	}
	public static String autotrim(String s) {
		if(s.equals("")) {
			return s;
		}
		else {
			while(s.charAt(0)==' ' || s.charAt(0)=='\n'|| s.charAt(0)=='.') {
				s=s.substring(1);
			}
			while(s.charAt(s.length()-1)==' ' || s.charAt(s.length()-1)=='\n'|| s.charAt(s.length()-1)=='.') {
				s=s.substring(0,s.length()-1);
			}
			for(int i = 1; i<s.length();i++) {
				if(s.charAt(i)==' ' || s.charAt(i)=='\n'|| s.charAt(i)=='.') {
					if(s.charAt(i-1)==s.charAt(i)) {
						s=s.substring(0,i)+s.substring(i+1);
					}
				}
			}
			s.replace("\n ", "\n");
			return s;
		}
	}
	/**
	 *  This method creates a file that starts with the length of the database, then a separator(\n\n@\n) and then all Ficha objects with separators between them
	 * @param path:the path to which the file is written
	 */
	public static void backup(String path) {
		StringBuilder sbp = new StringBuilder(path);
		StringBuilder sb = new StringBuilder("");
		ArrayList <Ficha> arl=getAll();
		sb.append(Integer.toString(arl.size()));
		sb.append("\n\n@#\n");
		for(int i= 0; i<arl.size();i++) {
			sb.append(arl.get(i).toString().replaceAll("'", "%&/%"));
			sb.append("\n\n@#\n");
		}
		sbp.append(LocalDate.now().toString());
		sbp.append("_");
		String s= LocalTime.now().toString();
		s=s.replaceAll(":", "_");
		s=s.replace('.', '_');
		sbp.append(s);
		sbp.append(".fichasbackup");
		Path file = Paths.get(sbp.toString());
		try {
			Files.write(file, sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainWindow.exceptions=e.getMessage();
		}
	}
	public static void restore(String path) {
		Path file = Paths.get(path);
		String data="";
		try {
			data = new String(Files.readAllBytes(file));
			int size =Integer.parseInt(data.substring(0, data.indexOf("\n")));
			String aux="";
			ArrayList<Ficha> arrl = new ArrayList<Ficha>();
			for(int i=0; i<(size);i++) {
				data=data.substring(data.indexOf("\n\n@#\n")+5);
				aux=data.substring(0,data.indexOf("\n\n@#\n"));
				arrl.add(new Ficha(aux));
				
			}
			
			
			try {
				execute("DROP TABLE log",false);
			}
			catch(Exception ex) {
				
			}
			execute("CREATE TABLE log (count INTEGER IDENTITY, eng VARCHAR(100), Examp VARCHAR(10000), pronunciation VARCHAR(100), use VARCHAR (10000), knownEtoS BOOLEAN DEFAULT FALSE NOT NULL,knownStoE BOOLEAN DEFAULT FALSE NOT NULL, PRIMARY KEY (count))",false);
			insertAll(arrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainWindow.exceptions=e.getMessage();
		}
		
	}
	public static void insertAll(ArrayList<Ficha> arrl) {
		Connection con = UConnection.getConnection();
		try {
			for(int i=0;i<arrl.size();i++) {
				String sql="INSERT INTO log(eng,Examp,use, pronunciation,knownEtoS,knownStoE)  VALUES ("+arrl.get(i).toSQLString()+",'"+arrl.get(i).getKnownEtoS()+"','"+arrl.get(i).getKnownStoE()+"')";
				PreparedStatement ps= con.prepareStatement(sql);
				ps.execute();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
			MainWindow.exceptions=e.getMessage();
		}
	} 
	public static String getConfig(String id) {
		Connection con=UConnection.getConnection();
		String s="";
		try {
			String sql="SELECT (data) FROM config WHERE id='"+id+"'";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs =ps.executeQuery();
			while(rs.next()) {
				s=rs.getString("data");
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
		return s;
	}
	public static void updateConfig(String id, String newValue) {
		Connection con=UConnection.getConnection();
		boolean b=false;
		try {
			String sql="UPDATE config SET data='"+newValue+"' WHERE id='"+id+"'";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			if(ps.getUpdateCount()==0) {
				throw new Exception("The row could not be found");
			}
			
		}
		catch(Exception ex) {
			b=false;
			try {
				String sql="INSERT INTO config values ('"+id+"','"+newValue+"')";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.execute();
				b=true;
			}
			catch(Exception e){b=false;}
			if(b) return;
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
		
	}
	
	public static void addDB(Ficha f) {
		Connection con = UConnection.getConnection();
		try {
			if(f.getExamplesString().length()>10000) {
				throw new Exception("Too long");
			}
			String sql="INSERT INTO log(eng,Examp,use, pronunciation)  VALUES ("+f.toSQLString()+")";
			
			PreparedStatement ps= con.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
			MainWindow.exceptions=e.getMessage();
		}
	} 
	public static void updateFichaKnownEtoS(boolean b, Ficha f) 
	{
		Connection con = UConnection.getConnection();
		String sql  = "UPDATE log SET knownEtoS='"+ Boolean.toString(b).toUpperCase() + "' WHERE count="+f.getDBkey();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
	}
	public static void updateFichaKnownStoE(boolean b, Ficha f) 
	{
		Connection con = UConnection.getConnection();
		String sql  = "UPDATE log SET knownStoE='"+ Boolean.toString(b).toUpperCase() + "' WHERE count="+f.getDBkey();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
	}
	public static void updateAllKnownEtoS(boolean b) 
	{
		Connection con = UConnection.getConnection();
		String sql  = "SELECT count, Examp, known FROM log WHERE known='"+Boolean.toString(b).toUpperCase()+"'";
		ResultSet rs;
		Ficha f;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				f = new Ficha("","", "");
				f.setSQLExamples(rs.getString("Examp"));
				f.setUnknownEtoS();
				ps=con.prepareStatement("UPDATE log SET Examp='"+f.getExamplesString().replace("'","\"%&/%\"")+"', known='FALSE' WHERE count="+Integer.toString(rs.getInt("count")));
				ps.executeUpdate();
			}
			
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
	}
	public static void updateAllKnownStoE(boolean b) 
	{
		Connection con = UConnection.getConnection();
		String sql  = "SELECT count, Examp, known FROM log WHERE known='"+Boolean.toString(b).toUpperCase()+"'";
		ResultSet rs;
		Ficha f;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				f = new Ficha("","", "");
				f.setSQLExamples(rs.getString("Examp"));
				f.setUnknownStoE();
				ps=con.prepareStatement("UPDATE log SET Examp='"+f.getExamplesString().replace("'","\"%&/%\"")+"', known='FALSE' WHERE count="+Integer.toString(rs.getInt("count")));
				ps.executeUpdate();
			}
			
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
	}
	public static void updateFicha(Ficha f) {
		Connection con = UConnection.getConnection();
		PreparedStatement ps;
		String sql="UPDATE log SET eng='"+f.getEnglish().replace("'", "%&/%")+"', Examp='"+f.getExamplesString().replace("'", "%&/%")
				  +"', pronunciation='"+f.getPronunciation().replace("'", "%&/%")+"', use='"+f.getUse().replace("'", "%&/%")+"', knownEtoS='"+Boolean.toString(f.getKnownEtoS())+"', knownStoE='"+Boolean.toString(f.getKnownStoE())+"' WHERE count="+Integer.toString(f.getDBkey());
		try
		{
			ps=con.prepareStatement(sql);
			ps.execute();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
	}
	public static boolean checkAvailabilityEtoS() {
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log WHERE knownEtoS='FALSE'";
		boolean b = false;
		try 
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) 
			{
				b=true;
			}
			else {
				MainWindow.exceptions="All stored words are already known";
			}
			
		}
		catch(Exception ex) {
			
			MainWindow.exceptions=ex.getMessage();
			
		}
		return b;		
	}
	public static boolean checkAvailabilityStoE() {
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log WHERE knownEtoS='FALSE'";
		boolean b = false;
		try 
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) 
			{
				b=true;
			}
			else {
				MainWindow.exceptions="All stored words are already known";
			}
			
		}
		catch(Exception ex) {
			
			MainWindow.exceptions=ex.getMessage();
			
		}
		return b;		
	}
	/**
	 * 
	 * @param sql The statement to be executed
	 * @param result whether or not there is a result expected
	 * @return
	 */
	public static String execute (String sql, boolean result) {
		String s="";
		int colcount;
		Connection con = UConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs=null;
		try {
			ps=con.prepareStatement(sql);
			if(result) {
				rs=ps.executeQuery();
				if(rs.next()){
					StringBuilder sb = new StringBuilder("");
				do {
					colcount=rs.getMetaData().getColumnCount();
					for(int i=1;i<=colcount;++i) {
						sb.append(rs.getMetaData().getColumnLabel(i));
						sb.append(": ");
						sb.append(rs.getObject(i).toString());
						sb.append("\n");
					}
					sb.append("\n\n");
					
					
					
				}while(rs.next());
				s=sb.toString();
				}
					
				else {
					s="There is no result to show";
				}
			}
			
			else {
				//No result expected
				 s = Integer.toString(ps.executeUpdate());
			}
			
		}
		catch(SQLException ex) {
			s=ex.getMessage();
			ex.printStackTrace();
		}
		return s;
	}
	public static void eliminateFicha(Vector <ObservableFicha> list) {
		Connection con = UConnection.getConnection();
		PreparedStatement ps;
		String sql;
		for (int j=0; j<list.size();j++){
			sql ="DELETE FROM log WHERE count="+Integer.toString(list.elementAt(j).getDBkey());
			try 
			{
				ps=con.prepareStatement(sql);
				ps.execute();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
				MainWindow.exceptions=ex.getMessage();
			}
		}
		
	}
	public static void eliminateFicha(int index) {
		Connection con = UConnection.getConnection();
		PreparedStatement ps;
		String sql="DELETE FROM log WHERE count="+Integer.toString(index);
		try 
		{
			con.setAutoCommit(false);
			ps=con.prepareStatement(sql);
			if(ps.executeUpdate()==1) {
				con.commit();
			}
			else {
				con.rollback();
				MainWindow.exceptions="The deletion may affect more than one row. It has been cancelled";
			}
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
		
		
	}
	public static ObservableList<ObservableFicha> selectAll (){
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log ORDER BY count";
		List<ObservableFicha> l = new ArrayList<ObservableFicha>();
		Ficha f= new Ficha("","","");
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				f = new Ficha(rs.getString("eng"),rs.getString("pronunciation"),rs.getString("use"));
				f.setSQLExamples(rs.getString("Examp"));
				f.setDBkey(rs.getInt("count"));
				f.setKnownEtoS(rs.getBoolean("knownEtoS"));
				f.setKnownStoE(rs.getBoolean("knownStoE"));
				l.add(new ObservableFicha(f));
			}
			
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
			
		}
		
		return FXCollections.observableList(l);
	}
	public static ObservableList<ObservableFicha> selectSome (String s){
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log ";
		sql+="WHERE eng='"+s.replace("'", "%&/%");
		sql+="' ORDER BY count";
		List<ObservableFicha> l = new ArrayList<ObservableFicha>();
		Ficha f= new Ficha("","","");
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				f = new Ficha(rs.getString("eng"),rs.getString("pronunciation"),rs.getString("use"));
				f.setSQLExamples(rs.getString("Examp"));
				f.setDBkey(rs.getInt("count"));
				f.setKnownEtoS(rs.getBoolean("knownEtoS"));
				f.setKnownStoE(rs.getBoolean("knownStoE"));
				l.add(new ObservableFicha(f));
			}
			
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
			
		}
		
		return FXCollections.observableList(l);
	}
	public static Ficha getRndFichaEtoS () {
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log WHERE knownEtoS='FALSE' ORDER BY RAND() LIMIT 1";
		Ficha f= new Ficha("","","");
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			rs.next();
			f = new Ficha(rs.getString("eng"),rs.getString("pronunciation"),rs.getString("use"));
			f.setSQLExamples(rs.getString("Examp"));
			f.setDBkey(rs.getInt("count"));
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
			
		}
		
		return f;
		
	}
	public static Ficha getRndFichaStoE () {
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log WHERE knownStoE='FALSE' ORDER BY RAND() LIMIT 1";
		Ficha f= new Ficha("","","");
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			rs.next();
			f = new Ficha(rs.getString("eng"),rs.getString("pronunciation"),rs.getString("use"));
			f.setSQLExamples(rs.getString("Examp"));
			f.setDBkey(rs.getInt("count"));
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
			
		}
		
		return f;
		
	}
	public static ArrayList<Ficha> getAll(){
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log ORDER BY count";
		ArrayList<Ficha> l = new ArrayList<Ficha>();
		Ficha f= new Ficha("","","");
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				f = new Ficha(rs.getString("eng"),rs.getString("pronunciation"),rs.getString("use"));
				f.setSQLExamples(rs.getString("Examp"));
				f.setDBkey(rs.getInt("count"));
				f.setKnownEtoS(rs.getBoolean("knownEtoS"));
				l.add(f);
			}
			
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
			
		}
		
		return l;
	}
	public static void Speak(String s) {
		int speed;
		String v=Facade.getConfig("voice");
		String au=Facade.getConfig("s_speed");
		if(au!="") {
			speed =Integer.parseInt(au);
		}
		else {
			speed=s_speed;
		}
		if(sp!=null&&sp.isAlive()) return;
		sp= new Speak(s,v,speed);
		sp.start();
	}
	public static void SpeakwDelay(String s) {
		int speed,delay;
		String v=Facade.getConfig("voice");
		String au=Facade.getConfig("s_speed");
		String del=Facade.getConfig("s_delay");
		if(au!="") {
			speed =Integer.parseInt(au);
		}
		else {
			speed=s_speed;
		}
		if(del!="") {
			delay=Integer.parseInt(del);
		}
		else {
			delay=MainWindow.waitt;
		}
		if(sp!=null&&sp.isAlive()) return;
		sp= new Speak(s,v,delay,speed);
		sp.start();
	}
	public static ArrayList<Voice> getVoices(){
		ArrayList<Voice> arr= new ArrayList<Voice>();
		ProcessBuilder processBuilder = new ProcessBuilder();


		processBuilder.command("bash","-c", "say -v ? ");


		try {

			Process process = processBuilder.start();


			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			Voice v;
			while ((line = reader.readLine()) != null) {
				v=new Voice(line);
				arr.add(v);
			}

			int exitVal = process.waitFor();
			if(exitVal!=0) {
				throw new Exception("Exit value: 1");
			}

		} catch (Exception e) {
			MainWindow.exceptions=e.getMessage();
		}
		return arr;
		
	}
	
}
