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

package com.mcdawn.dawncraft;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import com.mcdawn.dawncraft.utils.*;

public class EventListener extends JavaPlugin implements Listener {
	Main base;

	public EventListener(Main base) {
        this.base = base;
    }
	
	
	/**
	 * All our events go after this point, you can reference this class from the command classes and create new variables
	 * Just remember to comment what you do ;)
	 */
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		PlayerInfo.setLastMaterialData(event.getPlayer().getName(), event.getClickedBlock().getDrops().toArray(new ItemStack[] { })[0].getData());
		if(PlayerInfo.cuboids.containsKey(event.getPlayer().getName()) && !PlayerInfo.cuboids.get(event.getPlayer().getName()).isSet()){
			Area cuboid = PlayerInfo.cuboids.get(event.getPlayer().getName());
			if(cuboid.isfirst()){
				cuboid.setPos1(event.getClickedBlock().getLocation());
				event.getPlayer().sendMessage("Point 1 set, click another block for point 2.");
				//event.getClickedBlock().setTypeIdAndData(PlayerInfo.getLastMaterialData(event.getPlayer().getName()).getItemTypeId(), PlayerInfo.getLastMaterialData(event.getPlayer().getName()).getData(), false); // hur dur
			}
			else{
				cuboid.setPos2(event.getClickedBlock().getLocation());
				event.getPlayer().sendMessage("Point 2 set, area has been selected.");
				//event.getClickedBlock().setTypeIdAndData(PlayerInfo.getLastMaterialData(event.getPlayer().getName()).getItemTypeId(), PlayerInfo.getLastMaterialData(event.getPlayer().getName()).getData(), false); // hur dur
				// hur dur dis doesnt want to work for some reason lawl
				/*String lastcmd = PlayerInfo.getLastCmd(event.getPlayer().getName(), 0);
				if (!lastcmd.startsWith("mark ")){
					General.getCmdFromString(lastcmd.split("\\s+")[0]).run(event.getPlayer(), lastcmd.substring(lastcmd.indexOf(" ") + 1).split("\\s+"), false);
				}*/
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		if(event.getMessage().toLowerCase().startsWith("help") || event.getMessage().equalsIgnoreCase("help")){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		if (PlayerInfo.devs.contains(event.getPlayer().getName()))
			PlayerInfo.setPermission(event.getPlayer().getName(), Integer.MAX_VALUE); // rofl
		
		/**
		if(Main.pdata.contains(event.getPlayer().getName())){
			event.setJoinMessage("Welcome "+event.getPlayer().getName()+" back to the server!");
		}
		else{
			event.setJoinMessage("Welcome "+event.getPlayer().getName()+" to the server for the first time!");
			Main.pdata.set(event.getPlayer().getName(), new PlayerInfo(event.getPlayer()));
		}
		*/
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		String message = event.getMessage();
		message = GeneralUtils.ParseColors(message);
		Main.log.info(event.getPlayer().getName() + ": " + message);
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			message = GeneralUtils.ReplaceVars(p,  message);
			p.sendMessage(event.getPlayer().getName() + ": " + ChatColor.RESET + message);
		}
		event.setCancelled(true);
	}
}
