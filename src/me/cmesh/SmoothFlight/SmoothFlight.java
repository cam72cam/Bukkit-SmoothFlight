package me.cmesh.SmoothFlight;

import java.util.logging.*;

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
	
	private SFPlayerListener listener;
	
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
		
		saveConfig();
		
		listener = new SFPlayerListener(this);
		fixLogger();
	}
	
	private void fixLogger()
	{
		log.setFilter(new Filter() 
		{
            public boolean isLoggable(LogRecord record)
            {
                if(record.getMessage().toLowerCase().endsWith(" was kicked for floating too long!"))
                {
                	Player p = getServer().getPlayer(record.getMessage().split(" ")[0]);
                    SFPlayer player = listener.Players.get(p.getUniqueId());
                    return !player.isFlying();
                }
                return true;
            }
        });
	}
	
	@Override
	public void onDisable() { }
}