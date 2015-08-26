package net.izenith.Commands;

import java.io.File;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import net.izenith.Main.IPlayer;
import net.izenith.Main.IPlayerHandler;
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
		Long time = null;
		String name = null;
		if(args.length > 0){
			Player player = Bukkit.getPlayer(args[0]);
			if(player == null){
				for(File file : new File(Util.getMain().getDataFolder().getPath() + System.getProperty("file.separator") + "players").listFiles()){
					YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
					if(config.getString("last_name").toLowerCase().equals(args[0].toLowerCase())){
						time = config.getLong("time");
						name = args[0];
						break;
					}
				}
			} else {
				IPlayer iPlayer = IPlayerHandler.getPlayer(player);
				time = iPlayer.getOnlineTime();
				name = player.getName();
			}
		} else {
			IPlayer iPlayer = new IPlayer((Player)sender);
			System.out.println(iPlayer);
			time = iPlayer.getOnlineTime();
			name = sender.getName();
		}
		if(time == null){
			sender.sendMessage(ChatColor.RED + "Player not found.");
			return;
		}
		Double timeHours = new Double(time)/(1000*60*60);
		DecimalFormat df = new DecimalFormat("#.##");
		String shortTime = df.format(timeHours);
		sender.sendMessage(ChatColor.BLUE + (name + " has played for " + ChatColor.GREEN + shortTime + " hours"));
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
	