package com;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpManager {
	
	private Snmp snmp;
	private Agent agent;
	
	public SnmpManager(Agent agent){
		this.agent=agent;
	}
	
	public void run() throws IOException{
		TransportMapping transport=new DefaultUdpTransportMapping();
		snmp=new Snmp(transport);
		transport.listen();
	}
	public ResponseEvent get(OID oids[]) throws IOException{
		PDU pdu=new PDU();
		for(OID oid:oids){
			pdu.add(new VariableBinding(oid));
		}
		pdu.setType(PDU.GET);
		ResponseEvent event=snmp.send(pdu, getTarget(),null);
		if(event!=null){
			return event;
		}
		throw new RuntimeException("GET timed out");

	}
	public String getAsString(OID oid) throws IOException{
		ResponseEvent event=get(new OID[]{
				oid
		});
		return event.getResponse().get(0).getVariable().toString();
	}
	private Target getTarget(){
		Address targetAddress=GenericAddress.parse(agent.getAddressIp()+"/"+agent.getPort());
		CommunityTarget target=new CommunityTarget();
		target.setCommunity(new OctetString(agent.getCommunity()));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Snmp getSnmp() {
		return snmp;
	}

	public void setSnmp(Snmp snmp) {
		this.snmp = snmp;
	}
	
}
