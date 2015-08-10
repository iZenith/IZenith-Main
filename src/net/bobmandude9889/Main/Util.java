package net.bobmandude9889.Main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.intellectualcrafters.plot.api.PlotAPI;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

@SuppressWarnings("deprecation")
public class Util {

	static PlotAPI plotAPI = null;

	public static PlotAPI getPlotAPI() {
		if (plotAPI == null) {
			plotAPI = new PlotAPI(getMain());
		}
		return plotAPI;
	}

	public static void line(Location l1, Location l2, Material material) {
		double xSlope = (l1.getBlockX() - l2.getBlockX());
		double ySlope = (l1.getBlockY() - l2.getBlockY()) / xSlope;
		double zSlope = (l1.getBlockZ() - l2.getBlockZ()) / xSlope;
		double y = l1.getBlockY();
		double z = l1.getBlockZ();
		double interval = 1 / (Math.abs(ySlope) > Math.abs(zSlope) ? ySlope : zSlope);
		for (double x = l1.getBlockX(); x - l1.getBlockX() < Math
				.abs(xSlope); x += interval, y += ySlope, z += zSlope) {
			new Location(l1.getWorld(), x, y, z).getBlock().setType(material);
		}
	}

	public static String parseColors(String message) {
		String chars = "abcdefrlomn1234567890";

		for (Character c : chars.toCharArray()) {
			for (int i = 0; i < message.length() - 1; i++) {
				if (message.substring(i, i + 2).equals("&" + c.toString())) {
					String replace = ChatColor.getByChar(c) + "";
					try{
					replace = message.substring(i - 1, i).equals("&") ? "&" + c.toString()
							: ChatColor.getByChar(c) + "";
					}catch(IndexOutOfBoundsException e){}
					message = message.substring(0, i) + replace + message.substring(i + 2, message.length());
					i++;
				}
			}
		}
		return message;
	}

