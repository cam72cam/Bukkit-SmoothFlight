package me.cmesh.SmoothFlight;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SFPlayer 
{
	private static SmoothFlight plugin;
	
	private Player player;
	private Long lastFly;
	private boolean hover;
	
	public SFPlayer(Player player, SmoothFlight instance)
	{
		this.player = player;
		plugin = instance;
	}
	
	public Material getTool()
	{
		return player.getItemInHand().getType();
	}
	
	private boolean hasPermission(String permission)
	{
		return player.isOp() || player.hasPermission(permission);
	}
	
	public boolean canFly()
	{
		return hasPermission("smoothflight.fly")
			&& plugin.flyTool == player.getItemInHand().getType()
			&& player.getFoodLevel() > 0;
	}
	
	public void fly()
	{
		if(plugin.smoke)
			SmokeUtil.spawnCloudRandom(player.getLocation(), 4);
		
		double speed = player.isSneaking() ? plugin.flySpeedSneak : plugin.flySpeed;
		
		Vector dir	=	player.getLocation().getDirection();
		dir = dir.multiply(speed);
		dir.setY(dir.getY()+0.60 * speed);
		player.setVelocity(dir);
		
		player.setFallDistance(0);
		
		hunger(plugin.hunger);
		
		lastFly = player.getWorld().getTime();
	}
	
	public void toggleHover()
	{
		if(hover)
			hover = false;
		else if(canFly())
			hover = true;
	}
	
	public boolean isHovering()
	{
		return hover;
	}
	
	public void hover()
	{
		if(player.getFoodLevel() <= 1 || player.getItemInHand().getType() != plugin.flyTool)
			hover = false;
		
		if(plugin.smoke)
			SmokeUtil.spawnCloudRandom(player.getLocation(), 1);
		
		player.setVelocity(new Vector(0,0.1D,0));
		player.setFallDistance(0);
		
		hunger(plugin.hunger/2);
		
		lastFly = player.getWorld().getTime();
	}

	private void hunger(int hunger)
	{
		if((!player.isOp() || plugin.opHunger) && !hasPermission("smoothflight.nohunger") && new Random().nextInt(100) < hunger)
			player.setFoodLevel(player.getFoodLevel()-1);
	}
	
	public boolean isFlying() 
	{
		return lastFly != null && lastFly >= (player.getWorld().getTime() - 100);
	}
}