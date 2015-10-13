package net.izenith.Events;

import java.util.Arrays;

import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import net.izenith.Main.Util;
import net.izenith.Main.Vars;

public class PacketListener implements Listener {

	public PacketListener() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Vars.main, ListenerPriority.NORMAL, Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
			@Override
			public void onPacketSending(PacketEvent event) {
				handlePing(event.getPacket().getServerPings().read(0));
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void handlePing(WrappedServerPing ping) {
		ping.setPlayers(Arrays.asList(new WrappedGameProfile("id1", Util.parseColors("&7&l&m--------[&6&liZenith &f&lMinecraft&7&l&m]--------"))
				,new WrappedGameProfile("id1", Util.parseColors("&7&l&m--------[&6&liZenith &f&lMinecraft&7&l&m]--------"))));
	}

}
