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
import org.bukkit.ChatColor;

import com.mcdawn.dawncraft.utils.GeneralUtils;

public class cmdHelp extends Cmd{
	@Override
	public String name() { return "help"; }
	@Override
	public String[] aliases() { return new String[] { "commands", "cmdhelp", "helpop" }; }
	@Override
	public CommandType type() { return CommandType.information; }
	@Override
	public int permission() { return 0; }
	@Override
	public void run(Player sender, String args[], boolean console){
		if(args.length==0){	
			sender.sendMessage("Type " + ChatColor.RED + "/help build" + ChatColor.RESET + " for all building related commands.");
			sender.sendMessage("Type " + ChatColor.RED + "/help moderation" + ChatColor.RESET + " for all moderation related commands.");
			sender.sendMessage("Type " + ChatColor.RED + "/help information" + ChatColor.RESET + " for all information related commands.");
			sender.sendMessage("Type " + ChatColor.RED + "/help other" + ChatColor.RESET + " for all other/uncategorized commands.");
			sender.sendMessage("Type " + ChatColor.RED + "/help <command>" + ChatColor.RESET + " for help on a command.");
		}
		else{
			String name = args[0];
			if (name.equalsIgnoreCase("moderation")){
				sender.sendMessage("Moderation Commands:");
				String listy ="";
				for(Cmd c : CommandList.all){
					if(c.type()==CommandType.moderation){
						listy += c.name()+", ";
					}
				}
				sender.sendMessage(listy);
			}
			else if (name.equalsIgnoreCase("build")){
				sender.sendMessage("Build Commands:");
				String listy ="";
				for(Cmd c : CommandList.all){
					if(c.type()==CommandType.build){
						listy += c.name()+", ";
					}
				}
				sender.sendMessage(listy);		
			}
			else if (name.equalsIgnoreCase("information")){
				sender.sendMessage("Information Commands:");
				String listy ="";
				for(Cmd c : CommandList.all){
					if(c.type()==CommandType.information){
						listy += c.name()+", ";
					}
				}
				sender.sendMessage(listy);
			}
			else if (name.equalsIgnoreCase("other")){
				sender.sendMessage("Other Commands:");
				String listy ="";
				for(Cmd c : CommandList.all){
					if(c.type()==CommandType.other){
						listy += c.name()+", ";
					}
				}
				sender.sendMessage(listy);
			}
			else {
				//This gets help for a specific command
				Cmd help = GeneralUtils.getCmdFromString(name);
				if (help == null) { sender.sendMessage("Command or category could not be found."); }
				help.help(sender);
				sender.sendMessage("Minimum Permission: " + ChatColor.GREEN + help.permission());
				sender.sendMessage("Aliases: " + ChatColor.GREEN + org.apache.commons.lang.StringUtils.join(help.aliases(), ", "));
			}
		}
	}
	@Override
	public void help(Player sender) {
		sender.sendMessage("/help [command/section] - Shows you all available commands. Get optional help on a command or list the commands in a section.");
	}

}
