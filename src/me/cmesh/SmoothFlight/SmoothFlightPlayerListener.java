package me.cmesh.SmoothFlight;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.Vector;

public class SmoothFlightPlayerListener extends PlayerListener
{
	private static SmoothFlight plugin;
	
	public SmoothFlightPlayerListener(SmoothFlight smoothFlight) 
	{
		plugin = smoothFlight;
	}
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (plugin.hasPermission(player,"smoothflight.fly"))
			if (plugin.flyTool == event.getPlayer().getItemInHand().getTypeId())
				if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
				{
					Vector dir = player.getLocation().getDirection().multiply(plugin.flySpeed);
					dir.setY(dir.getY()+0.60);
					player.setVelocity(dir);
					player.setFallDistance(0);
				}
	}
}
