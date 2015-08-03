package net.bobmandude9889.Main;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionHandler {

	public static String getGroupName(Player p){
		return PermissionsEx.getUser(p).getGroups(p.getWorld().getName())[0].getName();
	}
	
	public static PermissionGroup getGroup(Player p){
		return PermissionsEx.getUser(p).getGroups(p.getWorld().getName())[0];
	}
	
}
