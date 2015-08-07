package net.bobmandude9889.PlotEditor;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import com.plotsquared.bukkit.events.PlotDeleteEvent;

import net.bobmandude9889.Main.Util;

public class PlotListener implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlotClaim(PlayerClaimPlotEvent e){
		Plot plot = e.getPlot();
		Location l1 = new Location(plot.getWorld(),plot.getTop().getX() + 1,66,plot.getTop().getZ() + 1);
		Location l2 = new Location(plot.getWorld(),plot.getBottom().getX(),66,plot.getTop().getZ() + 1);
		Location l3 = new Location(plot.getWorld(),plot.getBottom().getX(),66,plot.getBottom().getZ());
		Location l4 = new Location(plot.getWorld(),plot.getTop().getX() + 1,66,plot.getBottom().getZ());
		
		Block b1 = Util.getLocation(l1).getBlock(); 
		b1.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull1 = ((Skull) b1.getState());
		skull1.setSkullType(SkullType.PLAYER);
		skull1.setOwner(e.getPlayer().getName());
		skull1.setRotation(BlockFace.SOUTH_EAST);
		skull1.update(true);
		Block slabs1 = Util.getLocation(l1).subtract(0, 1, 0).getBlock(); 
		slabs1.setTypeId(43);
		
		Block b2 = Util.getLocation(l2).getBlock();
		b2.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull2 = (Skull) b2.getState();
		skull2.setSkullType(SkullType.PLAYER);
		skull2.setOwner(e.getPlayer().getName());
		skull2.setRotation(BlockFace.SOUTH_WEST);
		skull2.update(true);
		Block slabs2 = Util.getLocation(l2).subtract(0, 1, 0).getBlock(); 
		slabs2.setTypeId(43);
		
		Block b3 = Util.getLocation(l3).getBlock();
		b3.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull3 = ((Skull) b3.getState());
		skull3.setSkullType(SkullType.PLAYER);
		skull3.setOwner(e.getPlayer().getName());
		skull3.setRotation(BlockFace.NORTH_WEST);
		skull3.update(true);
		Block slabs3 = Util.getLocation(l3).subtract(0, 1, 0).getBlock(); 
		slabs3.setTypeId(43);
		
		Block b4 = Util.getLocation(l4).getBlock();
		b4.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
		Skull skull4 = ((Skull) b4.getState());
		skull4.setSkullType(SkullType.PLAYER);
		skull4.setOwner(e.getPlayer().getName());
		skull4.setRotation(BlockFace.NORTH_EAST);
		skull4.update(true);
		Block slabs4 = Util.getLocation(l4).subtract(0, 1, 0).getBlock(); 
		slabs4.setTypeId(43);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDelete(PlotDeleteEvent e){
		Plot plot = new Plot(e.getWorld(),e.getPlotId(),null,true);
		Location l1 = new Location(plot.getWorld(),plot.getTop().getX() + 1,66,plot.getTop().getZ() + 1);
		Location l2 = new Location(plot.getWorld(),plot.getBottom().getX(),66,plot.getTop().getZ() + 1);
		Location l3 = new Location(plot.getWorld(),plot.getBottom().getX(),66,plot.getBottom().getZ());
		Location l4 = new Location(plot.getWorld(),plot.getTop().getX() + 1,66,plot.getBottom().getZ());
		
		Util.getLocation(l1).getBlock().setTypeId(0);
		Util.getLocation(l1.subtract(0, 1, 0)).getBlock().setTypeId(44);
		
		Util.getLocation(l2).getBlock().setTypeId(0);
		Util.getLocation(l2.subtract(0, 1, 0)).getBlock().setTypeId(44);
		
		Util.getLocation(l3).getBlock().setTypeId(0);
		Util.getLocation(l3.subtract(0, 1, 0)).getBlock().setTypeId(44);
		
		Util.getLocation(l4).getBlock().setTypeId(0);
		Util.getLocation(l4.subtract(0, 1, 0)).getBlock().setTypeId(44);
	}
	
}