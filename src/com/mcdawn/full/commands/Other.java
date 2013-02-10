package com.mcdawn.full.commands;

import org.bukkit.command.*;

public class Other extends Category implements CommandExecutor {
	@Override
	public String getName() { return "other"; }

	@Override
	public String[] getAllCommands() { return new String[] { }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		return false;
	}
}
