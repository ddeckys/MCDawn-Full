package com.mcdawn.full.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mcdawn.full.MCDawn;
import com.mcdawn.full.Util;

public class ModerationCommands extends Category implements CommandExecutor {
	@Override
	public String getName() { return "moderation"; }
	
	@Override
	public String getHelpCategory() { return "mod"; }

	@Override
	public String[] getAllCommands() { return new String[] { "kick" }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		@SuppressWarnings("unused")
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		switch (cmd.getName().toLowerCase()) {
			case "kick":
				if(args.length==0){
					sender.sendMessage(ChatColor.RED+"Please select a player to kick.");
					return true;
				}
				Player target = Util.getPlayerFromString(args[0]);
				if (target==null){
					sender.sendMessage(ChatColor.RED+"Can't find player.");
					return true;
				}
				String kickername = (sender instanceof Player ? sender.getName() : "Console [" + MCDawn.thisPlugin.getConfig().getString("general.consoleName") + "]");
				String reason = "";
				for(int temp = 1 ; temp < args.length ; temp++){
					reason = args[temp]+" ";
				}
				String tname = target.getName();
				target.kickPlayer(reason);
				Bukkit.getServer().broadcastMessage(kickername+" kicked "+tname+" from the server.");
				//testing addition
				return true;
			default: return false;
		}
	}

}
