package me.Kruithne.SatingSpleefers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class SatingSpleefers extends JavaPlugin {
	
	Server server;
	Logger log = Logger.getLogger("Minecraft");
	
	public Location spleefArena_L1 = null;
	public Location spleefArena_L2 = null;
	
	public Location spleefDropNet_L1 = null;
	public Location spleefDropNet_L2 = null;
	
	public Location spleefRedRoom_L1 = null;
	public Location spleefRedRoom_L2 = null;
	
	public Location spleefBlueRoom_L1 = null;
	public Location spleefBlueRoom_L2 = null;
	
	public Location spleefGreenRoom_L1 = null;
	public Location spleefGreenRoom_L2 = null;
	
	public Location spleefYellowRoom_L1 = null;
	public Location spleefYellowRoom_L2 = null;
	
	public Location spleefPadRed_L1 = null;
	public Location spleefPadRed_L2 = null;
	
	public Location spleefPadBlue_L1 = null;
	public Location spleefPadBlue_L2 = null;
	
	public Location spleefPadGreen_L1 = null;
	public Location spleefPadGreen_L2 = null;
	
	public Location spleefPadYellow_L1 = null;
	public Location spleefPadYellow_L2 = null;
	
	public Location spleefPreRoomPort = null;
	
	public Location spleefRedRoomPort = null;
	public Location spleefBlueRoomPort = null;
	public Location spleefGreenRoomPort = null;
	public Location spleefYellowRoomPort = null;
	
	public Location spleefRedGate_L1 = null;
	public Location spleefRedGate_L2 = null;
	
	public Location spleefBlueGate_L1 = null;
	public Location spleefBlueGate_L2 = null;
	
	public Location spleefYellowGate_L1 = null;
	public Location spleefYellowGate_L2 = null;
	
	public Location spleefGreenGate_L1 = null;
	public Location spleefGreenGate_L2 = null;
	
	public Location spleefLatestWinsSign = null;
	public Location spleefScoreBoardSign = null;
	public Location spleefScoreBoardSignExtra = null;
	
	public Location spleefSpectatorRed = null;
	public Location spleefSpectatorBlue = null;
	public Location spleefSpectatorYellow = null;
	public Location spleefSpectatorGreen = null;
	
	public List<Location> spleefedBlocks = new ArrayList<Location>();
	
	public List<Location> spleefGateBlocks_Layer1 = new ArrayList<Location>();
	public List<Location> spleefGateBlocks_Layer2 = new ArrayList<Location>();
	public List<Location> spleefGateBlocks_Layer3 = new ArrayList<Location>();
	
	public boolean gameInProgress = false;
	public boolean gameStarting = false;
	public boolean preGame = false;
	
	private String url = null;
    private String user = null;
    private String password = null;
	
	public Debugging debugger = new Debugging();
	
	public List<Player> currentPlayers = new ArrayList<Player>();
	
	private DatabaseConnection database = null;
	
	@SuppressWarnings("unused")
	private SatingSpleefersPlayerListener pListener = null;
	@SuppressWarnings("unused")
	private SatingSpleefersEntityListener eListener = null;
	@SuppressWarnings("unused")
	private SatingSpleefersBlockListener bListener = null;
	
	WorldEditPlugin worldEdit;
	
	public void onDisable()
	{
		this.database.closeConnection();
	}
	 
	public void onEnable()
	{
		server = this.getServer();
		this.loadConfiguration();
		
		this.pListener = new SatingSpleefersPlayerListener(this);
		this.eListener = new SatingSpleefersEntityListener(this);
		this.bListener = new SatingSpleefersBlockListener(this);
		
		this.server.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

		    public void run() {
		        pulse();
		    }
		    
		}, 10L, 10L);
		
		this.database = new DatabaseConnection(this, this.url, this.user, this.password);
		
		this.worldEdit = this.getWorldEdit();
		this.database.establishConnection();
		
		this.rebuildFloor();
	}
	
	private WorldEditPlugin getWorldEdit()
    {
    	Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
    	
    	if (plugin == null || !(plugin instanceof WorldEditPlugin))
    	{
    		return null;
    	}
    	
    	return (WorldEditPlugin) plugin;
    }
	
	public boolean fullySetup()
	{
		if (spleefArena_L1 == null){ return false; }
		if (spleefArena_L2 == null){ return false; }
		if (spleefDropNet_L1 == null){ return false; }
		if (spleefDropNet_L2 == null){ return false; }
		if (spleefRedRoom_L1 == null){ return false; }
		if (spleefRedRoom_L2 == null){ return false; }
		if (spleefBlueRoom_L1 == null){ return false; }
		if (spleefBlueRoom_L2 == null){ return false; }
		if (spleefGreenRoom_L1 == null){ return false; }
		if (spleefGreenRoom_L2 == null){ return false; }
		if (spleefYellowRoom_L1 == null){ return false; }
		if (spleefYellowRoom_L2 == null){ return false; }
		if (spleefPadRed_L1 == null){ return false; }
		if (spleefPadRed_L2 == null){ return false; }
		if (spleefPadBlue_L1 == null){ return false; }
		if (spleefPadBlue_L2 == null){ return false; }
		if (spleefPadGreen_L1 == null){ return false; }
		if (spleefPadGreen_L2 == null){ return false; }
		if (spleefPadYellow_L1 == null){ return false; }
		if (spleefPadYellow_L2 == null){ return false; }
		if (spleefPreRoomPort == null){ return false; }
		if (spleefRedRoomPort == null){ return false; }
		if (spleefBlueRoomPort == null){ return false; }
		if (spleefGreenRoomPort == null){ return false; }
		if (spleefYellowRoomPort == null){ return false; }
		if (spleefRedGate_L1 == null){ return false; }
		if (spleefRedGate_L2 == null){ return false; }
		if (spleefBlueGate_L1 == null){ return false; }
		if (spleefBlueGate_L2 == null){ return false; }
		if (spleefGreenGate_L1 == null){ return false; }
		if (spleefGreenGate_L2 == null){ return false; }
		if (spleefYellowGate_L1 == null){ return false; }
		if (spleefYellowGate_L2 == null){ return false; }
		return true;
	}
	
	public void pulse()
	{
		checkInvalidLocations();
		
		if (!this.gameInProgress && !this.gameStarting)
		{
			int worldPlayerCount = 0;
			
			if (fullySetup())
			{
				try
				{
					List<Player> worldPlayers = spleefArena_L1.getWorld().getPlayers();
			    	
			    	if (!worldPlayers.isEmpty())
			    	{
			    		worldPlayerCount = worldPlayers.size();
			    	}
			    	
			    	if (worldPlayerCount > 1)
			    	{
			    		this.initGame();
			    	}
				}
				catch (ConcurrentModificationException e)
				{
					//Baby don't hurt, don't hurt me, no more.
				}
			}
		}
		
		if (this.gameInProgress && !this.preGame)
		{
			checkGameProgress();
		}
	}

	public void checkGameProgress()
	{
		List<Player> worldPlayers = spleefArena_L1.getWorld().getPlayers();
		
		Player lastManStanding = null;
		int playerCount = 0;
    	
    	if (!worldPlayers.isEmpty())
    	{
    		for (int i = 0; i < worldPlayers.size(); i++)
    		{
    			Player thePlayer = worldPlayers.get(i);
    			
    			CuboidSelection theArena = new CuboidSelection(thePlayer.getWorld(), this.spleefArena_L1, this.spleefArena_L2);
    			if (theArena.contains(thePlayer.getLocation()))
    			{
    				lastManStanding = thePlayer;
    				playerCount++;
    			}
    		}
    	}	
    	
    	if (playerCount == 0)
    	{
    		this.debugger.debug(Constants.debug_staleMatch);
    		this.spleefCast(Constants.spleefCastBadEnd);
    		endGame();
    	}
    	else if (playerCount == 1)
    	{
    		if (lastManStanding != null)
    		{
    			if (this.currentPlayers.contains(lastManStanding))
    			{
    				this.debugger.debug(Constants.debug_matchWon);
    				this.spleefGlobalCast(String.format(Constants.spleefCastWinner, lastManStanding.getName()));
    				this.updateLatestWins(lastManStanding);
    				this.addPlayerWin(lastManStanding);
    			
    			}
    			else
    			{
    				this.debugger.debug(Constants.debug_staleMatch);
    				this.spleefCast(Constants.spleefCastBadEnd);
    			}
    		}
    		else
    		{
    			this.debugger.debug(Constants.debug_winnerNull);
    			this.spleefCast(Constants.spleefCastBadEnd);
    		}
    		endGame();
    	}
	}
	
	public void endGame()
	{
		this.debugger.debug(Constants.debug_endedGame);
		this.repairFloor();
		this.gameInProgress = false;
		this.gameStarting = false;
		this.preGame = false;
	}
	
	public void initGame()
	{
		this.debugger.debug(Constants.debug_initGame);
		this.gameStarting = true;
		this.spleefCast(String.format(Constants.spleefCastNewGameStarting_minute, 1));
		this.delayedSpleefcast(15, String.format(Constants.spleefCastNewGameStarting_seconds, 45));
		this.delayedSpleefcast(30, String.format(Constants.spleefCastNewGameStarting_seconds, 30));
		this.delayedSpleefcast(45, String.format(Constants.spleefCastNewGameStarting_seconds, 15));
		this.delayedSpleefcast(55, String.format(Constants.spleefCastNewGameStarting_seconds, 5));
		this.scheduleNewGame(60);
	}
	
	public void delayedSpleefcast(int time, final String message) 
	{
		time = time * 20;
		this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
		    	spleefCast(message);
		    }
		    
		}, time);
	}
	
	public void scheduleNewGame(int time)
	{
		time = time * 20;
		this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
		    	startGame();
		    }
		    
		}, time);
	}
		
	public void startGame()
	{
		this.debugger.debug("Trying to start new game...");
		if (getAwaitingPlayers() > 1)
		{
			this.debugger.debug("There are more than 1 players on the pads, starting.");
			currentPlayers.clear();
			this.portFromPads();
			this.spleefCast(Constants.spleefCastNewGameStarted);
			this.gameInProgress = true;
			this.preGame = true;
			
			this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			    public void run() {
			    	openGates();
			    }
			    
			}, 200L);
		}
		else
		{
			this.debugger.debug("Not enough players, terminating the match");
			this.spleefCast(Constants.spleefCastNewGameNoPlayers);
		}
		this.gameStarting = false;
	}
	
	public int getAwaitingPlayers()
	{
		int awaitingPlayers = 0;
		List<Player> worldPlayers = spleefArena_L1.getWorld().getPlayers();
    	
    	if (!worldPlayers.isEmpty())
    	{
    		for (int i = 0; i < worldPlayers.size(); i++)
    		{
    			Player thePlayer = worldPlayers.get(i);
    			
    			CuboidSelection redPad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadRed_L1, this.spleefPadRed_L2);
    			CuboidSelection bluePad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadBlue_L1, this.spleefPadBlue_L2);
    			CuboidSelection yellowPad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadYellow_L1, this.spleefPadYellow_L2);
    			CuboidSelection greenPad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadGreen_L1, this.spleefPadGreen_L2);
    			
    			if (redPad.contains(thePlayer.getLocation()))
    			{
    				awaitingPlayers++;
    			}
    			else if (bluePad.contains(thePlayer.getLocation()))
    			{
    				awaitingPlayers++;
    			}
    			else if (yellowPad.contains(thePlayer.getLocation()))
    			{
    				awaitingPlayers++;
    			}
    			else if (greenPad.contains(thePlayer.getLocation()))
    			{
    				awaitingPlayers++;
    			}
    		}
    	}
		return awaitingPlayers;
	}
	
	public void portFromPads()
	{
		this.debugger.debug("Porting players from the pads");
		List<Player> worldPlayers = this.spleefArena_L1.getWorld().getPlayers();
    	
    	if (!worldPlayers.isEmpty())
    	{
    		for (int i = 0; i < worldPlayers.size(); i++)
    		{
    			Player thePlayer = worldPlayers.get(i);    			
    			Location playerLocation = thePlayer.getLocation();
    			
    			CuboidSelection redPad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadRed_L1, this.spleefPadRed_L2);
    			CuboidSelection bluePad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadBlue_L1, this.spleefPadBlue_L2);
    			CuboidSelection yellowPad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadYellow_L1, this.spleefPadYellow_L2);
    			CuboidSelection greenPad = new CuboidSelection(thePlayer.getWorld(), this.spleefPadGreen_L1, this.spleefPadGreen_L2);
    			
    			if (redPad.contains(playerLocation))
    			{
    				thePlayer.teleport(this.spleefRedRoomPort);
    				this.currentPlayers.add(thePlayer);
    				this.debugger.debug("ADDING PLAYER TO MATCH LIST " + thePlayer.getName());
    			}
    			else if (bluePad.contains(playerLocation))
    			{
    				thePlayer.teleport(this.spleefBlueRoomPort);
    				this.currentPlayers.add(thePlayer);
    				this.debugger.debug("ADDING PLAYER TO MATCH LIST " + thePlayer.getName());
    			}
    			else if (yellowPad.contains(playerLocation))
    			{
    				thePlayer.teleport(this.spleefYellowRoomPort);
    				this.currentPlayers.add(thePlayer);
    				this.debugger.debug("ADDING PLAYER TO MATCH LIST " + thePlayer.getName());
    			}
    			else if (greenPad.contains(playerLocation))
    			{
    				thePlayer.teleport(this.spleefGreenRoomPort);
    				this.currentPlayers.add(thePlayer);
    				this.debugger.debug("ADDING PLAYER TO MATCH LIST " + thePlayer.getName());
    			}
    		}
    	}
	}
	
	public void spleefCast(String message)
	{
		List<Player> worldPlayers = this.spleefArena_L1.getWorld().getPlayers();
    	
    	if (!worldPlayers.isEmpty())
    	{
    		for (int i = 0; i < worldPlayers.size(); i++)
    		{
    			Player thePlayer = worldPlayers.get(i);
    			thePlayer.sendMessage(ChatColor.GOLD + message);
    		}
    	}
	}
	
	public void spleefGlobalCast(String message)
	{
		this.server.broadcastMessage(Constants.pluginOutputTag + ": " + ChatColor.GOLD + message);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	 {
		try
		{
			 if (cmd.getName().equalsIgnoreCase("ss") || cmd.getName().equalsIgnoreCase("spleef") || cmd.getName().equalsIgnoreCase("satingspleefers"))
			 {
				 if(args.length >= 1)
				 {
					if (sender.hasPermission(Constants.permissionAdmin))
					{
						Player commandPlayer = (Player) sender;
						if (args[0].equalsIgnoreCase("setarena"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							if (playerSelection != null)
							{
								this.spleefArena_L1 = playerSelection.getMaximumPoint();
								this.spleefArena_L2 = playerSelection.getMinimumPoint();
								
								this.writeConfiguration();
								this.outputToPlayer(Constants.commandNotice_arenaSet, commandPlayer);
							}
						}
						else if (args[0].equalsIgnoreCase("setnet"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							if (playerSelection != null)
							{
								this.spleefDropNet_L1 = playerSelection.getMaximumPoint();
								this.spleefDropNet_L2 = playerSelection.getMinimumPoint();
								
								this.writeConfiguration();
								this.outputToPlayer(Constants.commandNotice_netSet, commandPlayer);
							}
						}
						else if (args[0].equalsIgnoreCase("setredgate"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefRedGate_L1 = playerSelection.getMaximumPoint();
							this.spleefRedGate_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_gateSet, "red"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setbluegate"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefBlueGate_L1 = playerSelection.getMaximumPoint();
							this.spleefBlueGate_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_gateSet, "red"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setyellowgate"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefYellowGate_L1 = playerSelection.getMaximumPoint();
							this.spleefYellowGate_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_gateSet, "red"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setgreengate"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefGreenGate_L1 = playerSelection.getMaximumPoint();
							this.spleefGreenGate_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_gateSet, "red"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setredpad"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefPadRed_L1 = playerSelection.getMaximumPoint();
							this.spleefPadRed_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_padSet, "red"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setbluepad"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefPadBlue_L1 = playerSelection.getMaximumPoint();
							this.spleefPadBlue_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_padSet, "blue"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setyellowpad"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefPadYellow_L1 = playerSelection.getMaximumPoint();
							this.spleefPadYellow_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_padSet, "yellow"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setgreenpad"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefPadGreen_L1 = playerSelection.getMaximumPoint();
							this.spleefPadGreen_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_padSet, "green"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setredroom"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefRedRoom_L1 = playerSelection.getMaximumPoint();
							this.spleefRedRoom_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomSet, "red"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setblueroom"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefBlueRoom_L1 = playerSelection.getMaximumPoint();
							this.spleefBlueRoom_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomSet, "blue"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setyellowroom"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefYellowRoom_L1 = playerSelection.getMaximumPoint();
							this.spleefYellowRoom_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomSet, "yellow"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setgreenroom"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefGreenRoom_L1 = playerSelection.getMaximumPoint();
							this.spleefGreenRoom_L2 = playerSelection.getMinimumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomSet, "green"), commandPlayer);
							
						}
						else if (args[0].equalsIgnoreCase("setpre"))
						{
							this.spleefPreRoomPort = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomPort, "lobby"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setredport"))
						{
							this.spleefRedRoomPort = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomPort, "red"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setblueport"))
						{
							this.spleefBlueRoomPort = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomPort, "blue"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setyellowport"))
						{
							this.spleefYellowRoomPort = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomPort, "yellow"), commandPlayer);
						}					
						else if (args[0].equalsIgnoreCase("setgreenport"))
						{
							this.spleefGreenRoomPort = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_roomPort, "green"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setgreenspectator"))
						{
							this.spleefSpectatorGreen = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_spectatorPort, "green"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setredspectator"))
						{
							this.spleefSpectatorRed = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_spectatorPort, "red"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setbluespectator"))
						{
							this.spleefSpectatorBlue = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_spectatorPort, "blue"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setyellowspectator"))
						{
							this.spleefSpectatorYellow = commandPlayer.getLocation();
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_spectatorPort, "yellow"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("repair"))
						{
							this.rebuildFloor();
							this.outputToPlayer(Constants.commandNotice_floorRepair, commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setsignscores"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefScoreBoardSign = playerSelection.getMaximumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_signSet, "scores"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setsignscoresextra"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefScoreBoardSignExtra = playerSelection.getMaximumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_signSet, "scores (extra)"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("setsignlatest"))
						{
							Selection playerSelection = this.worldEdit.getSelection(commandPlayer);
							this.spleefLatestWinsSign = playerSelection.getMaximumPoint();
							
							this.writeConfiguration();
							this.outputToPlayer(String.format(Constants.commandNotice_signSet, "latest winners"), commandPlayer);
						}
						else if (args[0].equalsIgnoreCase("debug"))
						{
							this.debugger.toggleDebug(commandPlayer);
							this.outputToPlayer("Debug toggled", commandPlayer);
						}
						else
						{
							this.outputToPlayer(Constants.commandErrorNoCommand, (Player) sender);
						}
					}
				 }
				 else
				 {
					 Player getThatPlayer = (Player) sender;
					 getThatPlayer.teleport(this.spleefPreRoomPort);
				 }
				 return true;
			 }
			 return false;
		}
		catch (NullPointerException e)
		{
			return false;
		}
	 }
	
	public void updateLatestWins(Player winner)
	{
		if (this.spleefLatestWinsSign != null)
		{
			this.debugger.debug("Updated latest wins sign..");
			Block theBlock = this.spleefLatestWinsSign.getBlock();
			if (theBlock.getType() == Material.WALL_SIGN)
			{
				BlockState state = theBlock.getState();
				if (state instanceof Sign)
				{
					Sign sign = (Sign)state;
					sign.setLine(3, sign.getLine(2));
					sign.setLine(2, sign.getLine(1));
					sign.setLine(1, sign.getLine(0));
					sign.setLine(0, winner.getName());
					sign.update(true);
				}
			}
		}
	}
	
	public Sign clearSign(Sign sign)
	{
		sign.setLine(0, "");
		sign.setLine(1, "");
		sign.setLine(2, "");
		sign.setLine(3, "");
		
		return sign;
	}
	
	public void addPlayerWin(Player winner)
	{
		this.database.query(String.format("INSERT INTO spleefScore (ID) VALUES('%s') ON DUPLICATE KEY UPDATE Wins = Wins + 1", winner.getName()));
		this.updateScoreboard();
	}

	public void updateScoreboard()
	{
		if (this.spleefScoreBoardSign != null)
		{
			this.debugger.debug("Updated scoreboard sign...");
			Block theBlock = this.spleefScoreBoardSign.getBlock();
			Block theBlockExtra = this.spleefScoreBoardSignExtra.getBlock();
			
			if (theBlock.getType() == Material.WALL_SIGN && theBlockExtra.getType() == Material.WALL_SIGN)
			{
				BlockState state = theBlock.getState();
				BlockState stateExtra = theBlockExtra.getState();
				
				if (state instanceof Sign && stateExtra instanceof Sign)
				{
					ResultSet leaderData = this.database.getQuery("SELECT ID, Wins FROM spleefScore ORDER BY Wins DESC LIMIT 4");	
					try
					{
						Sign sign = (Sign)state;
						Sign signExtra = (Sign) stateExtra;
						
						sign = this.clearSign(sign);
						signExtra = this.clearSign(signExtra);
						
						int currentLine = 0;
						while (leaderData.next())
						{
							sign.setLine(currentLine, String.format("%s. %s", currentLine + 1, leaderData.getString("ID")));
							signExtra.setLine(currentLine, String.format("with %s wins", leaderData.getString("Wins")));
							currentLine++;
						}
						sign.update(true);
						signExtra.update(true);
					}
					catch (SQLException e)
					{
						this.outputToConsole(String.format(Constants.SQLError, e.getMessage()), Level.SEVERE);
					}
				}
			}
		}
	}
	
	public void writeConfigValue(String key, String value)
	{
		this.getConfig().set(String.format(Constants.configSettingPath, key), value);
	}
	
	public void writeConfigLocationValue(String key, Location value)
	{
		if (value != null)
		{
			this.getConfig().set(String.format(Constants.configSettingPath + "_X", key), value.getX());
			this.getConfig().set(String.format(Constants.configSettingPath + "_Y", key), value.getY());
			this.getConfig().set(String.format(Constants.configSettingPath + "_Z", key), value.getZ());
			this.getConfig().set(String.format(Constants.configSettingPath + "_world", key), value.getWorld().getName());
		}
	}
	
	public void repairFloor()
	{
		if (fullySetup())
		{		
			this.debugger.debug("Repairing floor...");
			this.repairAFloorTile();
		}
	}
	
	public void repairAFloorTile()
	{
		if (fullySetup())
		{		
			this.debugger.debug("Repairing floor tile.");
			if (!this.spleefedBlocks.isEmpty())
			{
				Location theBlock = this.spleefedBlocks.get(0);
				theBlock.getBlock().setType(Material.STEP);
				theBlock.getBlock().setData(Byte.parseByte("1"));
				this.spleefedBlocks.remove(0);
				
				this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				    public void run() {
				    	repairAFloorTile();
				    }
				    
				}, 1L);
			}
		}
	}

	public void rebuildFloor()
	{
		this.debugger.debug("Rebuilding floor fully...");
		this.outputToConsole("! -- REBUILDING spleef floor FULLY -- !", Level.INFO);
		Location point1 = this.spleefArena_L1.clone();
		Location point2 = this.spleefArena_L2.clone();
		
		point1.setY(point2.getY());
		
		double currentX = point2.getX();
		double currentZ = point2.getZ();
		
		while (currentX < point1.getX())
		{
			//this.outputToConsole("Moving cursor to X: " + currentX, Level.INFO);
			while (currentZ < point1.getZ())
			{
				//this.outputToConsole("Moving cursor to Z: " + currentZ, Level.INFO);
				Location currentLocation = new Location(point1.getWorld(), currentX, point1.getY(), currentZ);
				Block currentBlock = currentLocation.getBlock();
				
				if (currentBlock.getType() == Material.AIR)
				{
					currentBlock.setType(Material.STEP);
					currentBlock.setData(Byte.parseByte("1"));
				}
				currentZ++;
			}
			currentZ = point2.getZ();
			currentX++;
		}
	}
	
	public void setGates(Location loc1, Location loc2, Double y, Material mat)
	{
		double diffX = Math.floor(loc1.getX() - loc2.getX()) / 2;
		double diffZ = Math.floor(loc1.getZ() - loc2.getZ()) / 2;
		
		Location firstBlock = loc1;
		Location secondBlock = new Location(loc1.getWorld(), loc1.getX() - diffX, y, loc1.getZ() - diffZ);
		Location thirdBlock = loc2;
		
		firstBlock.setY(y);
		thirdBlock.setY(y);
		
		firstBlock.getBlock().setType(mat);
		secondBlock.getBlock().setType(mat);
		thirdBlock.getBlock().setType(mat);
	}

	public void openGates()
	{
		//Do lowest - layer 1
		this.debugger.debug("Opening spleef gates");
		
		this.setGates(this.spleefRedGate_L1, this.spleefRedGate_L2, spleefRedRoomPort.getY(), Material.AIR);
		this.setGates(this.spleefBlueGate_L1, this.spleefBlueGate_L2, spleefBlueRoomPort.getY(), Material.AIR);
		this.setGates(this.spleefGreenGate_L1, this.spleefGreenGate_L2, spleefGreenRoomPort.getY(), Material.AIR);
		this.setGates(this.spleefYellowGate_L1, this.spleefYellowGate_L2, spleefYellowRoomPort.getY(), Material.AIR);
		
				
		this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
		    	setGates(spleefRedGate_L1, spleefRedGate_L2, spleefRedRoomPort.getY() + 1, Material.AIR);
				setGates(spleefBlueGate_L1, spleefBlueGate_L2, spleefBlueRoomPort.getY() + 1, Material.AIR);
				setGates(spleefYellowGate_L1, spleefYellowGate_L2, spleefYellowRoomPort.getY() + 1, Material.AIR);
				setGates(spleefGreenGate_L1, spleefGreenGate_L2, spleefGreenRoomPort.getY() + 1, Material.AIR);
		    }
		    
		}, 20L);
		
		this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
		    	setGates(spleefRedGate_L1, spleefRedGate_L2, spleefRedRoomPort.getY() + 2, Material.AIR);
				setGates(spleefBlueGate_L1, spleefBlueGate_L2, spleefBlueRoomPort.getY() + 2, Material.AIR);
				setGates(spleefYellowGate_L1, spleefYellowGate_L2, spleefYellowRoomPort.getY() + 2, Material.AIR);
				setGates(spleefGreenGate_L1, spleefGreenGate_L2, spleefGreenRoomPort.getY() + 2, Material.AIR);
		    }
		    
		}, 40L);
		
		this.spleefCast(Constants.spleefCastBeginMatch);
		
		this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
		    	shutGates();
		    }
		    
		}, 200L);
	}
	
	public void shutGates()
	{
		this.debugger.debug("Shutting spleef gates");
		this.setGates(this.spleefRedGate_L1, this.spleefRedGate_L2, this.spleefRedRoomPort.getY() + 2, Material.IRON_FENCE);
		this.setGates(this.spleefBlueGate_L1, this.spleefBlueGate_L2, this.spleefBlueRoomPort.getY() + 2, Material.IRON_FENCE);
		this.setGates(this.spleefYellowGate_L1, this.spleefYellowGate_L2, this.spleefYellowRoomPort.getY() + 2, Material.IRON_FENCE);
		this.setGates(this.spleefGreenGate_L1, this.spleefGreenGate_L2, this.spleefGreenRoomPort.getY() + 2, Material.IRON_FENCE);
		
		this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
		    	setGates(spleefRedGate_L1, spleefRedGate_L2, spleefRedRoomPort.getY() + 1, Material.IRON_FENCE);
				setGates(spleefBlueGate_L1, spleefBlueGate_L2, spleefBlueRoomPort.getY() + 1, Material.IRON_FENCE);
				setGates(spleefYellowGate_L1, spleefYellowGate_L2, spleefYellowRoomPort.getY() + 1, Material.IRON_FENCE);
				setGates(spleefGreenGate_L1, spleefGreenGate_L2, spleefGreenRoomPort.getY() + 1, Material.IRON_FENCE);
		    }
		    
		}, 20L);
		
		this.server.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
		    	setGates(spleefRedGate_L1, spleefRedGate_L2, spleefRedRoomPort.getY(), Material.IRON_FENCE);
				setGates(spleefBlueGate_L1, spleefBlueGate_L2, spleefBlueRoomPort.getY(), Material.IRON_FENCE);
				setGates(spleefYellowGate_L1, spleefYellowGate_L2, spleefYellowRoomPort.getY(), Material.IRON_FENCE);
				setGates(spleefGreenGate_L1, spleefGreenGate_L2, spleefGreenRoomPort.getY(), Material.IRON_FENCE);
		    }
		    
		}, 40L);
		
		this.preGame = false;
	}
	
	public void checkInvalidLocations()
	{
		if (fullySetup())
		{
			try 
			{
				List<Player> worldPlayers = this.spleefArena_L1.getWorld().getPlayers();
				
				if (!worldPlayers.isEmpty())
				{
					for (int i = 0; i < worldPlayers.size(); i++)
					{
						Player currentPlayer = worldPlayers.get(i);
						
						if (currentPlayer.getGameMode() != GameMode.CREATIVE)
						{					
							CuboidSelection arenaDropNet = new CuboidSelection(currentPlayer.getWorld(), this.spleefDropNet_L1, this.spleefDropNet_L2);
							
							if (arenaDropNet.contains(currentPlayer.getLocation())) //Falls under arena.
							{
								this.removePlayerFromArena(currentPlayer);
							}
							
							CuboidSelection arena = new CuboidSelection(currentPlayer.getWorld(), this.spleefArena_L1, this.spleefArena_L2);
							
							if (arena.contains(currentPlayer.getLocation())) // Inside arena before a game started.
							{
								if (!this.gameInProgress && !this.preGame)
								{
									this.removePlayerFromArena(currentPlayer);
								}
							}
							
			    			CuboidSelection redRoom = new CuboidSelection(currentPlayer.getWorld(), this.spleefRedRoom_L1, this.spleefRedRoom_L2);
			    			CuboidSelection blueRoom = new CuboidSelection(currentPlayer.getWorld(), this.spleefBlueRoom_L1, this.spleefBlueRoom_L2);
			    			CuboidSelection yellowRoom = new CuboidSelection(currentPlayer.getWorld(), this.spleefYellowRoom_L1, this.spleefYellowRoom_L2);
			    			CuboidSelection greenRoom = new CuboidSelection(currentPlayer.getWorld(), this.spleefGreenRoom_L1, this.spleefGreenRoom_L2);
			    			
			    			if (this.gameInProgress && !this.preGame)
			    			if (redRoom.contains(currentPlayer.getLocation()))
			    			{
			    				this.removePlayerFromArena(currentPlayer);
			    			}
			    			else if (blueRoom.contains(currentPlayer.getLocation()))
			    			{
			    				this.removePlayerFromArena(currentPlayer);
			    			}
			    			else if (yellowRoom.contains(currentPlayer.getLocation()))
			    			{
			    				this.removePlayerFromArena(currentPlayer);
			    			}
			    			else if (greenRoom.contains(currentPlayer.getLocation()))
			    			{
			    				this.removePlayerFromArena(currentPlayer);
			    			}
						}
					}
				}
			}
			catch(ConcurrentModificationException e)
			{
				//Damn greedy threads, not much to do about this but catch it and hug it tightly.
			}
		}
	}
	
	public void removePlayerFromArena(Player thePlayer)
	{
		this.debugger.debug("Removing " + thePlayer.getName() + " from the spleef arena");
		this.currentPlayers.remove(thePlayer.getName());
		thePlayer.teleport(this.spleefPreRoomPort);
	}
	
	public Location getLocationFromConfig(String key)
	{
		Location returnLoc = null;
		if (this.getConfig().contains(String.format(Constants.configSettingPath, key + "_X")))
		{
			Double getX = Double.parseDouble(this.getConfig().getString(String.format(Constants.configSettingPath, key + "_X")));
			Double getY = Double.parseDouble(this.getConfig().getString(String.format(Constants.configSettingPath, key + "_Y")));
			Double getZ = Double.parseDouble(this.getConfig().getString(String.format(Constants.configSettingPath, key + "_Z")));
			World getWorld = this.server.getWorld(this.getConfig().getString(String.format(Constants.configSettingPath, key + "_world")));
			
			returnLoc = new Location(getWorld, getX, getY, getZ);
		}
		return returnLoc;
	}
	
	public String getStringFromConfig(String key)
	{
		String returnString = null;
		if (this.getConfig().contains(String.format(Constants.configSettingPath, key)))
		{
			returnString = this.getConfig().getString(String.format(Constants.configSettingPath, key));
		}
		return returnString;
	}
	
	public void writeConfiguration()
	{	
		this.writeConfigLocationValue("spleefArena_L1", this.spleefArena_L1);
		this.writeConfigLocationValue("spleefArena_L2", this.spleefArena_L2);
		this.writeConfigLocationValue("spleefDropNet_L1", this.spleefDropNet_L1);
		this.writeConfigLocationValue("spleefDropNet_L2", this.spleefDropNet_L2);
		this.writeConfigLocationValue("spleefRedRoom_L1", this.spleefRedRoom_L1);
		this.writeConfigLocationValue("spleefRedRoom_L2", this.spleefRedRoom_L2);
		this.writeConfigLocationValue("spleefBlueRoom_L1", this.spleefBlueRoom_L1);
		this.writeConfigLocationValue("spleefBlueRoom_L2", this.spleefBlueRoom_L2);
		this.writeConfigLocationValue("spleefGreenRoom_L1", this.spleefGreenRoom_L1);
		this.writeConfigLocationValue("spleefGreenRoom_L2", this.spleefGreenRoom_L2);
		this.writeConfigLocationValue("spleefYellowRoom_L1", this.spleefYellowRoom_L1);
		this.writeConfigLocationValue("spleefYellowRoom_L2", this.spleefYellowRoom_L2);
		this.writeConfigLocationValue("spleefPadRed_L1", this.spleefPadRed_L1);
		this.writeConfigLocationValue("spleefPadRed_L2", this.spleefPadRed_L2);
		this.writeConfigLocationValue("spleefPadBlue_L1", this.spleefPadBlue_L1);
		this.writeConfigLocationValue("spleefPadBlue_L2", this.spleefPadBlue_L2);
		this.writeConfigLocationValue("spleefPadGreen_L1", this.spleefPadGreen_L1);
		this.writeConfigLocationValue("spleefPadGreen_L2", this.spleefPadGreen_L2);
		this.writeConfigLocationValue("spleefPadYellow_L1", this.spleefPadYellow_L1);
		this.writeConfigLocationValue("spleefPadYellow_L2", this.spleefPadYellow_L2);
		this.writeConfigLocationValue("spleefPreRoomPort", this.spleefPreRoomPort);
		this.writeConfigLocationValue("spleefRedRoomPort", this.spleefRedRoomPort);
		this.writeConfigLocationValue("spleefBlueRoomPort", this.spleefBlueRoomPort);
		this.writeConfigLocationValue("spleefGreenRoomPort", this.spleefGreenRoomPort);
		this.writeConfigLocationValue("spleefYellowRoomPort", this.spleefYellowRoomPort);
		this.writeConfigLocationValue("spleefRedGate_L1", this.spleefRedGate_L1);
		this.writeConfigLocationValue("spleefRedGate_L2", this.spleefRedGate_L2);
		this.writeConfigLocationValue("spleefBlueGate_L1", this.spleefBlueGate_L1);
		this.writeConfigLocationValue("spleefBlueGate_L2", this.spleefBlueGate_L2);
		this.writeConfigLocationValue("spleefGreenGate_L1", this.spleefGreenGate_L1);
		this.writeConfigLocationValue("spleefGreenGate_L2", this.spleefGreenGate_L2);
		this.writeConfigLocationValue("spleefYellowGate_L1", this.spleefYellowGate_L1);
		this.writeConfigLocationValue("spleefYellowGate_L2", this.spleefYellowGate_L2);
		this.writeConfigLocationValue("spleefLatestWinsSign", this.spleefLatestWinsSign);
		this.writeConfigLocationValue("spleefScoreBoardSign", this.spleefScoreBoardSign);
		this.writeConfigLocationValue("spleefScoreBoardSignExtra", this.spleefScoreBoardSignExtra);
		this.writeConfigLocationValue("spleefSpectatorRed", this.spleefSpectatorRed);
		this.writeConfigLocationValue("spleefSpectatorBlue", this.spleefSpectatorBlue);
		this.writeConfigLocationValue("spleefSpectatorGreen", this.spleefSpectatorGreen);
		this.writeConfigLocationValue("spleefSpectatorYellow", this.spleefSpectatorYellow);
		this.writeConfigValue("dbUrl", this.url);
		this.writeConfigValue("dbUser", this.user);
		this.writeConfigValue("dbPassword", this.password);

		this.saveConfig();
	}
	
	public void loadConfiguration()
	{
		this.spleefArena_L1 = getLocationFromConfig("spleefArena_L1");
		this.spleefArena_L2 = getLocationFromConfig("spleefArena_L2");
		this.spleefDropNet_L1 = getLocationFromConfig("spleefDropNet_L1");
		this.spleefDropNet_L2 = getLocationFromConfig("spleefDropNet_L2");
		this.spleefRedRoom_L1 = getLocationFromConfig("spleefRedRoom_L1");
		this.spleefRedRoom_L2 = getLocationFromConfig("spleefRedRoom_L2");
		this.spleefBlueRoom_L1 = getLocationFromConfig("spleefBlueRoom_L1");
		this.spleefBlueRoom_L2 = getLocationFromConfig("spleefBlueRoom_L2");
		this.spleefGreenRoom_L1 = getLocationFromConfig("spleefGreenRoom_L1");
		this.spleefGreenRoom_L2 = getLocationFromConfig("spleefGreenRoom_L2");
		this.spleefYellowRoom_L1 = getLocationFromConfig("spleefYellowRoom_L1");
		this.spleefYellowRoom_L2 = getLocationFromConfig("spleefYellowRoom_L2");
		this.spleefPadRed_L1 = getLocationFromConfig("spleefPadRed_L1");
		this.spleefPadRed_L2 = getLocationFromConfig("spleefPadRed_L2");
		this.spleefPadBlue_L1 = getLocationFromConfig("spleefPadBlue_L1");
		this.spleefPadBlue_L2 = getLocationFromConfig("spleefPadBlue_L2");
		this.spleefPadGreen_L1 = getLocationFromConfig("spleefPadGreen_L1");
		this.spleefPadGreen_L2 = getLocationFromConfig("spleefPadGreen_L2");
		this.spleefPadYellow_L1 = getLocationFromConfig("spleefPadYellow_L1");
		this.spleefPadYellow_L2 = getLocationFromConfig("spleefPadYellow_L2");
		this.spleefPreRoomPort = getLocationFromConfig("spleefPreRoomPort");
		this.spleefRedRoomPort = getLocationFromConfig("spleefRedRoomPort");
		this.spleefBlueRoomPort = getLocationFromConfig("spleefBlueRoomPort");
		this.spleefGreenRoomPort = getLocationFromConfig("spleefGreenRoomPort");
		this.spleefYellowRoomPort = getLocationFromConfig("spleefYellowRoomPort");
		this.spleefRedGate_L1 = getLocationFromConfig("spleefRedGate_L1");
		this.spleefRedGate_L2 = getLocationFromConfig("spleefRedGate_L2");
		this.spleefBlueGate_L1 = getLocationFromConfig("spleefBlueGate_L1");
		this.spleefBlueGate_L2 = getLocationFromConfig("spleefBlueGate_L2");
		this.spleefGreenGate_L1 = getLocationFromConfig("spleefGreenGate_L1");
		this.spleefGreenGate_L2 = getLocationFromConfig("spleefGreenGate_L2");
		this.spleefYellowGate_L1 = getLocationFromConfig("spleefYellowGate_L1");
		this.spleefYellowGate_L2 = getLocationFromConfig("spleefYellowGate_L2");
		this.spleefLatestWinsSign = getLocationFromConfig("spleefLatestWinsSign");
		this.spleefScoreBoardSign = getLocationFromConfig("spleefScoreBoardSign");
		this.spleefScoreBoardSignExtra = getLocationFromConfig("spleefScoreBoardSignExtra");
		this.spleefSpectatorRed = getLocationFromConfig("spleefSpectatorRed");
		this.spleefSpectatorBlue = getLocationFromConfig("spleefSpectatorBlue");
		this.spleefSpectatorGreen = getLocationFromConfig("spleefSpectatorGreen");
		this.spleefSpectatorYellow = getLocationFromConfig("spleefSpectatorYellow");
		this.url = getStringFromConfig("dbUrl");
		this.user = getStringFromConfig("dbUser");
		this.password = getStringFromConfig("dbPassword");
	}

	public void outputToConsole(String messageText, Level messageLevel)
	{
		log.log(messageLevel, String.format("%s %s", Constants.pluginConsoleTag, messageText));
	}

	public void outputToPlayer(String messageText, Player player)
	{
		player.sendMessage(String.format("%s: %s", Constants.pluginOutputTag, messageText));
	}

}
