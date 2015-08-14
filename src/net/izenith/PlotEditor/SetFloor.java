package net.izenith.PlotEditor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;

import net.izenith.Commands.HubCommand;
import net.izenith.Main.Util;

public class SetFloor implements HubCommand {
	
	@Override
	public String getName() {
		return "setfloor";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			PlotAPI api = Util.getPlotAPI();
			Player player = (Player) sender;
			Plot plot = api.getPlot(player.getLocation());
			System.out.println(plot.getId().toString());
			if (!plot.isOwner(player.getUniqueId()) && !sender.hasPermission("setfloor.others")) {
				sender.sendMessage(ChatColor.RED + "This is not your plot!");
				return;
			}
			
			Location l1 = plot.getBottom();
			Location l2 = plot.getTop();
			System.out.println(l1 + " " + l2);
			Byte data = 0;
			if(args.length > 1){
				data = Byte.parseByte(args[1]);
			}
			for (int x = l1.getX(); x <= l2.getX(); x++) {
				for (int z = l1.getZ(); z <= l2.getZ(); z++) {
					Block block = new org.bukkit.Location(player.getWorld(), x, 64, z).getBlock();
					block.setType(Material.getMaterial(Integer.parseInt(args[0])));
					block.setData(data);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			sender.sendMessage(ChatColor.RED + "Invalid arguments!");
		}
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}

	@Override
	public boolean hasPermission() {
		return false;
	}

	@Override
	public Permission getPermission() {
		return null;
	}

}
