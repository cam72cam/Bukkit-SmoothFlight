package me.cmesh.SmoothFlight;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SFPlayer 
{
	private static SmoothFlight plugin;
	
	private Player player;
	public Long lastFly;
	
	public Player self() {return player;}
	
	public SFPlayer(Player player, SmoothFlight instance)
	{
		this.player = player;
		plugin = instance;
	}
	
	public boolean canFly()
	{
		return 
			plugin.hasPermission(player,"smoothflight.fly")
			&& plugin.flyTool == player.getItemInHand().getTypeId()
			&& player.getFoodLevel() > 0;
	}
	
	public void fly()
	{
		if(plugin.smoke)
		{
			SmokeUtil.spawnCloudRandom(player.getLocation(), 4);
		}
		
		Vector dir = player.getLocation().getDirection().multiply(plugin.flySpeed);
		dir.setY(dir.getY()+0.60);
		player.setVelocity(dir);
		player.setFallDistance(0);
		
		if((!player.isOp() || plugin.opHunger) && new Random().nextInt(100) < plugin.hunger)
		{
			player.setFoodLevel(player.getFoodLevel()-1);
		}
		
		lastFly = player.getWorld().getTime();
	}
}