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

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.superlandnetwork.Lobby.Main;

public class SignListener implements Listener {

	
	@EventHandler
	public void SignIntercat(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN){
				Sign s = (Sign)e.getClickedBlock().getState();
//				System.out.println("Linie 1: " + s.getLine(1));
//				System.out.println("Loc: " + s.getLocation().toString());
//				System.out.println("byte: " + s.getBlock().getData());
				if(s.getLine(1).equalsIgnoreCase("[§aLobby§0]")){
					/** KFFA **/
					if (s.getLine(0).equalsIgnoreCase("KFFA - 1")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "KFFA1");
					} else if (s.getLine(0).equalsIgnoreCase("KFFA - 2")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "KFFA2");
					} else if (s.getLine(0).equalsIgnoreCase("KFFA - 3")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "KFFA3");
					} else if (s.getLine(0).equalsIgnoreCase("KFFA - 4")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "KFFA4");
					} else if (s.getLine(0).equalsIgnoreCase("KFFA - 5")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "KFFA5");
					} else if (s.getLine(0).equalsIgnoreCase("KFFA - 6")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "KFFA6");
					/** OITC **/
					} else if (s.getLine(0).equalsIgnoreCase("OITC - 1")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "OITC1");
					} else if (s.getLine(0).equalsIgnoreCase("OITC - 2")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "OITC2");
					} else if (s.getLine(0).equalsIgnoreCase("OITC - 3")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "OITC3");
					} else if (s.getLine(0).equalsIgnoreCase("OITC - 4")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "OITC4");
					} else if (s.getLine(0).equalsIgnoreCase("OITC - 5")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "OITC5");
					} else if (s.getLine(0).equalsIgnoreCase("OITC - 6")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "OITC6");
					/** BedWars **/
					} else if (s.getLine(0).equalsIgnoreCase("BedWars - 1")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "BedWars1");
					} else if (s.getLine(0).equalsIgnoreCase("BedWars - 2")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "BedWars2");
					} else if (s.getLine(0).equalsIgnoreCase("BedWars - 3")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "BedWars3");
					} else if (s.getLine(0).equalsIgnoreCase("BedWars - 4")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "BedWars4");
					} else if (s.getLine(0).equalsIgnoreCase("BedWars - 5")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "BedWars5");
					} else if (s.getLine(0).equalsIgnoreCase("BedWars - 6")) {
						Main.getInstance().sendConnect(e.getPlayer().getName(), "BedWars6");
					}
				}
			}
		}
	}
	
}
