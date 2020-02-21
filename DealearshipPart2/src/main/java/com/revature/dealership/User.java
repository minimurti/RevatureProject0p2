package com.revature.dealership;

import java.io.Serializable;
import java.util.LinkedList;


public abstract class User implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -8572654777650376391L;
	private String name;
	//private transient String pwd;
	private String pwd;
	
	
	private final String userType;
	
	
	
	
	
	public User(String inName, String inPwd, String type) {
		super();
		this.name = inName;
		this.pwd = inPwd;
		this.userType = type;
		
	}
	
	
//	
//	public String getPwd() {
//		return pwd;
//	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getUserType() {
		return userType;
	}

	
	
	
	public abstract boolean PromptUser(); 



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return name;
	}



	
	
	
	
	
	


	


	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
}
