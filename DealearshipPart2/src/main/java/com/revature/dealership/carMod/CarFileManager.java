package com.revature.dealership.carMod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
//import java.util.ListIterator;



import com.revature.dealership.Car;

import com.revature.dealership.Driver;
import com.revature.dealership.Offer;
import com.revature.dealership.User;
//import com.revature.dealership.Offer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	
	
	private LinkedList<Car> carList;



	public String sysoutCarList() {
		Iterator<Car> i = carList.iterator();
		String ret = "";
		int j = 0;
		Car nextCar = null;
		while(i.hasNext()){
			j++;
			nextCar = i.next();
			System.out.println(j + ". " + nextCar.toString());
			ret += j + ". " + nextCar.toString() + "\n";
			
		}
		return ret;
	}
	
	
	public void sysoutOfferList() {
		Iterator<Car> i = carList.iterator();

		while(i.hasNext()){
			i.next().PrintOffers();
		}
	}

	
	
	public LinkedList<Offer> getAcceptedOfferList() {
		Iterator<Car> i = carList.iterator();
		LinkedList<Offer> ret = new LinkedList<Offer>();
		Car current = null;
		while(i.hasNext()){
			current = i.next();
			if(current.isAccepted()) {
				ret.add(current.getOffer(0));
			}
		}
		
		return ret;
	}


	public CarFileManager() {
		
		checkCarFile();
		
		carList = readCarList();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
	public boolean AddOffer(int i, Offer offer) {
		if(i > carList.size()) {
			return false;
		}
		if(carList.get(i).isAccepted()) {
			System.out.println("Someone Already Ownes this.");
			return false;
		}
		carList.get(i).addOffer(offer);
		writeCarList();
		return true;
		
	}
	
	
	public boolean AcceptOffer(int carNumb, User Offereee) {
		if(carNumb > carList.size()) {
			return false;
		}
		if(carList.get(carNumb).isAccepted()) {
			System.out.println("Cannot Accept an Accepted Offer!\n");
			return false;
		}
		
		carList.get(carNumb).AcceptOffer(Offereee);
		writeCarList();
		return true;
		
	}
	
	
	public boolean RejectOffer(int carNumb, int offerNumb) {
		if(carNumb > carList.size()) {
			return false;
		}
		
		if(carList.get(carNumb).getOffer(offerNumb).getAccepted()) {
			System.out.println("Cannot Reject an Accepted Offer!");
			return false;
		}
		
		
		carList.get(carNumb).RejectOffer(offerNumb);;
		writeCarList();
		return true;
		
	}
	
	
	
	
	
	
	
	
	
	////////////////////////////////////File managemtn 
	
	private void writeCarList() {
		String filename;
		filename = "cars.dat";
		
		
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(carList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	

	
	






	@SuppressWarnings("unchecked")
	public LinkedList<Car> readCarList() {
		
		String filename = "Cars.dat";


		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);

			this.carList = (LinkedList<Car>) ois.readObject();

			
			fin.close();
			ois.close();
			
			return carList;
			

		} catch (Exception ex) {
			//Scanner scan = new Scanner(System.in);
			
			System.out.println("No List detected! ");
			System.out.println("Create password for accountname \"admin\": ");
			
			String password = Driver.input.next();
	
			
			if(password.isEmpty()) {
				ex.printStackTrace();
			}
			else {
				
				
			}
			

			

			
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		
		return null;
	
	}
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void checkCarFile() {
		/////////////////////CHECK Status of car list file
		String filename = "cars.dat";


		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);

		
			this.carList = (LinkedList<Car>) ois.readObject();

			
			fin.close();
			ois.close();
			
			
			

		} catch (Exception ex) {
			//Scanner exinput = new Scanner(System.in);
			

			initializeCarList();
		
			
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

/////////////////////done with CHECK Status of car list file
		
		
		

		
		
		
		
	}
	
	
	
	
	public Car CreateNewCar(Car inCar) {
		
		carList.add(inCar);
		writeCarList();
		
		
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("insert into car (make, model, year , color)\n" + 
					"values (?,?,?,?);");
	//		PreparedStatement ps = conn.prepareStatement("insert into offer values ('Jordan','1');");
			ps.setString(1, inCar.getMake());
			ps.setString(2, inCar.getModel());
			ps.setInt(3, inCar.getYear());
			ps.setString(4, inCar.getColor());
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
		
		this.carList.remove(i);
		writeCarList();
		
		
	}

	
	private void initializeCarList() {
		
		
		String filename;
		filename = "cars.dat";
		
		
		this.carList = new LinkedList<Car>();
		

		
		
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(carList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}


	public LinkedList<Car> getCarList() {
		
		return this.carList;
	}
	
	
	
	


	
	
}
