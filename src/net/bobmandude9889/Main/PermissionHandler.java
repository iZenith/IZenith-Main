package net.bobmandude9889.Main;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionHandler {

	public static String getGroupName(Player p){
		return getGroup(p).getName();
	}
	
	public static PermissionGroup getGroup(Player p){
		PermissionGroup[] groups = PermissionsEx.getUser(p).getGroups(p.getWorld().getName()); 
		return groups[groups.length - 1];
	}
	
}
