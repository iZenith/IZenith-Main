package net.izenith.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.izenith.Main.Util;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(Util.getConfig().getStringList("inventory_interact_worlds").contains(e.getWhoClicked().getWorld().getName())){
			e.setCancelled(false);
		}
	}

}
