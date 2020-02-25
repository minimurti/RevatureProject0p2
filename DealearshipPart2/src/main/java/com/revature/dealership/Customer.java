package com.revature.dealership;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
//import org.apache.log4j.Logger;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
//import java.util.Scanner;



public class Customer extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285296186529033766L;

	//LinkedList<Car> Cars;
	
	public Customer(String name, String pwd) {
		super(name, pwd, "Customer");
		//Cars = new LinkedList<Car>();
		// TODO Auto-generated constructor stub
		
	}
	
	
//	public void addCar(Car carInput) {
//		Cars.add(carInput);
//	}
	

	@Override
	public boolean PromptUser() {
		
		System.out.println("\n\nWhat would you like to do?");
		System.out.println("1. Make an Offer?");
		System.out.println("2. List all cars on lot?");
		System.out.println("3. List your cars");
		System.out.println("4. List your payments");
		System.out.println("5. Exit");
		System.out.println("(Select option by entering a number)");
		
		int choice;
		try {
			choice = Driver.input.nextInt();
		}catch(InputMismatchException e) {
			Driver.log.warn("Username " + this.getName() + " caused error", e);
			choice = 0;
			Driver.input.next();

		}catch(Exception e) {
			choice = 0;
			Driver.log.error("Username " + this.getName() + " caused error", e);
		}
		
		
		
		
		switch (choice) {
		case 1:
			MakeOffer();
			
			return true;
			//break;
			
		case 2:
			listCars();
			
			return true;
			///break;
			
		case 3:
			listMyCars();
			return true;
			
		case 4:
			listPayments();
			return true;
			
		case 5:
			
			return false;

		default:
			System.out.println("Please Enter a Valid Number.");
			return true;
		}
		
		
		
		
		//return false;
	}
	
	
	
	
	private void listPayments() {
		System.out.println( listPaymentsIn());
		
	}

	
	public String listPaymentsIn() {

		
		return Driver.cfm.sysoutOfferByUser(this);


	}

	private void MakeOffer() {
		// TODO Auto-generated method stub
		listCars();
		System.out.println("Choose Car By Number: ");
		int i = Driver.input.nextInt();
		//Car carOfOffer = Driver.cfm.readCarList().get(i);
		
		System.out.println("Enter Amount: $");
		double amountOfOffer = Driver.input.nextDouble();
		
		
		MakeOfferTest(i,amountOfOffer);

	}
	
	
	public void MakeOfferTest(int i, double amountOfOffer) {

		
		if(Driver.cfm.AddOffer(i, amountOfOffer, this.getName())) {
			System.out.println("Offer recieved!");
		}else
			System.out.println("Invalid Input!");
		
		
		Driver.log.info("User " + this.getName() + " made an offer of $" + amountOfOffer);
		
		
	}

//	public String listCarsTest() {
//		return listCars();
//	}
	
	private String listCarsTest() {
		return Driver.cfm.sysoutCarListAvalible();
	}
	
	private void listCars() {
		System.out.println(listCarsTest());
	}


	public void listMyCars() {
		

		System.out.println("You own the following: \n " + listMyCarTest());
	}
	
	public String listMyCarTest() {
		return Driver.cfm.sysoutCarByUser(this);
	}
	
	
	
	
	
	

}
