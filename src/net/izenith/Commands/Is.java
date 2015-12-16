package net.izenith.Commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class Is implements HubCommand {

	@Override
	public String getName() {
		return "is";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String commandLabel,
			String[] args) {
		if (args.length > 0) {
			int randInt = new Random().nextInt(3);
			switch (randInt) {
			case 0:
				s.sendMessage(ChatColor.GREEN + "Yes." + args);
				break;
			case 1:
				s.sendMessage(ChatColor.YELLOW + "Maybe.");
				break;
			case 2:
				s.sendMessage(ChatColor.RED + "Nu.");
				break;
			}
		}else{
			s.sendMessage(ChatColor.RED + "Add the question...");
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
