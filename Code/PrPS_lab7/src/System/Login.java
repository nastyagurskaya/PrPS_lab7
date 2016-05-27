package System;

import java.sql.*;
import java.util.Scanner;

import Interface.SingIn;


public class Login implements SingIn {
	public static Connection conn;
	public static Statement statmt;
	private String Login;
	private String Password;
	private String Role;
	private Scanner in;
	public Login(Connection conn,String login) throws SQLException{
		this.conn=conn;
		 in = new Scanner(System.in);
		 this.Login = login;
	}
	public void authorization() throws SQLException{
		statmt = conn.createStatement();
		try{ ResultSet rs = statmt.executeQuery( "SELECT * FROM Users WHERE Login='"+Login+"';");
	         Password = rs.getString("Password");
	         Role = rs.getString("Role");
		      rs.close();
		}
		catch(SQLException e){
			System.out.println("Неверный логин: "+e.getMessage());
			System.exit(0);
		}
	      statmt.close();
	}
	
	public boolean cheakPassword(){
		String pass="";
		System.out.println("Введите пароль");
		while(pass.equals(""))pass=in.nextLine();
		return pass.equals(Password);
	}
	public String getRole(){
		return Role;
	}
	public String getLogin(){
		return Login;
	}
}