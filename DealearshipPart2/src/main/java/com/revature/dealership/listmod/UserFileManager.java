package com.revature.dealership.listmod;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.dealership.Customer;
import com.revature.dealership.Driver;
import com.revature.dealership.User;
import com.revature.dealership.carMod.Employee;

public class UserFileManager {
	

	private static String url = "jdbc:postgresql://localhost:5432/postgres";
	private static String username = "postgres";
	private static String password = "postgres";
	
	
	
	




	
	public UserFileManager() {
		CheckForAdmin();
	}
//	private LinkedList<User> userList;


//	
//	protected void deleteRecent() {
//		userList.removeLast();
//		writeUserList();
//	}
	

	public void CheckForAdmin() {
		
		if(CheckNameAvailibility("admin")) {
			System.out.println("No List detected! ");
			System.out.println("Create password for accountname \"admin\" :");
			
			String password = Driver.input.next();
			CreateADMIN(password);
		}
		

	}
	
	
	public boolean CheckNameAvailibility(String usernameIN) {

		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select * from eluser where username = ?;");
			
			
			ps.setString(1, usernameIN);
			ResultSet rs = ps.executeQuery();
			
			
			
			if(rs.next()) {
				return false;
				}
			else {
				return true;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	
	}
	





	
	
	
	public User checkUser(String usernameIN, String passwordIN) {

		try(Connection conn = DriverManager.getConnection(url, username, password)){
			PreparedStatement ps = conn.prepareStatement("select * from eluser where username = ? and pass = ?;");
			
			ps.setString(1, usernameIN);
			ps.setString(2, passwordIN);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int i = rs.getInt(3);
				switch (i) {
				case 1:
					return new Customer(usernameIN, passwordIN);

				case 2:
					return new Employee(usernameIN, passwordIN);	
					
				case 3:
					return new Admin(passwordIN);
				
					
					

				default:
					break;
				}
				
				
				
				
				
				}
			else {
				return null;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		return null;
		
	}
	
	





	
	
	
	
	public User CreateNewCustomerAccount(String usernameIN, String passwordIN) {
		
		
		
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("insert into eluser values (?,?,1);");

			ps.setString(1, usernameIN);
			ps.setString(2, passwordIN);
			
//			
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println(e);
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		return new Customer(usernameIN, passwordIN);
		
		
	}

	
	protected User CreateNewEmployeeAccount(String usernameIN, String passwordIN) {
		
		
		
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("insert into eluser values (?,?,2);");

			ps.setString(1, usernameIN);
			ps.setString(2, passwordIN);
			
//			
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println(e);
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	
		
		return new Employee(usernameIN, passwordIN);
	}
	
	private User CreateADMIN(String passwordIN) {
		
		
		
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			
			PreparedStatement ps = conn.prepareStatement("insert into eluser values ('admin',?,3);");

	
			ps.setString(1, passwordIN);
			
//			
			
			ps.execute();
			
		} catch(SQLException e) {
			System.out.println(e);
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	
		
		return new Admin(passwordIN);
	}
	
	
	
	
	
	
}
