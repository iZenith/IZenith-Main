package net.bobmandude9889.Chat;

import java.util.ArrayList;
import java.util.List;
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

public class ChatHandler implements Listener{

	Plugin main;
	
	public ChatHandler(Plugin main){
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chat(AsyncPlayerChatEvent e){
		try{
		String format = main.getConfig().getString("chat." + PermissionHandler.getGroupName(e.getPlayer()));
		String message = format.replaceAll("%player%", e.getPlayer().getDisplayName());
		message = message.replaceAll("%message%", Matcher.quoteReplacement(e.getMessage()));
		message = message.replaceAll("%prefix%", PermissionHandler.getGroup(e.getPlayer()).getPrefix());
		String chars = "abcdefrlomn1234567890";
		for(Character c : chars.toCharArray()){
			for(int i = 0; i < message.length() - 1; i++){
				if(message.substring(i,i+2).equals("&" + c.toString())){
					String replace = message.substring(i-1,i).equals("&")?"&" + c.toString():ChatColor.getByChar(c) + "";
					message = message.substring(0,i - 1) + replace + message.substring(i+2,message.length());
					i++;
				}
			}
		}
		if(format != null){
			e.setCancelled(true);
			for(final Player p : main.getServer().getOnlinePlayers()){
				if(message.toLowerCase().contains(p.getName().toLowerCase()) && !e.getPlayer().equals(p)){
					int i = message.toLowerCase().indexOf(p.getName().toLowerCase());
					String messageP = message.substring(0, i) + ChatColor.RED + ChatColor.BOLD + message.substring(i, p.getName().length() + i) + ChatColor.getByChar(format.charAt(format.indexOf("%message%") - 1)) + message.substring(p.getName().length() + i);
					p.sendMessage(messageP);
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 1f);
					main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable(){
						@Override
						public void run(){
							p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 1.25f);
						}
					}, 10l);
				} else {
					p.sendMessage(message);
				}
			}
		}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
