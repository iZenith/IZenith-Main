package net.izenith.Commands;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;

import net.izenith.Main.Util;

public class RefreshHeads implements HubCommand{

	@Override
	public String getName() {
		return "rskull";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) return;
		Player p = (Player) sender;
		PlotAPI api = Util.getPlotAPI();
		Plot plot = api.getPlot(p.getLocation());
		System.out.println(plot);
		if (plot == null) return;
		HashSet<UUID> owners = plot.getOwners();
		String owner = null;
		for(UUID uuid : owners){
			owner = Bukkit.getOfflinePlayer(uuid).getName();
			break;
		}

		@SuppressWarnings("deprecation")
		Location c1 = plot.getCorners()[0];
		@SuppressWarnings("deprecation")
		Location c2 = plot.getCorners()[1];
		
		org.bukkit.Location test = Util.convLocation(c1.clone());
		test.setY(200);
		test.getBlock().setType(Material.STONE);
		
		Location l1 = new Location(plot.getHome().getWorld().toString(),
				c2.getX() + 1, 66, c2.getZ() + 1);
		Location l2 = new Location(plot.getHome().getWorld().toString(),
				c1.getX() - 1, 66, c2.getZ() + 1);
		Location l3 = new Location(plot.getHome().getWorld().toString(),
				c1.getX() - 1, 66, c1.getZ() - 1);
		Location l4 = new Location(plot.getHome().getWorld().toString(),
				c2.getX() + 1, 66, c1.getZ() - 1);
		Util.CreatePlotSkull(plot, owner, l1, l2, l3, l4);
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}

	@Override
	public boolean hasPermission() {
		return true;
	}

	@Override
	public Permission getPermission() {
		return new Permission("plot.rskull");
	}

}
