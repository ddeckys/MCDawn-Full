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
import com.mcdawn.dawncraft.utils.*;

public class cmdMark extends Cmd {
	@Override
	public String name() { return "mark"; }
	@Override
	public String[] aliases() { return new String[] { "select" }; }
	@Override
	public CommandType type() { return CommandType.build; }
	@Override
	public int permission() { return 0; }
	@Override
	public void run(Player sender, String args[], boolean console){
		if(!console){
			sender.sendMessage("Click 2 points to select your area.");
			Area a = new Area();
			PlayerInfo.cuboids.put(((Player)sender).getName(), a);
		}
		else{
			sender.sendMessage("You can't perform this from the console!");
		}
	}
	@Override
	public void help(Player sender) {
		sender.sendMessage("/mark - Marks an area for use with another command.");
	}
}