package org.V1_10_R1;

import java.util.ArrayList;

import org.api.CustomEnchantment;
import org.api.EnchantRegister;
import org.api.ResponseManager;
import org.api.TagWrapper;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import net.minecraft.server.v1_10_R1.NBTTagList;

public class Implemenation implements TagWrapper{
	
	private final ResponseManager responseManager = ResponseManager.getInstance();
	
	@Override
	public ItemStack setTag(ItemStack item, CustomEnchantment ench, int level) {
		if (checkTag(item, ench.getId())) {
			item = removeTag(item, ench);}
		
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		final NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		final NBTTagList enchList = (compound.getList("enchantments", 10) == null ? new NBTTagList() : compound.getList("enchantments", 10));		
		final NBTTagCompound enchComp = new NBTTagCompound();
		
		enchComp.set("id", new NBTTagInt(ench.getId()));
		enchComp.set("lvl", new NBTTagInt(level));
		
		enchList.add(enchComp);
		compound.set("enchantments", enchList);
		nmsItem.setTag(compound);
		
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	@Override
	public ItemStack removeTag(ItemStack item, CustomEnchantment ench) {
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		final NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		
		if (compound.get("enchantments") == null) { 
			responseManager.setResponseId(6);
			return item;
		}
		
		final NBTTagList enchList = compound.getList("enchantments", 10);
		boolean found = false;
		
		for (int i = 0; i < enchList.size(); i++) {
			NBTTagCompound enchComp = enchList.get(i);
			if (enchComp.getInt("id") == ench.getId()) {
				found = true;
				enchList.remove(i);
			}
		}
		if (!found) {
			responseManager.setResponseId(6);
			return item;
		}
		
		compound.set("enchantments", enchList);
		nmsItem.setTag(compound);
		
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	@Override
	public ItemStack clearTags(ItemStack item) {
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		final NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		compound.remove("enchantments");
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	public ArrayList<CustomEnchantment> getTags(ItemStack item) {
		final ArrayList<CustomEnchantment> enchantList = new ArrayList<CustomEnchantment>();
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		
		if (!nmsItem.hasTag()) return enchantList;
		if (nmsItem.getTag().get("enchantments") == null) return enchantList;
		
		final NBTTagList nbtEnchantList = (NBTTagList) nmsItem.getTag().getList("enchantments", 10);
		
		for (int i = 0; i < nbtEnchantList.size(); i++) {
			CustomEnchantment ench = EnchantRegister.enchFromId(nbtEnchantList.get(i).getInt("id"));
			if (ench == null) continue;
			enchantList.add(ench);
		}
		return enchantList;
	}
	
	public boolean checkTag(ItemStack item, int id) {
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		final NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		
		if (compound.getList("enchantments", 10) == null) { 
			return false;
		}
		
		final NBTTagList enchList = compound.getList("enchantments", 10);

		for (int i = 0; i < enchList.size(); i++) {
			NBTTagCompound enchComp = enchList.get(i);
			if (enchComp.getInt("id") == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getLevel(ItemStack item, int id) {
		if (!checkTag(item, id)) return 0;
		
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		final NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		final NBTTagList enchList = compound.getList("enchantments", 10);
		
		for (int i = 0; i < enchList.size(); i++) {
			NBTTagCompound enchComp = enchList.get(i);
			if (enchComp.getInt("id") == id) {
				return enchComp.getInt("lvl");
			}
		}
		return 0;
	}

	@Override
	public ItemStack addGlow(ItemStack item) {
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		final NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		final NBTTagList ench = new NBTTagList();
		
		compound.set("ench", ench);
		nmsItem.setTag(compound);
		
		return CraftItemStack.asCraftMirror(nmsItem);
	}

	@Override
	public ItemStack removeGlow(ItemStack item) {
		final net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		final NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		
		compound.remove("ench");		
		nmsItem.setTag(compound);
		
		return CraftItemStack.asCraftMirror(nmsItem);
	}
}
