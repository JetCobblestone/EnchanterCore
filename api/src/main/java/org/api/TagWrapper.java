package org.api;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

//Versional interface. All version implementations implement this.
public interface TagWrapper {
	
	public ItemStack setTag(ItemStack item, CustomEnchantment ench, int level);
	
	public ArrayList<CustomEnchantment> getTags(ItemStack item);
	
	public ItemStack removeTag(ItemStack item, CustomEnchantment ench);

	public ItemStack clearTags(ItemStack item);
	
	public boolean checkTag(ItemStack item, int id);
	
	public int getLevel(ItemStack item, int id);
	
	public ItemStack addGlow(ItemStack item);
	
	public ItemStack removeGlow(ItemStack item);
}