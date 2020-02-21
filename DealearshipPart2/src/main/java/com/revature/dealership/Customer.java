package com.revature.dealership;

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

	LinkedList<Car> Cars;
	
	public Customer(String name, String pwd) {
		super(name, pwd, "Customer");
		Cars = new LinkedList<Car>();
		// TODO Auto-generated constructor stub
		
	}
	
	
	public void addCar(Car carInput) {
		Cars.add(carInput);
	}
	

	@Override
	public boolean PromptUser() {
		
		System.out.println("What would you like to do?");
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
	
	
	
	
	private String listPayments() {
		listMyCars();
		System.out.println("Choose Car By Number: ");
		int i = Driver.input.nextInt();
		return listPaymentsIn(i-1);
		
	}

	
	public String listPaymentsIn(int i) {
	
		
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		try {
			System.out.println("Total of $" + numberFormat.format(Cars.get(i).getOffer(0).getAmount()) + ", with 60 remaining payments of $" + numberFormat.format(Cars.get(i).getOffer(0).getAmount()/60) + " per month, over the course of 5 years 0% APR.");
			return "Total of $" + numberFormat.format(Cars.get(i).getOffer(0).getAmount()) + ", with 60 remaining payments of $" + numberFormat.format(Cars.get(i).getOffer(0).getAmount()/60) + " per month, over the course of 5 years 0% APR.";
			
		}catch(InputMismatchException e) {
			Driver.log.warn("Username " + this.getName() + " caused error", e);
			
				//Log.warn("Please Enter Valid Input");
				System.out.println("Please Enter Valid Input");
				
		}catch(IndexOutOfBoundsException e) {
			Driver.log.warn("Username " + this.getName() + " caused error", e);
			
				//Log.warn("Please Enter Valid Range");
				System.out.println("Please Enter Valid Range");
		}catch(IllegalAccessError e) {
			Driver.log.warn("Username " + this.getName() + " caused error", e);
			
				//Log.warn("Please Enter Valid Input");
				System.out.println("Please Enter Valid Input");
		}
		catch(Exception e) {
			Driver.log.warn("Username " + this.getName() + " caused error", e);
			
				
		}
		return "Error";

	}

	private void MakeOffer() {
		// TODO Auto-generated method stub
		listCars();
		System.out.println("Choose Car By Number: ");
		int i = Driver.input.nextInt();
		Car carOfOffer = Driver.cfm.readCarList().get(i-1);
		
		System.out.println("Enter Amount: $");
		double amountOfOffer = Driver.input.nextDouble();
		
		
		
		
		if(Driver.cfm.AddOffer(i - 1, new Offer(this, carOfOffer, amountOfOffer))) {
			System.out.println("Offer recieved!");
		}else
			System.out.println("Invalid Input!");
		
		
		Driver.log.info("User " + this.getName() + " made an offer of $" + amountOfOffer + carOfOffer);
	}
	
	
	public void MakeOfferTest(int i, double amountOfOffer) {
		// TODO Auto-generated method stub
		listCars();
		System.out.println("Choose Car By Number: ");
		//int i = Driver.input.nextInt();
		Car carOfOffer = Driver.cfm.readCarList().get(i-1);
		
		System.out.println("Enter Amount: $");
		//double amountOfOffer = Driver.input.nextDouble();
		
		
		
		
		if(Driver.cfm.AddOffer(i - 1, new Offer(this, carOfOffer, amountOfOffer))) {
			System.out.println("Offer recieved!");
		}else
			System.out.println("Invalid Input!");
		
		
		
	}

	public String listCarsTest() {
		return listCars();
	}
	
	private String listCars() {
		return Driver.cfm.sysoutCarList();
	}
	


	public String listMyCars() {
		Iterator<Car> i = Cars.iterator();
		int j = 0;
		Car currentCar = null;
		String ret = "";
		while(i.hasNext()) {
			 j++;
			 currentCar = i.next();
			 System.out.println(j + ". " + currentCar);
			 ret += j + ". " + currentCar + "\n";
			 
		}
		return  ret;
	}
	
	
	
	
	
	
	

}
