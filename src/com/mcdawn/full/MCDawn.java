package com.mcdawn.full;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.event.*;

public class MCDawn extends JavaPlugin implements Listener {
	public static MCDawn thisPlugin;
	
	public static String getVersion() { return "1.0.0"; }
	public static String getExtraVersion() { return "alpha"; }
	
	public static Logger logger;
	
	@Override
	public void onEnable() {
		thisPlugin = this;
		try {
			logger = getLogger();
			logger.info("Enabled MCDawn, v" + getVersion() + (getExtraVersion() == "" ? "" : "-" + getExtraVersion() + "."));
			getServer().getPluginManager().registerEvents(new EventListener(), this);
			
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	
	@Override
	public void onDisable() {
		logger.info("Disabled MCDawn.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("test")) {
			sender.sendMessage("ALLO");
			return true;
		}
		return false;
	}
}
