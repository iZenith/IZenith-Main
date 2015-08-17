package net.izenith.Commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
		Long time = Util.getOnlineTime(Bukkit.getPlayer(args[0]));
		Double timeHours = new Double(time)/(1000*60*60);
		DecimalFormat df = new DecimalFormat("#.##");
		String shortTime = df.format(timeHours);
		sender.sendMessage(ChatColor.BLUE + args[0] + " has played for " + ChatColor.GREEN + shortTime + " hours");
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
	