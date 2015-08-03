package net.bobmandude9889.Commands;

import net.bobmandude9889.Main.Vars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class Lel implements HubCommand {

	@Override
	public String getName() {
		return "lel";
	}

	@Override
	public String[] getAliases() {
		return new String[] {"lawl"};
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Vars.main.getServer().dispatchCommand(sender, "lol");
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
