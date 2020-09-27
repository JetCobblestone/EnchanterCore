package org.plugin.commands;

import org.api.CustomEnchantment;
import org.api.EnchantRegister;
import org.api.ResponseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.plugin.enchantmentSystem.EnchantAdder;
import org.plugin.enchantmentSystem.EnchantManager;

public class CommandNode implements CommandExecutor{
	private final EnchantAdder enchantAdder = EnchantManager.getInstance().getEnchantAdder();
	private final ResponseManager responseManager = ResponseManager.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		
		if (!(sender instanceof Player)) return false;
		final Player player = (Player) sender;
		if (!player.hasPermission("Enchanter")) {
			player.sendMessage(ChatColor.RED + "ERROR: You do not have permission to use this command");
			return false;
		}
		
		
		if (args[0].equals("enchant")) {
			
			if (args[1] == null) return false;
			
			CustomEnchantment ench = EnchantRegister.enchFromName(args[1]);
			if (ench == null) {
				player.sendMessage(ChatColor.RED + "ERROR: Enchantment not found.");
				return false;
			}
			
			int lvl = 1;
			if (args.length >= 3 ) {
				try {
					lvl = Integer.valueOf(args[2]);
				}
				catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "ERROR: " + args[2] + " is not a valid value.");
					return false;
				}
			}
			
			final ItemStack item = enchantAdder.addEnchant(player.getItemInHand(), ench, lvl);
			
			final int response = responseManager.getResponseId();
			if (response != 0) {
				player.sendMessage(responseManager.getResponses().get(response));
				return false;
			}
			
			player.setItemInHand(item);
			return true;
		}
		
		
		
		if (args[0].equals("remove")) {
			
			if (args[1] == null) return false;
			
			CustomEnchantment ench = EnchantRegister.enchFromName(args[1]);
			if (ench == null) return false;
			
			final ItemStack item = enchantAdder.removeEnchant(player.getItemInHand(), ench);	
			
			final int response = responseManager.getResponseId();
			if (response != 0) {
				player.sendMessage(ChatColor.RED + "ERROR: " + responseManager.getResponses().get(response));
				return false;
			}
			
			player.setItemInHand(item);
			return true;
		}
		
		return false;
	}
}
