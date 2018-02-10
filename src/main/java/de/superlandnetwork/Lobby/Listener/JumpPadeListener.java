//  _            _      _           
// | |     ___  | |__  | |__   _  _ 
// | |__  / _ \ | '_ \ | '_ \ | || |
// |____| \___/ |_.__/ |_.__/  \_, |
//                             |__/ 
//
// Copyright (C) Filli-IT (Einzelunternehmen) & Ursin Filli - All Rights Reserverd
// Unauthorized copying of the this file, via any medium is strictly prohibited
// Proprietary and confidential
// Written by Ursin Filli <ursin.filli@Filli-IT.ch>

package de.superlandnetwork.Lobby.Listener;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumpPadeListener implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void JumpPadeMove(PlayerMoveEvent e){
		if(e.getPlayer().getLocation().subtract(0.0D, 0.0D, 0.0D).getBlock().getType() == Material.STONE_PLATE){
			Player p = e.getPlayer();
			World w = p.getWorld();
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			p.playEffect(new Location(w, x, y, z), Effect.ENDER_SIGNAL, 10);
			p.playSound(new Location(w, x, y, z), Sound.UI_BUTTON_CLICK, 10.0F, 10.0F);
			Vector v = p.getLocation().getDirection().multiply(4.0D).setY(0.7D);
			p.setVelocity(v);
		}
	}
	
}