	public static String getItemName(ItemStack is) {
		if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
			return is.getItemMeta().getDisplayName();
		}
		return null;
	}

	public static Permission getPermissions() {
		Permission permission = null;
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return permission;
	}

	public static boolean isIn(Location loc1, Location loc2, Location loc3) {
		int n = 0;
		if (loc2.getBlockX() > loc3.getBlockX() && loc1.getBlockX() >= loc3.getBlockX()
				&& loc1.getBlockX() <= loc2.getBlockX()) {
			n++;
		} else if (loc1.getBlockX() >= loc2.getBlockX() && loc1.getBlockX() <= loc3.getBlockX()) {
			n++;
		}
		if (loc2.getBlockY() > loc3.getBlockY() && loc1.getBlockY() >= loc3.getBlockY()
				&& loc1.getBlockY() <= loc2.getBlockY()) {
			n++;
		} else if (loc1.getBlockY() >= loc2.getBlockY() && loc1.getBlockY() <= loc3.getBlockY()) {
			n++;
		}
		if (loc2.getBlockZ() > loc3.getBlockZ() && loc1.getBlockZ() >= loc3.getBlockZ()
				&& loc1.getBlockZ() <= loc2.getBlockZ()) {
			n++;
		} else if (loc1.getBlockZ() >= loc2.getBlockZ() && loc1.getBlockZ() <= loc3.getBlockZ()) {
			n++;
		}
		if (n == 3) {
			return true;
		} else {
			return false;
		}
	}

	public static ItemStack newItemMeta(Material material, String name, String lore, int i) {
		name = parseColors(name);
		ItemStack is = new ItemStack(material, i);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		if (lore != null) {
			String[] loreA = lore.split(",");
			List<String> loreL = new ArrayList<String>();
			for (String l : loreA) {
				l = parseColors(l);
				loreL.add(l);
			}
			im.setLore(loreL);
		}
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack newItemMeta(Material material, String name, String lore, int i, short durability) {
		ItemStack is = newItemMeta(material, name, lore, i);
		is.setDurability(durability);
		return is;
	}

	public static void initScoreboard() {
		try {
			Vars.scoreboard.registerNewTeam("Visitor").setPrefix(parseColors("&7"));
			Vars.scoreboard.registerNewTeam("Member").setPrefix(parseColors("&a"));
			Vars.scoreboard.registerNewTeam("MemberP").setPrefix(parseColors("&a"));
			Vars.scoreboard.registerNewTeam("Trusted").setPrefix(parseColors("&b"));
			Vars.scoreboard.registerNewTeam("Mod").setPrefix(parseColors("&9"));
			Vars.scoreboard.registerNewTeam("Admin").setPrefix(parseColors("&c"));
			Vars.scoreboard.registerNewTeam("Owner").setPrefix(parseColors("&4"));
		} catch (Exception e) {
		}
		for (Team t : Vars.scoreboard.getTeams()) {
			Vars.teams.add(t);
		}
	}

	public static void setTeam(Player player) {
		for (Team t : Vars.teams) {
			if (t.getName().equalsIgnoreCase(getPermissions().getPrimaryGroup(player))) {
				t.addPlayer(player);
				return;
			}
		}
	}

	public static String getColoredName(Player player) {
		ChatColor color = getGroupColor(getPermissions().getPrimaryGroup(player));
		String name = player.getName();
		int length = name.length();
		if (length > 14) {
			int subtract = length - 14;
			name = name.substring(0, length - subtract - 1);
		}
		return color + name;
	}

	public static ChatColor getGroupColor(String group) {
		ChatColor color = ChatColor.WHITE;
		for (String s : Bukkit.getPluginManager().getPlugin("IZenith").getConfig().getStringList("colors")) {
			String[] sp = s.split(",");
			if (group.equalsIgnoreCase(sp[0])) {
				color = ChatColor.getByChar(sp[1].toCharArray()[0]);
				break;
			}
		}
		return color;
	}

	public static Main getMain() {
		return (Main) Bukkit.getPluginManager().getPlugin("IZenith");
	}

	public static Server getServer() {
		return getMain().getServer();
	}

	public static FileConfiguration getConfig() {
		return getMain().getConfig();
	}

	public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
		// get the main content part, this doesn't return the armor
		String content = itemStackArrayToBase64(playerInventory.getContents());
		String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

		return new String[] { content, armor };
	}

	public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(items.length);

			// Save every element in the list
			for (int i = 0; i < items.length; i++) {
				dataOutput.writeObject(items[i]);
			}

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack[] items = new ItemStack[dataInput.readInt()];

			// Read the serialized inventory
			for (int i = 0; i < items.length; i++) {
				items[i] = (ItemStack) dataInput.readObject();
			}

			dataInput.close();
			return items;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static void getKit(Player player, String name) {
		name = name.toLowerCase();
		getMain().reloadConfig();
		List<String> kit = getConfig().getStringList("kits." + player.getUniqueId() + "." + name);
		if (kit.size() == 0)
			kit = getConfig().getStringList("kits.global." + name);
		if (kit != null) {
			try {
				ItemStack[] contents = itemStackArrayFromBase64(kit.get(0));
				ItemStack[] armor = itemStackArrayFromBase64(kit.get(1));
				player.getInventory().setContents(contents);
				player.getInventory().setArmorContents(armor);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setKit(Player player, String name) {
		name = name.toLowerCase();
		String[] arrayKit = playerInventoryToBase64(player.getInventory());
		List<String> kit = new ArrayList<String>();
		kit.add(arrayKit[0]);
		kit.add(arrayKit[1]);
		getConfig().set("kits." + player.getUniqueId() + "." + name, kit);
		getMain().saveConfig();
	}

	public static void setGlobalKit(Player player, String name) {
		name = name.toLowerCase();
		String[] arrayKit = playerInventoryToBase64(player.getInventory());
		List<String> kit = new ArrayList<String>();
		kit.add(arrayKit[0]);
		kit.add(arrayKit[1]);
		getConfig().set("kits.global." + name, kit);
		getMain().saveConfig();
	}

	public static void removeKit(Player player, String name) {
		name = name.toLowerCase();
		getConfig().set("kits." + player.getUniqueId() + "." + name, null);
		getMain().saveConfig();
	}

	public static void removeGlobalKit(String name) {
		name = name.toLowerCase();
		getConfig().set("kits.global." + name, null);
		getMain().saveConfig();
	}

	public static boolean hasJoined(Player player) {
		getMain().reloadConfig();
		List<String> players = getConfig().getStringList("players");
		if (!getConfig().contains("players"))
			return false;
		return players.contains(player.getUniqueId().toString());
	}

	public static void setJoined(Player player) {
		List<String> players = getConfig().getStringList("players");
		if (players == null)
			players = new ArrayList<String>();
		players.add(player.getUniqueId().toString());
		getConfig().set("players", players);
		getMain().saveConfig();
	}

	public static boolean containsIgnoreCase(String message, String regex) {
		return message.toLowerCase().contains(regex.toLowerCase());
	}

	public static boolean startsWithIgnoreCase(String message, String regex) {
		return message.toLowerCase().startsWith(regex.toLowerCase());
	}

	public static Location getLocation(com.intellectualcrafters.plot.object.Location location){
		return new Location(Bukkit.getWorld(location.getWorld()),location.getX(),location.getY(),location.getZ());
	}
	
	public static void suspend(Player player){
		getConfig().set("suspended." + player.getUniqueId().toString(), getPermissions().getPlayerGroups(player)[0]);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set suspended");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add -*");
		player.sendMessage(ChatColor.RED + "You are now suspended and no longer have any permissions");
		Util.getMain().saveConfig();
	}
	
	public static void removeSuspend(Player player){
		Util.getMain().reloadConfig();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set " + getConfig().getString("suspended." + player.getUniqueId().toString()));
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " remove -*");
		getConfig().set("suspended." + player.getUniqueId().toString(), null);
		player.sendMessage(ChatColor.GREEN + "You are no longer suspended!");
		Util.getMain().saveConfig();
	}
	
}
