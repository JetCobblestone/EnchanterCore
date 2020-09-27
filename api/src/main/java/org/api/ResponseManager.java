package org.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class ResponseManager {
	private static ResponseManager instance;
	private ResponseManager() {
		//default responses
		addResponse(0, "No errors");
		addResponse(1, "Enchantment can not be applied to this item");
		addResponse(2, "Input level cannot be less than or equal to 0");
		addResponse(3, "Input level exceeds the max level of the enchantment");
		addResponse(4, "Enchantment clashes with an enchantment already on the weapon");
		addResponse(5, "Enchantment could not be found in item lore");
		addResponse(6, "Attempted to remove an enchantment from an item which didn't have that enchantment");
		addResponse(7, "Item cannot be null");
		addResponse(8, "Tried to get the level of an enchantment from an item, but the item doesn't have that enchantment");
	}
	
	public static ResponseManager getInstance() {
		if (instance == null) {
			instance = new ResponseManager();
		}
		return instance;
	}
	
	
	private int responseId;
	public int getResponseId() {return responseId;}
	public void setResponseId(int responseId) {this.responseId = responseId;}
	
	
	private Map<Integer, String> responses = new HashMap<Integer, String>();
	
	public Map<Integer, String> getResponses() {
		return responses;
	}
	
	public void addResponse(Integer id, String message) {
		if (responses.containsKey(id)) {
			Bukkit.getLogger().warning(ChatColor.YELLOW + "Enchanter Core: Response with an id of " + id + " has been overriden.");;
		}
		responses.put(id, message);
	}
	
	
}
