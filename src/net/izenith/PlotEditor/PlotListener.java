package net.izenith.PlotEditor;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import com.plotsquared.bukkit.events.PlotDeleteEvent;
import com.plotsquared.bukkit.events.PlotMergeEvent;

import net.izenith.Main.Util;

public class PlotListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlotClaim(final PlayerClaimPlotEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Util.getMain(),
				new Runnable() {
					@Override
					public void run() {
						Plot plot = e.getPlot();
						String owner = e.getPlayer().getName();
						Location l1 = new Location(plot.getWorld().toString(),
								plot.getCorners()[0].getX() + 1, 66, plot
										.getCorners()[0].getZ() + 1);
						Location l2 = new Location(plot.getWorld().toString(),
								plot.getCorners()[1].getX(), 66, plot
										.getCorners()[1].getZ() + 1);
						Location l3 = new Location(plot.getWorld().toString(),
								plot.getCorners()[2].getX(), 66, plot
										.getCorners()[2].getZ());
						Location l4 = new Location(plot.getWorld().toString(),
								plot.getCorners()[3].getX() + 1, 66, plot
										.getCorners()[3].getZ());

						Util.CreatePlotSkull(plot, owner, l1, l2, l3, l4);
					}
				}, 20);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDelete(final PlotDeleteEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Util.getMain(),
				new Runnable() {
					@Override
					public void run() {
						Plot plot = new Plot(e.getWorld(), e.getPlotId());
						Location l1 = new Location(plot.getWorld().toString(),
								plot.getCorners()[0].getX() + 1, 66, plot
										.getCorners()[0].getZ() + 1);
						Location l2 = new Location(plot.getWorld().toString(),
								plot.getCorners()[1].getX(), 66, plot
										.getCorners()[1].getZ() + 1);
						Location l3 = new Location(plot.getWorld().toString(),
								plot.getCorners()[2].getX(), 66, plot
										.getCorners()[2].getZ());
						Location l4 = new Location(plot.getWorld().toString(),
								plot.getCorners()[3].getX() + 1, 66, plot
										.getCorners()[3].getZ());

						Util.getLocation(l1).getBlock().setTypeId(0);
						Util.getLocation(l1.subtract(0, 1, 0)).getBlock()
								.setTypeId(44);

						Util.getLocation(l2).getBlock().setTypeId(0);
						Util.getLocation(l2.subtract(0, 1, 0)).getBlock()
								.setTypeId(44);

						Util.getLocation(l3).getBlock().setTypeId(0);
						Util.getLocation(l3.subtract(0, 1, 0)).getBlock()
								.setTypeId(44);

						Util.getLocation(l4).getBlock().setTypeId(0);
						Util.getLocation(l4.subtract(0, 1, 0)).getBlock()
								.setTypeId(44);
					}
				}, 20);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMerge(final PlotMergeEvent e) {
		final Plot plot = e.getPlot();
		final Location l1 = new Location(plot.getWorld().toString(),
				plot.getCorners()[0].getX() + 1, 66,
				plot.getCorners()[0].getZ() + 1);
		final Location l2 = new Location(plot.getWorld().toString(),
				plot.getCorners()[1].getX(), 66,
				plot.getCorners()[1].getZ() + 1);
		final Location l3 = new Location(plot.getWorld().toString(),
				plot.getCorners()[2].getX(), 66, plot.getCorners()[2].getZ());
		final Location l4 = new Location(plot.getWorld().toString(),
				plot.getCorners()[3].getX() + 1, 66,
				plot.getCorners()[3].getZ());
		Util.getLocation(l1).getBlock().setTypeId(0);
		Util.getLocation(l1.subtract(0, 1, 0)).getBlock().setTypeId(44);

		Util.getLocation(l2).getBlock().setTypeId(0);
		Util.getLocation(l2.subtract(0, 1, 0)).getBlock().setTypeId(44);

		Util.getLocation(l3).getBlock().setTypeId(0);
		Util.getLocation(l3.subtract(0, 1, 0)).getBlock().setTypeId(44);

		Util.getLocation(l4).getBlock().setTypeId(0);
		Util.getLocation(l4.subtract(0, 1, 0)).getBlock().setTypeId(44);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Util.getMain(),
				new Runnable() {
					@Override
					public void run() {
						HashSet<UUID> owners = e.getPlot().getOwners();
						String owner = owners.toString();
						if (owners.size() >= 1) {
							owner = "MHF_Exclamation";
						}
						Util.CreatePlotSkull(plot, owner, l1, l2, l3, l4);
					}
				}, 20);
	}
}