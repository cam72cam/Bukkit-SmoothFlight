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
	public int maxHeight;
	public int minHeight;
	
	private SFPlayerListener listener;
	
	@Override
	public void onEnable()
	{
		config();
		listener = new SFPlayerListener(this);
		fixLogger();
	}
	
	private void config()
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
		
		String base = "smoothflight.fly.";
		
		flySpeed = config.getDouble(base + "speed", 1.0);
		config.set(base + "speed", flySpeed);
		
		flySpeedSneak = config.getDouble(base+"flySpeedSneak", 0.5);
		config.set(base+"flySpeedSneak", flySpeedSneak);
		
		hunger = config.getInt(base+"hunger", 20);
		config.set(base+"hunger", hunger);
		
		opHunger = config.getBoolean(base+"opHunger", true);
		config.set(base+"opHunger", opHunger);
		
		smoke = config.getBoolean(base+"smoke", true);
		config.set(base+"smoke", smoke);
		
		maxHeight = config.getInt(base+"maxHeight", 200);
		config.set(base+"maxHeight", maxHeight);
		
		minHeight = config.getInt(base+"minHeight", 50);
		config.set(base+"minHeight", minHeight);
		
		saveConfig();
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
}