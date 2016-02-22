package net.izenith.CommandSpy;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;

import net.izenith.Main.Util;
import net.izenith.Main.Vars;

public class CommandListener implements Listener {

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

		if (Util.startsWithIgnoreCase(e.getMessage(), "/suicide")) {
			e.setCancelled(true);
			e.getPlayer().setHealth(0);
			Bukkit.getServer().broadcastMessage(ChatColor.BLUE + e.getPlayer().getName() + ChatColor.DARK_PURPLE + " took the easy way out!");
		}

		if (Util.startsWithIgnoreCase(e.getMessage(), "/reload")){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "This command is currently disabled.");
		}

		if (Util.startsWithIgnoreCase(e.getMessage(), "/plots") || Util.startsWithIgnoreCase(e.getMessage(), "/cr")) {
			e.setCancelled(true);
			e.getPlayer().performCommand("warp plots");
		}

		if (Util.startsWithIgnoreCase(e.getMessage(), "/info")) {
			e.setCancelled(true);
			Bukkit.dispatchCommand(e.getPlayer(), "warp info");
		}
		
		if (Util.startsWithIgnoreCase(e.getMessage(), "/p")){
			Player p = e.getPlayer();
			if (!(p instanceof Player)) return;
			PlotAPI api = Util.getPlotAPI();
			Plot plot = api.getPlot(p);
			if (plot == null) return;
			HashSet<UUID> owners = plot.getOwners();
			String owner = owners.toString();
			if (owners.size() >= 1) {
				owner = "MHF_Exclamation";
			}
			@SuppressWarnings("deprecation")
			Location l1 = new Location(plot.getWorld().toString(),
					plot.getCorners()[0].getX() + 1, 66, plot
							.getCorners()[0].getZ() + 1);
			@SuppressWarnings("deprecation")
			Location l2 = new Location(plot.getWorld().toString(),
					plot.getCorners()[1].getX(), 66, plot
							.getCorners()[1].getZ() + 1);
			@SuppressWarnings("deprecation")
			Location l3 = new Location(plot.getWorld().toString(),
					plot.getCorners()[2].getX(), 66, plot
							.getCorners()[2].getZ());
			@SuppressWarnings("deprecation")
			Location l4 = new Location(plot.getWorld().toString(),
					plot.getCorners()[3].getX() + 1, 66, plot
							.getCorners()[3].getZ());
			Util.CreatePlotSkull(plot, owner, l1, l2, l3, l4);
		}
		
		if (Util.startsWithIgnoreCase(e.getMessage(), "/pex")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Util.getMain(), new Runnable() {
				public void run() {
					Util.updatePlayerList();
				}
			}, 20L);
		}
	}

}
