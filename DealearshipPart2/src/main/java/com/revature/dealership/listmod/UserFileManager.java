package com.revature.dealership.listmod;
import com.revature.dealership.*;
import com.revature.dealership.carMod.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.ListIterator;



public class UserFileManager {
	
	private LinkedList<User> userList;

	public UserFileManager() {
		
		checkUserFile();
		
		userList = readUserList();
		// TODO Auto-generated constructor stub
	}

	
	protected void deleteRecent() {
		userList.removeLast();
		writeUserList();
	}
	
	
	public LinkedList<User> getUserList() {
		return userList;
	}
	
	private void writeUserList() {
		String filename;
		filename = "users.dat";
		
		
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(userList);
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
	
	
	
	public boolean CheckNameAvailibility(String username) {

		ListIterator<User> listIterator = userList.listIterator();
		while (listIterator.hasNext()) {
			
			if(listIterator.next().getName().equals(username)) {
				return false;
			}
			
		}
		
		return true;
	}
	
	
	public void AddCartoUser(User input, Car carInput) {
		ListIterator<User> listIterator = userList.listIterator();
		User current = null;
		while (listIterator.hasNext()) {
			current = listIterator.next();
			if( current.equals(input)) {
				((Customer) current).addCar(carInput);
				writeUserList();
				return;
			}
			
		}

		
	}
	
	
	
	
	






	
	
	
	public User checkUser(String username, String password) {

		
		boolean first = true;
		
		ListIterator<User> listIterator = userList.listIterator();
		while (listIterator.hasNext()) {
			
			User check = listIterator.next();
			
			
			
			if(check.equals(new Customer(username, password))) {
				return check;
			}
			
			
			if(check.equals(new Employee(username, password))) {
				return check;
			}
			
			if(first && check.equals(new Admin(password)) && username.equals(new String("admin")) ) {///add check for admin later
				return check;
			}
			else {
				first = false;
			}
		
		
			
		}
		
		return null;
		
	}
	
	






	@SuppressWarnings("unchecked")
	public LinkedList<User> readUserList() {
		
		String filename = "users.dat";


		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);

			this.userList = (LinkedList<User>) ois.readObject();

			if(userList.isEmpty()) {
				System.out.println("File Corruption Detected. Delete user.dat file and restart");
				return null;
			}
			
			fin.close();
			ois.close();
			
			return userList;
			

		} catch (Exception ex) {
			//Scanner scan = new Scanner(System.in);
			
			System.out.println("No List detected! ");
			System.out.println("Create password for accountname \"admin\": ");
			
			String password = Driver.input.next();
	
			
			if(password.isEmpty()) {
				ex.printStackTrace();
			}
			else {
				
				initializeUserList(new Admin(password));
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
	public void checkUserFile() {
		/////////////////////CHECK Status of user list file
		String filename = "users.dat";


		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);

		
			this.userList = (LinkedList<User>) ois.readObject();

			if(userList.isEmpty()) {
				System.out.println("File Corruption Detected. Delete User.date file and restart");
				return;
			}
			
			fin.close();
			ois.close();
			
			
			

		} catch (Exception ex) {
			//Scanner exinput = new Scanner(System.in);
			
			System.out.println("No List detected! ");
			System.out.println("Create password for accountname \"admin\" :");
			
			String password = Driver.input.next();
	

			
			
			if(password.isEmpty()) {
				ex.printStackTrace();
			}
			else {
				initializeUserList(new Admin(password));
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

/////////////////////done with CHECK Status of user list file
		
		
		

		
		
		
		
	}
	
	
	
	
	public User CreateNewCustomerAccount(String username, String password) {
		
		userList.add(new Customer(username, password));
		writeUserList();
		return readUserList().getLast();
		
	}

	
	protected User CreateNewEmployeeAccount(String username, String password) {///should only be used by admin via shared package

		userList.add(new Employee(username, password));
		writeUserList();
		return readUserList().getLast();
		
	}
	
	
	private void initializeUserList(Admin admin) {
		
		
		String filename;
		filename = "users.dat";
		
		
		this.userList = new LinkedList<User>();
		
		userList.add(admin);
		
		
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(userList);
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
	
	
	
	
}
