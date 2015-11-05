package net.izenith.Events;

import net.izenith.Main.Vars;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class InteractListener implements Listener {

	@EventHandler
	public void onClick(final PlayerInteractEvent e) {
		Action a = e.getAction();
		if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			BlockState b = e.getClickedBlock().getState(); //b is the block
			if (b instanceof Skull) { //this test if b is a skull
				if (!(p.isSneaking()) || (p.getItemInHand().getType() == Material.AIR)){
					Skull s = (Skull) b;
					String owner = s.getOwner();
					if (owner == null) {
					} else {
						p.sendMessage(ChatColor.YELLOW + "That is the head of " + ChatColor.GOLD + owner + ChatColor.YELLOW + ".");
						e.setCancelled(true);
					}
				}
			}
		}

		if (e.getPlayer().getLocation().getWorld().getName().equals("spawn") && e.getItem().getType().equals(Material.NETHER_STAR)) {
			Vars.tpGUI.open(e.getPlayer());
		}


	}
}
