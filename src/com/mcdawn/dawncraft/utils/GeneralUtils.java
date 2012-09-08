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
package com.mcdawn.dawncraft.utils;

import java.util.*;
import java.text.SimpleDateFormat;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.material.*;

import com.mcdawn.dawncraft.Commands.*;

public class GeneralUtils {
	public static void loadData(){
	}
	public static void saveData(){
	}
	
	public static Player getPlayerFromString(String playername){
    	for (Player p : Bukkit.getServer().getOnlinePlayers()){
    		if (p.getName().toLowerCase() == playername.toLowerCase()) {
    			return p;	
    		}
    		if (p.getName().toLowerCase().contains(playername.toLowerCase())) {
    			return p;
    		}
    	}
    	return null;
    }
	public static Cmd getCmdFromString(String cmdname){
		for (Cmd c : CommandList.all) {
			if (c.name().equalsIgnoreCase(cmdname)) {
				return c;
			}
			if (Arrays.asList(c.aliases()).contains(cmdname.toLowerCase())) {
				return c;
			}
		}
		return null;
    }
	
	public static Material getMaterialFromString(String input){
		boolean isInt = true;
		try { Integer.parseInt(input); }
		catch (Exception e) { isInt = false; }
		if (isInt) return Material.getMaterial(Integer.parseInt(input));
		else {
			if (Material.matchMaterial(input) != null)
				return Material.matchMaterial(input);
			else
				return Material.getMaterial(input);
		}
	}
	
	public static MaterialData getMaterialDataFromString(String input) {
		boolean isInt = true;
		try { Integer.parseInt(input); }
		catch (Exception e) { isInt = false; }
		if (isInt) return Material.getMaterial(Integer.parseInt(input)).getNewData((byte)0);
		else {
			if (input.contains(":")) {
				try { 
					isInt = true;
					try { Integer.parseInt(input.split(":")[1]); }
					catch (Exception e) { isInt = false; }
					if (isInt) return getMaterialFromString(input.split(":")[0]).getNewData(Byte.parseByte(input.split(":")[1]));
					else return getMaterialFromString(input.split(":")[0]).getNewData(getDyeColorFromString(input.split(":")[1]).getData());
				}
				catch (Exception e) { return null; }
			} else {
				if (getDyeColorFromString(input) != null) {
					try { return new MaterialData(Material.WOOL, getDyeColorFromString(input).getData()); }
					catch (Exception e) { return null; }
				}
				else {
					// idk yet, soo... HUR DUR
					try { return new MaterialData(getMaterialFromString(input)); }
					catch (Exception e) { return null; }
				}
			}
		}
	}
	
	public static DyeColor getDyeColorFromString(String input) {
		for (DyeColor c : DyeColor.values())
			if (c.toString().replace("_", "").equalsIgnoreCase(input))
				return c;
		return null;
	}
	
	// hooray for info from here: http://wiki.vg/Chat :D
	public static String ParseColors(String message) {
		for (int i = 0; i < 9; i++)
			message = message.replace("&" + i, "§" + i);
		for (char ch = 'a'; ch < 'z'; ch++) // too lazy to filter out all the colors, but any non-color ones just default to white, i believe... o.o
			message = message.replace("&" + ch, "§" + ch);
		return message;
    }
	public static String ReplaceVars(Player p, String message) {
		message = message.replace("$name", p.getName());
		message = message.replace("$date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		message = message.replace("$time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
		message = message.replace("$ip", p.getAddress().getAddress().getHostAddress());
		message = message.replace("$world", p.getWorld().getName());
		message = message.replace("$motd", Bukkit.getServer().getMotd());
		// we will add moar latar
		return message;
	}
}
