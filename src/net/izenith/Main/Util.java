package net.izenith.Main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.earth2me.essentials.Essentials;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.commands.MainCommand;
import com.intellectualcrafters.plot.commands.RequiredType;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.plotsquared.bukkit.object.BukkitPlayer;
import com.plotsquared.general.commands.Command;

import net.izenith.Commands.AdminChat;
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

	public static Essentials ess;

	public static void LoadEssentials() {
		ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	}

	public static void CreatePlotSkull(Plot plot, String owner, com.intellectualcrafters.plot.object.Location l1, com.intellectualcrafters.plot.object.Location l2, com.intellectualcrafters.plot.object.Location l3, com.intellectualcrafters.plot.object.Location l4) {
		Block b1 = Util.getLocation(l1).getBlock();
		b1.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull1 = ((Skull) b1.getState());
		skull1.setSkullType(SkullType.PLAYER);
		skull1.setOwner(owner);
		skull1.setRotation(BlockFace.SOUTH_EAST);
		skull1.update(true);
		Block slabs1 = Util.getLocation(l1).subtract(0, 1, 0).getBlock();
		slabs1.setTypeIdAndData(43, (byte) 8, true);

		Block b2 = Util.getLocation(l2).getBlock();
		b2.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull2 = (Skull) b2.getState();
		skull2.setSkullType(SkullType.PLAYER);
		skull2.setOwner(owner);
		skull2.setRotation(BlockFace.SOUTH_WEST);
		skull2.update(true);
		Block slabs2 = Util.getLocation(l2).subtract(0, 1, 0).getBlock();
		slabs2.setTypeIdAndData(43, (byte) 8, true);

		Block b3 = Util.getLocation(l3).getBlock();
		b3.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull3 = ((Skull) b3.getState());
		skull3.setSkullType(SkullType.PLAYER);
		skull3.setOwner(owner);
		skull3.setRotation(BlockFace.NORTH_WEST);
		skull3.update(true);
		Block slabs3 = Util.getLocation(l3).subtract(0, 1, 0).getBlock();
		slabs3.setTypeIdAndData(43, (byte) 8, true);

		Block b4 = Util.getLocation(l4).getBlock();
		b4.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull4 = ((Skull) b4.getState());
		skull4.setSkullType(SkullType.PLAYER);
		skull4.setOwner(owner);
		skull4.setRotation(BlockFace.NORTH_EAST);
		skull4.update(true);
		Block slabs4 = Util.getLocation(l4).subtract(0, 1, 0).getBlock();
		slabs4.setTypeIdAndData(43, (byte) 8, true);
	}

	public static void RegisterPlotCommands() {
		Command<PlotPlayer> cmd = new Command<PlotPlayer>("middle", "/p middle", "Teleport you to the center of the plot", "plot.middle", new String[] { "center", "m" }, RequiredType.PLAYER) {

			@Override
			public boolean onCommand(PlotPlayer p, String[] args) {
				Plot plot = p.getCurrentPlot();
				if (plot == null) {
					p.sendMessage(Util.parseColors("&7&l[&6&liZenith&f&lPlots&7&l] &6You are not in a plot."));
					return false;
				}
				com.intellectualcrafters.plot.object.Location b = plot.getCorners()[0];
				com.intellectualcrafters.plot.object.Location t = plot.getCorners()[1];

				System.out.println(b + ", " + t);
				double midX = b.getX() + t.getX();
				midX = midX / 2;
				double midZ = b.getZ() + t.getZ();
				midZ = midZ / 2;
				com.intellectualcrafters.plot.object.Location midLoc = null;
				boolean isOnLand = false;
				for (int y = 256; y >= 0; y--) {
					midLoc = new com.intellectualcrafters.plot.object.Location(p.getLocation().getWorld(), (int) midX, y, (int) midZ);
					if (((Location) midLoc.toBukkitLocation()).getBlock().getType() != Material.AIR) {
						isOnLand = true;
						midLoc.add(0, 1, 0);
						break;
					}
				}
				BukkitPlayer bP = (BukkitPlayer) p;
				if (!isOnLand) {
					bP.setFlight(true);
				}
				p.sendMessage(Util.parseColors("&7&l[&6&liZenith&f&lPlots&7&l] &6You have been teleported to the center of the plot."));
				p.teleport(midLoc);
				return true;
			}
		};
		MainCommand.getInstance().addCommand(cmd);
	}

	public static void line(Location l1, Location l2, Material material) {
		double xSlope = (l1.getBlockX() - l2.getBlockX());
		double ySlope = (l1.getBlockY() - l2.getBlockY()) / xSlope;
		double zSlope = (l1.getBlockZ() - l2.getBlockZ()) / xSlope;
		double y = l1.getBlockY();
		double z = l1.getBlockZ();
		double interval = 1 / (Math.abs(ySlope) > Math.abs(zSlope) ? ySlope : zSlope);
		for (double x = l1.getBlockX(); x - l1.getBlockX() < Math.abs(xSlope); x += interval, y += ySlope, z += zSlope) {
			new Location(l1.getWorld(), x, y, z).getBlock().setType(material);
		}
	}

	public static String parseColors(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static boolean contains(char[] array, Character character) {
		for (Character c : array) {
			if (c == character)
				return true;
		}
		return false;
	}

	public static String getItemName(ItemStack is) {
		if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
			return is.getItemMeta().getDisplayName();
		}
		return null;
	}

	public static Permission getPermissions() {
		Permission permission = null;
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return permission;
	}

	public static boolean isIn(Location loc1, Location loc2, Location loc3) {
		int n = 0;
		if (loc2.getBlockX() > loc3.getBlockX() && loc1.getBlockX() >= loc3.getBlockX() && loc1.getBlockX() <= loc2.getBlockX()) {
			n++;
		} else if (loc1.getBlockX() >= loc2.getBlockX() && loc1.getBlockX() <= loc3.getBlockX()) {
			n++;
		}
		if (loc2.getBlockY() > loc3.getBlockY() && loc1.getBlockY() >= loc3.getBlockY() && loc1.getBlockY() <= loc2.getBlockY()) {
			n++;
		} else if (loc1.getBlockY() >= loc2.getBlockY() && loc1.getBlockY() <= loc3.getBlockY()) {
			n++;
		}
		if (loc2.getBlockZ() > loc3.getBlockZ() && loc1.getBlockZ() >= loc3.getBlockZ() && loc1.getBlockZ() <= loc2.getBlockZ()) {
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

	public static ItemStack newItemMeta(Material material, String name, String lore, int amount) {
		name = parseColors(name);
		ItemStack is = new ItemStack(material, amount);
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

	public static ItemStack newItemMeta(Material material, String name, String lore, int amount, short durability) {
		ItemStack is = newItemMeta(material, name, lore, amount);
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
			Vars.scoreboard.registerNewTeam("Developer").setPrefix(parseColors("&e"));
			Vars.scoreboard.registerNewTeam("Admin").setPrefix(parseColors("&c"));
			Vars.scoreboard.registerNewTeam("Owner").setPrefix(parseColors("&4"));
		} catch (Exception e) {
		}
		for (Team t : Vars.scoreboard.getTeams()) {
			Vars.teams.add(t);
		}
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
		return (Main) Vars.main;
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

	public static void setGlobalKit(Player player, String name) {
		name = name.toLowerCase();
		String[] arrayKit = playerInventoryToBase64(player.getInventory());
		List<String> kit = new ArrayList<String>();
		kit.add(arrayKit[0]);
		kit.add(arrayKit[1]);
		getConfig().set("kits.global." + name, kit);
		getMain().saveConfig();
	}

	public static void removeGlobalKit(String name) {
		name = name.toLowerCase();
		getConfig().set("kits.global." + name, null);
		getMain().saveConfig();
	}

	public static boolean hasJoined(Player player) {
		File dataFolder = new File(Util.getMain().getDataFolder().getPath() + System.getProperty("file.separator") + "players");
		return new File(dataFolder.getPath() + System.getProperty("file.separator") + player.getUniqueId() + ".yml").exists();
	}

	public static boolean containsIgnoreCase(String message, String regex) {
		return message.toLowerCase().contains(regex.toLowerCase());
	}

	public static boolean startsWithIgnoreCase(String message, String regex) {
		return message.toLowerCase().startsWith(regex.toLowerCase());
	}

	public static Location getLocation(com.intellectualcrafters.plot.object.Location location) {
		return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
	}

	public static void suspend(Player player) {
		getConfig().set("suspended." + player.getUniqueId().toString(), getPermissions().getPlayerGroups(player)[0]);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set suspended");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add -*");
		player.sendMessage(ChatColor.RED + "You are now suspended and no longer have any permissions");
		Util.getMain().saveConfig();
	}

	public static void removeSuspend(Player player) {
		Util.getMain().reloadConfig();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group set " + getConfig().getString("suspended." + player.getUniqueId().toString()));
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " remove -*");
		getConfig().set("suspended." + player.getUniqueId().toString(), null);
		player.sendMessage(ChatColor.GREEN + "You are no longer suspended!");
		Util.getMain().saveConfig();
	}

	public static void setAllOnlineTimes() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			IPlayerHandler.getPlayer(player).setOnlineTime();
		}
	}

	public static void loadOnlineTime(Player player) {
		Vars.times.put(player, System.currentTimeMillis());
	}

	public static void loadAllOnlineTimes() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			loadOnlineTime(player);
		}
	}

	public static void updatePlayerList() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			IPlayer iPlayer = IPlayerHandler.getPlayer(player);
			iPlayer.setTeam();
			//iPlayer.sendTabFootHeader();
		}
	}

	public static void sendAdminMessage(String message, Player sender) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(new AdminChat().getPermission())) {
				player.sendMessage(ChatColor.WHITE + "Admin" + ChatColor.GOLD + "Chat " + sender.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.RED + message);
			}
		}
	}

	public static FileConfiguration getOfflinePlayerFile(String name) {
		for (File file : new File(Util.getMain().getDataFolder().getPath() + System.getProperty("file.separator") + "players").listFiles()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			return config;
		}
		return null;
	}

	/*
	 * public static void convertFileSystem(){ for(String uuid :
	 * getConfig().getStringList("players")){ File dataFolder = new
	 * File(Util.getMain().getDataFolder().getPath() +
	 * System.getProperty("file.separator") + "players"); if
	 * (!dataFolder.exists()) dataFolder.mkdir(); File file = new
	 * File(dataFolder.getPath() + System.getProperty("file.separator") + uuid +
	 * ".yml"); try { file.createNewFile(); } catch (IOException e) {
	 * e.printStackTrace(); } YamlConfiguration config =
	 * YamlConfiguration.loadConfiguration(file); config.set("last_name",
	 * "unknown"); config.set("commandspy",
	 * getConfig().getString("commandspy.players." + uuid + ".filter"));
	 * config.set("kits", getConfig().get("kits." + uuid)); config.set("time",
	 * getConfig().getLong("times." + uuid)); try { config.save(file); } catch
	 * (IOException e) { e.printStackTrace(); } } }
	 */

	public static String buildString(Collection<String> args, String seperator, int startingArg, int maxLength) {
		List<String> retList = new ArrayList<String>();
		String ret = "";
		for (int i = startingArg; i < args.size(); i++) {
			String s = (String) args.toArray()[i];
			ret += s + seperator;
			if (ret.length() > maxLength) {
				retList.add(ret);
				ret = "";
			}
		}
		retList.add(ret);
		ret = "";
		for (String s : retList) {
			ret += s + "\n";
		}
		if (ret.length() > 2) {
			ret = ret.substring(0, ((ret.length() - 2) - seperator.length()) + 1);
		}
		return ret;
	}

	public static boolean isVerified(String key) {
		return getConfig().getStringList("console_keys").contains(key);
	}

	public static void addKey(String key) {
		List<String> keys = getConfig().getStringList("console_keys");
		if (keys == null)
			keys = new ArrayList<String>();
		keys.add(key);
		getConfig().set("console_keys", keys);
		getMain().saveConfig();
	}

	public static void removeKey(String key) {
		List<String> keys = getConfig().getStringList("console_keys");
		if (keys == null)
			return;
		keys.remove(key);
		getConfig().set("console_keys", keys);
		getMain().saveConfig();
	}

	public static String addRandomKey() {
		Random gen = new Random();
		int max = (int) ('z');
		int min = (int) ('a');
		String key = "";
		for (int i = 0; i < 15; i++) {
			int code = gen.nextInt(max - min);
			key += (char) (code + min);
		}
		addKey(key);
		return key;
	}

	public static boolean isURL(String s) {
		try {
			new URL(s);
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}

	public static boolean isPlayerName(String s) {
		return Bukkit.getPlayer(s) != null;
	}

	/*
	 * public static ConnectionServer openRemoteConsoleServer(){ PacketHandler
	 * handler = new PacketHandler(); handler.addListener("key", new
	 * PacketListener(){
	 * 
	 * @Override public void packetReceived(Packet packet, Connection
	 * connection) {
	 * 
	 * } }); ConnectionServer server = null; try { server = new
	 * ConnectionServer(25566,handler); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

}
