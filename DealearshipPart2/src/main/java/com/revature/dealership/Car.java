package com.revature.dealership;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Car implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8886423260978171832L;
	
	private String make;
	private String model;
	private int year;
	
	private String color;
	
	private boolean isAccepted = false;
	
	private double listPrice;
	private LinkedList<Offer> offers;
	

	public Car(String make, String model, int year, String color, double listPrice) {
		super();
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
		this.listPrice = listPrice;
		offers = new LinkedList<Offer>();
	}
	
	public void AcceptOffer(User user){
		this.isAccepted = true;
		Iterator<Offer> i = offers.iterator();
		Offer current = null;
		while(i.hasNext()) {
			   current = i.next();
			   
			   if(user == current.getOfferer()) {
				   current.setAccepted(true);
			   }
			   else {
				   i.remove();////removes (rejects the offer)
			   }
			   
			   
		}
		
		
	}
	
	public void RejectOffer(int i ) {
		
		offers.remove(i);
		
//		Iterator<Offer> i = offers.iterator();
//
//		while(i.hasNext()) {
//			   
//			   if(user == i.next().getOfferer()) {
//				   i.remove();////removes (rejects the offer)
//			   }
//			   
//		}
	}
	
	public Offer getOffer(int i) {
		return offers.get(i);
	}

	@Override
	public String toString() {
		return "Make=" + make + ", Model=" + model + ", Year=" + year + ", Color=" + color + ", List Price="
				+ listPrice + " Number of Offers= " + offers.size();
	}
	
	public void addOffer (Offer input) {
		offers.add(input);
	}
	
	
	
	public String PrintOffers() {
		Iterator<Offer> i = offers.iterator();
		int j = 0;
		String ret = "";
		Offer current;
		while(i.hasNext()){
			j++;
			current = i.next();
			System.out.println(j + ". " + current.toString());
			ret += j + ". " + current.toString() + "\n";

		}
		System.out.println("\n ");
		return ret;
	}
	
	
	public User getOfferUser(int i) {
		return offers.get(i).getOfferer();
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (isAccepted ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(listPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((offers == null) ? 0 : offers.hashCode());
		result = prime * result + year;
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
		Car other = (Car) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (isAccepted != other.isAccepted)
			return false;
		if (Double.doubleToLongBits(listPrice) != Double.doubleToLongBits(other.listPrice))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (offers == null) {
			if (other.offers != null)
				return false;
		} else if (!offers.equals(other.offers))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	public String getColor() {
		return color;
	}
	
	
	
	
}
