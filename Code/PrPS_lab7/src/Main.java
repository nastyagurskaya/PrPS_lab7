import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import System.Login;
import System.Register;
import System.ServiseOperator;
import Entity.Subscriber;
public class Main {
	public static Connection conn;
	public static void ConnectDatabase() throws ClassNotFoundException, SQLException 
	{
		   conn = null;
		   Class.forName("org.sqlite.JDBC");
		   conn = DriverManager.getConnection("jdbc:sqlite:Database\\System.db");
		   
		   System.out.println("���� ����������!");
	}
	public static void showMainMenu(){
	    System.out.println("\n����\n1. �����");
	    System.out.println("2. ������������������");
	    System.out.println("3. �����");
	}
	public static void CloseDB() throws ClassNotFoundException, SQLException
	{
		conn.close();
		System.out.println("����� �������!");
	}
	public static void main(String[] args)throws ClassNotFoundException, SQLException, IOException{
		int choose=0,choose1=0;
		Scanner in = new Scanner(System.in);
		ConnectDatabase();
		showMainMenu();
		try{
			choose = in.nextInt();
		}
		catch(Exception ex){
			System.out.println("Invalid input");
		}
		while(true){
			switch(choose){
				case 1:{
					String logi="";
					System.out.println("������� �����");
					while(logi.equals(""))logi=in.nextLine();
					Login log = new Login(conn,logi);
					log.authorization();
					if(log.cheakPassword()) System.out.println("����������� ������ �������!");
					else System.out.println("�������� ������");
					if(log.getRole().equals("client")){
						Subscriber client = new Subscriber(conn,log.getLogin());
						client.mainMenu();
					}
					if(log.getRole().equals("oper")){
						ServiseOperator oper= new ServiseOperator(conn);
						oper.mainMenu();
					}
					if(log.getRole().equals("register")){
						Register reg= new Register(conn);
						reg.mainMenu();
					}
					break;
				}
				case 2:{
					Subscriber client = new Subscriber(conn);
					client.toRegister();
					break;
				}
				case 3:{
					CloseDB();
					System.exit(0);
				}
			}
			showMainMenu();
			choose = in.nextInt();
		}
	}
}
