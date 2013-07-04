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

import com.mcdawn.dawncraft.utils.GeneralUtils;
import com.mcdawn.dawncraft.utils.PlayerInfo;

public class cmdKick extends Cmd {
	@Override
	public String name() { return "kick"; }
	@Override
	public String[] aliases() { return new String[] { "k" }; }
	@Override
	public CommandType type() { return CommandType.moderation; }
	@Override
	public int permission() { return 80; }
	@Override
	public void run(Player sender, String args[], boolean console){
		if(args.length==0){
			sender.sendMessage(ChatColor.RED+"Please select a player to kick.");
			help(sender);
		}
		else{
			Player target = GeneralUtils.getPlayerFromString(args[0]);
			if (target==null){
				sender.sendMessage(ChatColor.RED+"Can't find player.");
			}
			else{
				if(PlayerInfo.isProtected(target.getName())){
					Bukkit.getServer().broadcastMessage(sender.getName()+" attempted to kick a MCDawn Developer!");
				}
				else{
					String reason = "";
					for(int temp = 1 ; temp < args.length ; temp++){
						reason = args[temp]+" ";
					}
					String tname = target.getName();
					target.kickPlayer(reason);
					Bukkit.getServer().broadcastMessage(sender.getName()+" kicked "+tname+" from the server.");
				}
			}
		}
	}
	@Override
	public void help(Player sender) {
		sender.sendMessage("/kick <player> [reason] - Kicks a player from the server with the reason.");
	}

}
