package me.cmesh.SmoothFlight;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SmoothFlight extends JavaPlugin
{
	public int flyTool;
	public double flySpeed;
	
	@Override
	public void onEnable()
	{
		FileConfiguration config = getConfig();
		
		flyTool = config.getInt("smoothflight.fly.tool", 288);
		config.set("smoothflight.fly.tool", 288);
		flySpeed = config.getDouble("smoothflight.fly.speed", 1.0);
		config.set("smoothflight.fly.speed", 1.0);
		
		saveConfig();
		
		new SmoothFlightPlayerListener(this);
	}
	
	public boolean hasPermission(Player player, String permission)
	{
		return player.isOp() || player.hasPermission(permission);
	}
	
	@Override
	public void onDisable() { }
}
