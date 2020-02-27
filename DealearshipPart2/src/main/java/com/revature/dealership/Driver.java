package com.revature.dealership;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.dealership.carMod.CarFileManager;
import com.revature.dealership.listmod.*;
//package com.revature.driver;

//import jdk.internal.org.jline.utils.Log;

import org.apache.log4j.Logger;

public class Driver {
	
	public static Logger log = Logger.getRootLogger();
	
	public static Scanner input = new Scanner(System.in);
	public static UserFileManager ufm = new UserFileManager();
	public static CarFileManager cfm = new CarFileManager();
	
	

	public static void main(String[] args) {
		

	
//		 cfm.SHOWTIME();//sets up a list of pre-existing cars and users. (For normas Use, comment out);
//		 System.out.println("Show Time!\n");
//		

		
		System.out.println("\n\n\nWelcome to the New Revature Car Leasing Program!\n\n");
		
		
		System.out.println("Enter Username: ");
		String username = input.next();
		
		
		
		
		
		System.out.println("Enter Password: ");
		String password = input.next();

		
		
		User CurrentUser = ufm.checkUser(username, password);
		
		while(CurrentUser == null) {
			
			System.out.println("Username and Password Combination not Found.");
			log.info("Username " + username + " entered but not found");
			System.out.println("Creat New Customer Account? (Y/n)");
			String answer = input.next();
			//String newUsername = null;
			
			if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("yes")) {
				
				boolean UniqueName = false;
				
				if(ufm.CheckNameAvailibility(username)) {
					UniqueName = true;
				}

				
				while(!UniqueName) {
					System.out.println("That Username is taken.");
					System.out.println("Enter a Unique Username: ");
					username = input.next();
					if(ufm.CheckNameAvailibility(username)) {
						UniqueName = true;
					}
					else {
						
					}
				}
				
				
				System.out.println("Enter Password: ");
				String newPassword = input.next();
				
				CurrentUser = ufm.CreateNewCustomerAccount(username, newPassword);
				log.info("Username " + username + " Created a Customer Account");
			}else {
				System.out.println("Enter Username: ");
				
				username = input.next();

				System.out.println("Enter Password: ");
				password = input.next();

				
				
				CurrentUser = ufm.checkUser(username, password);
			}
				
			
			

		}
		
		log.info("Username " + username + " has logged in to as a(n) " + CurrentUser.getUserType() + ".");
		
		boolean continueLoop = true;
		
		while(continueLoop) {
		try {
			continueLoop = CurrentUser.PromptUser();
			
			
		}catch(InputMismatchException e) {
			Driver.input.next();
			Driver.log.warn("Username " + CurrentUser.getName() + " caused error", e);
			
			System.out.println("Please Enter Valid Input");
		}catch(IndexOutOfBoundsException e) {
			Driver.log.warn("Username " + CurrentUser.getName() + " caused error", e);
			
			//Log.warn("Please Enter Valid Range");
			System.out.println("Please Enter Valid Range");
		}catch(IllegalAccessError e) {
			Driver.log.warn("Username " + CurrentUser.getName() + " caused error", e);
			
			//Log.warn("Please Enter Valid Input");
			System.out.println("Please Enter Valid Input");
		}
		
		
		
		}
		
		
		input.close();
		
		
		return; 
	}
	
	
	public static boolean test() {
		return true;
	}
	
	







	
		
	
	
	
	
	
	

}
