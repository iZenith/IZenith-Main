package net.bobmandude9889.CommandSpy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.bobmandude9889.Commands.CommandSpy;
import net.bobmandude9889.Main.Vars;

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
	}
	
}
