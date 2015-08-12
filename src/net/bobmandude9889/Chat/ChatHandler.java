package net.bobmandude9889.Chat;

import java.util.regex.Matcher;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import net.bobmandude9889.Main.PermissionHandler;
import net.bobmandude9889.Main.Util;

public class ChatHandler implements Listener {

	// Custom chat handler for mentioning players in chat and per group chat formats.

	// Set to highest priority in order to override essentials chat handler
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chat(AsyncPlayerChatEvent e) {
		// Get main plugin class from Util
		Plugin main = Util.getMain();
		try {
			// Get format for the group of a player from the config	
			String format = main.getConfig().getString("chat." + PermissionHandler.getGroupName(e.getPlayer()));
			// Replace the tags with there values
			String message = format.replaceAll("%player%", e.getPlayer().getDisplayName());
			message = message.replaceAll("%message%", Matcher.quoteReplacement(e.getMessage()));
			message = message.replaceAll("%prefix%", PermissionHandler.getGroup(e.getPlayer()).getPrefix());
			// Use Util to convert from & code to ChatColors
			message = Util.parseColors(message);

			// Make sure that a format for the players group exists
			if (format != null) {
				// Cancel the sending of the message so iZenith can handle send it instead
				e.setCancelled(true);
				// Loop through players rather than broadcast so that a different message could be set per player ie. Colored names for mentions
				for (final Player player : main.getServer().getOnlinePlayers())
					// Check if the current player is being mentioned and is not themselves
					if (Util.containsIgnoreCase(message, player.getName()) && !e.getPlayer().equals(player)) {
						// Get index of players name for replacement
						int i = message.toLowerCase().indexOf(player.getName().toLowerCase());
						// Color player name
						String messageP = message.substring(0, i) + ChatColor.RED + ChatColor.BOLD + message.substring(i, player.getName().length() + i) + ChatColor.getByChar(format.charAt(format.indexOf("%message%") - 1)) + message.substring(player.getName().length() + i);
						player.sendMessage(messageP);
						// Send a tune to notify player
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
						main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
							@Override
							public void run() {
								player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1.25f);
							}
						}, 10l);
					} else {
						// Send normal message if the player was not mentioned
						player.sendMessage(message);
					}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
