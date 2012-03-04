package me.Kruithne.SatingSpleefers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SatingSpleefersEntityListener implements Listener
{
	SatingSpleefers rPlugin = null;
	
	public SatingSpleefersEntityListener(SatingSpleefers plugin)
	{
		this.rPlugin = plugin;
        this.rPlugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		try
		{
			if (event.getEntity() instanceof Player)
			{
				if (event.getEntity().getWorld() == this.rPlugin.spleefArena_L1.getWorld())
				{
					event.setCancelled(true);
				}
			}
		}
		catch (NullPointerException e)
		{
			//do do do 
		}
	}
	
}
