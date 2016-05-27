package System;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Entity.Order;
public class Register {

	private String FIO;
	public static Connection conn;
	public static Statement statmt;
	private Scanner in;

	public Register(Connection conn){
		this.conn=conn;
		in = new Scanner(System.in);
	}
	public static void showOperMenu(){
	    System.out.println("\nМЕНЮ Сервис-Регисратор\n1. Показать список заказов для отправки");
	    System.out.println("2. Выйти");
	}
	public void mainMenu() throws SQLException{
		int choose =0;
		showOperMenu();
		try{
			choose = in.nextInt();
		}
		catch(Exception ex){
			System.out.println("Invalid input");
		}
		boolean flag=true;
		while(flag){
			switch(choose){
				case 1:{
					this.showOrderList();
					this.sendOrder();
					break;
				}
				case 2:{
					return;
				}
			}
			showOperMenu();
			choose = in.nextInt();
		}
	}
	public void showOrderList() throws SQLException{
		statmt = conn.createStatement();
		int k=0;
		ResultSet rs = statmt.executeQuery( "SELECT * FROM OrderDatabase;");
		 while ( rs.next() ) {
			 k++;
			 int id = rs.getInt("id");
	         String prodname = rs.getString("NameOfProduct");
	         String client = rs.getString("NameOfClient");
	         String  state = rs.getString("State");
	         String address = rs.getString("ClientAddress");
	         Integer price  = rs.getInt("PriceOfOrder");
	         System.out.println(k+". "+ "id: " + id);
	         System.out.println("Name of product: " + prodname );
	         System.out.println( "Name of client: " + client );
	         System.out.println( "Address: " +address );
	         System.out.println( "Price: " + price +"$");
	         System.out.println( "State: " + state);
	         System.out.println();
	      }
		 rs.close();
		 statmt.close();
	}
	public void sendOrder() throws SQLException{
		statmt = conn.createStatement();
		System.out.println( "Введите id товара для отправки");
		Integer id=0;
		try{
			id = in.nextInt();
		}
		catch(Exception ex){
			System.out.println("Invalid input");
		}
		String sql = "UPDATE OrderDatabase set State='uploaded' WHERE id="+id+";";
		statmt.executeUpdate(sql);
		System.out.println("Товар помечен как отравленный");
		statmt.close();
	}
}