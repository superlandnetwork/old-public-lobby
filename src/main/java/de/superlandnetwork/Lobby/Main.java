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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.superlandnetwork.API.API;
import de.superlandnetwork.API.PlayerAPI.PermEnum;
import de.superlandnetwork.API.PlayerAPI.PlayerAPI;
import de.superlandnetwork.API.Utils.Friends;
import de.superlandnetwork.API.Utils.ItemBuilder;
import de.superlandnetwork.API.Utils.ScorbordManager;
import de.superlandnetwork.Lobby.Listener.BuildListener;
import de.superlandnetwork.Lobby.Listener.ChangeListener;
import de.superlandnetwork.Lobby.Listener.ChatListener;
import de.superlandnetwork.Lobby.Listener.DamageListener;
import de.superlandnetwork.Lobby.Listener.DropListener;
import de.superlandnetwork.Lobby.Listener.InteractListener;
import de.superlandnetwork.Lobby.Listener.JoinListener;
import de.superlandnetwork.Lobby.Listener.JumpPadeListener;
import de.superlandnetwork.Lobby.Listener.SignListener;

public class Main extends JavaPlugin implements PluginMessageListener{
		
	public static ArrayList<String> ShowAll = new ArrayList<>();
	public static ArrayList<String> ShowTeam = new ArrayList<>();
	public static ArrayList<String> HideAll = new ArrayList<>();
	
	private static Main instance;
	
	public String Prefix = "§7[§aLobby§7] §f";
	
	public Scedular scedular = new Scedular();
	
