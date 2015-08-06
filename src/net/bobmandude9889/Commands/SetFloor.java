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
import org.bukkit.plugin.PluginManager;

import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.Vector;
import com.worldcretornica.plotme_core.bukkit.PlotMe_CorePlugin;
import com.worldcretornica.plotme_core.bukkit.api.BukkitPlayer;

public class SetFloor implements HubCommand {

	private PlotMeCoreManager plotAPI;
    private PlotMe_CorePlugin plotmeBukkitHook;
	
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
		PluginManager pm = Bukkit.getPluginManager();
        if (pm.isPluginEnabled("PlotMe")) {
            plotmeBukkitHook = (PlotMe_CorePlugin) pm.getPlugin("PlotMe");
            plotAPI = PlotMeCoreManager.getInstance();
            Bukkit.getLogger().info("Hooked to PlotMe!");
        }
		try {
			BukkitPlayer player = (BukkitPlayer) plotmeBukkitHook.wrapPlayer(((Player) sender));
			Plot plot = plotAPI.getPlotById(plotAPI.getPlotId(player.getLocation()), player.getWorld());
			if (plot.getOwner() == null || ((plot.getOwner() != null && !Bukkit.getPlayer(plot.getOwner()).equals(sender.getName())) && !sender.hasPermission("setfloor.others"))) {
				sender.sendMessage(ChatColor.RED + "This is not your plot!");
				return;
			}
			
			Vector l1 = plot.getPlotBottomLoc();
			Vector l2 = plot.getPlotTopLoc();
			Byte data = 0;
			if(args.length > 1){
				data = Byte.parseByte(args[1]);
			}
			for (int x = l1.getBlockX(); x <= l2.getBlockX(); x++) {
				for (int z = l1.getBlockZ(); z <= l2.getBlockZ(); z++) {
					Block block = new Location(((Player) sender).getWorld(), x, 64, z).getBlock();
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
