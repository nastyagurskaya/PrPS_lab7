package Entity;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Subscriber {

	private String Address="";
	private String FIO="";
	private String login="";
	private String password="";
	public static Connection conn;
	public static Statement statmt;
	private Scanner in;
	
	public Subscriber(Connection conn,String login) throws SQLException{
		this.conn=conn;
		in = new Scanner(System.in);
		this.login=login;
	}
	public Subscriber(Connection conn) throws SQLException{
		this.conn=conn;
		in = new Scanner(System.in);
	}
	public static void showClientnMenu(){
	    System.out.println("\nМЕНЮ Клиент\n1. Сделать заказ");
	    System.out.println("2. Просмотреть заказы");
	    System.out.println("3. Выйти");
	}
	public void mainMenu() throws SQLException, IOException{
		int choose =0;
		showClientnMenu();
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
					System.out.println("\nКаталог:");
					this.chooseProducts();
					this.toMakeOrder();
					break;
				}
				case 2:{
					System.out.println("\nСписок заказов:");
					this.getListOfOrders();
					break;
				}
				case 3:{
					return;
				}
			}
			showClientnMenu();
			choose = in.nextInt();
		}
	}
	public void chooseProducts() throws SQLException, IOException{
		statmt = conn.createStatement();
		 ResultSet rs = statmt.executeQuery( "SELECT * FROM Catalog;" );
		 while ( rs.next() ) {
	         String name = rs.getString("NameOfProduct");
	         String  description = rs.getString("Description");
	         Integer price  = rs.getInt("Price");
	         File file = new File("C:\\Users\\m s i\\Desktop\\Catalog\\"+name+".jpg");
	         FileOutputStream fos = new FileOutputStream(file); 
	         InputStream input = rs.getBinaryStream("Image");
             byte[] buffer = new byte[1024];
             while (input.read(buffer) > 0) {
                 fos.write(buffer);
             }
             fos.close();
	         System.out.println( "Name: " + name );
	         System.out.println( "Description: " + description );
	         System.out.println( "Price: " + price +"$");
	         System.out.println();
	      }
		 rs.close();
		 statmt.close();
	}
	public void getListOfOrders() throws SQLException{
		statmt = conn.createStatement();
		int k=0;
		ResultSet rs = statmt.executeQuery("SELECT * FROM Clients WHERE Login='"+login+"';");
		FIO = rs.getString("FIO");
		rs = statmt.executeQuery( "SELECT * FROM OrderDatabase WHERE NameOfClient='"+FIO+"';");
		 while ( rs.next() ) {
			 k++;
	         String prodname = rs.getString("NameOfProduct");
	         String client = rs.getString("NameOfClient");
	         String  state = rs.getString("State");
	         Integer price  = rs.getInt("PriceOfOrder");
	         
	         System.out.println(k+". "+ "Name of product: " + prodname );
	         System.out.println( "Name of client: " + client );
	         System.out.println( "Price: " + price +"$");
	         System.out.println( "State: " + state);
	         System.out.println();
	      }
		 rs.close();
		 statmt.close();
	}
	public void toMakeOrder() throws SQLException{
		System.out.println("Введите имя понравившегося товара");
		String name="";
		if(name.equals("")) name=in.nextLine();
		name=in.nextLine();
		Order ord= new Order(conn);
		 ord.addToOrderDataBase(name, login);
	}
	public void CreateRecord(String login,String password,String FIO,String Address) throws SQLException
	{

		statmt = conn.createStatement();
		try{
			statmt.executeUpdate("INSERT INTO Users ('Login', 'Password','Role') VALUES ('"+login+"', '"+password+"', 'client'); ");
			statmt.executeUpdate("INSERT INTO Clients ('Login', 'FIO','Address') VALUES ('"+login+"', '"+FIO+"', '"+Address+"'); ");
			System.out.println("Вы зарегестированы");
			statmt.close();
		}
		catch (SQLException ex){
			System.out.println(ex.getMessage());
			
		}
		statmt.close();
	}
	public void toRegister() throws SQLException{
		Scanner in = new Scanner(System.in);
		System.out.println("Введите логин");
		while(login.equals(""))login=in.nextLine();
		System.out.println("Введите пароль");
		while(password.equals(""))password=in.nextLine();
		System.out.println("Введите ФИО");
		while(FIO.equals(""))FIO=in.nextLine();
		System.out.println("Введите адресс");
		while(Address.equals(""))Address=in.nextLine();
		CreateRecord(login,password,FIO,Address);
	}

}