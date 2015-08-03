package net.bobmandude9889.Commands;

import net.bobmandude9889.Main.Vars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class Stap implements HubCommand{

	@Override
	public String getName() {
		return "stap";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Vars.main.getServer().dispatchCommand(sender, "stop Server is staping!");
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
		return new Permission("stap");
	}

}
