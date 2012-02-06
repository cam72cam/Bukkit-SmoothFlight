package me.cmesh.SmoothFlight;

//import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SmoothFlight extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public Material flyTool;
	public double flySpeed;
	public double flySpeedSneak;
	public int hunger;
	public boolean opHunger; 
	public boolean smoke;
	public boolean usePermissions;
	
	@Override
	public void onEnable()
	{
		FileConfiguration config = getConfig();
		
		try
		{
			flyTool = Material.getMaterial(config.getString("smoothflight.fly.tool", Material.FEATHER.name()));
			config.set("smoothflight.fly.tool", flyTool.name());
		}
		catch (java.lang.NullPointerException e)
		{
			log.info("[SMOOTHFLIGHT] Could not load material: " + config.getString("smoothflight.fly.tool"));
		}
		
		flySpeed = config.getDouble("smoothflight.fly.speed", 1.0);
		config.set("smoothflight.fly.speed", flySpeed);
		
		flySpeedSneak = config.getDouble("smoothflight.fly.flySpeedSneak", 0.5);
		config.set("smoothflight.fly.flySpeedSneak", flySpeedSneak);
		
		hunger = config.getInt("smoothflight.fly.hunger", 20);
		config.set("smoothflight.fly.hunger", hunger);
		
		opHunger = config.getBoolean("smoothflight.fly.opHunger", true);
		config.set("smoothflight.fly.opHunger", opHunger);
		
		smoke = config.getBoolean("smoothflight.fly.smoke", true);
		config.set("smoothflight.fly.smoke", smoke);
		
		usePermissions = config.getBoolean("smoothflight.fly.usePermissions", true);
		config.set("smoothflight.fly.usePermissions", usePermissions);
		
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