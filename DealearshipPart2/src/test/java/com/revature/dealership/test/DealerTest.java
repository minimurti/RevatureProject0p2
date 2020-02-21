package com.revature.dealership.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dealership.Car;
import com.revature.dealership.Customer;
import com.revature.dealership.Driver;
import com.revature.dealership.User;
import com.revature.dealership.carMod.CarFileManager;
import com.revature.dealership.carMod.Employee;
import com.revature.dealership.listmod.Admin;
import com.revature.dealership.listmod.UserFileManager;

public class DealerTest {
	static User AdminUser;
	static User EmployeeUser;
	static User CustomerUser;
	static Car TestCar;
	public static UserFileManager ufm = null;
	public static CarFileManager cfm = null;
	//User CustomerUser;
	

	
	@BeforeClass // runs once before each test
	public static void setUp() throws Exception {
		
		InputStream sysInBackup = System.in;

		
		 // backup System.in to restore it later
		
		String simulatedUserInput = "adminPass" + System.getProperty("line.separator") + "admin" + System.getProperty("line.separator") + "adminPass" + System.getProperty("line.separator") + "Johny" + System.getProperty("line.separator")
	    + "y" + System.getProperty("line.separator") + "jpass" + System.getProperty("line.separator") + "exit" + System.getProperty("line.separator");
		
		System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
		
        Path temp = Files.move 
        (Paths.get("users.dat"),  
        Paths.get("datBackup/users.dat.backup")); 
  

        
        Path carTemp = Files.move 
        (Paths.get("cars.dat"),  
        Paths.get("datBackup/cars.dat.backup")); 

		
		////////////////////////////////////////////////////////////backup previos dat file
		

		
		

		

		
		Driver.main(null);
		
		ufm = new UserFileManager();
		cfm = new CarFileManager();
		
		
		TestCar = cfm.CreateNewCar(new Car("Toyota", "Prius", 2008, "Green", 7000.00));
		CustomerUser = ufm.CreateNewCustomerAccount("Jordan Customer", "jpass");
		
		AdminUser = ufm.checkUser("admin", "adminPass");
		EmployeeUser = ufm.checkUser("Johny", "jpass");
		//EmployeeUser = ufm.checkUser("admin", "adminPass");
		
		//AdminUser.PromptUser();
		
		
		
		System.setIn(sysInBackup);
		

		
		
	}
	
	

	
	
	
	@Test
	public void EmpViewPaymentsOnCars() {
		Car carToAdd = new Car("Basic", "GreyCar", 2017, "Grey", 35000.00);
		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		int indexOne = Driver.cfm.getCarList().indexOf(carToAdd);
		
		carToAdd = new Car("Basic", "RedCar", 2017, "Red", 35000.00);
		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		int indexTwo = Driver.cfm.getCarList().indexOf(carToAdd);
		
		carToAdd = new Car("Basic", "GreenCar", 2017, "Green", 35000.00);
		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		int indexThree = Driver.cfm.getCarList().indexOf(carToAdd);
		
		
		User Bob = Driver.ufm.CreateNewCustomerAccount("Bob", "Incredible");
		User Edna = Driver.ufm.CreateNewCustomerAccount("Edna", "Darling");
		
		((Customer) Bob).MakeOfferTest(indexOne +1, 60000.0);
		((Employee) EmployeeUser).AcceptOfferTest(indexOne + 1, 1);
		((Customer) Bob).MakeOfferTest(indexTwo +1, 30000.0);
		((Employee) EmployeeUser).AcceptOfferTest(indexTwo + 1, 1);
		((Customer) Edna).MakeOfferTest(indexThree +1, 20000.0);
		((Employee) EmployeeUser).AcceptOfferTest(indexThree + 1, 1);
		
		
		String AllPayments = ((Employee) Driver.ufm.checkUser("Johny", "jpass")).ListAllPayments();
		
		
		
		
		assertTrue(AllPayments.contains("Bob owes a total of $60000.00") && AllPayments.contains("Bob owes a total of $30000.00") && AllPayments.contains("Edna owes a total of $20000.00"));

	}
	
	
	@Test
	public void CustViewPaymentsOnNOCars() {

		User Kenny = Driver.ufm.CreateNewCustomerAccount("Kenny", "IamMysterion");
		

		
		((Customer) Driver.ufm.checkUser("Kenny", "IamMysterion")).listPaymentsIn(0);

	}
	
	
	
