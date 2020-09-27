package org.V1_14_R1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import org.api.CustomEnchantment;
import org.api.EnchantRegister;
import org.api.ResponseManager;
import org.api.TagWrapper;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Implemenation implements TagWrapper{
	
	final JavaPlugin plugin;
	private final NamespacedKey enchListKey;
	private final NamespacedKey glowKey;
	private final ResponseManager responseManager = ResponseManager.getInstance();

	public Implemenation(JavaPlugin plugin) {
		this.plugin = plugin;
		enchListKey = new NamespacedKey(plugin, "EnchanterEnchList");
		glowKey = new NamespacedKey(plugin, "Glow");
		
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			}
		try {
			GlowEffect glow = new GlowEffect(glowKey);
			Enchantment.registerEnchantment(glow);
			}
		catch (IllegalArgumentException e){
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public ItemStack setTag(ItemStack item, CustomEnchantment customEnchantment, int level) {
		//converts item and gets PDC
		final ItemMeta itemMeta = item.getItemMeta();
		final PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
		
		//Stores the level of the enchantment under it's name in the PDC
		final NamespacedKey key = new NamespacedKey(plugin, customEnchantment.getName());
		pdc.set(key, PersistentDataType.INTEGER, level);
		
		int[] cache = new int[0];
		
		//checks if the PDC already has an array of enchantments. If it does, it sets those values to cache
		if (pdc.has(enchListKey, PersistentDataType.INTEGER_ARRAY)) {cache = pdc.get(enchListKey, PersistentDataType.INTEGER_ARRAY);}
		
		//If the enchantment array already has the enchantment, it will return before adding the enchantment to the array. (since it's already on there)
		if (checkTag(item, customEnchantment.getId()) == true) {
			item.setItemMeta(itemMeta);
			return item;
		}
		
		//Creates a copy of the enchantment array, increasing the size by one before adding the new enchantment
		final int[] enchIdList = Arrays.copyOf(cache, cache.length + 1);
		enchIdList[enchIdList.length - 1] = customEnchantment.getId();
	
		pdc.set(enchListKey, PersistentDataType.INTEGER_ARRAY, enchIdList);

		item.setItemMeta(itemMeta);
		return item;
	}

	public ArrayList<CustomEnchantment> getTags(ItemStack item) {
		
		final PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
		final ArrayList<CustomEnchantment> enchList = new ArrayList<>();
		
		if (!pdc.has(enchListKey, PersistentDataType.INTEGER_ARRAY)) return null;
		
		final int[] enchIdArray = pdc.get(enchListKey, PersistentDataType.INTEGER_ARRAY);
		
		for (int i = 0; i < enchIdArray.length; i++) {
			enchList.add(EnchantRegister.enchFromId(enchIdArray[i]));
		}
		
		return enchList;
	}

	@Override
	public ItemStack removeTag(ItemStack item, CustomEnchantment ench) {

		if (!checkTag(item, ench.getId())){
			responseManager.setResponseId(6);
			return item;
		}
		final NamespacedKey key = new NamespacedKey(plugin, ench.getName());
		final ItemMeta itemMeta = item.getItemMeta();
		final PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
		final int[] enchArray = pdc.get(enchListKey, PersistentDataType.INTEGER_ARRAY);
		final int[] newArray = new int[enchArray.length];
		
		for (int i = 0; i < enchArray.length; i++) {
			Bukkit.getConsoleSender().sendMessage("AIBFfqg");
			if (enchArray[i] == ench.getId()) continue;
			newArray[i] = enchArray[i];
		}
		
		pdc.set(enchListKey, PersistentDataType.INTEGER_ARRAY, newArray);
		pdc.remove(key);
		item.setItemMeta(itemMeta);
		return item;
	}

	@Override
	public ItemStack clearTags(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.getPersistentDataContainer().set(enchListKey, PersistentDataType.INTEGER_ARRAY, new int[0]);
		item.setItemMeta(itemMeta);
		return item;
	}

	@Override
	public boolean checkTag(ItemStack item, int id) {
		final ItemMeta itemMeta = item.getItemMeta();
		final PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
		
		if (!pdc.has(enchListKey, PersistentDataType.INTEGER_ARRAY)) {return false;}
		
		final int[] array = pdc.get(enchListKey, PersistentDataType.INTEGER_ARRAY);
	
		for (int i = 0; i < array.length; i++) {
			if (array[i] == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getLevel(ItemStack item, int id) {
		if (!checkTag(item, id)) {
			responseManager.setResponseId(8);
			return 0;
		}
		
		final PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
		final CustomEnchantment customEnchantment = EnchantRegister.enchFromId(id);
		final NamespacedKey key = new NamespacedKey(plugin, customEnchantment.getName());
		
		return pdc.get(key, PersistentDataType.INTEGER);
	}

	@Override
	public ItemStack addGlow(ItemStack item) {
		final GlowEffect glow = new GlowEffect(glowKey);
		final ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(glow, 1, true);
		item.setItemMeta(meta);
		
		return item;
	}

	@Override
	public ItemStack removeGlow(ItemStack item) {
		final GlowEffect glow = new GlowEffect(glowKey);
		final ItemMeta meta = item.getItemMeta();
		
		meta.removeEnchant(glow);
		item.setItemMeta(meta);
		
		return item;
	}
}
