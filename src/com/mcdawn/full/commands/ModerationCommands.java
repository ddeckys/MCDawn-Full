package com.mcdawn.full.commands;

import org.bukkit.command.*;

public class ModerationCommands extends Category implements CommandExecutor {
	@Override
	public String getName() { return "moderation"; }
	
	@Override
	public String getHelpCategory() { return "mod"; }

	@Override
	public String[] getAllCommands() { return new String[] { }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		return false;
	}

}
