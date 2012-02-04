package me.cmesh.SmoothFlight;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SFPlayerListener implements Listener
{
	private static SmoothFlight plugin;
	
	public HashMap<String, SFPlayer> Players = new HashMap<String, SFPlayer>();
	
	public SFPlayerListener(SmoothFlight smoothFlight) 
	{
		plugin = smoothFlight;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent event)
	{
		SFPlayer player = getPlayer(event.getPlayer());
		if(event.getReason().contains("Flying") && player.lastFly != null && player.lastFly >= player.self().getWorld().getTime() - 100)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Players.remove(event.getPlayer().getName());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		SFPlayer player = getPlayer(event.getPlayer());
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			if(player.canFly())
			{
				player.fly();
			}
	}
	
	private void setPlayer(Player player)
	{
		Players.put(player.getName(), new SFPlayer(player, plugin));
	}
	
	private SFPlayer getPlayer(Player player)
	{
		String key = player.getName();
		if(!Players.containsKey(key))
		{
			setPlayer(player);
		}
		return Players.get(key);
	}
}
