package com.mcdawn.full.irc;

import java.io.IOException;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jibble.pircbot.*;

import com.mcdawn.full.*;

public class IRC extends PircBot implements Runnable {
	public IRC() { new Thread(this).run(); }
	
	public static ChatColor getColor() { return ChatColor.getByChar(MCDawn.thisPlugin.getConfig().getString("irc.color")); }
	
	public void run() {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		setName(config.getString("irc.nick"));
		setVerbose(false);
		try {
			if (config.getString("irc.password") != "")
				connect(config.getString("irc.server"), config.getInt("irc.port"));
			else
				connect(config.getString("irc.server"), config.getInt("irc.port"), config.getString("irc.password"));
		}
		catch (IOException | IrcException e) {
			e.printStackTrace();
			MCDawn.logger.info("Failed to connect to IRC.");
		}
		if (config.getString("irc.identify") != "")
			sendMessage("NICKSERV", "IDENTIFY " + config.getString("irc.identify"));
		joinChannel(config.getString("irc.channel"));
	}
	
	public void onConnect() {
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] Connected to IRC.");
	}
	
	public void onDisconnect() {
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] Disconnected from IRC.");
	}
	
	public void onJoin (String channel, String sender, String login, String hostname) {
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] " + sender + " has joined " + channel);
	}
	
	public void onPart (String channel, String sender, String login, String hostname) {
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] " + sender + " has left " + channel);
	}
	
	public void onKick (String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] " + recipientNick + " was kicked by " + kickerNick + " from " + channel + " (" + reason + ")");
	}
	
	public void onQuit (String sourceNick, String sourceLogin, String sourceHostname, String reason) {
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] " + sourceNick + " has quit (" + reason + ")");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if (message.charAt(0) == '!') {
			switch (message.substring(1).toLowerCase()) {
				case "players":
					Player[] players = Bukkit.getServer().getOnlinePlayers();
					say("There are currently " + players.length + " players online");
					String playerlist = "";
					for (Player p : players) playerlist += p.getName() + ", ";
					playerlist = playerlist.substring(0, playerlist.length() - 2);
					say(playerlist);
					return;
				case "ip":
					say("Server IP: " + (Bukkit.getIp().trim() != "" ? Bukkit.getIp() + ":" + Bukkit.getPort() : Util.getPublicIP() + ":" + Bukkit.getPort()));
					return;
			}
		}
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] " + sender + ": " + ChatColor.RESET + Util.ircToMinecraft(message));
	}
	
	public void onAction(String sender, String login, String hostname, String target, String action) {
		Bukkit.getServer().broadcastMessage(getColor() + "[IRC] *" + sender + " " + action);
	}
	
	public void say(String message) { say(MCDawn.thisPlugin.getConfig().getString("irc.channel"), message); }
	
	public void say(String channel, String message) {
		sendMessage(channel, Util.minecraftToIRC(message));
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
