package org.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;

public class CustomEnchantment {
	private String name;
	private int maxLv;
	private int id;
	private Effect effect;
	private final List<CustomEnchantment> incompatibleCustomEnchs = new ArrayList<CustomEnchantment>();
	private final List<Enchantment> incompatibleVanillaEnchs = new ArrayList<Enchantment>();
	private final List<Material> applicableMaterials = new ArrayList<Material>();
	
	public CustomEnchantment(String name, int maxLv, Effect effect) {
		this.name = name;
		this.maxLv = maxLv;
		this.effect = effect;
		EnchantRegister.addEnchantment(this);
	}
	
	public String getName() {return name;}
	public int getMaxLv() {return maxLv;}
	public int getId() {return id;}
	
	public void setId(int id) {this.id = id;}
	
	public void trigger(Event event) {effect.run(event);}
	
	public interface Effect {
		public void run(Event event);
	}
	
	public List<CustomEnchantment> getIncompatibleCustomEnchs(){
		return incompatibleCustomEnchs;
	}
	public List<Enchantment> getIncompatibleVanillaEnchs(){
		return incompatibleVanillaEnchs;
	}
	public List<Material> getApplicableMaterials(){
		return applicableMaterials;
	}
}
