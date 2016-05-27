package Entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javafx.scene.SubScene;


public class Order {
	public static Connection conn;
	public static Statement statmt;
	private String Customer;
	private String ClientAddress;
	private int id;
	private String Product;
	private int Price;
	private Scanner in;
	
	public Order(Connection conn) throws SQLException{
		this.conn=conn;
		 in = new Scanner(System.in);
	}

	public void addToOrderDataBase(String prodName,String login) throws SQLException{
		statmt = conn.createStatement();
		ResultSet rs = statmt.executeQuery("SELECT Price FROM Catalog WHERE NameOfProduct='"+prodName+"';");
		Price  = rs.getInt("Price");
		rs = statmt.executeQuery("SELECT * FROM Clients WHERE Login='"+login+"';");
		ClientAddress = rs.getString("Address");
		Customer = rs.getString("FIO");
		id = (int)(Math.random()*900000+100000);
		try{
			statmt.executeUpdate("INSERT INTO OrderDatabase (id, 'NameOfClient','ClientAddress','PriceOfOrder','NameOfProduct','State') VALUES ('"+id+"', '"+Customer+"', '"+ClientAddress+"', '"+Price+"', '"+prodName+"','saved'); ");
			System.out.println("Заказ сохранён");
			statmt.close();
		}
		catch (SQLException ex){
			System.out.println(ex.getMessage());
			statmt.close();
		}
		rs.close();
	}

	public String getCustomer(){
		return null;
	}

	public String getProducts(){
		return null;
	}

	public void setCustomer(String cust){

	}

	public void setProducts(String prod){

	}

}