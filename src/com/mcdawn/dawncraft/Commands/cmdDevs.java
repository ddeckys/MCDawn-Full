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

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mcdawn.dawncraft.utils.PlayerInfo;

public class cmdDevs extends Cmd {
	@Override
	public String name() { return "devs"; }
	@Override
	public String[] aliases() { return new String[] { "developers" }; }
	@Override
	public CommandType type() { return CommandType.information; }
	@Override
	public int permission() { return 0; }
	@Override
	public void run(Player sender, String args[], boolean console){
		sender.sendMessage("DawnCraft Development Team: " + ChatColor.RED + org.apache.commons.lang.StringUtils.join(PlayerInfo.devs, ", "));
	}
	@Override
	public void help(Player sender){
		sender.sendMessage("/devs - Shows you the list of DawnCraft Developers.");
	}
}
