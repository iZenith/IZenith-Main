package net.izenith.Main;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import net.bobmandude9889.GUI.GUI;
import net.bobmandude9889.GUI.GUIHandler;
import net.izenith.Chat.ChatHandler;
import net.izenith.CommandSpy.CommandFilter;
import net.izenith.CommandSpy.CommandListener;
import net.izenith.Commands.AddPlot;
import net.izenith.Commands.AdminChat;
import net.izenith.Commands.ClearChat;
import net.izenith.Commands.CommandSpy;
import net.izenith.Commands.Donated;
import net.izenith.Commands.GKit;
import net.izenith.Commands.GetConsoleKey;
import net.izenith.Commands.HubCommand;
import net.izenith.Commands.Kit;
import net.izenith.Commands.Lel;
import net.izenith.Commands.PlayTime;
import net.izenith.Commands.Ranks;
import net.izenith.Commands.RefreshHeads;
import net.izenith.Commands.Rename;
import net.izenith.Commands.Report;
import net.izenith.Commands.ServerIp;
import net.izenith.Commands.Suspend;
import net.izenith.Commands.Translate;
import net.izenith.Commands.Trusted;
import net.izenith.Commands.UpdateList;
import net.izenith.Commands.WorldEditPerms;
import net.izenith.Events.BlockPlaceListener;
import net.izenith.Events.BowListener;
import net.izenith.Events.DamageListener;
import net.izenith.Events.InteractListener;
import net.izenith.Events.InventoryClickListener;
import net.izenith.Events.ItemDropListener;
import net.izenith.Events.PlayerLogListener;
import net.izenith.Events.PlayerMoveListener;
import net.izenith.Events.ServerListHandler;
import net.izenith.Gamemode.TeleportListener;
import net.izenith.PlotEditor.PlotListener;
import net.izenith.PlotEditor.SetFloor;

public class Vars {

	public static HubCommand[] commands;
	public static HashMap<Player, CommandFilter> commandSpy;
	public static HashMap<Player, String> createClock;
	public static List<String> clocks;
	public static Main main;
	public static ScoreboardManager manager;
	public static Scoreboard scoreboard;
	public static List<Team> teams;
	public static GUIHandler guiHandler;
	public static GUI tpGUI;
	public static List<Player> adminChat;
	public static ProtocolManager protocolManager;
	public static ServerSocket remoteConsoleSocket;
 
	public static void init(Main plugin) {
		guiHandler = new GUIHandler(plugin);
		tpGUI = new GUI(9,Util.parseColors("&6&liZenith &f&lWarps"),guiHandler);
		tpGUI.addButton(Util.newItemMeta(Material.BRICK, Util.parseColors("&aPlots"), Util.parseColors("&aWarp to creative plot world."), 1), 1, new Runnable(){
			@Override
			public void run(){
				Bukkit.dispatchCommand(tpGUI.getWhoClicked(), "warp plots");
			}
		});
		
		tpGUI.addButton(Util.newItemMeta(Material.SIGN, Util.parseColors("&aInfo"), Util.parseColors("&aWarp to info area."), 1), 4, new Runnable(){
			@Override
			public void run(){
				Bukkit.dispatchCommand(tpGUI.getWhoClicked(), "warp info");
			}
		});
		
		tpGUI.addButton(Util.newItemMeta(Material.EXP_BOTTLE, Util.parseColors("&aTrusted"), Util.parseColors("&aWarp to trusted only world."), 1), 7, new Runnable(){
			@Override
			public void run(){
				Bukkit.dispatchCommand(tpGUI.getWhoClicked(), "void");
			}
		});
		
		manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getMainScoreboard();
		teams = new ArrayList<Team>();
		main = plugin;
		commands = new HubCommand[] { 
				new CommandSpy(), 
				new Lel(),
				new Report(),
				new SetFloor(),
				new Kit(),
				new GKit(),
				new Trusted(),
				new Ranks(),
				new Suspend(),
				new ServerIp(),
				new Donated(),
				new WorldEditPerms(),
				new AddPlot(),
				new PlayTime(),
				new UpdateList(),
				new AdminChat(),
				new ClearChat(),
				new Translate(),
				new GetConsoleKey(),
				new Rename(),
				new RefreshHeads()
				//new Console()
				};
		commandSpy = new HashMap<Player, CommandFilter>();
		adminChat = new ArrayList<Player>();
		
		/*
		 * createClock = new HashMap<Player, String>(); clocks =
		 * plugin.getConfig().getStringList("clocks");
		 */
		List<Listener> lis = new ArrayList<Listener>();
		//lis.add(new PlayerInteractHandler());
		lis.add(new ChatHandler());
		lis.add(new PlayerLogListener());
		lis.add(new TeleportListener());
		lis.add(new CommandListener());
		lis.add(new PlotListener());
		lis.add(new Report());
		lis.add(new InteractListener());
		lis.add(new PlayerMoveListener());
		lis.add(new DamageListener());
		lis.add(new BowListener());
		lis.add(new BlockPlaceListener());
		lis.add(new InventoryClickListener());
		lis.add(new ItemDropListener());
		new ServerListHandler();
		//lis.add(new FrameListener());
		for (Listener l : lis) {
			plugin.getServer().getPluginManager().registerEvents(l, plugin);
		}
		/* new TimeHandler(); */
		protocolManager = ProtocolLibrary.getProtocolManager();
	}

}