	public void onEnable(){
		instance = this;
		registerEvents();
		Bukkit.getConsoleSender().sendMessage(Prefix + "§aEnabled!");
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		scedular.startScedular();
		Bukkit.getWorld("Lobby").setAutoSave(false);
		Bukkit.getWorld("Lobby").setThundering(false);
		Bukkit.getWorld("Lobby").setStorm(false);
		Bukkit.getWorld("Lobby").setTime(0L);
		Bukkit.getWorld("Lobby").setGameRuleValue("doDaylightCycle", "false");
		Bukkit.getWorld("Lobby").setGameRuleValue("announceAdvancements", "false");
		Bukkit.getWorld("Lobby").setGameRuleValue("doFireTick", "false");//
		Bukkit.getWorld("Lobby").setGameRuleValue("disableElytraMovementCheck", "true");
		Bukkit.getWorld("Lobby").setGameRuleValue("doMobSpawning", "false");
	}
	
	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(Prefix + "§cDisabled!");
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new DropListener(), this);
		Bukkit.getPluginManager().registerEvents(new JumpPadeListener(), this);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new SignListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
	}
	
	public static void SetScorbord(Player p) {
		Scoreboard bord = ScorbordManager.getScorebord(p);
		Objective score = bord.registerNewObjective("aaa", "dumy");
		score.setDisplayName("§eSuperLandNetwork§7.§bde");
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		if(API.getInstance().ServerID != 0)
			score.getScore("§aLobby-" + API.getInstance().ServerID).setScore(13);
		else 	
			score.getScore("§aSilent Lobby").setScore(13);
		score.getScore(" ").setScore(12);
		score.getScore("§6Dein Rang").setScore(11);
		// -- RANG START --
		PlayerAPI api = new PlayerAPI(p.getUniqueId());
		PermEnum e = getRank(api);
		score.getScore(e.getName()).setScore(10);
		// -- RANG ENDE --
		score.getScore("  ").setScore(9);
		score.getScore("§6Coins").setScore(8);
		score.getScore("§e" + api.getCoins()).setScore(7);
		score.getScore("   ").setScore(6);
		score.getScore("§6Spielzeit (h)").setScore(5);
		// -- TIME START --
		long time = api.getOnlineTime();
		long timeH = time / 60;
		score.getScore("§b"+timeH+"§7h").setScore(4);
		// -- TIME ENDE --
		score.getScore("    ").setScore(3);
		score.getScore("§6Freunde").setScore(2);
		// -- FRIEND START --
		int FriendsOnline = 0;
		if (!api.getFriendsUUID().isEmpty()) {
			for (String s : api.getFriendsUUID()) {
				if(new PlayerAPI(UUID.fromString(s)).isOnline())
					FriendsOnline++;
			}
		}
		score.getScore("§a"+FriendsOnline+"§7/" + api.getFriendsUUID().size()).setScore(1);
		// -- FRIEND ENDE --
		p.setScoreboard(bord);
	}
	
	private static PermEnum getRank(PlayerAPI api) {
		PermEnum e = PermEnum.SPIELER;
		for(PermEnum e2 : PermEnum.values()) {
			if(api.IsPlayerInGroup(e2.getId())) 
				return e2;
		}
		return e;
	}

	public static void removeScorebord(Player p){
		Scoreboard bord = ScorbordManager.getScorebord(p);
		Objective score = bord.getObjective("aaa");
		score.unregister();
		p.setScoreboard(bord);
	}
	
	public static void RegisterTeams(Player p) {
		Scoreboard bord = ScorbordManager.getScorebord(p);
		bord.registerNewTeam("0012Spieler").setPrefix(PermEnum.SPIELER.getTabList());
		bord.registerNewTeam("0011Premium").setPrefix(PermEnum.PREMIUM.getTabList());
		bord.registerNewTeam("0010PremiumPlus").setPrefix(PermEnum.PREMIUMPLUS.getTabList());
		bord.registerNewTeam("009YouTube").setPrefix(PermEnum.YOUTUBER.getTabList());
		bord.registerNewTeam("0008Builder").setPrefix(PermEnum.BUILDER.getTabList());
		bord.registerNewTeam("0008Builderin").setPrefix(PermEnum.BUILDERIN.getTabList());
		bord.registerNewTeam("0008Helfer").setPrefix(PermEnum.HELFER.getTabList());
		bord.registerNewTeam("0008Helferin").setPrefix(PermEnum.HELFERIN.getTabList());
		bord.registerNewTeam("0006Supporter").setPrefix(PermEnum.SUPPORTER.getTabList());
		bord.registerNewTeam("0006Supporterin").setPrefix(PermEnum.SUPPORTERIN.getTabList());
		bord.registerNewTeam("0005Moderator").setPrefix(PermEnum.MODERATOR.getTabList());
		bord.registerNewTeam("0005Moderatorin").setPrefix(PermEnum.MODERATORIN.getTabList());
		bord.registerNewTeam("0004SrModerator").setPrefix(PermEnum.SRMODERATOR.getTabList());
		bord.registerNewTeam("0004SrModeratin").setPrefix(PermEnum.SRMODERATORIN.getTabList());
		bord.registerNewTeam("0003Devloper").setPrefix(PermEnum.DEVELOPER.getTabList());
		bord.registerNewTeam("0003Devloperin").setPrefix(PermEnum.DEVELOPERIN.getTabList());
		bord.registerNewTeam("0002Admin").setPrefix(PermEnum.ADMINISTRATOR.getTabList());
		bord.registerNewTeam("0002Adminin").setPrefix(PermEnum.ADMINISTRATORIN.getTabList());
		bord.registerNewTeam("0001Owner").setPrefix(PermEnum.OWNER.getTabList());
		bord.registerNewTeam("0001Ownerin").setPrefix(PermEnum.OWNERIN.getTabList());
		AntiCollision();
	}
		 
	private static void AntiCollision() {
		Scoreboard bord = Bukkit.getScoreboardManager().getMainScoreboard();
		for(Team t : bord.getTeams()) {
			t.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
		}
	}
	 
	@SuppressWarnings("deprecation")
	public static void setPrefix(Player player) {
		Scoreboard bord = player.getScoreboard();
		for(Player all : Bukkit.getOnlinePlayers()) {
			String team = "0012Spieler";
			UUID UUID = all.getUniqueId();
			PlayerAPI api = new PlayerAPI(UUID);
			if(api.IsPlayerInGroup(PermEnum.PREMIUM.getId())) {
				team = "0011Premium";
			} else if(api.IsPlayerInGroup(PermEnum.PREMIUMPLUS.getId())) {
				team = "0010PremiumPlus";
			} else if(api.IsPlayerInGroup(PermEnum.YOUTUBER.getId())) {
				team = "0009YouTube";
			} else if(api.IsPlayerInGroup(PermEnum.BUILDER.getId())) {
				team = "0008Builder";
			} else if(api.IsPlayerInGroup(PermEnum.BUILDERIN.getId())) {
				team = "0008Builderin";
			} else if(api.IsPlayerInGroup(PermEnum.HELFER.getId())) {
				team = "0008Helfer";
			} else if(api.IsPlayerInGroup(PermEnum.HELFERIN.getId())) {
				team = "0008Helferin";
			} else if(api.IsPlayerInGroup(PermEnum.SUPPORTER.getId())) {
				team = "0006Supporter";
			} else if(api.IsPlayerInGroup(PermEnum.SUPPORTERIN.getId())) {
				team = "0006Supporterin";
			} else if(api.IsPlayerInGroup(PermEnum.MODERATOR.getId())) {
				team = "0005Moderator";
			} else if(api.IsPlayerInGroup(PermEnum.MODERATORIN.getId())) {
				team = "0005Moderatorin";
			} else if(api.IsPlayerInGroup(PermEnum.SRMODERATOR.getId())) {
				team = "0004SrModerator";
			} else if(api.IsPlayerInGroup(PermEnum.SRMODERATORIN.getId())) {
				team = "0004SrModeratin";
			} else if(api.IsPlayerInGroup(PermEnum.DEVELOPER.getId())) {
				team = "0003Devloper";
			} else if(api.IsPlayerInGroup(PermEnum.DEVELOPERIN.getId())) {
				team = "0003Devloperin";
			} else if(api.IsPlayerInGroup(PermEnum.ADMINISTRATOR.getId())) {
				team = "0002Admin";
			} else if(api.IsPlayerInGroup(PermEnum.ADMINISTRATORIN.getId())) {
				team = "0002Adminin";
			} else if(api.IsPlayerInGroup(PermEnum.OWNER.getId())) {
				team = "0001Owner";
			} else if(api.IsPlayerInGroup(PermEnum.OWNERIN.getId())) {
				team = "0001Ownerin";
			}
//			if(Main.getInstance().NickedPlayers.contains(UUID))
//				team = "0011Premium";
			bord.getTeam(team).addPlayer(all);
		}
		player.setScoreboard(bord);
	}
	
	//-------------------------------------------------------
	
	public static Inventory CommpasInv(String Name){ 
		Inventory inv = Bukkit.createInventory(null, 45, "§eWähle einen Spielmodus");
		List<String> Lore1 = new ArrayList<>();
		List<String> Lore2 = new ArrayList<>();
		List<String> Lore3 = new ArrayList<>();
		Lore1.add("§7Zu den Lobby schildern");
		Lore2.add("§7Zum Spawn");
		Lore3.add("§7Coming Soon");
		ItemStack BedWars = new ItemBuilder().getItem("§bBedWars", Material.BED, 1, Lore1);
		ItemStack Spawn = new ItemBuilder().getItem("§7Spawn", Material.NETHER_STAR, 1, Lore2);
		ItemStack CityBuild = new ItemBuilder().getItem("§cCitybuild", Material.BRICK, 1, Lore3);
		ItemStack SG = new ItemBuilder().getItem("§aSurvivalGames", Material.IRON_SWORD, 1, Lore1);
		ItemStack OITC = new ItemBuilder().getItem("§dOnInTheCamber", Material.DIAMOND_SWORD, 1, Lore1);
		ItemStack KFFA = new ItemBuilder().getItem("§3KnockbackFFA", Material.FISHING_ROD, 1, Lore1);
		ItemStack SkyWars = new ItemBuilder().getItem("§eSkyWars", Material.GRASS, 1, Lore1);
		ItemStack Community = new ItemBuilder().getItem("§8Community", Material.MAGMA_CREAM, 1, Lore3);
		ItemStack TTT = new ItemBuilder().getItem("§4TTT", Material.STICK, 1, Lore3);
		ItemStack Blue = new ItemBuilder().getItem(" ", Material.STAINED_GLASS_PANE, 1, (byte) 11);
		ItemStack Red = new ItemBuilder().getItem(" ", Material.STAINED_GLASS_PANE, 1, (byte) 14);
		//Linie 1:
		inv.setItem(0, Red);
		inv.setItem(1, Blue);
		inv.setItem(2, null);
		inv.setItem(3, null);
		inv.setItem(4, Community);
		inv.setItem(5, null);
		inv.setItem(6, null);
		inv.setItem(7, Blue);
		inv.setItem(8, Red);
		//Linie 2:
		inv.setItem(9, Blue);
		inv.setItem(10, null);
		inv.setItem(11, null);
		inv.setItem(12, KFFA);
		inv.setItem(13, null);
		inv.setItem(14, CityBuild);
		inv.setItem(15, null);
		inv.setItem(16, null);
		inv.setItem(17, Blue);
		//Linie 3:
		inv.setItem(18, null);
		inv.setItem(19, null);
		inv.setItem(20, OITC);
		inv.setItem(21, null);
		inv.setItem(22, Spawn);
		inv.setItem(23, null);
		inv.setItem(24, SG);
		inv.setItem(25, null);
		inv.setItem(26, null);
		//Linie 4:
		inv.setItem(27, Blue);
		inv.setItem(28, null);
		inv.setItem(29, null);
		inv.setItem(30, BedWars);
		inv.setItem(31, null);
		inv.setItem(32, SkyWars);
		inv.setItem(33, null);
		inv.setItem(34, null);
		inv.setItem(35, Blue);
		//Linie 5:
		inv.setItem(36, Red);
		inv.setItem(37, Blue);
		inv.setItem(38, null);
		inv.setItem(39, null);
		inv.setItem(40, TTT);
		inv.setItem(41, null);
		inv.setItem(42, null);
		inv.setItem(43, Blue);
		inv.setItem(44, Red);
		return inv;
	}
	
	public static Inventory ServerInv(){
		Inventory inv = Bukkit.createInventory(null, 36, "§bLobby wechseln");
		List<String> Lore1 = new ArrayList<>();
		Lore1.add("§7" + Bukkit.getOnlinePlayers().size() + " / " + Bukkit.getMaxPlayers());
		List<String> Lore6 = new ArrayList<>();
		Lore6.add("§70 / 0");
		ItemStack Lobby = new ItemBuilder().getItem("§aLobby 1", Material.GLOWSTONE_DUST, 1, Lore1);
		ItemStack Lobby2 = new ItemBuilder().getItem("§aLobby 2", Material.SULPHUR, 1, Lore6);
		ItemStack Lobby3 = new ItemBuilder().getItem("§aLobby 3", Material.SULPHUR, 1, Lore6);
		ItemStack Lobby4 = new ItemBuilder().getItem("§aLobby 4", Material.SULPHUR, 1, Lore6);
		ItemStack Lobby5 = new ItemBuilder().getItem("§aLobby 5", Material.SULPHUR, 1, Lore6);
		ItemStack Lobby6 = new ItemBuilder().getItem("§aLobby 6", Material.SULPHUR, 1, Lore6);
		ItemStack Close = new ItemBuilder().getItem("§cClose", Material.BARRIER, 1);
		ItemStack Emerald = new ItemBuilder().getItem("§aSeite 1", Material.EMERALD);
		inv.setItem(11, Lobby);
		inv.setItem(12, Lobby2);
		inv.setItem(13, Lobby3);
		inv.setItem(14, Lobby4);
		inv.setItem(15, Lobby5);
		inv.setItem(16, Lobby6);
		inv.setItem(35, Close);
		inv.setItem(27, Close);
		inv.setItem(31, Emerald);
		return inv;
	}

	public static Inventory HideInv(String Name) { 
		Inventory inv = Bukkit.createInventory(null, 9, "§eSpieler verstecken");
		ItemStack ShowAll = new ItemBuilder().getItem("§aAlle Anzeigen", Material.WOOL, 1, (byte) 13);
		if(Main.ShowAll.contains(Name)) { 
			ItemMeta ShowAllMeta = ShowAll.getItemMeta();
			ShowAllMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			ShowAllMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			ShowAll.setItemMeta(ShowAllMeta);
		}
		ItemStack ShowTeam = new ItemBuilder().getItem("§5Nur Youtuber / Teammitglieder", Material.WOOL, 1, (byte) 10);
		if(Main.ShowTeam.contains(Name)) { 
			ItemMeta ShowTeamMeta = ShowTeam.getItemMeta();
			ShowTeamMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			ShowTeamMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			ShowTeam.setItemMeta(ShowTeamMeta);
		}
		ItemStack HideAll = new ItemBuilder().getItem("§cAlle Verstecken", Material.WOOL, 1, (byte) 14);
		if(Main.HideAll.contains(Name)) { 
			ItemMeta HideAllMeta = HideAll.getItemMeta();
			HideAllMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			HideAllMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			HideAll.setItemMeta(HideAllMeta);
		}
		inv.setItem(2, ShowAll);
		inv.setItem(4, ShowTeam);
		inv.setItem(6, HideAll);
		return inv;
	}
	
	//-----------------------------------------------------------------------------------
	
	public void sendConnect(String Name, String Server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(Server);

		Player player = Bukkit.getPlayerExact(Name);

		player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}
	
	public static void giveItems(Player p){
		p.setGameMode(GameMode.ADVENTURE);
		p.setFoodLevel(20);
		p.setHealth(20.0D);
		p.getInventory().clear();
		//-------------------------
		ItemStack getCommpas = new ItemBuilder().getItem("§aNavigator §7(Rechtskick)", Material.COMPASS, 1);
		ItemStack getLobbyChanger = new ItemBuilder().getItem("§bLobby wechseln", Material.NETHER_STAR, 1);
		ItemStack getFrend = new ItemBuilder().getSkinnedHead("§eFreunde §7(Rechtskick)", p.getName());
		p.getInventory().setItem(0, getCommpas);
		p.getInventory().setItem(7, getLobbyChanger);
		p.getInventory().setItem(8, getFrend);
		
		if(API.getInstance().ServerID != 0) {
			ItemStack Hide = new ItemBuilder().getItem("§6Spieler verstecken", Material.BLAZE_ROD, 1);
			p.getInventory().setItem(1, Hide);
		}
		
		if(p.hasPermission("ccl.lobby.silentlobby")){
			if(API.getInstance().ServerID != 0) {
				ItemStack getSilentLobby = new ItemBuilder().getItem("§5Silent Lobby", Material.TNT, 1);
				p.getInventory().setItem(3, getSilentLobby);
			}
		}
		
		if(p.hasPermission("ccl.nick")) {
			PlayerAPI api = new PlayerAPI(p.getUniqueId());
			ItemStack getNick;
			if(!api.AutoNick()) {
				getNick = new ItemBuilder().getItem("§5AutoNick §c<Deaktiviert>", Material.NAME_TAG, 1);
			} else { 
				getNick = new ItemBuilder().getItem("§5AutoNick §a<Aktiviert>", Material.NAME_TAG, 1);
			}
			p.getInventory().setItem(5, getNick);
		}
		
		if(p.hasPermission("ccl.lobby.schutzschild")){
			ItemStack getNick = new ItemBuilder().getItem("§5Schutzschild §c<Deaktiviert>", Material.EYE_OF_ENDER, 1);
			p.getInventory().setItem(6, getNick);
		}
	}
	
	/**
	 * @return the scedular
	 */
	public Scedular getScedular() {
		return scedular;
	}

	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		return;
	}
	
	public static Inventory FriendsInv(String UUID) {
		ArrayList<String> Friends = new PlayerAPI(java.util.UUID.fromString(UUID)).getFriendsUUIDOrdet();
		ArrayList<String> Reqs = new PlayerAPI(java.util.UUID.fromString(UUID)).getFriendRequests();
		Inventory inv = Bukkit.createInventory(null, 54, "§4Freunde §7Seite - 1");
		ItemStack Black = new ItemBuilder().getItem(" ", Material.STAINED_GLASS_PANE, 1, (byte) 7);
		if(Friends.size() < 36) {
			for(int i=0; i<Friends.size(); i++) {
				inv.setItem(i, new Friends(Friends.get(i)).getHead());
			}
		} else {
			for(int i=0; i<36; i++) {
				inv.setItem(i, new Friends(Friends.get(i)).getHead());
			}
		}
		ItemStack Close = new ItemBuilder().getItem("§4Schliessen", Material.INK_SACK, 1, (byte) 1);

		inv.setItem(36, Black);
		inv.setItem(37, Black);
		inv.setItem(38, Black);
		inv.setItem(39, Black);
		inv.setItem(40, Black);
		inv.setItem(41, Black);
		inv.setItem(42, Black);
		inv.setItem(43, Black);
		inv.setItem(44, Black);
		//
		inv.setItem(45, new ItemBuilder().getItem("§câœ˜", Material.PAPER, 1));
		inv.setItem(46, null);
		ArrayList<String> Lore = new ArrayList<>();
		Lore.add(" ");
		Lore.add("§6Freunde: §e" + Friends.size() + "§8/§e" + getMax(java.util.UUID.fromString(UUID)));
		inv.setItem(47, new ItemBuilder().getSkinnedHead("§a" + Bukkit.getPlayer(java.util.UUID.fromString(UUID)).getName(), Bukkit.getPlayer(java.util.UUID.fromString(UUID)).getName(), Lore));
		inv.setItem(48, null);
		inv.setItem(49, Close);
		inv.setItem(50, null);
		inv.setItem(51, new ItemBuilder().getItem("§eAnfragen§8: §a" + Reqs.size(), Material.BOOK, Reqs.size()));
		inv.setItem(52, null);
		inv.setItem(53, new ItemBuilder().getItem("§eNächste Seite Â»", Material.PAPER, 1));
		return inv;
	}

	private static int getMax(UUID uuid) {
		PlayerAPI api = new PlayerAPI(uuid);
		if(api.getPlayerGroup() == 1)
			return 500;
		if(api.getPlayerGroup() == 2)
			return 750;
		if(api.getPlayerGroup() == 3)
			return 750;
		if(api.getPlayerGroup() == 4)
			return 1000;
		if(api.getPlayerGroup() == 5)
			return 1000;
		if(api.getPlayerGroup() == 6)
			return 1000;
		if(api.getPlayerGroup() == 7)
			return 1000;
		if(api.getPlayerGroup() == 8)
			return 1000;
		if(api.getPlayerGroup() == 9)
			return 1000;
		if(api.getPlayerGroup() == 10)
			return 1000;
		if(api.getPlayerGroup() == 11)
			return 1000;
		if(api.getPlayerGroup() == 12)
			return 1000;
		if(api.getPlayerGroup() == 13)
			return 1000;
		if(api.getPlayerGroup() == 14)
			return 1000;
		if(api.getPlayerGroup() == 15)
			return 1000;
		if(api.getPlayerGroup() == 16)
			return 1000;
		if(api.getPlayerGroup() == 17)
			return 1000;
		if(api.getPlayerGroup() == 18)
			return 1000;
		if(api.getPlayerGroup() == 19)
			return 1000;
		if(api.getPlayerGroup() == 20)
			return 1000;
		return 500;	
	}
	
}
