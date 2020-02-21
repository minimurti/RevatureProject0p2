package com.revature.dealership.listmod;

//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//import java.util.LinkedList;

import com.revature.dealership.Driver;
import com.revature.dealership.User;

public class Admin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1337400792577253620L;



	protected Admin(String inPwd) {
		super("admin", inPwd, "Admin");
		// TODO Auto-generated constructor stub
		
		
		
	}
	
	
	

	
	
	


                                  

	@Override
	public boolean PromptUser() {
		// TODO Auto-generated method stub
		System.out.println("Welcome System Administrator.");
		System.out.println("Add or Remove an Employee Username or Type \"exit\":");
		String testEmployeeName = Driver.input.next();
		
		if(testEmployeeName.equalsIgnoreCase("exit")) {
			return false;
		}
		
		if(!Driver.ufm.CheckNameAvailibility(testEmployeeName)) {
			System.out.println("Username \"" + testEmployeeName + "\" already taken.");
			return true;
		}
		else {
			System.out.println("Create \"" + testEmployeeName + "\" employee account? (Y/n):");
			String answer = Driver.input.next();
			if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("yes")) {
				CreateEmployee(testEmployeeName);
			}
			return true;
		}
		
		
		

		
	}
	
	
	
	
	private void CreateEmployee(String EmployeeName) {
		
		
		System.out.println("Create password for " + EmployeeName + " :");
		
		String password = Driver.input.next();


		
		
		if(password.isEmpty()) {
			System.out.println("Please Enter Atleast One Character!");
			return;
		}
		
		System.out.println("Created!");
		Driver.ufm.CreateNewEmployeeAccount(EmployeeName, password);
		return;
	}
	
	
	
	public void deleteLastUser() {
		Driver.ufm.deleteRecent();
	}
	
	

}
