package net.bobmandude9889.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

import net.bobmandude9889.CommandSpy.CommandFilter;
import net.bobmandude9889.Main.Main;
import net.bobmandude9889.Main.Util;
import net.bobmandude9889.Main.Vars;

public class CommandSpy implements HubCommand, Listener {

	public CommandSpy(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@Override
	public String getName() {
		return "commandspy";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "commandpsy", "cs" };
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try{
			switch (args[0]) {
			case "off":
				Vars.commandSpy.remove((Player) sender);
				Vars.main.getConfig().set("commandspy.players." + ((Player) sender).getUniqueId(), null);
				Vars.main.saveConfig();
				sender.sendMessage(ChatColor.GREEN + "CommandSpy turrned off!");
				break;
			case "create":
				List<String> list = new ArrayList<String>();
				if (args.length >= 3) {
					List<String> temp = Vars.main.getConfig().getStringList("commandspy.filters." + args[2]);
					if (temp != null)
						list = temp;
				}
				Vars.main.getConfig().set("commandspy.filters." + args[1], list);
				Vars.main.saveConfig();
				sender.sendMessage(ChatColor.GREEN + "Created filter " + args[1]);
				break;
			case "list":
				Set<String> filters = Util.getConfig().getConfigurationSection("commandspy.filters").getKeys(false);
				sender.sendMessage(ChatColor.BLUE + "Filters:");
				for(String filter : filters){
					sender.sendMessage(ChatColor.GREEN + filter);
				}
				break;
			default:
				if (Vars.main.getConfig().getStringList("commandspy.filters." + args[0]) != null) {
					if (args.length > 1) {
						switch (args[1]) {
						case "add":
							String[] a = { "r", "p", "c", "a" };
							String s = args[2].substring(0, 1);
							boolean b = false;
							for (String str : a) {
								if (str.equals(s)) {
									b = true;
								}
							}
							if (!b) {
								help((Player) sender);
								return;
							}
							List<String> accept = Vars.main.getConfig().getStringList("commandspy.filters." + args[0]);
							accept.add(s + " " + args[3]);
							Vars.main.getConfig().set("commandspy.filter." + args[0], accept);
							Vars.main.saveConfig();
							Vars.commandSpy.clear();
							loadFilters();
							sender.sendMessage(ChatColor.GREEN + "Added to filter!");
							break;
						case "remove":
							String[] a1 = { "r", "p", "c", "a" };
							String s1 = args[2].substring(0, 1);
							boolean b1 = false;
							for (String str : a1) {
								if (str.equals(s1)) {
									b = true;
								}
							}
							if (!b1) {
								help((Player) sender);
								return;
							}
							List<String> accept1 = Vars.main.getConfig().getStringList("commandspy.filters." + args[0]);
							accept1.remove(s1 + " " + args[3]);
							Vars.main.getConfig().set("commandspy.filter." + args[0], accept1);
							Vars.main.saveConfig();
							Vars.commandSpy.clear();
							loadFilters();
							sender.sendMessage(ChatColor.GREEN + "Removed from filter!");
							break;
						case "list":
							List<String> accept2 = Vars.main.getConfig().getStringList("commandspy.filters." + args[0]);
							for (String str : accept2) {
								String pref = ChatColor.GREEN + "";
								switch (str.substring(0, 1)) {
								case "r":
									pref += "Rank " + ChatColor.BLACK + ": ";
									break;
								case "p":
									pref += "Player " + ChatColor.BLACK + ": ";
									break;
								case "c":
									pref += "Command " + ChatColor.BLACK + ": ";
									break;
								case "a":
									pref += "Arugment " + ChatColor.BLACK + ": ";
									break;
								}
								sender.sendMessage(pref + ChatColor.WHITE + str.substring(1, str.length()));
							}
							break;
						}
					} else {
						List<String> accept = Vars.main.getConfig().getStringList("commandspy.filters." + args[0]);
						if (Vars.commandSpy.containsKey((Player) sender)) {
							Vars.commandSpy.remove((Player) sender);
						}
						Vars.commandSpy.put((Player) sender, new CommandFilter(accept));
						Vars.main.getConfig().set("commandspy.players." + ((Player) sender).getUniqueId() + ".filter", args[0]);
						Vars.main.saveConfig();
						sender.sendMessage(ChatColor.GREEN + "Switched filter to " + args[0]);
					}
				} else {
					help(sender);
				}
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			help(sender);
			return;
		}
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
		return new Permission("commandspy.use");
	}

	public String[] subArray(String[] array, int start, int end) {
		String[] ret = new String[array.length - (end - start)];
		for (int i = start; i < end; i++) {
			int n = i - start;
			ret[n] = array[i];
		}
		return ret;
	}

	public void help(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "Commands:");
		sender.sendMessage(ChatColor.BLUE + "/commandspy all");
		sender.sendMessage(ChatColor.BLUE + "/commandspy off");
		sender.sendMessage(ChatColor.BLUE + "/commandspy default");
		sender.sendMessage(ChatColor.BLUE + "/commandspy <filter>");
		sender.sendMessage(ChatColor.BLUE + "/commandspy <filter> add rank|player|command|argument <value>");
		sender.sendMessage(ChatColor.BLUE + "/commandspy <filter> remove rank|player|command|argument <value>");
		sender.sendMessage(ChatColor.BLUE + "/commandspy <filter> list");
		sender.sendMessage(ChatColor.BLUE + "/commandspy create <name> [template filter]");
		sender.sendMessage(ChatColor.BLUE + "/commandspy list");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		setupFilter(e.getPlayer());
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		for (Player p : Vars.main.getServer().getOnlinePlayers()) {
			CommandFilter filter = Vars.commandSpy.get(p);
			if (filter != null) {
				if (filter.canPass(e)) {
					p.sendMessage(ChatColor.RED + e.getPlayer().getName() + ChatColor.GRAY + " performed the command: " + ChatColor.GREEN + e.getMessage());
				}
			}
		}
	}

	public static void setupFilter(Player player) {
		Main main = Vars.main;
		FileConfiguration config = main.getConfig();
		Object o = config.get("commandspy.players." + player.getUniqueId());
		if (o != null) {
			String filterName = config.getString("commandspy.players." + player.getUniqueId() + ".filter");
			List<String> list = config.getStringList("commandspy.filters." + filterName);
			if (list != null) {
				Vars.commandSpy.put(player, new CommandFilter(list));
			}
		}
	}

	public static void loadFilters() {
		for (Player p : Vars.main.getServer().getOnlinePlayers()) {
			CommandSpy.setupFilter(p);
		}
	}

}
