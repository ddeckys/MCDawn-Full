package com.mcdawn.full.irc;

import java.io.IOException;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.jibble.pircbot.*;

import com.mcdawn.full.*;

public class GlobalChat extends PircBot implements Runnable {
	public String server = "irc.synirc.net";
	public int port = 6667;
	public String channel = "#MCDawn";
	
	public GlobalChat() { new Thread(this).run(); }
	
	public static ChatColor getColor() { return ChatColor.getByChar(MCDawn.thisPlugin.getConfig().getString("globalchat.color")); }
	
	public void run() {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		setName(config.getString("globalchat.nick"));
		setVerbose(false);
		try { connect(server, port); }
		catch (IOException | IrcException e) {
			e.printStackTrace();
			MCDawn.logger.info("Failed to connect to globalchat.");
		}
		if (config.getString("globalchat.identify") != "")
			sendMessage("NICKSERV", "IDENTIFY " + config.getString("globalchat.identify"));
		joinChannel(channel);
	}
	
	public void onConnect() {
		Util.broadcastConsoleAndDevs(">[Global] " + getColor() + "Connected to MCDawn Global Chat.");
	}
	
	public void onDisconnect() {
		Util.broadcastConsoleAndDevs(">[Global] " + getColor() + "Disconnected from MCDawn Global Chat.");
	}
	
	public void onJoin (String channel, String sender, String login, String hostname) {
		Util.broadcastDevs(">[Global] " + getColor() + sender + "has joined " + channel);
	}
	
	public void onPart (String channel, String sender, String login, String hostname) {
		Util.broadcastDevs(">[Global] " + getColor() + sender + "has left " + channel);
	}
	
	public void onKick (String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		Util.broadcastDevs(">[Global] " + getColor() + recipientNick + "was kicked by " + kickerNick + " from " + channel + " (" + reason + ")");
	}
	
	public void onQuit (String sourceNick, String sourceLogin, String sourceHostname, String reason) {
		Util.broadcastDevs(">[Global] " + getColor() + sourceNick + "has quit (" + reason + ")");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		Bukkit.getServer().broadcastMessage(">[Global] " + getColor() + sender + ": " + ChatColor.RESET + Util.ircToMinecraft(message));
	}
	
	public void onAction(String sender, String login, String hostname, String target, String action) {
		Bukkit.getServer().broadcastMessage(">[Global] " + getColor() + "*" + sender + " " + Util.ircToMinecraft(action));
	}
	
	public void say(String message) { say(message, true); }
	
	public void say(String message, boolean displayInGame) {
		sendMessage(channel, Util.minecraftToIRC(message));
		if (displayInGame) Bukkit.getServer().broadcastMessage(getColor() + "<[Global] " + message);
	}
	
	public void reset() {
		shutdown();
		new Thread(this).run();
	}
	
	public void shutdown() {
		disconnect();
		dispose();
	}
}
