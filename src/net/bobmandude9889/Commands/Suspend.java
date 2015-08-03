package net.bobmandude9889.Commands;

import net.bobmandude9889.Main.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Suspend extends Util implements HubCommand {

	@Override
	public String getName() {
		return "suspend";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			@SuppressWarnings("unused")
			Player p = getServer().getPlayer(args[0]);
			
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Please enter a player");
		}
	}

	@Override
	public boolean onlyPlayers() {
		return false;
	}

	@Override
	public boolean hasPermission() {
		return true;
	}

	@Override
	public Permission getPermission() {
		return new Permission("hub.suspend");
	}

}
