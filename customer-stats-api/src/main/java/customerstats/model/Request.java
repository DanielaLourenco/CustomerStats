package customerstats.model;

import java.sql.Timestamp;

public class Request {
	private int customerID;
	private int tagID;
	private String userID;
	private String remoteIP;
	private Timestamp timestamp;

	public Request() {

	}
	
	public Request(int customerID, int tagID, String userID, String remoteIP, Timestamp timestamp) {
		this.customerID = customerID;
		this.tagID = tagID;
		this.userID = userID;
		this.remoteIP = remoteIP;
		this.timestamp = timestamp;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getTagID() {
		return tagID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
