package com.mcdawn.full;

import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.*;

public class EventListener implements Listener {
	@EventHandler
	public void onWorldInitEvent(WorldInitEvent event) {
		
	}
	
	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		event.setMessage(Util.parseChat(event.getMessage()));
	}
}
