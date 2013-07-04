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
import com.mcdawn.dawncraft.utils.Area.CuboidType;

public class cmdCuboid extends Cmd {
	@Override
	public String name() { return "cuboid"; }
	@Override
	public String[] aliases() { return new String[] { "z", "box" }; }
	@Override
	public CommandType type() { return CommandType.build; }
	@Override
	public int permission() { return 50; }
	
	public cmdCuboid() { }
	@Override
	public void run(Player sender, String args[], boolean console){
		if (console) { sender.sendMessage("Command not usable from Console."); return; }
		if (!PlayerInfo.cuboids.containsKey(sender.getName())) {
			sender.sendMessage("No area has been marked yet. Using /mark...");
			GeneralUtils.getCmdFromString("mark").run(sender, new String[] { }, console); 
		} else {
			if (args.length == 0) { 
				if (!sender.getItemInHand().getType().isBlock()) { sender.sendMessage("Cannot place item that is not a block."); return; }
				PlayerInfo.cuboids.get(sender.getName()).Cuboid(sender.getItemInHand().getData(), CuboidType.SOLID); 
			}
			else if (args.length == 1) {
				if (!GeneralUtils.getMaterialDataFromString(args[0]).getItemType().isBlock()) { sender.sendMessage("Cannot place item that is not a block."); return; }
				if (GeneralUtils.getMaterialDataFromString(args[0]) != null) {
					PlayerInfo.cuboids.get(sender.getName()).Cuboid(GeneralUtils.getMaterialDataFromString(args[0]), CuboidType.SOLID);
				}
				else if (CuboidType.valueOf(args[0]) != null) {
					PlayerInfo.cuboids.get(sender.getName()).Cuboid(sender.getItemInHand().getData(), CuboidType.SOLID);
				}
				else { sender.sendMessage("Could not find material or type."); return; }
			}
			else if (args.length == 2) {
				if (GeneralUtils.getMaterialDataFromString(args[0]) == null || CuboidType.valueOf(args[1]) == null) {
					sender.sendMessage("Could not find material or type."); return;
				}
				else PlayerInfo.cuboids.get(sender.getName()).Cuboid(GeneralUtils.getMaterialDataFromString(args[0]), CuboidType.valueOf(args[1]));
			}
			else { help(sender); return; }
			//General.ClearSelection(sender.getName()); // clear after done?
		}
	}
	@Override
	public void help(Player sender) {
		sender.sendMessage("/cuboid [material] [type] - Sets a cuboid of blocks within the marked points.");
		sender.sendMessage("Valid types are: solid/hollow/walls/holes/wire/random");
		sender.sendMessage("If no type is given, solid is used by default.");
	}
}
