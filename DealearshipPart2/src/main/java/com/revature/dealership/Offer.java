package com.revature.dealership;

import java.io.Serializable;

public class Offer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3357984333445655156L;
	
	private User Offerer;
	private Car car;
	private double amount;
	
	private boolean isAccepted = false;
	

	public User getOfferer() {
		return Offerer;
	}

	public Car getCar() {
		return this.car;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	
	public boolean getAccepted() {
		return this.isAccepted;
	}

	public Offer(User offerer, Car car, double amount) {
		super();
		Offerer = offerer;
		this.car = car;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Amount=$" + amount + ", Accepted=" + isAccepted + ", Offerer=" + Offerer + ", Car=" + car;
	}
	
	
	
}
