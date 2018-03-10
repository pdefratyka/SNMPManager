package com.main;

import java.io.IOException;

import org.snmp4j.smi.OID;

import com.Agent;
import com.SnmpManager;

public class SnmpClient {
	public static void main(String[] args) throws IOException {
		String addressIp="153.19.121.167";
		String community="public";
		int port=161;
		
		Agent agent=new Agent(addressIp,port,community);
		SnmpManager client=new SnmpManager(agent);
		
		client.run();
		
		String sysDescr = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
		System.out.println(sysDescr);
		client.getSnmp().close();
	}

}
