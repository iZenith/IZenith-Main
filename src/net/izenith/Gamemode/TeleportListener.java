package net.izenith.Gamemode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;

import net.izenith.Main.Util;

public class TeleportListener implements Listener {

	@EventHandler
	public void onTeleport(final PlayerTeleportEvent e) {
		if (!e.getFrom().getWorld().equals(e.getTo().getWorld())) {
			Player p = e.getPlayer();
			MultiverseCore mv = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
			MVWorldManager wm = mv.getMVWorldManager();
			MultiverseWorld toWorld = wm.getMVWorld(e.getTo().getWorld().getName());
			MultiverseWorld fromWorld = wm.getMVWorld(e.getFrom().getWorld().getName());
			System.out.println(toWorld.getName());
			if (toWorld != fromWorld && !p.getGameMode().equals(toWorld.getGameMode())) {
				p.setGameMode(toWorld.getGameMode());
			}
		}
		if(e.getTo().getWorld().getName().equals("spawn")){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Util.getMain(),new Runnable(){
				@Override
				public void run(){
					Util.getKit(e.getPlayer(), "nether_star");
				}
			},40l);
		}
	}

}
