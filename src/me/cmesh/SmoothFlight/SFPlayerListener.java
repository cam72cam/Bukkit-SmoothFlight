package me.cmesh.SmoothFlight;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SFPlayerListener implements Listener
{
	private static SmoothFlight plugin;
	
	public HashMap<UUID, SFPlayer> Players = new HashMap<UUID, SFPlayer>();
	
	public SFPlayerListener(SmoothFlight smoothFlight) 
	{
		plugin = smoothFlight;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerKick(PlayerKickEvent event)
	{
		SFPlayer player = getPlayer(event.getPlayer());
		if(player.isFlying())
		{
<<<<<<< HEAD
			event.setCancelled(true);
=======
			String reason = event.getReason();
			if (reason != null && reason.equals("Flying is not enabled on this server"))
			{
				event.setReason("");
				event.setCancelled(true);
			}
>>>>>>> 44349f9e9bd887cd73394637007d58c84ff1882c
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Players.remove(event.getPlayer().getUniqueId());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		SFPlayer player = getPlayer(event.getPlayer());
		Action action = event.getAction();
		
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
			if(player.canFly())
				player.fly();
		
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
			player.toggleHover();
		
		if (player.isHovering() && player.getTool() != plugin.flyTool)
			player.toggleHover();
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		SFPlayer player = getPlayer(event.getPlayer());
		if(player.isHovering())
		{
			player.hover();
		}
	}
	
	private void setPlayer(Player player)
	{
		Players.put(player.getUniqueId(), new SFPlayer(player, plugin));
	}
	
	private SFPlayer getPlayer(Player player)
	{
		UUID key = player.getUniqueId();
		if(!Players.containsKey(key))
		{
			setPlayer(player);
		}
		return Players.get(key);
	}
}
