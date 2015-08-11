package net.bobmandude9889.Events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class SkullName implements Listener{

	@EventHandler
	public void onClick(final PlayerInteractEvent e){
		Action a = e.getAction();
		if (a.equals(Action.RIGHT_CLICK_BLOCK)){
			Player p = e.getPlayer();
			BlockState b = e.getClickedBlock().getState(); //b is the block
			if (b instanceof Skull){ //this test if b is a skull
				Skull s = (Skull) b;
				String owner = s.getOwner();
				if (owner == null){
				}else if (owner == "Flubber_MC"){
					p.sendMessage(ChatColor.YELLOW + "That is the head of a really stupid person named " + ChatColor.GOLD + owner + ChatColor.YELLOW + ". PS. He is really mean so never be friends with him!!");
				}else{
				p.sendMessage(ChatColor.YELLOW + "That is the head of " + ChatColor.GOLD + owner + ChatColor.YELLOW + ".");
				}
				e.setCancelled(true);
			}
		}
	}
}
