package System;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import com.sun.javafx.collections.MappingChange.Map;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import Interface.AnalizingOrder;

/**
 * @author m s i
 * @version 1.0
 * @created 25-май-2016 18:37:32
 */
public class ServiseOperator implements AnalizingOrder {

	private String Login;
	public static Connection conn;
	public static Statement statmt;
	private Scanner in;
	private ArrayList list;
	
	public ServiseOperator(Connection conn){
		this.conn=conn;
		in = new Scanner(System.in);
	}
	public static void showOperMenu(){
	    System.out.println("\nМЕНЮ Сервис-Оператор\n1. Показать список наименований улиц");
	    System.out.println("2. Показать список самых популярных товаров");
	    System.out.println("3. Выйти");
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
					this.getStreetList();
					break;
				}
				case 2:{
					this.getMostPopular();
					break;
				}
				case 3:{
					return;
				}
			}
			showOperMenu();
			choose = in.nextInt();
		}
	}
	public int getChangeOfPrise(){
		return 0;
	}

	public void getMostPopular() throws SQLException{
		statmt = conn.createStatement();
		int k=0;
		ResultSet rs = statmt.executeQuery( "SELECT NameOfProduct FROM OrderDatabase;");
		HashMap<String, Integer> prodList =  new HashMap<String, Integer>();
		 while ( rs.next() ) {
	         String address = rs.getString("NameOfProduct");
	         if(!prodList.containsKey(address))prodList.put(address, 1);
	         else{
	        	 int value =prodList.get(address)+1;
	        	 prodList.replace(address, value);
	         }
	      }
		 Object[] a = prodList.entrySet().toArray();
		 Arrays.sort(a, new Comparator<Object>() {
			 public int compare(Object o1, Object o2) {
		            return ((HashMap.Entry<String, Integer>) o2).getValue().compareTo(
		                    ((HashMap.Entry<String, Integer>) o1).getValue());
		  }});
		 for (Object e : a) {
		        System.out.println("Name of product: " +((HashMap.Entry<String, Integer>) e).getKey() + "\n Ammount of orders: "
		                + ((HashMap.Entry<String, Integer>) e).getValue());
		    }
		 rs.close();
		 statmt.close();
	}
	private ArrayList extracted(HashMap<String, Integer> streetList) {
		return new ArrayList(streetList.entrySet());
	}

	public void getStreetList() throws SQLException{
		statmt = conn.createStatement();
		int k=0;
		ResultSet rs = statmt.executeQuery( "SELECT Address FROM Clients;");
		HashMap<String, Integer> streetList =  new HashMap<String, Integer>();
		 while ( rs.next() ) {
	         String address = rs.getString("Address");
	         if(!streetList.containsKey(address))streetList.put(address, 1);
	         else{
	        	 int value =streetList.get(address)+1;
	        	 streetList.replace(address, value);
	         }
	      }
		 Object[] a = streetList.entrySet().toArray();
		 Arrays.sort(a, new Comparator<Object>() {
			 public int compare(Object o1, Object o2) {
		            return ((HashMap.Entry<String, Integer>) o2).getValue().compareTo(
		                    ((HashMap.Entry<String, Integer>) o1).getValue());
		  }});
		 for (Object e : a) {
		        System.out.println("Address: "  +((HashMap.Entry<String, Integer>) e).getKey() + "\n Number of clients: "
		                + ((HashMap.Entry<String, Integer>) e).getValue());
		    }
		 rs.close();
		 statmt.close();
	}
	
}