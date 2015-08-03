package net.bobmandude9889.Commands;

import net.bobmandude9889.Main.Vars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class Plots implements HubCommand {

	@Override
	public String getName() {
		return "plots";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Vars.main.getServer().dispatchCommand(sender, "warp plots");
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
