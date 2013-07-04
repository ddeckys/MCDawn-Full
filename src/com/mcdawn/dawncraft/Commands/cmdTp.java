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

import org.bukkit.entity.Player;

import com.mcdawn.dawncraft.utils.GeneralUtils;

public class cmdTp extends Cmd {
	@Override
	public String name() { return "tp"; }
	@Override
	public String[] aliases() { return new String[] {"teleport" }; }
	@Override
	public CommandType type() { return CommandType.other; }
	@Override
	public int permission() { return 80; }
	@Override
	public void run(Player sender, String args[], boolean console){
		if(console){
			sender.sendMessage("You can't use this command from the console!");
		}
		else{
			Player p = (Player) sender;
			if(args.length==0){
				sender.sendMessage("You must specify someone to teleport to.");
				help(sender);
			}
			else{
				Player to = GeneralUtils.getPlayerFromString(args[0]);
				if(to==null){
					sender.sendMessage("Can't find player.");
				}
				else{
					p.teleport(to);
					sender.sendMessage("Teleported.");
				}
			}
		}
	}
	@Override
	public void help(Player sender) {
		sender.sendMessage("/tp [player] - Teleports you to another player");
	}
}
