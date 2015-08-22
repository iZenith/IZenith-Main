package net.izenith.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.izenith.FrameAPI.FrameAction;

public class FrameListener implements Listener{

	@EventHandler
	public void onFrameDestroy(FrameDestroyEvent e){
		if(e.getAction().equals(FrameAction.ShiftLeftClick)){
			ItemStack item = e.getItemFrame().getItem();
			ItemMeta iMeta = item.getItemMeta();
			iMeta.setDisplayName("");
			item.setItemMeta(iMeta);
			e.getItemFrame().setItem(item);
			e.setCancelled(true);
		}
	}
	
}
