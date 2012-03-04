package me.Kruithne.SatingSpleefers;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

public class SatingSpleefersPlayerListener implements Listener
{
	SatingSpleefers rPlugin = null;
	
	public SatingSpleefersPlayerListener(SatingSpleefers plugin)
	{
		this.rPlugin = plugin;
        this.rPlugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player thePlayer = event.getPlayer();
		
		if (thePlayer.getWorld() == this.rPlugin.spleefArena_L1.getWorld())
		{
			if (this.rPlugin.currentPlayers.contains(thePlayer))
			{
				CuboidSelection arena = new CuboidSelection(thePlayer.getWorld(), this.rPlugin.spleefArena_L1, this.rPlugin.spleefArena_L2);
				
				if (!arena.contains(thePlayer.getLocation())) // Inside arena before a game started.
				{
					this.rPlugin.currentPlayers.remove(thePlayer);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		try
		{
			if (event.getPlayer().getWorld() == this.rPlugin.spleefArena_L1.getWorld())
			{
				if (this.rPlugin.currentPlayers.contains(event.getPlayer()) && event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.STEP && event.getClickedBlock().getData() == Byte.parseByte("1") && (event.getClickedBlock().getY() - this.rPlugin.spleefArena_L2.getY()) < 1)
				{
					event.getClickedBlock().setType(Material.AIR);
					this.rPlugin.spleefedBlocks.add(event.getClickedBlock().getLocation());
				}
				else
				{
					this.rPlugin.debugger.debug("The list does not contain " + event.getPlayer().getName() + ", block break denied");
				}
				
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.WALL_SIGN)
				{
					BlockState state = event.getClickedBlock().getState();
					Player thePlayer = event.getPlayer();
					
					if (state instanceof Sign)
					{
						Sign sign = (Sign)state;
						
						if (sign.getLine(0).equals("YELLOW"))
						{
							if (this.rPlugin.spleefSpectatorYellow != null)
							{
								thePlayer.teleport(this.rPlugin.spleefSpectatorYellow);
							}
							else
							{
								this.rPlugin.outputToPlayer(Constants.warpSignInactive, thePlayer);
							}
						}
						else if (sign.getLine(0).equals("RED"))
						{
							if (this.rPlugin.spleefSpectatorRed != null)
							{
								thePlayer.teleport(this.rPlugin.spleefSpectatorRed);
							}
							else
							{
								this.rPlugin.outputToPlayer(Constants.warpSignInactive, thePlayer);
							}
						}
						else if (sign.getLine(0).equals("BLUE"))
						{
							if (this.rPlugin.spleefSpectatorBlue != null)
							{
								thePlayer.teleport(this.rPlugin.spleefSpectatorBlue);
							}
							else
							{
								this.rPlugin.outputToPlayer(Constants.warpSignInactive, thePlayer);
							}
						}
						else if (sign.getLine(0).equals("GREEN"))
						{
							if (this.rPlugin.spleefSpectatorGreen != null)
							{
								thePlayer.teleport(this.rPlugin.spleefSpectatorGreen);
							}
							else
							{
								this.rPlugin.outputToPlayer(Constants.warpSignInactive, thePlayer);
							}
						}
					}
				}
			}
		}
		catch (NullPointerException e)
		{
			//ignore this, expected if not setup.
		}
	}
	
}
