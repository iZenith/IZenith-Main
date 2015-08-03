package net.bobmandude9889.Color;

import net.bobmandude9889.Main.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends Util implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void join(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		player.setPlayerListName(getColoredName(player));
		setTeam(player);
	}
	
}
