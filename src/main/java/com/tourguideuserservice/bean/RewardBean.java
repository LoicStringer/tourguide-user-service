package com.tourguideuserservice.bean;




public class RewardBean {

	private VisitedLocationBean visitedLocationBean;
	private AttractionBean attractionbean;
	private int rewardCentralPoints;
	
	public RewardBean() {
	}

	public RewardBean(VisitedLocationBean visitedLocationBean, AttractionBean attractionbean,
			int rewardCentralPoints) {
		super();
		this.visitedLocationBean = visitedLocationBean;
		this.attractionbean = attractionbean;
		this.rewardCentralPoints = rewardCentralPoints;
	}

	public VisitedLocationBean getVisitedLocationBean() {
		return visitedLocationBean;
	}

	public void setVisitedLocationBean(VisitedLocationBean visitedLocationBean) {
		this.visitedLocationBean = visitedLocationBean;
	}

	public AttractionBean getAttractionbean() {
		return attractionbean;
	}

	public void setAttractionbean(AttractionBean attractionbean) {
		this.attractionbean = attractionbean;
	}

	public int getRewardCentralPoints() {
		return rewardCentralPoints;
	}

	public void setRewardCentralPoints(int rewardCentralPoints) {
		this.rewardCentralPoints = rewardCentralPoints;
	}
	
	
}
