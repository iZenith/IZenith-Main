package net.bobmandude9889.Commands;

import java.util.ArrayList;
import java.util.List;

import net.bobmandude9889.Main.Vars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Kit implements HubCommand {

	@Override
	public String getName() {
		return "kit";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		FileConfiguration config = Vars.main.getConfig();
		Player player = (Player) sender;
		if(args[0].equalsIgnoreCase("create")){
			@SuppressWarnings("unused")
			List<String> kits;
			if(config.contains("kits." + player.getName())){
				kits = config.getStringList("kits." + player.getName());
			} else {
				kits = new ArrayList<String>();
			}
			
			return;
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
