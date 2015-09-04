package net.izenith.Chat;

import java.util.regex.Matcher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import net.izenith.Main.PermissionHandler;
import net.izenith.Main.Util;
import net.izenith.Main.Vars;

public class ChatHandler implements Listener {

	// Custom chat handler for mentioning players in chat and per group chat formats.

	// Set to highest priority in order to override essentials chat handler
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chat(AsyncPlayerChatEvent e) {
		if(Vars.adminChat.contains(e.getPlayer()) || e.getMessage().startsWith(",") && e.getPlayer().hasPermission("izenith.adminchat")){
			e.setCancelled(true);
			Util.sendAdminMessage(e.getMessage().startsWith(",") ? e.getMessage().substring(1) : e.getMessage(), e.getPlayer());
			return;
		}
		
		// Get main plugin class from Util
		Plugin main = Util.getMain();
		try {
			// Get format for the group of a player from the config	
			String format = main.getConfig().getString("chat." + PermissionHandler.getGroupName(e.getPlayer()));
			// Replace the tags with there values
			String message = format.replaceAll("%player%", e.getPlayer().getDisplayName());
			message = message.replaceAll("%prefix%", PermissionHandler.getGroup(e.getPlayer()).getPrefix());
			// Use Util to convert from & code to ChatColors
			message = Util.parseColors(message);

			// Bukkit replaces formats message colors by default
			// ut = untranslated
			String pMessage = Matcher.quoteReplacement(e.getMessage());
			String utMessage = message.replaceAll("%message%", pMessage);
			
			// Make sure that a format for the players group exists
			if (format != null) {
				// Cancel the sending of the utMessage so iZenith can handle send it instead
				e.setCancelled(true);
				// Loop through players rather than broadcast so that a different utMessage could be set per player ie. Colored names for mentions
				for (final Player player : main.getServer().getOnlinePlayers())
					// Check if the current player is being mentioned and is not themselves
					if (Util.containsIgnoreCase(utMessage, player.getName()) && !e.getPlayer().equals(player)) {
						// Get index of players name for replacement
						int i = utMessage.toLowerCase().indexOf(player.getName().toLowerCase());
						// Color player name
						String utMessageP = utMessage.substring(0, i) + ChatColor.RED + ChatColor.BOLD + utMessage.substring(i, player.getName().length() + i) + ChatColor.getByChar(format.charAt(format.indexOf("%utMessage%") - 1)) + utMessage.substring(player.getName().length() + i);
						player.sendMessage(utMessageP);
						// Send a tune to notify player
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
						main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
							@Override
							public void run() {
								player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1.25f);
							}
						}, 10l);
					} else {
						// Send normal utMessage if the player was not mentioned
						player.sendMessage(utMessage);
					}
			}
			
			// t = translated
			/*String language = Util.detectLanguage(pMessage);
			if(!language.equals("en")){
				String tMessage = message.replaceAll("%message%", Util.getTranslation(pMessage,language,"en"));
				Bukkit.broadcastMessage(tMessage);
			}*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
