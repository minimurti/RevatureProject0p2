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

	
	Customer cust = new Customer("cust","cust");
	Employee emp = new Employee("emp","emp");
	
	
	
	@Before 
	public void Prepare(){
		Driver.cfm.SHOWTIME();
	}
	
	
	
	@Test
	public void AcceptOffer() {////////////////////////////////////////////////////////////////////////////
		
		
		
		 String Tester = (new Customer("Mike","mike")).listMyCarTest();
		 
		 assertFalse(Tester.contains("Reva"));//////show mike doesnt own the car

		
		
		 Tester = emp.listOffersTest();
		
		 assertTrue(Tester.contains("$28000.0 from Nick for the Revature, Reva, 2020"));
		 assertTrue(Tester.contains("$29999.0 from Mike for the Revature, Reva, 2020"));//show all offers exist
		 assertTrue(Tester.contains("$23000.0 from Tyler for the Revature, Reva, 2020"));
		 
		
		 emp.AcceptOfferTest(1, 1);
		 
		 Tester = emp.listCarsTest();
		 
		 assertFalse(Tester.contains("$28000.0 from Nick for the Revature, Reva, 2020"));
		 assertFalse(Tester.contains("$29999.0 from Mike for the Revature, Reva, 2020"));//show all offers inactive
		 assertFalse(Tester.contains("$23000.0 from Tyler for the Revature, Reva, 2020"));
		 
		
		 Tester = (new Customer("Mike","mike")).listMyCarTest();
		 
		 assertTrue(Tester.contains("Revature"));//show mike now does have the car

	}
	
	
	@Test
	public void ListPayments() {
		
		String Tester = emp.ListAllPayments();
		
		assertFalse(Tester.contains("Mike owes a total of $29,999.00 with 60 remaining"));
		
		 emp.AcceptOfferTest(1, 1);
		 
		 Tester = emp.ListAllPayments();
		 
		 assertTrue(Tester.contains("Mike owes a total of $29,999.00 with 60 remaining"));
		 		 
		 
	}
	
	
	@Test
	public void ListMyCars() {
		
		String Tester = (new Customer("Mike","mike")).listMyCarTest();
		 
		assertFalse(Tester.contains("Reva"));//////show mike doesnt own the car
		
		emp.AcceptOfferTest(1, 1);
		 
		Tester = (new Customer("Mike","mike")).listMyCarTest();
		 
		 assertTrue(Tester.contains("Reva"));
		 		 
		 
	}
	
	@Test
	public void ListMyPayments() {
		
		String Tester = (new Customer("Mike","mike")).listPaymentsIn();
		 
		assertFalse(Tester.contains("29,999.00"));//////show mike doesnt own the car
		
		emp.AcceptOfferTest(1, 1);
		 
		Tester = (new Customer("Mike","mike")).listPaymentsIn();
		 
		 assertTrue(Tester.contains("29,999.00"));
		 		 
		 
	}
	
	
	
	
	
	
	
	
	@Test
	public void RejectOffer() {
		
		
		
		 String Tester = emp.listOffersTest();
		
		 assertTrue(Tester.contains("Mike"));
		
		 emp.RejectOfferTest(1);
		 
		 Tester = emp.listCarsTest();
		 
		 assertFalse(Tester.contains("Mike"));

	}
	
	
	@Test
	public void AddCar() {
		
		Car addme = new Car("BLLLLLLLLLL", "r", 2000, "blue", 1234.0);
		
		 String Tester = emp.listCarsTest();
		
		 assertFalse(Tester.contains("BLLLLLLLLLL"));
		
		 emp.AddCarToLotTester(addme);
		 
		 Tester = emp.listCarsTest();
		 
		 assertTrue(Tester.contains("BLLLLLLLLLL"));

	}
	
	
	
	@Test
	public void RemoveCar() {
		
		 String Tester = emp.listCarsTest();
		
		 assertTrue(Tester.contains("Camry"));
		
		 emp.RemoveCarTest(4);
		 
		 Tester = emp.listCarsTest();
		 
		 assertFalse(Tester.contains("Camry"));

	}

	
	
	@Test
	public void ListCarsTest() {
		
		Car addme = new Car("gcgcgc", "r", 2000, "blue", 1234.0);
		
		emp.AddCarToLotTest(addme);
		
		String Tester = emp.listCarsTest();
		
		assertTrue(Tester.contains("gcgcgc"));
	}

	
	
	@Test
	public void EmpAddCar() {
		
		Car addme = new Car("lrclrclrc", "r", 2000, "blue", 1234.0);
		
		emp.AddCarToLotTest(addme);
		
		String Tester = emp.listCarsTest();
		
		assertTrue(Tester.contains("lrclrclrc"));
	}
	
	
	
	@Test
	public void EmpListOffers() {
		
		cust.MakeOfferTest(4, 84848.0);
		
		
		String Tester = emp.listOffersTest();
		
		assertTrue(Tester.contains("8484"));
	}
	
	
	
	@Test
	public void CustListCars() {
		
		
		String Tester = cust.listCarsTest();
		
		assertTrue(Tester.contains("Revature"));
	}
	
	
	@Test
	public void MakeOfferTest() {
		
		cust.MakeOfferTest(4, 987987987987.0);
		
		String Tester = emp.listOffersTest();
		
		assertTrue(Tester.contains("987987987"));
		
		
	}
	
	@Test
	public void NullTest() {
		assertTrue(true);
	}
	
	

}
