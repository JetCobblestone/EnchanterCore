package org.plugin.loreSystem;

public class LoreManager {
	private static LoreManager instance;
	private LoreManager() {instance = this;}
	
	public static LoreManager getInstance() {
		if (instance == null) {
			instance = new LoreManager();
		}
		return instance;
	}
	
	
	private class DefaultLore implements LoreAdder {}
	private LoreAdder loreAdder = new DefaultLore();
	
	public LoreAdder getLoreAdder() {
		return loreAdder;
	}
	public void overrideLoreAdder(LoreAdder adder) {
		loreAdder = adder;
	}
}