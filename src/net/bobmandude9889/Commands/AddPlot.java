package net.bobmandude9889.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import net.md_5.bungee.api.ChatColor;

public class AddPlot implements HubCommand{

	@Override
	public String getName() {
		return "addplot";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player){
			sender.sendMessage(ChatColor.RED + "This command can only be used from the console.");
			return;
		}
		Player player = Bukkit.getPlayer(args[0]);
		if(player != null){
			for(int i = 1; i < 100; i++){
				if(player.hasPermission("plots.plot." + i)){
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add -plots.plot." + i);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add plots.plot." + i + 1);
					break;
				}
			}
		}
	}

	@Override
	public boolean onlyPlayers() {
		return false;
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
