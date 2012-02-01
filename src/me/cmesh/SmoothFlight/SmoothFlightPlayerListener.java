package me.cmesh.SmoothFlight;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class SmoothFlightPlayerListener implements Listener
{
	private static SmoothFlight plugin;
	
	public SmoothFlightPlayerListener(SmoothFlight smoothFlight) 
	{
		plugin = smoothFlight;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (plugin.hasPermission(player,"smoothflight.fly"))
			if (plugin.flyTool == player.getItemInHand().getTypeId())
				if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
				{
					Vector dir = player.getLocation().getDirection().multiply(plugin.flySpeed);
					dir.setY(dir.getY()+0.60);
					player.setVelocity(dir);
					player.setFallDistance(0);
					
					if(/*!player.isOp() &&*/ new Random().nextInt(100) < 20)
					{
						player.setFoodLevel(player.getFoodLevel()-1);
					}
				}
	}
}
