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
		
		
		String ret = "";
		NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
		
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select c.spot, c.\"year\", make, model, listprice, c.owner from car c;");
			
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				ret += "Parking Spot #" + rs.getInt(1) + ": " + rs.getInt(2) + ", " + rs.getString(3)+ ", " + rs.getString(4) + ": " + n.format(rs.getDouble(5));
				if(rs.getString(6) != null){
					
					ret += ": purchased by " + rs.getString(6);
				}
				ret += ".\n";
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return ret;
		
}
	
	
public String sysoutCarListAvalible() {
		
	String ret = "";
	NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
	
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("select c.spot, c.\"year\", make, model, listprice from car c where c.owner is null;");
		
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ret += "Parking Spot #" + rs.getInt(1) + ": " + rs.getInt(2) + ", " + rs.getString(3)+ ", " + rs.getString(4) + ": " + n.format(rs.getDouble(5)) + ".\n";
		}
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
	
	
	return ret;
	
	
}

	
	
	
	
	
	public String sysoutOfferList() {//Pending
		String ret="";
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select offer.id, amount, username, make, model, car.year from offer \n" + 
					"inner join status on offer.status = status.id inner join car on offer.car = car.spot \n" + 
					"where def = 'Pending'\n" + 
					"order by offer.car asc;");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ret += rs.getInt(1) + ": $" + rs.getDouble(2) + " from " + rs.getString(3) + " for the " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getInt(6) + ".\n\n";
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	
	public void sysoutOfferListByCar(int carID) {//Pending
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select offer.id, amount, username, make, model, car.year from offer \n" + 
					"inner join status on offer.status = status.id inner join car on offer.car = car.spot \n" + 
					"where def = 'Pending' and car.spot = ? \n" + 
					"order by id asc;");
			ps.setInt(1, carID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println( rs.getInt(1) + ": $" + rs.getDouble(2) + " from " + rs.getString(3) + " for the " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getInt(6) + ".\n");
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
				ret += "You owe a total of " + n.format(rs.getDouble(1)) + " for your " + rs.getInt(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + " with 60 remaining payments of " + n.format(rs.getDouble(1)/60) + ".\n";
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
		PreparedStatement ps = conn.prepareStatement("select car.\"year\", make, model from car where car.\"owner\" = ?;");
		
		ps.setString(1, user.getName());
		
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ret +=  rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ".\n";
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
	
	String owner = "";
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("select username from offer o where id = ?;");
		ps.setInt(1, o);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			owner = rs.getString(1);
		}

	}catch(SQLException e) {
		e.printStackTrace();
	}
	
	
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("CALL Accept(?,?)");
		ps.setInt(1, c);
		ps.setInt(2, o);
		
		ps.execute();
	

	}catch(SQLException e) {
		e.printStackTrace();
	}
	
	
	
	
//	
//	try(Connection conn = DriverManager.getConnection(url, username, password)){
//		PreparedStatement ps = conn.prepareStatement("update offer set status = 3 where id = ?;");
//		ps.setInt(1, o);
//		
//		ps.execute();
//
//	}catch(SQLException e) {
//		e.printStackTrace();
//	}
//	
//	
//	try(Connection conn = DriverManager.getConnection(url, username, password)){
//		PreparedStatement ps = conn.prepareStatement("update offer set status = 2 where car = ? and id != ?;");
//		ps.setInt(1, c);
//		ps.setInt(2, o);
//
//		
//		ps.execute();
//	}catch(SQLException e) {
//		e.printStackTrace();
//	}
//	
	
	try(Connection conn = DriverManager.getConnection(url, username, password)){
		PreparedStatement ps = conn.prepareStatement("update car set owner = ? where spot = ?;");
		ps.setString(1, owner);
		ps.setInt(2, c);

		
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
	
	
	
	
	public void SHOWTIME() {
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("drop table car;\n" + 
					"drop table offer;\n" + 
					"drop table eluser;\n" + 
					"drop table status;\n" + 
					"drop table userAuth;\n" + 
					"\n" + 
					"\n" + 
					"CREATE TABLE userAuth(\n" + 
					"	auth_id SERIAL PRIMARY key,\n" + 
					"	auth_name varchar not null\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE offer(\n" + 
					"\n" + 
					"	username varchar not null,\n" + 
					"	car SERIAL not null,\n" + 
					"	amount numeric not null,\n" + 
					"	status int not null,\n" + 
					"	\n" + 
					"	id SERIAL PRIMARY key\n" + 
					");\n" + 
					"\n" + 
					"\n" + 
					"ALTER TABLE offer ALTER COLUMN status\n" + 
					"SET DEFAULT 1;\n" + 
					"\n" + 
					"CREATE TABLE status (\n" + 
					"	id Serial not null,\n" + 
					"	def varchar not null\n" + 
					"	\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE eluser (\n" + 
					"	username varchar primary key,\n" + 
					"	pass varchar not null,\n" + 
					"	authLevl int not null\n" + 
					"	\n" + 
					");\n" + 
					"	\n" + 
					"CREATE TABLE car (\n" + 
					"	spot  SERIAL PRIMARY key,\n" + 
					"	\n" + 
					"	make varchar NOT NULL,\n" + 
					"	model varchar NOT NULL,\n" + 
					"	\n" + 
					"\n" + 
					"	year int NOT NULL,\n" + 
					"	color varchar NOT NULL,\n" + 
					"\n" + 
					"	listprice numeric not null,\n" + 
					"	owner varchar null\n" + 
					"\n" + 
					");\n" + 
					"\n" + 
					"\n" + 
					"\n" + 
					"insert into status values (1,'Pending');\n" + 
					"insert into status values (2,'Rejected');\n" + 
					"insert into status values (3,'Accepted');\n" + 
					"\n" + 
					"\n" + 
					"insert into userAuth values(1,'Employee');\n" + 
					"insert into userAuth values(2,'Employee');\n" + 
					"insert into userAuth values(3,'Admin');\n" + 
					"\n" + 
					"\n" + 
					"insert into eluser values('admin','admin',3),('Katie','aoeu',2),('Mike','mike',1),('Minh','minh',1),('Matt','matt',1),('Tyler','tyler',1),('Tom','tom',1),('Allen','mike',1),('Kenneth','ken',1),('Nick','nick',1);\n" + 
					"insert into car (make, model, year , color, listprice) values('Revature','Reva',2020,'Orange',30000),('Revature','Reva',2019,'Black',25000),('Revature','Ture Bike',2022,'Black',13000),('Toyota','Camry',2019,'Black',30000);\n" + 
					"insert into offer values('Mike',1,29999),('Kenneth',3,12000),('Minh',3,12000),('Tyler',2,23000),('Tom',2,22000),('Tyler',1,23000),('Nick',1,28000),('Nick',4,28000);");
	//		
			
			
			
			
			
			
			
			
		
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println(e);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	
	
	


	
	
}
