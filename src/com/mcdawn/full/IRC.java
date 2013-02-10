package com.mcdawn.full;

import org.jibble.pircbot.*;

public class IRC {
	private String server;
	private int port;
	private String password;
	private String nick;
	private String identify;
	private String channel;
	
	public IRC(String server, int port, String password, String nick, String identify, String channel) {
		this.server = server; this.port = port; this.nick = nick; this.identify = identify; this.channel = channel;
		
	}
	
	public void disconnect() {
		
	}
	
	public void restart() {
		
	}
}
