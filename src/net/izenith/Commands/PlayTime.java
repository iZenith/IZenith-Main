package net.izenith.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import net.izenith.Main.Util;
import net.md_5.bungee.api.ChatColor;

public class PlayTime implements HubCommand{

	@Override
	public String getName() {
		return "playtime";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"play","givemearimjobandbitemyear"};
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Long time;
		if(args.length > 0){
			time = Util.getOnlineTime(Bukkit.getPlayer(args[0]));
		} else {
			time = Util.getOnlineTime((Player) sender);
		}
		Double timeHours = new Double(time)/(1000*60*60);
		sender.sendMessage(ChatColor.BLUE + (args.length > 0 ? args[0] : sender.getName()) + " has played for " + ChatColor.GREEN + timeHours + " hours");
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
	