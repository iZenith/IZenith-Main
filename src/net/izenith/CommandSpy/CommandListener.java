package net.izenith.CommandSpy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.izenith.Commands.CommandSpy;
import net.izenith.Main.Util;
import net.izenith.Main.Vars;

public class CommandListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		CommandSpy.setupFilter(e.getPlayer());
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		for (Player p : Vars.main.getServer().getOnlinePlayers()) {
			CommandFilter filter = Vars.commandSpy.get(p);
			if (filter != null) {
				if (filter.canPass(e)) {
					p.sendMessage(ChatColor.RED + e.getPlayer().getName() + ChatColor.GRAY + " performed the command: " + ChatColor.GREEN + e.getMessage());
				}
			}
		}
		
		if(Util.startsWithIgnoreCase(e.getMessage(),"/suicide")){
			e.setCancelled(true);
			e.getPlayer().setHealth(0);
			Bukkit.getServer().broadcastMessage(ChatColor.BLUE + e.getPlayer().getName() + ChatColor.DARK_PURPLE + " took the easy way out!");
		}
		
		if(Util.startsWithIgnoreCase(e.getMessage(), "/reload")) e.setCancelled(true);
		
		if(Util.startsWithIgnoreCase(e.getMessage(), "/plots")){
			e.setCancelled(true);
			e.getPlayer().performCommand("warp plots");
		}
		
		if(Util.startsWithIgnoreCase(e.getMessage(), "/info")){
			e.setCancelled(true);
			Bukkit.dispatchCommand(e.getPlayer(), "warp info");
		}
	}
	
}