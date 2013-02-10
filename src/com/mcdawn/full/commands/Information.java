package com.mcdawn.full.commands;

import org.bukkit.command.*;

public class Information extends Category implements CommandExecutor {
	@Override
	public String getName() { return "information"; }

	@Override
	public String[] getAllCommands() { return new String[] { }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
		case "help":
			switch (args.length) {
			case 0:
				sender.sendMessage("Type /help building for all building commands.");
				break;
			case 1:
				break;
			default: sender.sendMessage(cmd.getUsage()); return false;
			}
			return true;
		default: return false;
		}
	}
}
