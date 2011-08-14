package me.cmesh.SmoothFlight;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class SmoothFlight extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	private final SmoothFlightPlayerListener playerListener = new SmoothFlightPlayerListener(this);
	public PermissionHandler Permissions = null;
	public int flyTool;
	public double flySpeed;
	
	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
	
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
		Plugin permissions = getServer().getPluginManager().getPlugin("Permissions");
		
		if (Permissions == null)
		{
			Permissions = (permissions != null) ? ((Permissions)permissions).getHandler() : null;
		}

		reload();
		
		log.info(getDescription().getName()+" version "+getDescription().getVersion()+" is enabled!");
	}

	public void onDisable() 
	{
		log.info(getDescription().getName()+" version "+getDescription().getVersion()+" is disabled!");
	}
	
	private void reload()
	{
		getConfiguration().load();
		flyTool = getConfiguration().getInt("smoothflight.fly.tool", 288);
		flySpeed = getConfiguration().getDouble("smoothflight.fly.speed", 1.0);
		getConfiguration().save();
	}
	public boolean hasPermission(Player player, String permission)
	{
		return 
		player.isOp() 
		|| player.hasPermission(permission) 
		|| (Permissions ==null ? false : Permissions.has(player,permission));
	}
}
