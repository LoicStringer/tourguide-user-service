package com.tourguideuserservice.model;

import javax.money.CurrencyUnit;

import org.javamoney.moneta.Money;

public class UserTripPreferences {
	
	private int attractionProximity ;
	private CurrencyUnit currency  ;
	private Money lowerPricePoint ;
	private Money highPricePoint ;
	private int tripDuration ;
	private int ticketQuantity ;
	private int numberOfAdults ;
	private int numberOfChildren ;
	
	public UserTripPreferences() {
	}

	public int getAttractionProximity() {
		return attractionProximity;
	}

	public void setAttractionProximity(int attractionProximity) {
		this.attractionProximity = attractionProximity;
	}

	public CurrencyUnit getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}

	public Money getLowerPricePoint() {
		return lowerPricePoint;
	}

	public void setLowerPricePoint(Money lowerPricePoint) {
		this.lowerPricePoint = lowerPricePoint;
	}

	public Money getHighPricePoint() {
		return highPricePoint;
	}

	public void setHighPricePoint(Money highPricePoint) {
		this.highPricePoint = highPricePoint;
	}

	public int getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	public int getTicketQuantity() {
		return ticketQuantity;
	}

	public void setTicketQuantity(int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	@Override
	public String toString() {
		return "UserTripPreferences [attractionProximity=" + attractionProximity + ", currency=" + currency
				+ ", lowerPricePoint=" + lowerPricePoint + ", highPricePoint=" + highPricePoint + ", tripDuration="
				+ tripDuration + ", ticketQuantity=" + ticketQuantity + ", numberOfAdults=" + numberOfAdults
				+ ", numberOfChildren=" + numberOfChildren + "]";
	}
	
	

}
