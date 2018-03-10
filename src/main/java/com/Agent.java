package com;

public class Agent {
	private String addressIp;
	private int port;
	private String community;
	
	public Agent(String addressIp, int port, String community){
		this.addressIp=addressIp;
		this.port=port;
		this.community=community;
	}

	public String getAddressIp() {
		return addressIp;
	}

	public void setAddressIp(String addressIp) {
		this.addressIp = addressIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
	
}
