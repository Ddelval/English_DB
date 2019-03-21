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
			Ficha f;
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
			execute("CREATE TABLE log (count INTEGER IDENTITY, eng VARCHAR(100), Examp VARCHAR(10000), pronunciation VARCHAR(100), use VARCHAR (10000), known BOOLEAN DEFAULT FALSE NOT NULL, PRIMARY KEY (count))",false);
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
				String sql="INSERT INTO log(eng,Examp,use, pronunciation)  VALUES ("+arrl.get(i).toSQLString()+")";
				PreparedStatement ps= con.prepareStatement(sql);
				ps.execute();
			}
			/*StringBuilder sql = new StringBuilder("INSERT INTO log(eng,Examp,use, pronunciation)  VALUES ("+arrl.get(0).toSQLString()+")"); 
			for(int i=1;i<arrl.size();i++) {
				sql.append("\n");
				sql.append("INSERT INTO log(eng,Examp,use, pronunciation)  VALUES ("+arrl.get(i).toSQLString()+")");
			}
			PreparedStatement ps= con.prepareStatement(sql.toString());
			ps.execute();*/
			
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
		try {
			String sql="UPDATE config SET data='"+newValue+"' WHERE id='"+id+"'";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
			
		}
		catch(Exception ex) {
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
	public static void updateFichaKnown(boolean b, Ficha f) 
	{
		Connection con = UConnection.getConnection();
		String sql  = "UPDATE log SET known='"+ Boolean.toString(b).toUpperCase() + "' WHERE count="+f.getDBkey();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
		}
	}
	public static void updateAllKnown(boolean b) 
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
				f.setUnknown();
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
				  +"', pronunciation='"+f.getPronunciation().replace("'", "%&/%")+"', use='"+f.getUse().replace("'", "%&/%")+"', known='"+Boolean.toString(f.getKnown())+"' WHERE count="+Integer.toString(f.getDBkey());
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
	public static boolean checkAvailability() {
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log WHERE known='FALSE'";
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
				MainWindow.exceptions="No hay ninguna ficha";
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
					sb.append(Integer.toString(rs.getInt("count")));
					sb.append("\n");
					sb.append(rs.getString("eng"));
					sb.append("\n");
					sb.append(rs.getString("Examp"));
					sb.append("\n");
					sb.append(rs.getString("use"));
					sb.append("\n");
					sb.append(rs.getString("pronunciation"));
					sb.append("\n");
					sb.append(rs.getString("known"));
					sb.append("\n\n");
					s=sb.toString();
					
				}while(rs.next());
				}
				else {
					s="No hay resultado que mostrar";
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
				MainWindow.exceptions="La eliminación afectaría a más de una fila. Ha sido cancelada";
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
				f.setKnown(rs.getBoolean("known"));
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
				f.setKnown(rs.getBoolean("known"));
				l.add(new ObservableFicha(f));
			}
			
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			MainWindow.exceptions=ex.getMessage();
			
		}
		
		return FXCollections.observableList(l);
	}
	public static Ficha getRndFicha () {
		Connection con = UConnection.getConnection();
		String sql= "SELECT * FROM log WHERE known='FALSE' ORDER BY RAND() LIMIT 1";
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
				f.setKnown(rs.getBoolean("known"));
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
		String v=Facade.getConfig("voice");
		Speak sp= new Speak(s,v);
		sp.start();
	}
	
}
