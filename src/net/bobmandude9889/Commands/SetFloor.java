package net.bobmandude9889.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.worldcretornica.plotme.Plot;
import com.worldcretornica.plotme.PlotManager;

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
			Player player = (Player) sender;
			Plot plot = PlotManager.getPlotById(player.getLocation());
			if (!Bukkit.getPlayer(plot.getOwner()).equals(sender) && !sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "This is not your plot!");
				return;
			}
			Location l1 = PlotManager.getBottom(player.getWorld(), plot);
			Location l2 = PlotManager.getTop(player.getWorld(), plot);
			Byte data = 0;
			if(args.length > 1){
				data = Byte.parseByte(args[1]);
			}
			for (int x = l1.getBlockX(); x <= l2.getBlockX(); x++) {
				for (int z = l1.getBlockZ(); z <= l2.getBlockZ(); z++) {
					Block block = new Location(player.getWorld(), x, 64, z).getBlock();
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
