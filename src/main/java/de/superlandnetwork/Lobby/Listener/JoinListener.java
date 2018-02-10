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

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import de.superlandnetwork.API.API;
import de.superlandnetwork.API.PlayerAPI.PlayerAPI;
import de.superlandnetwork.API.Utils.Tablist;
import de.superlandnetwork.Lobby.Main;

public class JoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoinItemGive(PlayerJoinEvent e){
		Player p = e.getPlayer();
		p.getInventory().clear();
		p.setGameMode(GameMode.ADVENTURE);
		p.setHealth(20D);
		p.setFoodLevel(20);
		Main.giveItems(p);
		Main.SetScorbord(p);
		Main.RegisterTeams(p);
		if(Main.HideAll.contains(e.getPlayer().getName()))
			HideAll(e.getPlayer().getName());
		else if(Main.ShowAll.contains(e.getPlayer().getName()))
			ShowAll(e.getPlayer().getName());
		else if(Main.ShowTeam.contains(e.getPlayer().getName()))
			ShowTeam(e.getPlayer().getName());
		else
			Main.ShowAll.add(e.getPlayer().getName());
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(Main.HideAll.contains(all.getName())) {	
				all.hidePlayer(e.getPlayer());
			} else if(Main.ShowTeam.contains(all.getName())) {
				if(!e.getPlayer().hasPermission("ccl.Lobby.YouTeam"))
					all.hidePlayer(e.getPlayer());
			}
		}
		for(Player all : Bukkit.getOnlinePlayers()){
			Main.setPrefix(all);
		}
		new Tablist(p, "§eSuperLandNetwork.de Netzwerk §7- §aLobby", "§7Teamspeak: §eTs.SuperLandNetwork.de \n §7Shop: §eShop.SuperLandNetwork.de");
		e.setJoinMessage(null);
		if(API.getInstance().ServerID == 0) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(p.getName() != all.getName()) {
					all.hidePlayer(p);
					p.hidePlayer(all);
				}
			}
			p.sendMessage("§eDu §ebist §enun §ein §eder §eSilentLobby. §eHier §ebist §edu §evöllig §eungestört!");
		}
		if(Main.HideAll.size() != 0) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(Main.HideAll.contains(all.getName())) {
					all.hidePlayer(p);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		Main.removeScorebord(p);
		e.setQuitMessage(null);
		if(Main.HideAll.size() != 0) {
			if(Main.HideAll.contains(p.getName()))
				Main.HideAll.remove(p.getName());
		}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		PlayerAPI api = new PlayerAPI(e.getPlayer().getUniqueId());
		if (e.getResult() == Result.KICK_FULL) {
			if (api.IsPlayerInGroup(17) || api.IsPlayerInGroup(18)) {
				e.allow();
			}
		}
		if (e.getResult() == Result.KICK_WHITELIST) {
			e.disallow(Result.KICK_WHITELIST, "§4Das SuperLandNetwork.de Netzwerk ist zurzeit in der Closed Alpha!");
		}
	}
	
	/**
	 * @param name
	 */
	private void HideAll(String name) {
		Player p = Bukkit.getPlayer(name);
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(all.getName() != name)
				p.hidePlayer(all);
		}
	}

	/**
	 * @param name
	 */
	private void ShowTeam(String name) {
		Player p = Bukkit.getPlayer(name);
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(!all.hasPermission("ccl.Lobby.YouTeam")) {
				if(all.getName() != name)
					p.hidePlayer(all);
			} else {
				p.showPlayer(all);
			}
		}
	}

	/**
	 * @param name
	 */
	private void ShowAll(String name) {
		Player p = Bukkit.getPlayer(name);
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(all.getName() != name)
				p.showPlayer(all);
		}
	}
	
}
