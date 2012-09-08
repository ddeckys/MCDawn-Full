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

import com.mcdawn.dawncraft.utils.GeneralUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class cmdPlace extends Cmd {
	@Override
	public String name() { return "place"; }
	@Override
	public String[] aliases() { return new String[] { "pl" }; }
	@Override
	public CommandType type() { return CommandType.build; }
	@Override
	public int permission() { return 50; }
	@Override
	public void run(Player sender, String args[], boolean console){
		Location loca = sender.getLocation();
		if(args.length==0){
			if (!sender.getItemInHand().getType().isBlock()) { sender.sendMessage("Cannot place item that is not a block."); return; }
			loca.getWorld().getBlockAt(loca).setTypeIdAndData(sender.getItemInHand().getTypeId(), sender.getItemInHand().getData().getData(), false);
			sender.sendMessage("The item in your hand has been placed at your feet.");
		}
		else{
			if (!GeneralUtils.getMaterialDataFromString(args[0]).getItemType().isBlock()) { sender.sendMessage("Cannot place item that is not a block."); return; }
			if (GeneralUtils.getMaterialDataFromString(args[0]) == null) {
				sender.sendMessage("Can't find item.");
				return;
			}
			loca.getWorld().getBlockAt(loca).setTypeIdAndData(GeneralUtils.getMaterialDataFromString(args[0]).getItemTypeId(), GeneralUtils.getMaterialDataFromString(args[0]).getData(), false);
			sender.sendMessage("The given item has been placed at your feet.");
		}
	}
	@Override
	public void help(Player sender) {
		sender.sendMessage("/place [block] - Places a block at your feet. If none is specified places the current item at hand.");
	}
}
