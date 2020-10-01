package org.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;

//Public enchantment class. Take a lambda as the 3rd parameter of the constructor, allowing for the easy creation of an enchantment effect on creating an item.
public class CustomEnchantment {
	private String name;
	private int maxLv;
	private int id;
	private Effect effect;
	private List<CustomEnchantment> incompatibleCustomEnchs = new ArrayList<CustomEnchantment>();
	private List<Enchantment> incompatibleVanillaEnchs = new ArrayList<Enchantment>();
	private List<Material> applicableMaterials = new ArrayList<Material>();
	
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
	
	public List<CustomEnchantment> getIncompatibleCustomEnchs(){
		return incompatibleCustomEnchs;
	}
	public List<Enchantment> getIncompatibleVanillaEnchs(){
		return incompatibleVanillaEnchs;
	}
	public List<Material> getApplicableMaterials(){
		return applicableMaterials;
	}
	public void setApplicableMaterials(List<Material> applicableMaterials){
		this.applicableMaterials = applicableMaterials;
	}

	public interface Effect {
		public void run(Event event);
	}
}