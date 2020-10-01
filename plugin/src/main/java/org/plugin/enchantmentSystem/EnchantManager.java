package org.plugin.enchantmentSystem;

import java.util.List;
import java.util.Map;

import org.api.CustomEnchantment;
import org.api.ResponseManager;
import org.api.TagWrapper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.plugin.tagSystem.TagManager;

public class EnchantManager {
	//Singleton setup
	private static EnchantManager instance;
	private EnchantManager() {instance = this;}
	
	public static EnchantManager getInstance() {
		if (instance == null) {
			instance = new EnchantManager();
		}
		return instance;
	}
	
	//Basically does the same as LoreManager, but it contains a couple of utility functions.
	private class EnchantAdderImpl implements EnchantAdder {}
	private EnchantAdder enchantAdder = new EnchantAdderImpl();
	private ResponseManager responseManager = ResponseManager.getInstance();
	final TagWrapper tagWrapper = TagManager.getInstance().getImplemenation();
	
	public EnchantAdder getEnchantAdder() {
		return enchantAdder;
	}
	
	public void overrideEnchantAdder(EnchantAdder adder) {
		enchantAdder = adder;
	}
	
	public boolean defaultCheck(ItemStack item, CustomEnchantment ench, int lvl) {
		//Check for if the enchantment can be applied to the provided material
		final List<Material> applicableMaterials = ench.getApplicableMaterials();
		if (!applicableMaterials.contains(item.getType())) {
			responseManager.setResponseId(1);
			return false;
			}
		
		//Check for level too low
		if (lvl <= 0) {
			responseManager.setResponseId(2);
			return false;
			}
				
		//Check for level too high
		if (lvl > ench.getMaxLv()) {
			responseManager.setResponseId(3);
			return false;
			}
		
		//Check for vanilla clash
		final List<Enchantment> incompatibleVanillaEnchs = ench.getIncompatibleVanillaEnchs();
		final Map<Enchantment, Integer> vanillaEnchs = item.getEnchantments();
		for (Enchantment incompatibleEnch : incompatibleVanillaEnchs) {
			if (vanillaEnchs.containsKey(incompatibleEnch)) {
				responseManager.setResponseId(4);
				return false;
			}
		}
				
		//Check for custom clash
		final List<CustomEnchantment> incompatibleCustomEnchs = ench.getIncompatibleCustomEnchs();
		final List<CustomEnchantment> customEnchs = tagWrapper.getTags(item);
		for (CustomEnchantment customEnch : incompatibleCustomEnchs) {
			if (customEnchs.contains(customEnch)) {
				responseManager.setResponseId(4);
				return false;
			}
		}
		return true;
	}
	
	public boolean nullCheck(ItemStack item) {
		if (item.getType() == Material.AIR) {
			responseManager.setResponseId(7);
			return false;
		}
		return true;
	}
}