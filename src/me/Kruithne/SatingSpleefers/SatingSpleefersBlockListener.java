package me.Kruithne.SatingSpleefers;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class SatingSpleefersBlockListener implements Listener
{
	SatingSpleefers rPlugin = null;
	
	public SatingSpleefersBlockListener(SatingSpleefers plugin)
	{
		this.rPlugin = plugin;
        this.rPlugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	
	@EventHandler
	public boolean onBlockBreak(BlockBreakEvent event)
	{
		if (event.getPlayer().getWorld() == this.rPlugin.spleefArena_L1.getWorld() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
		{
			event.setCancelled(true);
		}
		return true;
	}
	
	@EventHandler
	public boolean onBlockPlace(BlockPlaceEvent event)
	{
		if (event.getPlayer().getWorld() == this.rPlugin.spleefArena_L1.getWorld() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
		{
			event.setCancelled(true);
		}
		return true;
	}
	
}
