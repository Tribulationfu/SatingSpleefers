package me.Kruithne.SatingSpleefers;

public class Constants {
	
	public static String pluginConsoleTag = "[SatingSpleefers]";
	public static String pluginOutputTag = "[Spleef]";
	
	public static String configDirectory = "plugins/SatingSpleefers";
	public static String configFileName = "satingspleefers.config";
	public static String configDescription = "Configuration file for SatingSpleefers";
	public static String configFailed = "Failed to create the configuration file, using built-in config.";
	public static String configWriteFailed = "Unable to write config, set it up correctly.";
	
	public static String coordFormat = "%s, %s, %s, %s";
	
	public static String commandErrorNoCommand = "That is not the command you are looking for. /spleef for command help.";	
	
	public static String permissionAdmin = "satingspleefers.admin";
	public static String configSettingPath = "satingspleefers.%s";
	
	public static String commandNotice_arenaSet = "The arena boundries have been set";
	public static String commandNotice_netSet = "The arena drop net boundries have been set";
	public static String commandNotice_gateSet = "The %s gate has been identified";
	public static String commandNotice_padSet = "The %s pad boundries have been set";
	public static String commandNotice_roomSet = "The boundries for the %s room have been set";
	public static String commandNotice_roomPort = "The port for the %s room has been set to your location";
	public static String commandNotice_spectatorPort = "The port for the %s spectator wing has been set to your location";
	public static String commandNotice_floorRepair = "The arena floor has been repaired";
	public static String commandNotice_signSet = "The %s sign has been set";
	
	public static String warpSignInactive = "Sorry, this warp sign is currently not working";
		
	
	public static String spleefCastNewGameStarting_minute = "New spleef match starting in %s minute! (Move to your selected colour pad in the lobby to play!)";
	public static String spleefCastNewGameStarting_seconds = "New spleef match starting in %s seconds!";
	public static String spleefCastNewGameStarted = "The spleef match has begun, gates opening in 10 seconds!";
	public static String spleefCastNewGameNoPlayers = "Not enough players, match cancelled.";
	public static String spleefCastBeginMatch = "Let the spleefing begin!";
	public static String spleefCastBadEnd = "The match concluded with no winner.";
	public static String spleefCastWinner = "%s has triumphed in the spleef arena!";
	
	public static String SQLError = "SQL Error: %s";
	
	public static String debug_staleMatch = "No players found within the arena boundries: ending stale";
	public static String debug_matchWon = "Single player located inside arena boundries: ending winner";
	public static String debug_winnerNull = "Single player found, not registered, ending stale";
	public static String debug_endedGame = "Ended the game";
	public static String debug_initGame = "Setting up new game and queing it.";
	
}
