package org.plugin.tagSystem;

import org.api.TagWrapper;
import org.bukkit.Bukkit;
import org.plugin.EnchanterCore;

public class TagManager {

	//Singleton class setup
	private static TagManager instance;
	private TagManager() {};
	
	public static TagManager getInstance() {
		if (instance == null) {
			instance = new TagManager();
		}
		return instance;
	}
	
	//Actual content
	private TagWrapper implementation;	
	private final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

	//Sets the implementation vased on the version. 1.14+ uses the 1.14 implementation.
	public void createInstance() {
		try {
			switch (serverVersion) {
			case "1_8_R3": implementation = new org.V1_8_R3.Implemenation(); break;
			case "1_9_R2": implementation = new org.V1_9_R2.Implemenation(); break;
			case "1_10_R1": implementation = new org.V1_10_R1.Implemenation(); break;
			case "1_11_R1": implementation = new org.V1_11_R1.Implemenation(); break;
			case "1_12_R1": implementation = new org.V1_12_R1.Implemenation(); break;
			case "1_13_R2": implementation = new org.V1_13_R2.Implemenation(); break;
			case "1_14_R1":
			case "1_15_R1":
			case "1_16_R1": implementation = new org.V1_14_R1.Implemenation(EnchanterCore.getInstance()); break;}
			
			if (implementation == null) {
				Bukkit.getLogger().severe("Version " + serverVersion + " is not supported by EnchanterCore. EnchanterCore disabling.");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public TagWrapper getImplemenation() {
		return implementation;
	}
		
}
