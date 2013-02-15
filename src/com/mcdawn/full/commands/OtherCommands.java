package com.mcdawn.full.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mcdawn.full.MCDawn;
import com.mcdawn.full.Util;

public class OtherCommands extends Category implements CommandExecutor {
	@Override
	public String getName() { return "other"; }
	
	@Override
	public String getHelpCategory() { return "other"; }

	@Override
	public String[] getAllCommands() { return new String[] { "global", "say" }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		switch (cmd.getName().toLowerCase()) {
			case "global":
				if (!config.getBoolean("globalchat.useGlobalChat")) { sender.sendMessage("MCDawn Global Chat has been disabled by the server owner."); return true; }
				if (args.length == 0) return false;
				MCDawn.globalChat.say((sender instanceof Player ? sender.getName() : "Console [" + MCDawn.thisPlugin.getConfig().getString("general.consoleName") + "]") + ": " + ChatColor.RESET + org.apache.commons.lang.StringUtils.join(args, " "));
				return true;
			case "say":
				if (args.length == 0) return false;
				String name = (sender instanceof Player ? sender.getName() : "Console [" + ChatColor.GREEN + MCDawn.thisPlugin.getConfig().getString("general.consoleName") + ChatColor.RESET + "]"),
						message = Util.parseChat(org.apache.commons.lang.StringUtils.join(args, " "));
				Bukkit.getServer().broadcastMessage(name + ": " + ChatColor.RESET + message);
				MCDawn.irc.say(name + ": " + ChatColor.RESET + message);
				return true;
			case "":
				
				return true;
			default: return false;
		}
	}
}
