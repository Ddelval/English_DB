package fX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Voice {
	private String name;
	private String language;
	private String dialect;
	public Voice(String s) {
		int m,n,d;
		name=s.substring(0, s.indexOf(" "));
		s=s.substring(s.indexOf(" "));
		while(s.charAt(0)==' ') s=s.substring(1);
		m=s.indexOf('_');
		n=s.indexOf('-');
		if(m<0)m=100;
		if(n<0)n=100;
		d=Math.min(m, n);
		if(d==100) {
			language=dialect="-";
			return;
		}
		language =s.substring(0,d);
		dialect=s.substring(d+1,s.indexOf(" "));
	}
	public String getName() {
		return name;
	}
	public String getLanguage() {
		return language;
	}
	public String getDialect() {
		return dialect;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public String toString() {
		return name+" ("+language+"-"+dialect+")";
	}
	
	
	
}

