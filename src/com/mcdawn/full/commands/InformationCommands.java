package com.mcdawn.full.commands;

import org.apache.commons.lang.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import com.mcdawn.full.MCDawn;

public class InformationCommands extends Category implements CommandExecutor {
	@Override
	public String getName() { return "information"; }

	@Override
	public String getHelpCategory() { return "info"; }
	
	@Override
	public String[] getAllCommands() { return new String[] { "help" }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
			case "help":
				switch (args.length) {
					case 0:
						for (Category c : Category.getAll())
							sender.sendMessage("Type " + ChatColor.GREEN + "/help " + c.getHelpCategory().toLowerCase().trim() + ChatColor.RESET + " for all " + c.getName() + " commands.");
						break;
					case 1:
						Category c = getCategoryByHelp(args[0]);
						if (c != null) {
							sender.sendMessage("All " + ChatColor.GREEN + c.getName() + ChatColor.RESET + " related commands:");
							sender.sendMessage(StringUtils.join(c.getAllCommands(), ", "));
						} else {
							PluginCommand command = MCDawn.thisPlugin.getCommand(args[0]);
							if (command != null) {
								sender.sendMessage("Command: " + ChatColor.GREEN + command.getName());
								sender.sendMessage("Description: " + ChatColor.GREEN + command.getDescription());
								sender.sendMessage("Usage: " + ChatColor.GREEN + command.getUsage());
								sender.sendMessage("Aliases: " + ChatColor.GREEN + StringUtils.join(command.getAliases(), ", "));
							} else { sender.sendMessage("Could not find category or command."); return true; }
						}
						break;
					default: return false;
				}
				return true;
			case "players":
				// TODO: Add ranks/Chat Colors/Prefixs once PlayerInfo is ready
				sender.sendMessage("Current Online Players:");
				for (Player p : Bukkit.getServer().getOnlinePlayers())
					sender.sendMessage(p.getDisplayName());
			default: return false;
		}
	}
}
