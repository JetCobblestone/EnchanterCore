package org.plugin;

import org.api.EnchantRegister;
import org.bukkit.plugin.java.JavaPlugin;
import org.plugin.commands.CommandNode;
import org.plugin.tagSystem.TagManager;

public class EnchanterCore extends JavaPlugin {
	private static EnchanterCore instance;
	
	public static EnchanterCore getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		EnchantRegister.setPlugin(instance);
		
		//Sets the version interface
		TagManager.getInstance().createInstance();
		
		getCommand("Enchanter").setExecutor(new CommandNode());
	}
	
	@Override
	public void onDisable() {

	}
}
