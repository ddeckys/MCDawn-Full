package com.mcdawn.full.commands;

import org.bukkit.command.*;

public class Building extends Category implements CommandExecutor {
	public Building() { }
	@Override
	public String getName() { return "building"; }

	@Override
	public String[] getAllCommands() { return new String[] { }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		return false;
	}
}
