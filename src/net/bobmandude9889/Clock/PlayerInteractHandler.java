package net.bobmandude9889.Clock;

import java.util.ArrayList;
import java.util.List;

import net.bobmandude9889.Main.Vars;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractHandler implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(Vars.createClock.containsKey(e.getPlayer())){
			e.setCancelled(true);
			Location clicked = e.getClickedBlock().getLocation();
			String info = Vars.createClock.get(e.getPlayer()) + "," + clicked.getWorld().getName() + "," + clicked.getBlockX() + "," + clicked.getBlockY() + "," + clicked.getBlockZ();
			Vars.clocks.add(info);
			List<String> list = Vars.main.getConfig().getStringList("clocks");
			if(list == null) list = new ArrayList<String>();
			list.add(info);
			Vars.main.getConfig().set("clocks", list);
			Vars.main.saveConfig();
			Vars.createClock.remove(e.getPlayer());
			e.getPlayer().sendMessage(ChatColor.GREEN + "Clock created");
		}
	}
	
}
