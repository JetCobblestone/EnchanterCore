package org.plugin;

import org.api.CustomEnchantment;
import org.api.TagWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.plugin.tagSystem.TagManager;

public class Enchantments implements Listener{
	
	private final static TagWrapper tagWrapper = TagManager.getInstance().getImplemenation();
	
	private static CustomEnchantment force;
	private static CustomEnchantment flex;
	private static CustomEnchantment teleport;
	
	public static void loadEnchantments() {
		force = new CustomEnchantment("Force", 3, e -> {
			final PlayerInteractEntityEvent event = (PlayerInteractEntityEvent) e;
			final int lvl = tagWrapper.getLevel(event.getPlayer().getItemInHand(), force.getId());
			final Vector pDirection = event.getPlayer().getLocation().getDirection();
			final Vector vector = new Vector(pDirection.getX()/2*lvl, 0.5, pDirection.getZ()/2*lvl);
			Entity target = event.getRightClicked();
			target.setVelocity(target.getVelocity().add(vector));
		});	
		
		flex = new CustomEnchantment("Flex", 3, e -> {
			PlayerInteractEvent event = (PlayerInteractEvent) e;
			event.getPlayer().sendMessage("You flexed!");
			
		});	
		
		teleport = new CustomEnchantment("Teleport", 3, e -> {
			final PlayerInteractEntityEvent event = (PlayerInteractEntityEvent) e;
			
			if (!(event.getRightClicked() instanceof LivingEntity)) return;
			
			final Player player = event.getPlayer();
			final LivingEntity target = (LivingEntity) event.getRightClicked();
			final Location targetLoc = target.getLocation();
			
			final double a = targetLoc.getX();
			final double b = targetLoc.getY();
			final double c = targetLoc.getZ();
			
			boolean check = false;
			int counter = 0;
			Location destination = null;
			
			while (!check) {
				if (counter == 50) {
					player.playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 1f, 1f);
					player.sendMessage(ChatColor.RED + "Teleport failed - not enough space");
					return;
				}
				
				final double x = (Math.random() * 6 - 3);
				final double m = Math.sqrt(9-Math.pow(x, 2));
				final double z = (Math.random() * 2 * m - m);
				
				final double xSq = Math.pow(x, 2);
				final double zSq = Math.pow(z, 2);
				
				final double y = Math.sqrt(9-xSq-zSq) + b;
				
				destination = new Location(player.getWorld(), x+a, y, z+c);
				
				final Location destination2 = new Location(player.getWorld(), x+a, y+1, z+c);
				final Material blockType = destination.getBlock().getType();
				final Material blockType2 = destination2.getBlock().getType();
				
				if (blockType.equals(Material.AIR) && blockType2.equals(Material.AIR)) {
					check = true;
				}
				counter++;
			}
			
			player.teleport(destination);
			destination.setDirection(target.getEyeLocation().subtract(player.getEyeLocation()).toVector());
			player.teleport(destination);
			player.playSound(destination, Sound.ENDERMAN_TELEPORT, 1f, 1f);
		});	
		
		teleport.getApplicableMaterials().add(Material.DIAMOND_SWORD);
		flex.getApplicableMaterials().add(Material.DIAMOND_SWORD);
	}
	
	@EventHandler
	public void onBlock (PlayerInteractEntityEvent event) {
		if (tagWrapper.checkTag(event.getPlayer().getItemInHand(), force.getId())) {
			force.trigger(event);
		};
	}
	
	@EventHandler
	public void flex (PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
		if (tagWrapper.checkTag(event.getPlayer().getItemInHand(), flex.getId())) {
			flex.trigger(event);
		};
	}
	
	@EventHandler
	public void teleport (PlayerInteractEntityEvent event) {
		if (tagWrapper.checkTag(event.getPlayer().getItemInHand(), teleport.getId())) {
			teleport.trigger(event);
		};
	}
}

