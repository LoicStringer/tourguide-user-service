package com.tourguideuserservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tourguideuserservice.bean.ProviderBean;
import com.tourguideuserservice.bean.RewardBean;
import com.tourguideuserservice.bean.VisitedLocationBean;


public class User {

	private UUID userId;
	private String userName;
	private String phoneNumber;
	private String emailAddress;
	private Date latestLocationTimestamp;
	private List<VisitedLocationBean> visitedLocationsList = new ArrayList<>();
	private List<RewardBean> rewardBeansList = new ArrayList<>();
	private UserTripPreferences preferences = new UserTripPreferences();
	private List<ProviderBean> tripDealsList = new ArrayList<>();
	
	public User() {
	}

	public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getLatestLocationTimestamp() {
		return latestLocationTimestamp;
	}

	public void setLatestLocationTimestamp(Date latestLocationTimestamp) {
		this.latestLocationTimestamp = latestLocationTimestamp;
	}

	public List<VisitedLocationBean> getVisitedLocationsList() {
		return visitedLocationsList;
	}

	public void setVisitedLocationsList(List<VisitedLocationBean> visitedLocationsList) {
		this.visitedLocationsList = visitedLocationsList;
	}

	public List<RewardBean> getRewardBeansList() {
		return rewardBeansList;
	}

	public void setRewardBeansList(List<RewardBean> rewardBeansList) {
		this.rewardBeansList = rewardBeansList;
	}

	public UserTripPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(UserTripPreferences preferences) {
		this.preferences = preferences;
	}

	public List<ProviderBean> getTripDealsList() {
		return tripDealsList;
	}

	public void setTripDealsList(List<ProviderBean> tripDealsList) {
		this.tripDealsList = tripDealsList;
	}

	
	
	
}
