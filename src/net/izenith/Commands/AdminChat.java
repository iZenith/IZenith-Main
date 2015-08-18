package net.izenith.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import net.izenith.Main.Util;
import net.izenith.Main.Vars;
import net.md_5.bungee.api.ChatColor;

public class AdminChat implements HubCommand {

	@Override
	public String getName() {
		return "adminchat";
	}

	@Override
	public String[] getAliases() {
		return new String[] {"ac"};
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length > 0 && args.length < 2){
			if(args[0].equalsIgnoreCase("toggle")){
				if(Vars.adminChat.contains((Player)sender)){
					Vars.adminChat.remove((Player) sender);
					sender.sendMessage(ChatColor.GREEN + "Admin Chat disabled!");
				}else{
					Vars.adminChat.add((Player) sender);
					sender.sendMessage(ChatColor.GREEN + "Admin Chat enabled!");
				}
				return;
			}
		}
		String message = "";
		for(String arg : args){
			message+=arg + " ";
		}
		Util.sendAdminMessage(message, ((Player)sender));
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}

	@Override
	public boolean hasPermission() {
		return true;
	}

	@Override
	public Permission getPermission() {
		return new Permission("izenith.adminchat");
	}

}
