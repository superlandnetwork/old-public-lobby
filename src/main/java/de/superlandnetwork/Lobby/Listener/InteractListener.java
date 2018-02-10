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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.superlandnetwork.API.API;
import de.superlandnetwork.API.PlayerAPI.PlayerAPI;
import de.superlandnetwork.API.Utils.ItemBuilder;
import de.superlandnetwork.Lobby.LobbyLocationEnum;
import de.superlandnetwork.Lobby.Main;

public class InteractListener implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getMaterial() == Material.COMPASS){
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§aNavigator §7(Rechtskick)"){
					e.getPlayer().openInventory(Main.CommpasInv(e.getPlayer().getName()));
					return;
				}
				return;
			}
			if(e.getMaterial() == Material.SKULL_ITEM){
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§eFreunde §7(Rechtskick)"){
					e.getPlayer().openInventory(Main.FriendsInv(e.getPlayer().getUniqueId().toString()));
					return;
				}
				return;
			}
			if(e.getMaterial() == Material.NETHER_STAR){
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§bLobby wechseln"){
					e.getPlayer().openInventory(Main.ServerInv());
					return;
				}
				return;
			}
			if(e.getMaterial() == Material.BLAZE_ROD){
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§6Spieler verstecken") {
					e.getPlayer().openInventory(Main.HideInv(e.getPlayer().getName()));
					e.setCancelled(true);
				}
				return;
			}
			if(e.getMaterial() == Material.TNT){
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§5Silent Lobby") {
					Main.getInstance().sendConnect(e.getPlayer().getName(), "Lobby0");
					return;
				}
				return;
			}
			if(e.getMaterial() == Material.NAME_TAG){
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§5AutoNick §c<Deaktiviert>"){
					PlayerAPI api = new PlayerAPI(e.getPlayer().getUniqueId());
					api.setAutoNick(1);
					e.getPlayer().sendMessage("§7[§5NICK§7] §aAutoNick Aktiviert!");
					ItemStack getNick = new ItemBuilder().getItem("§5AutoNick §a<Aktiviert>", Material.NAME_TAG, 1);
					e.getPlayer().getInventory().setItem(5, getNick);
					return;
				}
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§5AutoNick §a<Aktiviert>"){
					PlayerAPI api = new PlayerAPI(e.getPlayer().getUniqueId());
					api.setAutoNick(0);
					e.getPlayer().sendMessage("§7[§5NICK§7] §cAutoNick Deaktiviert!");
					ItemStack getNick = new ItemBuilder().getItem("§5AutoNick §c<Deaktiviert>", Material.NAME_TAG, 1);
					e.getPlayer().getInventory().setItem(5, getNick);
					return;
				}
				return;
			}
			if(e.getMaterial() == Material.EYE_OF_ENDER){
				if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§5Schutzschild §c<Deaktiviert>" || e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§5Schutzschild §a<Aktiviert>"){
					//TODO: Schutzschild | Lobby - SLN
					e.getPlayer().sendMessage("§7[§6System§7] §eCurrent §eDisabled!");
					e.setCancelled(true);
					return;
				}
				return;
			}
			return;
		}
		return;
	}
	
	//----------------------------------------------------------------
	
	@EventHandler
	public void playerInteractInventory(InventoryClickEvent e){
		//Navigator 
		if(e.getInventory().getTitle().equalsIgnoreCase("§eWähle einen Spielmodus")){
			e.setCancelled(true);
			//Spawn
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Spawn")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.Spawn);
				return;
			}
			//KFFA
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3KnockbackFFA")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.KnockbackFFA);
				return;
			}
			//BedWars
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bBedWars")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.BedWars);
				return;
			}
			//OITC
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dOnInTheCamber")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.OnInTheCamber);
				return;
			}
			//Citybuild
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCitybuild")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.CityBuild);
				return;
			}
			//Community
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Community")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.Community);
				return;
			}
			//TTT
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4TTT")) {
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.TTT);
				return;
			}
			//SkyWars
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSkyWars")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.SkyWars);
				return;
			}
			//SurvivalGames
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSurvivalGames")){
				e.getWhoClicked().closeInventory();
				TP((Player) e.getWhoClicked(), LobbyLocationEnum.SurvivalGames);
				return;
			}
			return;
		}
		//Lobby Wechsler
		if(e.getInventory().getTitle().equalsIgnoreCase("§bLobby wechseln")){
			e.setCancelled(true);
			//Lobby - 1
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby 1")){
				e.getWhoClicked().closeInventory();
				if(API.getInstance().ServerID != 0)
					Main.getInstance().sendConnect(e.getWhoClicked().getName(), "Lobby1");
				else
					e.getWhoClicked().sendMessage("§7[§6System§7] §cDu §cbist §cbereits §cauf §cdiesem §cServer!");
				return;
			}
			//Lobby - 2
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby 2")){
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage("§7[§6System§7] §cDieser §cServer §cist §4OFFLINE!");
				return;
			}
			//Lobby - 3
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby 3")){
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage("§7[§6System§7] §cDieser §cServer §cist §4OFFLINE!");
				return;
			}
			//Lobby - 4
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby 4")){
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage("§7[§6System§7] §cDieser §cServer §cist §4OFFLINE!");
				return;
			}
			//Lobby - 5
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby 5")){
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage("§7[§6System§7] §cDieser §cServer §cist §4OFFLINE!");
				return;
			}
			//Lobby - 6
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby 6")){
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage("§7[§6System§7] §cDieser §cServer §cist §4OFFLINE!");
				return;
			}
			//Lobby - 7
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aLobby 7")){
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage("§7[§6System§7] §cDieser §cServer §cist §4OFFLINE!");
				return;
			}
			//Close
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")){
				e.getWhoClicked().closeInventory();
				return;
			}
			return;
		}
		//PlayerHide 
		if(e.getInventory().getTitle().equalsIgnoreCase("§eSpieler verstecken")){
			e.setCancelled(true);
			//ShowAll
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAlle Anzeigen")){
				e.getWhoClicked().closeInventory();
				ShowAll(e.getWhoClicked().getName());
				if(!Main.ShowAll.contains(e.getWhoClicked().getName()))
					Main.ShowAll.add(e.getWhoClicked().getName());
				if(Main.ShowTeam.contains(e.getWhoClicked().getName()))
					Main.ShowTeam.remove(e.getWhoClicked().getName());
				if(Main.HideAll.contains(e.getWhoClicked().getName()))
					Main.HideAll.remove(e.getWhoClicked().getName());
				return;
			}
					
			//ShowTeam
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Nur Youtuber / Teammitglieder")){
				e.getWhoClicked().closeInventory();
				ShowTeam(e.getWhoClicked().getName());
				if(!Main.ShowTeam.contains(e.getWhoClicked().getName()))
					Main.ShowTeam.add(e.getWhoClicked().getName());
				if(Main.ShowAll.contains(e.getWhoClicked().getName()))
					Main.ShowAll.remove(e.getWhoClicked().getName());
				if(Main.HideAll.contains(e.getWhoClicked().getName()))
					Main.HideAll.remove(e.getWhoClicked().getName());
				return;
			}
					
			//HideAll
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cAlle Verstecken")){
				e.getWhoClicked().closeInventory();
				HideAll(e.getWhoClicked().getName());
				if(!Main.HideAll.contains(e.getWhoClicked().getName()))
					Main.HideAll.add(e.getWhoClicked().getName());
				if(Main.ShowAll.contains(e.getWhoClicked().getName()))
					Main.ShowAll.remove(e.getWhoClicked().getName());
				if(Main.ShowTeam.contains(e.getWhoClicked().getName()))
					Main.ShowTeam.remove(e.getWhoClicked().getName());
				return;
			}
			return;
		}
		if(e.getWhoClicked().getGameMode() != GameMode.CREATIVE){
			 e.setCancelled(true);
		}
		return;
	}

	
	public void TP(Player p, LobbyLocationEnum LocationEnum){
		Location Loc = new Location(Bukkit.getWorld("Lobby"), LocationEnum.getLocX(), LocationEnum.getLocY(), LocationEnum.getLocZ());
		Loc.setYaw(LocationEnum.getFace());
		p.teleport(Loc);
	}
	
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
		if(e.getRightClicked().getType() == EntityType.ARMOR_STAND)
			e.setCancelled(true);

		if(e.getRightClicked().getType() == EntityType.PAINTING)
			e.setCancelled(true);
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
