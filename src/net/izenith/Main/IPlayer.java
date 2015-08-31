package net.izenith.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

public class IPlayer {

	public Player player;
	public YamlConfiguration config;
	private File file;

	public IPlayer(Player player) {
		this.player = player;
		File dataFolder = new File(Util.getMain().getDataFolder().getPath(),"players");
		if (!dataFolder.exists())
			dataFolder.mkdir();
		this.file = new File(dataFolder.getPath(),player.getUniqueId() + ".yml");
		this.config = YamlConfiguration.loadConfiguration(file);
	}

	public void createFile() {
		try {
			file.createNewFile();
			config.set("last_name", player.getName());
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOnlineTime() {
		try {
			config.set("time", getOnlineTime());
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getOnlineTime() {
		Long time = config.getLong("time");
		time = time == null ? 0 : time;
		return time + (System.currentTimeMillis() - Vars.times.get(player));
	}

	public void removeKit(String name) {
		name = name.toLowerCase();
		config.set("kits." + name, null);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setKit(String name) {
		name = name.toLowerCase();
		String[] arrayKit = Util.playerInventoryToBase64(player.getInventory());
		List<String> kit = new ArrayList<String>();
		kit.add(arrayKit[0]);
		kit.add(arrayKit[1]);
		config.set("kits." + name, kit);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getKit(String name, boolean message) {
		name = name.toLowerCase();
		List<String> kit = config.getStringList("kits." + name);
		if (kit.size() == 0)
			kit = Util.getConfig().getStringList("kits.global." + name);
		if (kit != null) {
			try {
				ItemStack[] contents = Util.itemStackArrayFromBase64(kit.get(0));
				ItemStack[] armor = Util.itemStackArrayFromBase64(kit.get(1));
				player.getInventory().setContents(contents);
				player.getInventory().setArmorContents(armor);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(message)
			player.sendMessage(ChatColor.GREEN + "You have been given the kit " + ChatColor.BLUE + name);
	}
	
	public String getColoredName() {
		ChatColor color = Util.getGroupColor(PermissionHandler.getGroupName(player));
		String name = player.getName();
		int length = name.length();
		if (length > 14) {
			int subtract = length - 14;
			name = name.substring(0, length - subtract - 1);
		}
		return color + name;
	}
	
	@SuppressWarnings("deprecation")
	public void setTeam() {
		for (Team t : Vars.teams) {
			if (t.getName().equalsIgnoreCase(PermissionHandler.getGroupName(player))) {
				t.addPlayer(player);
				return;
			}
		}
	}
	
	public void setCommandSpy(String filter){
		config.set("commandspy", filter);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				System.out.println(scanner.nextLine());
			}
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCommandSpy(){
		return config.getString("commandspy");
	}
	
	public void setLastName(String name){
		config.set("last_name", name);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
