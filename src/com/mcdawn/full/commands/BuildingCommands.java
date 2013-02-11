package com.mcdawn.full.commands;

import org.bukkit.command.*;

public class BuildingCommands extends Category implements CommandExecutor {
	public BuildingCommands() { }
	@Override
	public String getName() { return "building"; }
	
	@Override
	public String getHelpCategory() { return "build"; }

	@Override
	public String[] getAllCommands() { return new String[] { }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		return false;
	}
}
