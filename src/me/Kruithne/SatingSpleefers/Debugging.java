package me.Kruithne.SatingSpleefers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;

public class Debugging {

	private List<Player> debugListeners = new ArrayList<Player>();
	
	public void addDebugListener(Player player)
	{
		if (!debugListeners.contains(player))
		{
			debugListeners.add(player);
		}
	}
	
	public void removeDebugListener(Player player)
	{
		if (debugListeners.contains(player))
		{
			debugListeners.add(player);
		}
	}
	
	public void toggleDebug(Player player)
	{
		if (!debugListeners.contains(player))
		{
			this.addDebugListener(player);
		}
		else
		{
			this.removeDebugListener(player);
		}
	}
	
	public void debug(String message)
	{
		Iterator<Player> debugListenersIterator = this.debugListeners.iterator();
		
		while (debugListenersIterator.hasNext())
		{
			this.debugToPlayer(message, debugListenersIterator.next());
		}
	}
	
	private void debugToPlayer(String message, Player player)
	{
		player.sendMessage(String.format("%s: %s", Constants.pluginOutputTag, message));
	}
	
}