	@Test
	public void CustViewPaymentsOnCars() {
		Car carToAdd = new Car("Nintendo", "Landmaster", 2017, "Grey", 65000.00);
		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		int indexAdded = Driver.cfm.getCarList().indexOf(carToAdd);
		
		User Fox = Driver.ufm.CreateNewCustomerAccount("Fox", "heya");
		
		((Customer) Fox).MakeOfferTest(indexAdded +1, 60000.0);
		((Employee) EmployeeUser).AcceptOfferTest(indexAdded + 1, 1);
		
		String payments = ((Customer) Driver.ufm.checkUser("Fox", "heya")).listPaymentsIn(0);
		//int i =0;
		
		assertTrue(payments.equalsIgnoreCase("Total of $60000.00, with 60 remaining payments of $1000.00 per month, over the course of 5 years 0% APR."));
		
	}
	
	
	
	@Test
	public void ViewOwnedCars() {
		Car carToAdd = new Car("Nintendo", "Kart", 2017, "Rainbow", 15000.00);
		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		int indexAdded = Driver.cfm.getCarList().indexOf(carToAdd);
		
		User Mario = Driver.ufm.CreateNewCustomerAccount("Mario", "itsame");
		
		((Customer) Mario).MakeOfferTest(indexAdded +1, 6125.0);
		((Employee) EmployeeUser).AcceptOfferTest(indexAdded + 1, 1);
		
		String carList = ((Customer) Driver.ufm.checkUser("Mario", "itsame")).listMyCars();
		//int i =0;
		
		assertTrue(carList.contains("Nintendo"));
		
	}
	
	
	@Test
	public void RemoveEmptyCar() {

		for(int i = 0; i < 20; i++) {

		((Employee) EmployeeUser).RemoveCarTest(0);

		
		}
		

	}
	
	
	@Test
	public void EmployeeRemoveCar() {
		Car carToAdd = new Car("Unique", "NewYork", 2017, "Gold", 15000.00);
		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		
		
		
		boolean iGive = Driver.cfm.getCarList().contains(carToAdd);
		int indexAdded = Driver.cfm.getCarList().indexOf(carToAdd);

		((Employee) EmployeeUser).RemoveCarTest(indexAdded);
		boolean iTAKE = !Driver.cfm.getCarList().contains(carToAdd);
		
		
		
		assertTrue(iGive && iTAKE);
	}
	
	
	@Test
	public void createNewUser() {
		User newUser = ufm.CreateNewCustomerAccount("New Customer", "npass");
		//suprize...
	}
	
	
	
	
	
	
	
	
	
	@Test
	public void SystemCanRejectDoubleChekck() {//the SystemRejectOffers is the true test, but if this fails it is not valid
		Car carToAdd = new Car("Ford", "Focus", 2017, "Silver", 15000.00);
		
		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		
		int index = Driver.cfm.getCarList().indexOf(carToAdd);
		
		((Customer) CustomerUser).MakeOfferTest(index + 1, 6125.0);
		((Customer) CustomerUser).MakeOfferTest(index + 1, 4125.0);
		((Customer) CustomerUser).MakeOfferTest(index + 1, 3125.0);
		((Customer) CustomerUser).MakeOfferTest(index + 1, 2125.0);
		
		
		
		
		Driver.cfm.getCarList().get(index).getOffer(0);
		Driver.cfm.getCarList().get(index).getOffer(1);
		Driver.cfm.getCarList().get(index).getOffer(2);
		Driver.cfm.getCarList().get(index).getOffer(3);//make sure the offers still there.
		
	
		
	}
	
	
	
	
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void SystemRejectOffers() {
		
		Car carToAdd = new Car("Ford", "Focus", 2017, "Silver", 15000.00);

		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		
		((Customer) CustomerUser).MakeOfferTest(1, 6125.0);
		((Customer) CustomerUser).MakeOfferTest(1, 4125.0);
		((Customer) CustomerUser).MakeOfferTest(1, 3125.0);
		((Customer) CustomerUser).MakeOfferTest(1, 2125.0);
		
		
		
		
		((Employee) EmployeeUser).AcceptOfferTest(1, 1);
		
		
		Car testCar = Driver.cfm.getCarList().get(0);
		
		//String shouldContain = "Amount=$6125.0, Accepted=false, Offerer=Jordan Customer, Car=Make=Toyota, Model=Camry, Year=2018, Color=Silver, List Price=17000.0";
		if(Driver.cfm.getCarList().get(0).getOffer(0).getAccepted()) {
			
			Driver.cfm.getCarList().get(0).getOffer(1);//if the offer is accepted, there should not be anything at index 1, since all else was rejected
			
		}
	
		
	}
	
	
	@Test
	public void testCustomerLogin() {
		assertEquals(new Customer("Jordan Customer", "jpass") , ufm.checkUser("Jordan Customer", "jpass"));
		//assertEquals(true, true);
	}
	

