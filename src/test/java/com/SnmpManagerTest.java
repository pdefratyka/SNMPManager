package com;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.snmp4j.smi.OID;

public class SnmpManagerTest {
	SnmpManager snmpManager;
	Agent agent;
	String addressIp;
	String community;
	int port;

	@Before
	public void setUp() throws IOException{
		community="public";
		port=161;
		agent=new Agent(addressIp,port,community);
		snmpManager=new SnmpManager(agent);
		snmpManager.run();
	}
	@Test
	public void getParamAsStringFromFirstMachineCorrect() throws IOException {
		String expectedValue="RICOH Aficio MP 301 3.14 / RICOH Network Printer C model / "
				+ "RICOH Network Scanner C model / RICOH Network Facsimile C model";
		addressIp="153.19.121.162";
		
		snmpManager.getAgent().setAddressIp(addressIp);
		String sysDescr=snmpManager.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
		
		assertThat(sysDescr,equalTo(expectedValue));
	}
	@Test
	public void getParamAsStringFromSecondMachineCorrect() throws IOException{
		String expectedValue="HP ETHERNET MULTI-ENVIRONMENT";
		addressIp="153.19.121.167";
		
		snmpManager.getAgent().setAddressIp(addressIp);
		String sysDescr=snmpManager.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
		
		assertThat(sysDescr,equalTo(expectedValue));
	}
	@Test(expected=NullPointerException.class)
	public void getParamAsStringFromMachineWrongAddressIp() throws IOException{
		addressIp="";
		snmpManager.getAgent().setAddressIp(addressIp);
		String sysDescr=snmpManager.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
	}
	@Test(expected=NullPointerException.class)
	public void getParamAsStringFromFirstMachineWrongPort() throws IOException{
		addressIp="153.19.121.162";
		port=162;
		
		snmpManager.getAgent().setAddressIp(addressIp);
		snmpManager.getAgent().setPort(port);
		String sysDescr=snmpManager.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));

	}
	@Test(expected=NullPointerException.class)
	public void getParamAsStringFromSecondMachineWrongPort() throws IOException{
		addressIp="153.19.121.167";
		port=162;
		
		snmpManager.getAgent().setAddressIp(addressIp);
		snmpManager.getAgent().setPort(port);
		String sysDescr=snmpManager.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));

	}
	@After
	public void cleanUp() throws IOException{
		snmpManager.getSnmp().close();
	}
}
