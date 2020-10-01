package org.plugin.loreSystem;

public class LoreManager {
	//Singleton setup
	private static LoreManager instance;
	private LoreManager() {instance = this;}
	
	public static LoreManager getInstance() {
		if (instance == null) {
			instance = new LoreManager();
		}
		return instance;
	}
	
	//Keeps hold of one LoreAdder to be used. By default, uses the default LoreAdder. Allows for default LoreAdder to be overriden by 3rd part.
	private class DefaultLore implements LoreAdder {}
	private LoreAdder loreAdder = new DefaultLore();
	
	public LoreAdder getLoreAdder() {
		return loreAdder;
	}
	public void overrideLoreAdder(LoreAdder adder) {
		loreAdder = adder;
	}
}