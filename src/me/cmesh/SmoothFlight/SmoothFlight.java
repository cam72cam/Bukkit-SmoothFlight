package me.cmesh.SmoothFlight;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SmoothFlight extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public int flyTool;
	public double flySpeed;
	public int hunger;
	public boolean opHunger; 
	public boolean smoke;
	
	@Override
	public void onEnable()
	{
		FileConfiguration config = getConfig();
		 
		flyTool = config.getInt("smoothflight.fly.tool", 288);
		config.set("smoothflight.fly.tool", flyTool);
		
		flySpeed = config.getDouble("smoothflight.fly.speed", 1.0);
		config.set("smoothflight.fly.speed", flySpeed);
		
		hunger = config.getInt("smoothflight.fly.hunger", 20);
		config.set("smoothflight.fly.hunger", hunger);
		
		opHunger = config.getBoolean("smoothflight.fly.opHunger", true);
		config.set("smoothflight.fly.opHunger", opHunger);
		
		smoke = config.getBoolean("smoothflight.fly.smoke", true);
		config.set("smoothflight.fly.opHunger", smoke);
		
		saveConfig();
		
		new SFPlayerListener(this);
	}	
	@Override
	public void onDisable() { }
	
	public boolean hasPermission(Player player, String permission)
	{
		return player.isOp() || player.hasPermission(permission);
	}
}