	@Test
	public void testEmployeeLogin() {
		assertEquals(new Employee("Johny", "jpass") , ufm.checkUser("Johny", "jpass"));
		//assertEquals(true, true);
	}
	

	
	
	@Test
	public void testEmployeeAcceptOffer() {
		
		//Car carToAdd = new Car("Toyota", "Camry", 2018, "Silver", 17000.00);

		((Customer) CustomerUser).MakeOfferTest(1, 6125.0);
		
		
		((Employee) EmployeeUser).AcceptOfferTest(1, 1);
		
		
		
		
		String out = Driver.cfm.getCarList().get(0).PrintOffers();
		
		String shouldContain = "Amount=$6125.0, Accepted=true";
		
	
		assertTrue(out.contains(shouldContain));
	}
	
	
	
	
	@Test
	public void testCustomerMakeOffer() {
		
		Car carToAdd = new Car("Toyota", "Camry", 2018, "Silver", 17000.00);

		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		
		
		int index = Driver.cfm.getCarList().indexOf(carToAdd);
		
		((Customer) CustomerUser).MakeOfferTest(index +1, 1234123.0);//specialNumb
		
		String out = Driver.cfm.getCarList().get(index).PrintOffers();
		
		String shouldContain = "Amount=$1234123";
	
		assertTrue(out.contains(shouldContain));
	}
	
	
	@Test
	public void testCustomerMakeOfferFalse() {
		
		//Car carToAdd = new Car("Toyota", "Camry", 2018, "Silver", 17000.00);

		((Customer) CustomerUser).MakeOfferTest(1, 6125.0);
		
		String out = Driver.cfm.getCarList().get(0).PrintOffers();
		
		String shouldNotContain = "1. Amount=$123456, Accepted=false, Offerer=Jordan Customer, Car=Make=Toyota, Model=Camry, Year=2018, Color=Silver, List Price=17000.0 Number of Offers= 1";
		
	
		assertFalse(out.contains(shouldNotContain));
	}

	
	@Test
	public void testCustomerViewCar() {
		
		//Car carToAdd = new Car("Toyota", "Camry", 2018, "Silver", 17000.00);

		String readList = ((Customer) CustomerUser).listCarsTest();
		
		String shouldContain = "1. Make=Toyota, Model=Camry, Year=2018, Color=Silver, List Price=17000.0";//ignoreNumOffers
	
		assertTrue(readList.contains(shouldContain));
	}
	
	@Test
	public void testCustomerViewCarFalse() {
		
		//Car carToAdd = new Car("Toyota", "Camry", 2018, "Silver", 17000.00);

		String readList = ((Customer) CustomerUser).listCarsTest();
		
		String shouldContain = "1. Make=Ridiculous, Model=Loca, Year=2018, Color=Silver, List Price=17000.0";
	
		assertFalse(readList.contains(shouldContain));
	}
	
	
	
	@Test
	public void testEmployeeAddCar() {
		
		Car carToAdd = new Car("Toyota", "Camry", 2018, "Silver", 17000.00);

		((Employee) EmployeeUser).AddCarToLotTest(carToAdd);
		
		
		assertEquals(carToAdd , Driver.cfm.getCarList().getLast());
		//assertEquals(true, true);
	}
	
	
	
	
	
	@After // runs once after each test
	public void tearDown() throws Exception {
	}
	
	
	

	
	
	@AfterClass// runs once after all tests finish
	public static void tearDownAfterClass() throws Exception {
		
		
		
		
	  
	        File file = new File("users.dat"); 
	          
	        if(file.delete()) 
	        { 
	            System.out.println("New User Test File deleted successfully"); 
	        } 
	        else
	        { 
	            System.out.println("Failed to delete the file"); 
	        } 
	    
	        File fileCar = new File("cars.dat"); 
	          
	        if(fileCar.delete()) 
	        { 
	            System.out.println("New Car test File deleted successfully"); 
	        } 
	        else
	        { 
	            System.out.println("Failed to delete the file"); 
	        } 
		
		
		
		
		
		
		
        Path tempBack = Files.move 
        (Paths.get("datBackup/users.dat.backup"),  
        Paths.get("users.dat")); 
  
        if(tempBack != null) 
        { 
            System.out.println("File renamed and moved successfully"); 
        } 
        else
        { 
            System.out.println("Failed to move the file"); 
        } 
        
        
        Path carTempBack = Files.move 
        (Paths.get("datBackup/cars.dat.backup"),  
        Paths.get("cars.dat")); 
  
        if(carTempBack != null) 
        { 
            System.out.println("File renamed and moved successfully"); 
        } 
        else
        { 
            System.out.println("Failed to move the file"); 
        } 
	
        
	}
	

}
