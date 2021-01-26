package com.tourguideuserservice.model;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.VisitedLocationBean;

public class UserReward {
	
	private VisitedLocationBean visitedLocationBean;
	private AttractionBean attractionBean;
	private int rewardCentralPoints;
	
	public UserReward() {
	}

	public UserReward(VisitedLocationBean visitedLocationBean, AttractionBean attractionBean,
			int rewardCentralPoints) {
		super();
		this.visitedLocationBean = visitedLocationBean;
		this.attractionBean = attractionBean;
		this.rewardCentralPoints = rewardCentralPoints;
	}

	public VisitedLocationBean getVisitedLocationBean() {
		return visitedLocationBean;
	}

	public void setVisitedLocationBean(VisitedLocationBean visitedLocationBean) {
		this.visitedLocationBean = visitedLocationBean;
	}

	public AttractionBean getAttractionBean() {
		return attractionBean;
	}

	public void setAttractionBean(AttractionBean attractionBean) {
		this.attractionBean = attractionBean;
	}

	public int getRewardCentralPoints() {
		return rewardCentralPoints;
	}

	public void setRewardCentralPoints(int rewardCentralPoints) {
		this.rewardCentralPoints = rewardCentralPoints;
	}

	@Override
	public String toString() {
		return "UserReward [visitedLocationBean=" + visitedLocationBean + ", attractionBean=" + attractionBean
				+ ", rewardCentralPoints=" + rewardCentralPoints + "]";
	}
	


}
