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

public enum LobbyLocationEnum {
	
	Spawn(249D, 101D, 32D, 0F),
	OnInTheCamber(352.500D, 99D, 32.500D, 270F),
	KnockbackFFA(249.500D, 99D, -70.500D, 180F),
	SurvivalGames(147.500D, 99D, -69.500D, 135F),
	SkyWars(249.500D, 99D, 135.500D, 0F),
	BedWars(146.500D, 99D, 32.500D, 90F),
	
	TTT(249D, 101D, 32D, 0F),
	Community(249D, 101D, 32D, 0F),
	CityBuild(249D, 101D, 32D, 0F);


	public double Loc1;
	public double Loc2;
	public double Loc3;
	public float LocFace;
	
	private LobbyLocationEnum(double Loc1, double Loc2, double Loc3, float Face) {
		this.Loc1 = Loc1;
		this.Loc2 = Loc2;
		this.Loc3 = Loc3;
		this.LocFace = Face;
	}
	
	public double getLocX() {
		return Loc1;
	}
	
	
	public double getLocY() {
		return Loc2;
	}
	
	public double getLocZ() {
		return Loc3;
	}
	
	public float getFace() {
		return LocFace;
	}
}
