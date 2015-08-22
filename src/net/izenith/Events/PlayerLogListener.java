package net.izenith.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.izenith.Main.Util;

public class PlayerLogListener extends Util implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(final PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Util.updatePlayerList();

		if (!Util.hasJoined(e.getPlayer())) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Util.getMain(), new Runnable(){
				@Override
				public void run(){
					String name = e.getPlayer().getName();
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + name + " times 20 60 20");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"title " + name + " subtitle {text:\"" + name + "\",color:\"gray\"}");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"title " + name + " title {text:\"Welcome to \",color:\"dark_gray\",extra:[{text:\"iZenith\",color:\"white\"},{text:\" Minecraft\",color:\"gold\"}]}");
					Util.setJoined(e.getPlayer());
				}
			},20);
		}
		
		String message = Util.getConfig().getString("join_message");
		message = Util.parseColors(message);
		message = message.replaceAll("%player%",e.getPlayer().getName());
		e.setJoinMessage(message);
		
		Util.loadOnlineTime(player);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onLeave(PlayerQuitEvent e){
		String message = Util.getConfig().getString("leave_message");
		message = Util.parseColors(message);
		message = message.replace("%player%",e.getPlayer().getName());
		e.setQuitMessage(message);
		
		Util.setOnlineTime(e.getPlayer());
	}
	
}
