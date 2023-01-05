package com.lynxdeer.sieged.other;

import com.lynxdeer.sieged.Sieged;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.logging.Level;

import static com.lynxdeer.sieged.Sieged.randomlySpawn;

public class Chat implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		event.setJoinMessage("§e" + p.getName() + " joined.");
		if (!p.hasPlayedBefore()) Bukkit.getScheduler().runTaskLater(Sieged.getPlugin(), () -> {randomlySpawn(p);}, 1L);
		Bukkit.getScheduler().runTaskLater(Sieged.getPlugin(), () -> {
			p.playSound(p.getLocation(), "entity.experience_orb.pickup", 1, 1);
			p.sendMessage("§6Welcome to the TOKO EarthSMP!");
			p.sendMessage("§eUse /map to go to the server's dynmap");
			p.sendMessage("§eSee the pinned messages in the discord channel for custom recipes");
		}, 1L);

	}

	@EventHandler
	public void onHideCMD(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/dynmap hide")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("§eYou cannot hide yourself on the dynmap! Use invisibility potions or crouch to hide yourself.");
		}
	}


	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage("§e" + event.getPlayer().getName() + " left.");
	}

	public static String getStringedItem(ItemStack i) {

		return i.getAmount() + ":" + i.getType().toString().toLowerCase() +
				"(" + (i.getEnchantments().size() == 0 ? i.getEnchantments().toString() : "noench") + ")"
				+ " [" + i.getItemMeta().getDisplayName() + "]";
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		String deathloc = Math.floor(p.getLocation().getX()) + ", " + Math.floor(p.getLocation().getY()) + ", " + Math.floor(p.getLocation().getZ());
		StringBuffer sb = new StringBuffer();
		char[] ch = deathloc.toCharArray();
		for (char c : ch) sb.append(Integer.toHexString(c));
		p.sendMessage("§6You died at " + deathloc + ".");
		Bukkit.getLogger().log(Level.INFO, "Player " + p + "died at encoded location " + sb);

		StringBuilder inv = new StringBuilder();
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) != null) inv.append(getStringedItem(Objects.requireNonNull(p.getInventory().getItem(i))));
		}

		Bukkit.getLogger().log(Level.INFO, "Inventory contained: " + inv);
		p.removeMetadata("combat", Sieged.getPlugin());
	}

	public void startCombatTimer(Player p) {
		if (p.hasMetadata("combat")) return;
		p.sendMessage(ChatColor.RED + "You are in combat! Do not log out!");
		BukkitTask runnable = new BukkitRunnable() {
			@Override
			public void run() {
				int combat = (p.hasMetadata("combat")) ? p.getMetadata("combat").get(0).asInt() : 0;
				if (combat > 0) {
					p.sendActionBar(ChatColor.GOLD + "[Combat: " + combat + "]");
					p.setMetadata("combat", new FixedMetadataValue(Sieged.getPlugin(), combat-1));
				} else {p.removeMetadata("combat", Sieged.getPlugin());p.sendMessage(ChatColor.GRAY + "You are no longer in combat."); this.cancel();}
			}
		}.runTaskTimer(Sieged.getPlugin(), 0L, 20L);
	}

	@EventHandler
	public void hitEvent(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		if (!(event.getDamager() instanceof Player)) return;

		if (event.isCancelled()) return;

		Player victim = (Player) event.getEntity();
		Player damager= (Player) event.getDamager();

		if (victim == damager) return;

		startCombatTimer(victim);
		startCombatTimer(damager);
		victim.setMetadata("combat", new FixedMetadataValue(Sieged.getPlugin(), 15));
		damager.setMetadata("combat", new FixedMetadataValue(Sieged.getPlugin(), 15));
		victim.setMetadata("lastattack", new FixedMetadataValue(Sieged.getPlugin(), damager.getName()));

	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		String m = e.getMessage();
		Player p = e.getPlayer();

		m = m.replaceAll(":shrug:", "¯\\\\" + (char) 0x5C + "_(ツ)_/¯");
		m = m.replaceAll(":tableflip:", "(╯°□°）╯︵ ┻━┻");
		m = m.replaceAll(":flip:", "(╯°□°）╯︵ ┻━┻");
		m = m.replaceAll(":skull:", "☠");
		m = m.replaceAll(":forgor:", "☠");
		m = m.replaceAll("%", "%%");

		if (m.startsWith("<rainbow>")) {
			m = m.replaceFirst("<rainbow>", "");
			if (m.startsWith(" ")) m = m.replaceFirst(" ", "");
			String[] rm = m.split("");
			int rc = 1;
			for (int i = 0; i < rm.length; i++) {

				if (rc == 1) rm[i] = "§c" + rm[i];
				if (rc == 2) rm[i] = "§6" + rm[i];
				if (rc == 3) rm[i] = "§e" + rm[i];
				if (rc == 4) rm[i] = "§a" + rm[i];
				if (rc == 5) rm[i] = "§9" + rm[i];
				if (rc == 6) rm[i] = net.md_5.bungee.api.ChatColor.of("#8133f5") + rm[i];
				rc++;
				if (rc > 6) rc = 1;


			}
			m = String.join("", rm);

		}

		e.setFormat("§f" + p.getName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', m));

	}

}
