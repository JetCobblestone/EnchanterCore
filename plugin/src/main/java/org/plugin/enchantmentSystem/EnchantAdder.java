package org.plugin.enchantmentSystem;

import org.api.CustomEnchantment;
import org.api.ResponseManager;
import org.api.TagWrapper;
import org.bukkit.inventory.ItemStack;
import org.plugin.loreSystem.LoreAdder;
import org.plugin.loreSystem.LoreManager;
import org.plugin.tagSystem.TagManager;

public interface EnchantAdder {
	
	final ResponseManager responseManager = ResponseManager.getInstance();
	final EnchantManager enchantManager = EnchantManager.getInstance();
	final LoreAdder loreAdder = LoreManager.getInstance().getLoreAdder();
	final TagWrapper tagWrapper = TagManager.getInstance().getImplemenation();
	
	//Calls all the functions needed for fully adding an enchantment to an item
	public default ItemStack addEnchant(ItemStack item, CustomEnchantment ench, int lvl) {
		//Resetting response
		responseManager.setResponseId(0);

		//Performs various checks on the item
		if (!enchantManager.nullCheck(item) || !enchantManager.defaultCheck(item, ench, lvl)) {
			return item;
		}
		
		ItemStack clone = item.clone();
		
		//Lore should be added before the tag
		clone = loreAdder.addLore(clone, ench, lvl);
		clone = tagWrapper.setTag(clone, ench, lvl);
		
		if (responseManager.getResponseId() == 0) {
			item = clone;
		}
		
		return item;
	}
	
	//Calls all the functions needed for fully removing an enchantment from an item.
	public default ItemStack removeEnchant(ItemStack item, CustomEnchantment ench) {
		//Resetting responses
		responseManager.setResponseId(0);
		responseManager.setResponseId(0);
		
		if (!enchantManager.nullCheck(item)) {
			return item;
		}
		
		ItemStack clone = item.clone();
		
		//Tag should be removed before the lore
		clone = tagWrapper.removeTag(clone, ench);
		clone = loreAdder.removeLore(clone, ench);

		if (responseManager.getResponseId() == 0) {
			item = clone;
		}
		
		return item;
	}
	
}
