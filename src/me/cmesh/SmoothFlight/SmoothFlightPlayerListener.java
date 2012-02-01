package me.cmesh.SmoothFlight;

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
<<<<<<< HEAD
		if (plugin.hasPermission(player,"smoothflight.fly"))
			if (plugin.flyTool == player.getItemInHand().getTypeId())
				if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
=======
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			if (plugin.flyTool == player.getItemInHand().getTypeId())
				if (plugin.hasPermission(player,"smoothflight.fly") || plugin.anyone)
>>>>>>> b5f017eda62d25733a102261955754b63384fe89
				{
					Vector dir = player.getLocation().getDirection().multiply(plugin.flySpeed);
					dir.setY(dir.getY()+0.60);
					player.setVelocity(dir);
					player.setFallDistance(0);
					if (!plugin.hasPermission(player,"smoothflight.nobreak"))
					{
						player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
						if(player.getItemInHand().getDurability() >= plugin.damageBreak)
						{
							if(player.getItemInHand().getAmount() == 1)
							{
								player.setItemInHand(null);
							}
							else
							{
								player.getItemInHand().setAmount(player.getItemInHand().getAmount() -1);
								player.getItemInHand().setDurability((short) 0);
							}
						}
					}
					return;
				}
	}
}
