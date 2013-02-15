package com.mcdawn.full;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.*;
import org.bukkit.ChatColor;

public class EventListener implements Listener {
	@EventHandler
	public void onWorldInitEvent(WorldInitEvent event) {
	}
	
	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		String message = Util.parseChat(event.getMessage());
		event.setFormat(p.getDisplayName() + ": " + ChatColor.RESET + message);
		if (MCDawn.thisPlugin.getConfig().getBoolean("irc.useIRC"))
			MCDawn.irc.say(p.getDisplayName() + ": " + message);
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		event.setJoinMessage(ChatColor.GREEN + "+ " + ChatColor.DARK_BLUE + p.getDisplayName() + ChatColor.RESET + " joined the game from " + ChatColor.RED + Util.getCountry(p.getAddress().getHostString()) + ChatColor.RESET + ".");
	}
}
