package com.mcdawn.dawncraft.utils;

//import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
//import java.util.Calendar;

public class Ticket implements ConfigurationSerializable {
	boolean open = true;
	boolean reported = false;
	String reportreason = "";
	String assigner = "";
	String offender = "";
	String reason = "";
	int amount = 0;
	int ticks = 0;
	//transient Calendar time = Calendar.getInstance();
	
	public Ticket() {
		
	}
    
    public Ticket(boolean open, boolean reported, String reportreason, String assigner, String offender, String reason, int amount, int ticks) {
        this.open = open;
        this.reported = reported;
        this.reportreason = reportreason;
        this.assigner = assigner;
        this.offender = offender;
        this.reason = reason;
        this.amount = amount;
        this.ticks = ticks;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>(8);
        
        map.put("open", new Boolean(open));
        map.put("reported", new Boolean(reported));
        map.put("reportreason", reportreason);
        map.put("assigner", assigner);
        map.put("offender", offender);
        map.put("amount", new Integer(amount));
        map.put("ticks", new Integer(ticks));
        
        return map;
    }
    
    @SuppressWarnings("unused")
    public static Ticket deserialize(Map<String, Object> map) {
        boolean open = (Boolean) map.get("open");
        boolean reported = (Boolean) map.get("reported");
        String reportreason = (String) map.get("reportreason");
        String assigner = (String) map.get("assigner");
        String offender = (String) map.get("offender");
        int amount = (Integer) map.get("amount");
        int ticks = (Integer) map.get("ticks");
        Ticket ticket = new Ticket();
        
        return ticket;
    }       
    
	void setup(String tassigner, String toffender, Integer tamount, Integer timeamount){
		assigner = tassigner;
		offender = toffender;
		amount = tamount;
		ticks = timeamount;
	}
	void setassigner(String player) {
		assigner = player;
	}
	void setoffender(String player){
		offender = player;
	}
	void setreason(String ticketreason) {
		reason = ticketreason;
	}
	void setamount(Integer ticketamount) {
		amount = ticketamount;
	}
	void setticks(Integer timeamount) {
		ticks = timeamount;
	}
	boolean updateticks() {
		if(!open)
			return false;
		if (ticks <= 0)
			return false;
		ticks--;
		return true;
		
	}
	String getassigner() {
		return assigner;
	}
	String getoffender() {
		return offender;
	}
	/**
	Calendar gettime() {
		return time;
	}
	*/
	String getreason() {
		return reason;
	}
	Integer getticks() {
		return ticks;
	}
}