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

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcdawn.dawncraft.Commands.*;
import com.mcdawn.dawncraft.utils.GeneralUtils;
import com.mcdawn.dawncraft.utils.PlayerInfo;

public class Main extends JavaPlugin implements Listener {
	// File configuration used for the MAIN config file, NOT FOR SAVING DATA YOU JONNEH DERP
	@SuppressWarnings("unused")
	private FileConfiguration config = new YamlConfiguration();
	// Don't ask, we hafta
	public static Logger log;
	// This is the file that all our data for EVERYTHING will be saved in. It is like a SQL db.
	public File file = new File(this.getDataFolder(), "data.txt");
	// This is what we will use to access and save stuff in the data file.
	public static FileConfiguration data = new YamlConfiguration();
	public void onEnable(){
    	getConfig().options().copyDefaults(true);
    	config = this.getConfig();
        log = this.getLogger();
        Bukkit.getPluginManager().registerEvents(this, this);
        EventListener events = new EventListener(this);
        Bukkit.getPluginManager().registerEvents(events, this);
        CommandList.LoadAll();
        log.info("[MCDawn] DawnCraft succesfully enabled!");
        saveDefaultConfig();
	    try {
	    	// Loads the file if it exists, remember this is our main data file
	    	if(file.exists()){
	    		data.load(file);
	       		PlayerInfo.loadFiles();
	        }
	        if(!file.exists()){
	           	file.createNewFile();
	        }
	        if(file.exists())
	        	PlayerInfo.loadFiles();
	        // This is how you can load files, but we wont use it mostly unless we have a really complicated object
			// test = (ArrayList<String>)SLAPI.load("plugins/Ddeckys Bounty Plugin/bounty.bin");
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	    for (Player p : Bukkit.getServer().getOnlinePlayers())
	    	if (PlayerInfo.devs.contains(p.getName()))
				PlayerInfo.setPermission(p.getName(), Integer.MAX_VALUE); // rofl
    }
	public void onDisable(){
		try {
			// This saves our main data file, if it doesn't exist it will create one.
			PlayerInfo.saveFiles();
			data.save(file);
			// This is another way to save files, probably wont use this unless we have a really complication object
			// SLAPI.save(test, "plugins/Ddeckys Bounty Plugin/bounty.bin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log.info("[MCDawn] DawnCraft has been disabled.");
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		try
		{
			boolean console = true;
			// Check if console
			if (sender instanceof Player)
				console = false;
			
			Cmd command = GeneralUtils.getCmdFromString(cmd.getName());
			if (command == null) { sender.sendMessage("Could not find command \"" + cmd.getName() + "\"."); return true; }
			if(command.permission() <= PlayerInfo.getPermission((Player)sender)) {
				log.info(sender.getName() + " used /" + command.name() + " " + Arrays.toString(args));
				PlayerInfo.setLastCmd(sender.getName(), command.name() + " " + Arrays.toString(args));
				command.run((Player)sender, args, console);
			}
			else { sender.sendMessage(ChatColor.RED+"You do not have permission for this command."); }
		}
		catch (Exception ex) { sender.sendMessage("Command failed."); ex.printStackTrace(); }
		return true;
	}
}
