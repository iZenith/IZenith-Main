package net.bobmandude9889.Commands;

import java.util.ArrayList;
import java.util.List;

import net.bobmandude9889.Main.Vars;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class Report implements HubCommand {

	@Override
	public String getName() {
		return "report";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			if (sender.hasPermission("report.admin")) {
				if (args[0].equalsIgnoreCase("list")) {
					List<String> list = Vars.main.getConfig().getStringList("reports");
					sender.sendMessage(ChatColor.GREEN + "Reports:");
					for (int i = 0; i < list.size(); i++) {
						sender.sendMessage(ChatColor.GRAY + "[" + i + "]" + ChatColor.AQUA + list.get(i));
					}
					return;
				} else if (args[0].equalsIgnoreCase("remove")) {
					int i = Integer.parseInt(args[1]);
					List<String> list = Vars.main.getConfig().getStringList("reports");
					if(list == null) list = new ArrayList<String>();
					list.remove(i);
					Vars.main.getConfig().set("reports", list);
					Vars.main.saveConfig();
					sender.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.GRAY + i);
					return;
				}
			}
			String message = "";
			for (String s : args) {
				message += s + " ";
			}
			List<String> list = Vars.main.getConfig().getStringList("reports");
			if(list == null) list = new ArrayList<String>();
			list.add(message);
			Vars.main.getConfig().set("reports", list);
			Vars.main.saveConfig();
			sender.sendMessage(ChatColor.GREEN + "Reported: " + ChatColor.RED + message);
		} catch (ArrayIndexOutOfBoundsException e) {
			sender.sendMessage(ChatColor.RED + "Invalid arguments");
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
