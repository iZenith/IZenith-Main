package net.bobmandude9889.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.bobmandude9889.Chat.ChatHandler;
import net.bobmandude9889.Color.PlayerJoinListener;
import net.bobmandude9889.CommandSpy.CommandFilter;
import net.bobmandude9889.CommandSpy.CommandListener;
import net.bobmandude9889.Commands.AddPlot;
import net.bobmandude9889.Commands.CommandSpy;
import net.bobmandude9889.Commands.Donated;
import net.bobmandude9889.Commands.GKit;
import net.bobmandude9889.Commands.HubCommand;
import net.bobmandude9889.Commands.Kit;
import net.bobmandude9889.Commands.Lel;
import net.bobmandude9889.Commands.Ranks;
import net.bobmandude9889.Commands.Report;
import net.bobmandude9889.Commands.ServerIp;
import net.bobmandude9889.Commands.Suspend;
import net.bobmandude9889.Commands.Trusted;
import net.bobmandude9889.Commands.WorldEditPerms;
import net.bobmandude9889.Events.SkullName;
import net.bobmandude9889.Gamemode.TeleportListener;
import net.bobmandude9889.PlotEditor.PlotListener;
import net.bobmandude9889.PlotEditor.SetFloor;

public class Vars {

	public static HubCommand[] commands;
	public static HashMap<Player, CommandFilter> commandSpy;
	public static HashMap<Player, String> createClock;
	public static List<String> clocks;
	public static Main main;
	public static ScoreboardManager manager;
	public static Scoreboard scoreboard;
	public static List<Team> teams;
 
	public static void init(Main plugin) {
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
				new AddPlot()
				};
		commandSpy = new HashMap<Player, CommandFilter>();
		
		/*
		 * createClock = new HashMap<Player, String>(); clocks =
		 * plugin.getConfig().getStringList("clocks");
		 */
		List<Listener> lis = new ArrayList<Listener>();
		//lis.add(new PlayerInteractHandler());
		lis.add(new ChatHandler(plugin));
		lis.add(new PlayerJoinListener());
		lis.add(new TeleportListener());
		lis.add(new CommandListener());
		lis.add(new PlotListener());
		lis.add(new Report());
		lis.add(new SkullName());
		for (Listener l : lis) {
			plugin.getServer().getPluginManager().registerEvents(l, plugin);
		}
		/* new TimeHandler(); */
	}

}
