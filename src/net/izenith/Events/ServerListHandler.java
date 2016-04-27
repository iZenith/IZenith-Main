package net.izenith.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import net.izenith.Main.IPlayerHandler;
import net.izenith.Main.Util;
import net.izenith.Main.Vars;

public class ServerListHandler {

	public ServerListHandler() {
		ProtocolManager pManager = ProtocolLibrary.getProtocolManager();
		pManager.removePacketListeners(Util.getMain());
		pManager.addPacketListener(new PacketAdapter(Vars.main, Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO)) {
			@Override
			public void onPacketSending(PacketEvent event) {
				StructureModifier<WrappedServerPing> pings = event.getPacket().getServerPings();
				WrappedServerPing ping = pings.read(0);
				handlePing(ping);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void handlePing(WrappedServerPing ping) {
		ping.setVersionName("iZenith Server 1.8.8");
		System.out.println("Sending player list");
		List<String> playerNames = new ArrayList<String>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			playerNames.add(IPlayerHandler.getPlayer(player).getColoredName(false));
		}
		String players = Util.parseColors(Util.buildString(playerNames, "&7, ", 0, 40));
		ping.setPlayers(Arrays.asList(new WrappedGameProfile("id1", Util.parseColors("&7&l&m--------------[&r &6&liZenith &f&lMinecraft &7&l&m]--------------\n&4&lSERVER IS STILL IN DEVELOPMENT.\n&4&lPLEASE REPORT ANY ISSUES TO AN ADMIN\n&7Online Players:")), new WrappedGameProfile("id2", Util.parseColors("&7") + players)));
	}

}
