package org.api;

import java.util.ArrayList;
import java.util.HashMap;

//Keeps track of the enchantments in lists to make them easy to access
public class EnchantRegister {
	private static HashMap<Integer, CustomEnchantment> idMap = new HashMap<Integer, CustomEnchantment>();
	private static HashMap<String, CustomEnchantment> nameMap = new HashMap<String, CustomEnchantment>();
	private static ArrayList<CustomEnchantment> enchList = new ArrayList<CustomEnchantment>();
	private static int counter = 0;
	
	public static void addEnchantment(CustomEnchantment ench){
		ench.setId(counter);
		idMap.put(counter, ench);
		nameMap.put(ench.getName(), ench);
		enchList.add(ench);
		counter++;
	}
	
	public static CustomEnchantment enchFromId(int id) {
		return idMap.get(id);
	}
	public static CustomEnchantment enchFromName(String name) {
		return nameMap.get(name);
	}
}
