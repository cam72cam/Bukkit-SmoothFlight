package me.cmesh.SmoothFlight;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SFPlayer 
{
	private static SmoothFlight plugin;
	
	private Player player;
	private Long lastFly;
	protected boolean hover;
	
	public Player self() {return player;}
	
	public SFPlayer(Player player, SmoothFlight instance)
	{
		this.player = player;
		plugin = instance;
	}
	
	public boolean canFly()
	{
		return 
			(!plugin.usePermissions || plugin.hasPermission(player,"smoothflight.fly"))
			&& plugin.flyTool == player.getItemInHand().getType()
			&& player.getFoodLevel() > 0;
	}
	
	public void fly()
	{
		if(plugin.smoke)
			SmokeUtil.spawnCloudRandom(player.getLocation(), 4);
		
		double speed = player.isSneaking() ? plugin.flySpeedSneak : plugin.flySpeed;
		
		Vector dir = player.getLocation().getDirection();
		dir = dir.multiply(speed);
		dir.setY(dir.getY()+0.60 * speed);
		player.setVelocity(dir);
		player.setFallDistance(0);
		
		if((!player.isOp() || plugin.opHunger) && new Random().nextInt(100) < plugin.hunger)
			player.setFoodLevel(player.getFoodLevel()-1);
		
		lastFly = player.getWorld().getTime();
	}
	
	public void setHover()
	{
		if(hover)
			hover = false;
		else if(canFly())
			hover = true;
	}
	
	public void hover()
	{
		if(player.getFoodLevel() <= 1)
			hover = false;
		
		if(plugin.smoke)
			SmokeUtil.spawnCloudRandom(player.getLocation(), 1);
		
		player.setVelocity(new Vector(0,0.1D,0));
		player.setFallDistance(0);
		
		if((!player.isOp() || plugin.opHunger) && new Random().nextInt(100) < plugin.hunger/2)
			player.setFoodLevel(player.getFoodLevel()-1);
		
		lastFly = player.getWorld().getTime();
	}

	public boolean isFlying() {
		return lastFly != null && lastFly >= (player.getWorld().getTime() - 100);
	}
}