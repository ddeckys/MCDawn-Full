package com.mcdawn.full.commands;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import com.mcdawn.full.*;

public class ModerationCommands extends Category implements CommandExecutor {
	@Override
	public String getName() { return "moderation"; }
	
	@Override
	public String getHelpCategory() { return "mod"; }

	@Override
	public String[] getAllCommands() { return new String[] { "kick" }; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
			case "kick":
				if(args.length == 0) return false;
				Player target = Util.getPlayerFromString(args[0]);
				if (target == null){ sender.sendMessage("Can't find player."); return true; }
				String kickername = (sender instanceof Player ? sender.getName() : "Console [" + MCDawn.thisPlugin.getConfig().getString("general.consoleName") + "]");
				String reason = (args.length == 1) ? "No reason given." : org.apache.commons.lang.StringUtils.join(args, " ").substring(args[0].length()).trim();
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "- " + ChatColor.RESET + target.getName() + " kicked (Kicked by " + kickername + " - " + reason + ")");
				target.kickPlayer(reason);
				return true;
			default: return false;
		}
	}

}
