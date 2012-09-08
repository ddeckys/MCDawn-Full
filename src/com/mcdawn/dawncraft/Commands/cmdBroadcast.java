/*
Copyright 2012 DawnCraft Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.mcdawn.dawncraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class cmdBroadcast extends Cmd {
	@Override
	public String name() { return "broadcast"; }
	@Override
	public String[] aliases() { return new String[] { "say" }; }
	@Override
	public CommandType type() { return CommandType.moderation; }
	@Override
	public int permission() { return 50; }
	@Override
	public void run(Player sender, String args[], boolean console){
		if (args.length == 0) { help(sender); return; }
		String message ="";
		for(int te = 0 ; te < args.length ; te++){
			if (te==0)
				message = args[0];
			else
				message = message+" "+args[te];
		}
		if (console)
			Bukkit.getServer().broadcastMessage(ChatColor.WHITE+"["+ChatColor.RED+"BROADCAST"+ChatColor.WHITE+"] Console"
		+ChatColor.GREEN+message);
		else
			Bukkit.getServer().broadcastMessage(ChatColor.WHITE+"["+ChatColor.RED+"BROADCAST"+ChatColor.WHITE+"] "+sender.getName()
					+ChatColor.GREEN+message);
		//unfinished!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Need to do colors
	}
	@Override
	public void help(Player sender){
		sender.sendMessage("/broadcast <message> - Broadcast a message to the entire server.");
	}
}
