package com.revature.dealership.carMod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
//import java.util.ListIterator;
import java.util.Locale;

import com.revature.dealership.Car;
//import com.revature.dealership.Driver;
//import com.revature.dealership.Offer;
import com.revature.dealership.User;
//import com.revature.dealership.Offer;

/*

Connection -> allows us to connect to our db
Statement -> raw SQL query 
Pepared Statement -> precompilest the SQL string without parameters,
once parameters are added 

*/







public class CarFileManager {

	private static String url = "jdbc:postgresql://localhost:5432/postgres";
	private static String username = "postgres";
	private static String password = "postgres";
	
	
	




	public String sysoutCarList() {
		
		
		
		
		LinkedList<Car> carList = readCarList();
		
		
		Iterator<Car> i = carList.iterator();
		String ret = "";
		Car nextCar = null;
		while(i.hasNext()){
			nextCar = i.next();
			System.out.println(nextCar.toString());
			ret += nextCar.toString() + "\n";
			
		}
		return ret;
	}
	
	
	public void sysoutOfferList() {//Pending
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select offer.id, amount, username, make, model, car.year from offer \n" + 
					"inner join status on offer.status = status.id inner join car on offer.car = car.spot \n" + 
					"where def = 'Pending'\n" + 
					"order by id asc;");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1) + ".: Amount = $" + rs.getDouble(2) + " Username = " + rs.getString(3) + " Make = " + rs.getString(4) + " Model = " + rs.getString(5) + " Year = " + rs.getInt(6));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void sysoutOfferListALL() {//Pending
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select offer.id, amount, username, make, model, car.year, status.def from offer\n" + "inner join status on offer.status = status.id\n" + "inner join car on offer.car = car.spot;");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1) + ".: Amount = $" + rs.getDouble(2) + " Username = " + rs.getString(3) + " Make = " + rs.getString(4) + " Model = " + rs.getString(5) + " Year = " + rs.getInt(6));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		//return pets;
	
		
		
//		LinkedList<Car> carList = readCarList();
//		
//		Iterator<Car> i = carList.iterator();
//
//		while(i.hasNext()){
//			i.next().PrintOffers();
//		}
	}
	
	
public String sysoutOfferByUser(User user) {
	
		String ret = "";
		NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select amount, car.\"year\", make, model from offer inner join status on offer.status = status.id inner join car on offer.car = car.spot where def = 'Accepted' and username = ?;");
			
			ps.setString(1, user.getName());
			
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ret += "You owe a total of " + n.format(rs.getDouble(1)) + " for your " + rs.getInt(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ".\n";
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return ret;
		
}

public String sysoutCarByUser(User user) {
	
	String ret = "";
	NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("select amount, car.\"year\", make, model from offer inner join status on offer.status = status.id inner join car on offer.car = car.spot where def = 'Accepted' and username = ?;");
		
		ps.setString(1, user.getName());
		
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ret +=  rs.getInt(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ".\n";
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	
	
	return ret;
	
}




public void RejectOffer(int i) {
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("update offer set status = 2 where id = ?;");
		ps.setInt(1, i);
		
		ps.execute();

	}catch(SQLException e) {
		e.printStackTrace();
	}
	
}


public void AcceptOffer(int c, int o) {
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("update offer set status = 3 where id = ?;");
		ps.setInt(1, o);
		
		ps.execute();

	}catch(SQLException e) {
		e.printStackTrace();
	}
	
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("update offer set status = 2 where car = ? and id != ?;");
		ps.setInt(1, c);
		ps.setInt(2, o);

		
		ps.execute();
	}catch(SQLException e) {
		e.printStackTrace();
	}


}


	

public String listAcceptedOffer() {
	
	String ret = "";
	
	NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("select username, amount, car.year, make, model from offer inner join status on offer.status = status.id inner join car on offer.car = car.spot where def = 'Accepted';");
		

		
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			
			
			ret += rs.getString(1) + " owes a total of " + n.format(rs.getDouble(2)) + " with 60 remaining payments of  " + n.format(rs.getDouble(2)/60.0) + " for their " + rs.getInt(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ".\n";
		}
			
	}catch(SQLException e) {
		e.printStackTrace();
	}
	
	System.out.println(ret);
	return ret;
	
}



	
//	public LinkedList<Offer> getAcceptedOfferList() {
//		
//		
//		"select offer.id, amount, username, make, model, car.year, status.def from offer\n" + 
//		"inner join status on offer.status = status.id\n" + 
//		"inner join car on offer.car = car.spot\n" + 
//		"where def = 'Accepted';"
//		
//		
//		
//		LinkedList<Car> carList = readCarList();
//		
//		Iterator<Car> i = carList.iterator();
//		LinkedList<Offer> ret = new LinkedList<Offer>();
//		Car current = null;
//		while(i.hasNext()){
//			current = i.next();
//			if(current.isAccepted()) {
//				ret.add(current.getOffer(0));
//			}
//		}
//		
//		return ret;
//	}




	
	
	
	
	
	
	public boolean AddOffer(int i, double amount, String usernameIN) {
		
		//LinkedList<Car> carList = readCarList();
		
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("insert into offer values (?,?,?);");

			ps.setString(1, usernameIN);
			ps.setInt(2, i);
			ps.setDouble(3, amount);
			
//			
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println(e);
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		
		return true;
		
	}
	
	

	
	
	
	
	
	
	
	
	
	////////////////////////////////////File managemtn 
	

	
	
	

	
	







	public LinkedList<Car> readCarList() {
		LinkedList<Car> pets = new LinkedList<Car>();
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Car");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				pets.add(new Car(rs.getString(2), rs.getString(3),  rs.getInt(4), rs.getString(5), rs.getDouble(6), rs.getInt(1)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pets;
	
	}
	
	
	
	
	public LinkedList<Car> readCarListByUser(User user) {
		LinkedList<Car> pets = new LinkedList<Car>();
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("\n" + 
					"select car.spot ,make, model, car.year, status.def from offer\n" + 
					"inner join status on offer.status = status.id\n" + 
					"inner join car on offer.car = car.spot\n" + 
					"where def = 'Accepted' and username = '?';");
			ps.setString(1, user.getName());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				pets.add(new Car(rs.getString(2), rs.getString(3),  rs.getInt(4), rs.getString(5), rs.getDouble(6), rs.getInt(1)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pets;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Car CreateNewCar(Car inCar) {
		
		//carList.add(inCar);
		//writeCarList();
		
		
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("insert into car (make, model, year , color, listprice)\n" + 
					"values (?,?,?,?,?);");
	//		PreparedStatement ps = conn.prepareStatement("insert into offer values ('Jordan','1');");
			ps.setString(1, inCar.getMake());
			ps.setString(2, inCar.getModel());
			ps.setInt(3, inCar.getYear());
			ps.setString(4, inCar.getColor());
			ps.setDouble(5, inCar.getListPrice());
//			
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println(e);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return readCarList().getLast();
	}
	
	
	

	public void removeCar(int i ) {
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("delete from car where spot = ?;");
	//		PreparedStatement ps = conn.prepareStatement("insert into offer values ('Jordan','1');");
			ps.setInt(1,i);
//			
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println(e);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	
	



	public LinkedList<Car> getCarList() {
		
		return readCarList();
	}
	
	
	
	


	
	
}
