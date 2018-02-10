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

package de.superlandnetwork.Lobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scedular {

	public static int CD;
	
	public void startScedular(){
		CD = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					Main.removeScorebord(all);
					Main.SetScorbord(all);
				}
			}

		}, 1200L, 1200L);
	}
	
}
