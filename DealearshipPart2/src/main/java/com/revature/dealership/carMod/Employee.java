package com.revature.dealership.carMod;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;

//import java.util.Iterator;

import com.revature.dealership.Car;
import com.revature.dealership.Driver;
//import com.revature.dealership.Offer;
//import com.revature.dealership.Offer;
import com.revature.dealership.User;

public class Employee extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5552278677877348969L;

	public Employee(String name, String pwd) {
		super(name, pwd, "Employee");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean PromptUser() {
		
		System.out.println("\n\nWould you like to:");
		System.out.println("1. Add a Car to the Lot?");
		System.out.println("2. List all the Cars?");
		System.out.println("3. List all Active offers?");
		System.out.println("4. Accept an offer?");
		System.out.println("5. Reject an offer?");
		System.out.println("6. Remove a Car to the Lot?");
		System.out.println("7. List Payments?");
		System.out.println("8. Exit?");
		System.out.println("(Select option by entering a number)");
		
		
		int choice = Driver.input.nextInt();
		
		switch (choice) {
		case 1:
			AddCarToLot();
			
			return true;
			//break;
			
		case 2:
			listCars();
			
			return true;
			///break;
			
		case 3:
			listOffers();
			return true;
			
		case 4:
			AcceptOffer();
			return true;
		
		case 5:
			RejectOffer();
			return true;
			
		case 6:
			RemoveCar();
			return true;
			
			
		case 7:
			ListAllPayments();
			return true;
		
		case 8:
			return false;

		default:
			System.out.println("Please Enter a Valid Number.");
			break;
		}
		
		
		
		
		return false;
	}

	public String ListAllPayments() {

		return Driver.cfm.listAcceptedOffer();
	}
	
	

	

	private void AcceptOffer() {//tested by tester
		System.out.println(Driver.cfm.sysoutCarListAvalible());
		System.out.println("Select a Car by Number");
		int c = Driver.input.nextInt();
		System.out.println("Choose offer by Number");
		//list the offers on a car
		Driver.cfm.sysoutOfferListByCar(c);
		int o = Driver.input.nextInt();

		AcceptOfferTest(c,o);

		
	}
	
	
	public void AcceptOfferTest(int c, int o) {//tested


		
		Driver.cfm.AcceptOffer(c,o);
	
		Driver.log.info("Employee User " + this.getName() + " Accepted offer ID: " + c);
		

		
		
	}
	
	
	private void RejectOffer() {//tested
		System.out.println(Driver.cfm.sysoutOfferList());
		System.out.println("Select an Offer by Number");
		int i = Driver.input.nextInt();

		
		RejectOfferTest(i);
//		
//		String username = Driver.input.next();
//		
//		Driver.cfm.readCarList().get(i).
		
		
	}
	
	
	public void RejectOfferTest(int i) {//testedByTester
		Driver.cfm.RejectOffer(i);
		
		Driver.log.info("Employee User " + this.getName() + " Accepted offer ID: " + i);
	}


	private void AddCarToLot(){//tested by tester 
		
		System.out.println("Enter Make: ");
		String make = Driver.input.next();
		System.out.println("Enter Model: ");
		String model = Driver.input.next();
		System.out.println("Enter Year: ");
		int year = Driver.input.nextInt();
		System.out.println("Enter Color: ");
		String color = Driver.input.next();
		System.out.println("Enter List Price: ");
		double listPrice = Driver.input.nextDouble();
		
		
		
		
		
		AddCarToLotTester(new Car(make, model, year, color, listPrice));
		
		
		
	}
	
	public void AddCarToLotTester(Car carIN) {//tested
		Driver.cfm.CreateNewCar(carIN);
	}
	
	
	private void RemoveCar() {//testedbyTest
		listCars();
		System.out.println("Select a Car by Number");
		int i = Driver.input.nextInt();
		
		RemoveCarTest(i);
	
		
	}
	
	
	public void RemoveCarTest(int i) {//tested
//		
	
		Driver.cfm.removeCar(i);

	}
	
	
	private void listCars() {//tested by Test
		System.out.println(listCarsTest());
	}
	
	public String listCarsTest() {//tested
		return Driver.cfm.sysoutCarList();
	}
	
	
	
	private void listOffers() {//tested by Test
		System.out.println(listOffersTest());
	}

	public String listOffersTest() {//tested
		return Driver.cfm.sysoutOfferList();
	}
	
	
	public void AddCarToLotTest(Car carToAdd) {//tested
		Driver.cfm.CreateNewCar(carToAdd);

		Driver.log.info("Employee User " + this.getName() + " added " + carToAdd + " to the lot.");
	
		
	}
	

	
	
	
}
