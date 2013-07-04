package com.mcdawn.dawncraft.utils;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.material.*;

//import com.mcdawn.dawncraft.Main;
//import com.mcdawn.dawncraft.SLAPI;

public class PlayerInfo {
	public static final ArrayList<String> devs = new ArrayList<String>(Arrays.asList("jonnyli1125", "ddeckys" )); // all lowercase
	
	public static HashMap<String, Area> cuboids = new HashMap<String, Area>();
	public static void ClearSelection(String player) { cuboids.remove(player); }
	
	static HashMap<String, Object> data = new HashMap<String, Object>();
	static HashMap<String, Integer> playerpermissions = new HashMap<String, Integer>();
	static HashMap<String, String> playertitles = new HashMap<String, String>();
	static HashMap<String, String> playerdisplaynames = new HashMap<String, String>();
	static HashMap<String, String> playerchatcolors = new HashMap<String, String>();
	static HashMap<String, Integer> playermoney = new HashMap<String, Integer>();
	static HashMap<String, ArrayList<String>> playerlastcmd = new HashMap<String, ArrayList<String>>();
	static HashMap<String, MaterialData> playerlastmaterialdata = new HashMap<String, MaterialData>();
	
	
	public static void loadFiles(){
	}
	public static void saveFiles(){
	}
	
	public static boolean isProtected(String player){
		if (devs.contains(player.toLowerCase())) 
			return true;
		return false;
	}
	public static void setLastMaterialData(String player, MaterialData m) {
		data.put("playerlastmaterial." + player, m);
	}
	public static MaterialData getLastMaterialData(String player) {
		if (data.containsKey("playerlastmaterial." + player))
			return (MaterialData)data.get("playerlastmaterial." + player);
		return null;
	}
	@SuppressWarnings("unchecked")
	public static void setLastCmd(String player, String lastcommand){
		// only used in the Main.java, so don't use this method in commands.
		ArrayList<String> lastcmds = new ArrayList<String>();
		if (data.containsKey("playerlastcmd."+player)) { lastcmds = (ArrayList<String>) data.get("playerlastcmd."+player); }
		lastcmds.add(0, lastcommand);
		data.put("playerlastcmd."+player, lastcmds);
	}
	@SuppressWarnings("unchecked")
	public static String getLastCmd(String player, int index){
		if (!data.containsKey("playerlastcmd."+player)) return null;
		ArrayList<String> lastcmds = (ArrayList<String>) data.get("playerlastcmd."+player);
		return lastcmds.get(index);
	}
	public static void setDisplayName(String player, String dispname){
		data.put("playerdisplaynames."+player, dispname);
	}
	public static String getDisplayName(String player){
		if(data.containsKey("playerdisplaynames."+player))
			return (String) data.get("playerdisplaynames."+player);
		else
			return player;
	}
	public static void setTitle(String player, String title){
		playertitles.put(player, title);
	}
	public static String getTitle(String player){
		if(data.containsKey("playertitles."+player))
			return (String) data.get("playertitles."+player);
			else{
				data.put("playertitles."+player, "");
				return "";
			}
	}
	public void setChatColor(String player, ChatColor color){
		data.put("playerchatcolores."+player, color.name());
	}
	public static ChatColor getChatColor(String player){
		if(data.containsKey("playerchatcolores."+player)){
			String color = (String) data.get("playerchatcolors."+player);
			return ChatColor.valueOf(color);
		}
		else{
			data.put("playerchatcolores."+player, ChatColor.WHITE.name());
			return ChatColor.WHITE;
		}
		
	}
	public static void setPermission(String player, Integer amount){
		data.put("playerpermissions."+player, amount);
	}
	/**
	 * Used for getting the permission level of a player
	 * @param player - player to get permission on
	 * @return - permission level as an int
	 */
	public static Integer getPermission(Player player){
		if (data.containsKey("playerpermissions."+player.getName())){
			return (Integer) data.get("playerpermissions."+player.getName());
		}
		else{
			data.put("playerpermissions."+player.getName(), 0);
			return 0;
		}
	}
	/**
	 * Used for getting the permission level of a player
	 * @param player - player to get permission on
	 * @return - permission level as an int
	 */
	public static Integer getPermission(String player){
		if (data.containsKey("playerpermissions."+player)){
			return (Integer) data.get("playerpermissions."+player);
		}
		else{
			data.put("playerpermissions."+player, 0);
			return 0;
		}
	}
}

/**
package com.mcdawn.dawncraft.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class PlayerInfo implements ConfigurationSerializable {
	String name;
	Integer permissionlevel = 10;
	ChatColor namecolor = ChatColor.WHITE;
	public boolean isprotected(){
		if (name.equalsIgnoreCase("ddeckys")||name.equalsIgnoreCase("jonnyli1125"))
			return true;
		return false;
	}
	 * This is what you want to use for creating a PlayerInfo object
	 * @param p - the player to create the object for
	public PlayerInfo(Player p){
		this.name=p.getName();
	}
	public PlayerInfo(String pname, Integer pleve, ChatColor pnamecolor){
		this.name=pname;
		this.permissionlevel=pleve;
		this.namecolor=pnamecolor;
	}
	public Integer getPermissionLevel(){
		return this.permissionlevel;
	}
	public void setPermissionLevel(Integer level){
		this.permissionlevel = level;
	}
	public ChatColor getNameColor(){
		return namecolor;
	}
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("name", name);
		map.put("permissionlevel", permissionlevel);
		map.put("namecolor", namecolor);
		return null;
	}
	
	public static PlayerInfo deserialize(Map<String, Object> map){
		String dname = (String) map.get("name");
		Integer dpermissionlevel = (Integer) map.get("permissionlevel");
		ChatColor dnamecolor = (ChatColor) map.get("namecolor");
		PlayerInfo pi = new PlayerInfo(dname, dpermissionlevel, dnamecolor);
		return pi;
	}
}
*/