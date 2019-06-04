package fX;
//Provides the connection with the database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.stage.Stage;

public class UConnection {
	private static Connection con = null;
	private static String driver= "org.hsqldb.jdbcDriver";
	private static String url = "jdbc:hsqldb:db";
	public static Connection getConnection(){
		if(con==null) {
			try{
				Runtime.getRuntime().addShutdownHook(new MyShDwnHook());
				Class.forName(driver);
				con =DriverManager.getConnection(url, "sa", "");
				
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			finally {
				return con;
			}
		}
		else {
			return con;
		}
	}
	public static void main(String[]args) {
		try {
			con=UConnection.getConnection();
			PreparedStatement p =con.prepareStatement("DROP TABLE log ");
			p.execute();
			p = con.prepareStatement("CREATE TABLE log (count INTEGER IDENTITY, eng VARCHAR(100), Examp VARCHAR(10000), pronunciation VARCHAR(100), use VARCHAR (10000), knownEtoS BOOLEAN DEFAULT FALSE NOT NULL,knownStoE BOOLEAN DEFAULT FALSE NOT NULL, PRIMARY KEY (count))");
			p.execute();
			/*PreparedStatement p = con.prepareStatement("CREATE TABLE config (id VARCHAR(100),data VARCHAR (100),PRIMARY KEY (id))");
			p.execute();
			*/
			/*PreparedStatement p = con.prepareStatement("INSERT INTO config VALUES('voice','Alex')");
			p.execute();
			*/
			/*Ficha f= new Ficha("Start", "∫t","No \n never");
			Example x= new Example("I started the race","Empecé la carrera", "Empezar");
			f.addExample(x);
			x= new Example("The start was too much for him","La salida fue demasiado para él", "Salida");
			f.addExample(x);
			x= new Example("I started the race","Empecé la carrera", "Empezar");
			f.addExample(x);
			Facade.addDB(f);*/
			
			/*PreparedStatement p = con.prepareStatement("SELECT * FROM log");
			ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getInt("count"));
				System.out.println(rs.getString("eng"));
				System.out.println(rs.getString("Examp"));
				System.out.println(rs.getString("use"));
				System.out.println(rs.getString("pronunciation"));
				System.out.println(rs.getBoolean("known"));
				System.out.print("\n");
				
			}*/
			//Facade.backup("");
			//Facade.restore("/Users/daviddelval/Desktop/1.fichasbackup");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static class MyShDwnHook extends Thread{
		public void run() {
			try {
				Connection con = UConnection.getConnection();
				con.commit();
				con.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
