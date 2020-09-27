package org.plugin.loreSystem;

import java.util.ArrayList;
import java.util.List;

import org.api.CustomEnchantment;
import org.api.ResponseManager;
import org.api.TagWrapper;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.plugin.tagSystem.TagManager;

public interface LoreAdder {
	final TagWrapper tagWrapper = TagManager.getInstance().getImplemenation();
	
	default ItemStack addLore(ItemStack item, CustomEnchantment ench, int lvl){
		
		if (item.getEnchantments().isEmpty() 
				&& tagWrapper.getTags(item).isEmpty()) 
		{item = tagWrapper.addGlow(item);}
		
		if (tagWrapper.checkTag(item, ench.getId())) {item = removeLore(item, ench);}
	
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		
		if (lore == null) {
			lore = new ArrayList<String>();
		}
		lore.add(0, ChatColor.RESET + "" + ChatColor.GRAY + ench.getName() + " " + RomanNumeral.toRoman(lvl));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	default ItemStack removeLore(ItemStack item, CustomEnchantment ench) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
		
		int x = -1;
		for (int i = 0; i < lore.size(); i++) {
			String string = ChatColor.stripColor(lore.get(i));
			string = string.substring(0, string.lastIndexOf(" "));
			
			if (string.equals(ench.getName())) {x = i;}
		}
		
		if (x == -1) {
			ResponseManager.getInstance().setResponseId(5);
			return item;
		}
		
		lore.remove(x);
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		if (item.getEnchantments().isEmpty() && tagWrapper.getTags(item).isEmpty()){
			item = tagWrapper.removeGlow(item);
		}
		
		return item;
	}
}
