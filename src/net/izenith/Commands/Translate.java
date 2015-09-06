package net.izenith.Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.izenith.Main.IPlayer;
import net.izenith.Main.IPlayerHandler;
import net.md_5.bungee.api.ChatColor;

public class Translate implements HubCommand {

	@Override
	public String getName() {
		return "translate";
	}

	@Override
	public String[] getAliases() {
		return new String[] {"tc"};
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		IPlayer player = IPlayerHandler.getPlayer((Player) sender);
		if(args.length > 0){
			if(args[0].equals("off")){
				player.setTranslate(false);
				sender.sendMessage(ChatColor.BLUE + "Translations have been turned off.");
			} else {
				player.setLanguage(args[0]);
				player.setTranslate(true);
				sender.sendMessage(ChatColor.BLUE + "Your language has been set to " + ChatColor.GREEN + args[0]);
			}
		} else {
			sender.sendMessage(ChatColor.GREEN + "Use \"/tc <language_code>\" to automatically translate all messages in to a language or \"/tc off\" to disable translations. You can find the language codes here: http://www.lingoes.net/en/translator/langcode.htm");
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	public static String getTranslation(String message,String from, String to){
		try {
			String[] messageSplit = message.split(" ");
			message = "";
			for(String s : messageSplit){
				message += s + "+";
			}
			String urlString = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20150904T210220Z.b1f160b5f9c09f25.4c3976574f85d91ace1918b0a717b655fcc63ae8&lang=" + from + "-" + to + "&text=" + message;
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String jsonCode = "";
			while((line = rd.readLine()) != null) {
				jsonCode += line;
			}
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(jsonCode).getAsJsonObject();
			return obj.get("text").getAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String detectLanguage(String message){
		try {
			String[] messageSplit = message.split(" ");
			message = "";
			for(String s : messageSplit){
				message += s + "+";
			}
			String urlString = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20150904T210220Z.b1f160b5f9c09f25.4c3976574f85d91ace1918b0a717b655fcc63ae8&text=" + message;
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String jsonCode = "";
			while((line = rd.readLine()) != null) {
				jsonCode += line;
			}
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(jsonCode).getAsJsonObject();
			return obj.get("lang").getAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
