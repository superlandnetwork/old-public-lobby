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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class DamageListener implements Listener{

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		e.setDeathMessage(null);
		e.setKeepInventory(true);
		e.setKeepLevel(true);
		e.setDroppedExp(0);
	}
	
	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onExploit(EntityExplodeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDemage(EntityDamageEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDemageByEntity(EntityDamageByEntityEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDemageByBlock(EntityDamageByBlockEvent e){
		e.setCancelled(true);
	}
	
}
