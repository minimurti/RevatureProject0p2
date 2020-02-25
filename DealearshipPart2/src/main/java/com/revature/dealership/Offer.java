//package com.revature.dealership;
//
//import java.io.Serializable;
//
//public class Offer implements Serializable {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -3357984333445655156L;
//	
//	private User Offerer;
//	private String username;
//
//	private double amount;
//	
//	private boolean isAccepted = false;
//	
//
//	public User getOfferer() {
//		return Offerer;
//	}
//
//
//
//	public double getAmount() {
//		return this.amount;
//	}
//
//	public void setAccepted(boolean isAccepted) {
//		this.isAccepted = isAccepted;
//	}
//	
//	public boolean getAccepted() {
//		return this.isAccepted;
//	}
//
//	public Offer(User offerer, Car car, double amount) {
//		super();
//		this.Offerer = offerer;
//
//		this.amount = amount;
//		this.username = offerer.getName();
//	}
//
//	public Offer(String username, Car car, double amount) {
//		super();
//		this.Offerer = null;
//		
//		this.amount = amount;
//		this.username = username;
//	}
//	
//	
//	
//	
//	
//	@Override
//	public String toString() {
//		return "Amount=$" + amount + ", Accepted=" + isAccepted + ", Offerer=" + username + ", Car=" + car;
//	}
//	
//	
//	
//}
