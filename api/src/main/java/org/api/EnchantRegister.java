package org.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

//Keeps track of the enchantments in lists to make them easy to access
public class EnchantRegister {
	private static JavaPlugin plugin;
	private static HashMap<Integer, CustomEnchantment> idMap = new HashMap<Integer, CustomEnchantment>();
	private static HashMap<String, CustomEnchantment> nameMap = new HashMap<String, CustomEnchantment>();
	private static ArrayList<CustomEnchantment> enchList = new ArrayList<CustomEnchantment>();
	
	public static void setPlugin(JavaPlugin enchanterCore) {
		plugin = enchanterCore;
	}
	
	public static void addEnchantment(CustomEnchantment ench){
		if (nameMap.containsKey(ench.getName())) {
			Bukkit.getLogger().severe("EnchanterCore tried to register enchantments with duplicate names, please don't use duplicate names. Shutting down.");
			Bukkit.getPluginManager().disablePlugin(plugin);
		}
		
		int id = ench.getName().hashCode();
		ench.setId(id);
		idMap.put(id, ench);
		nameMap.put(ench.getName(), ench);
		enchList.add(ench);
	}
	
	public static CustomEnchantment enchFromId(int id) {
		return idMap.get(id);
	}
	public static CustomEnchantment enchFromName(String name) {
		return nameMap.get(name);
	}
}