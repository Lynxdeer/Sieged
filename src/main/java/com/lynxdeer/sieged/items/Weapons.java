package com.lynxdeer.sieged.items;

import com.lynxdeer.sieged.Sieged;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;

import static com.lynxdeer.sieged.Sieged.getPlugin;

public class Weapons implements Listener {

	private static boolean dont = false;

	/*@EventHandler
	public void onSpearDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		Player d = (Player)event.getDamager();
		ItemStack i = d.getInventory().getItemInMainHand();
		String container = (Objects.requireNonNull(i.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(getPlugin(), "ci"), PersistentDataType.STRING));
		if (container == null) return;
		if (!container.endsWith("SPEAR")) return;
		if (dont) return;
		// Can't cancel event here because EntityDamageByEntityEvent runs before PlayerInteractEvent
		d.setMetadata("cd", new FixedMetadataValue(getPlugin(), d.getAttackCooldown()));
	}

	@EventHandler
	public void onSpearAttack(PlayerInteractEvent event) {

		if (!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
		Player p = event.getPlayer();
		float cd = (p.hasMetadata("cd")) ? p.getMetadata("cd").get(0).asFloat() : 1;
		ItemStack i = p.getInventory().getItemInMainHand();
		if (p.getCooldown(i.getType()) > 0) return;
		if (i.getType() == Material.AIR) return;
		String container = (Objects.requireNonNull(i.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(getPlugin(), "ci"), PersistentDataType.STRING));
		if (container == null) return;
		if (!container.endsWith("SPEAR")) return;
		assert i.getItemMeta().getLore() != null;


		float damage = 0;
		float range = 3;
		for (String lv : i.getItemMeta().getLore()) {
			if (ChatColor.stripColor(lv).endsWith("Damage")) {
				damage = Float.parseFloat(ChatColor.stripColor(lv).replaceAll(" Damage", ""));
			}
			if (ChatColor.stripColor(lv).endsWith("Range")) {
				range = Float.parseFloat(ChatColor.stripColor(lv).replaceAll(" Range", ""));
			}
		}

		p.sendMessage();
		damage*=cd;
		boolean crit = p.getVelocity().getY() < -0.11;

		Location loc = p.getEyeLocation();
		for (int a = 0; a < range*10; a++) {
			boolean bbreak = false;
			String sound = "custom.slashhit";

			for (LivingEntity loopentity : Sieged.hitbox(p, loc)) {

				if (crit) { damage*=1.5; p.getWorld().spawnParticle(Particle.CRIT, loopentity.getEyeLocation(), 20, 0, 0, 0, 0.5); sound = "custom.stronkhit"; }

				dont = true;

				if (loopentity.getNoDamageTicks() <= 0) {
					loopentity.damage(damage, p);
					p.getWorld().playSound(loc, sound, 1, 1);
					loopentity.setNoDamageTicks(10);
				}
				bbreak = true;
				dont = false;

			}

			if (bbreak) break;
			loc.add(loc.getDirection().multiply(new Vector(0.1, 0.1, 0.1)));

		}

	}



	@EventHandler
	public void onSpearThrow(PlayerInteractEvent event) {

		if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		Player p = event.getPlayer();



		ItemStack i = p.getInventory().getItemInMainHand();
		if (p.getCooldown(i.getType()) > 0) return;
		if (i.getType() == Material.AIR) return;
		String container = (Objects.requireNonNull(i.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(getPlugin(), "ci"), PersistentDataType.STRING));
		if (container == null) return;
		if (!container.endsWith("SPEAR")) return;


		float damage = 0;
		float range = 3;
		for (String lv : i.getItemMeta().getLore()) {
			if (ChatColor.stripColor(lv).endsWith("Damage")) {
				damage = Float.parseFloat(ChatColor.stripColor(lv).replaceAll(" Damage", ""));
			}
		}



		int charge = (p.hasMetadata("spearcharge")) ? Math.round(p.getMetadata("spearcharge").get(0).asFloat()) : 0;
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 7, 4, true, false, false));
		int bars = charge * 2 - 1;
		int graybars = 3 - charge;

		String bar = ChatColor.WHITE + "";
		for (int a1 = 0; a1 < bars; a1++) {bar = bar + "|";}

		bar = bar + ChatColor.DARK_GRAY;
		for (int a2 = 0; a2 < graybars; a2++) {bar = (bar + ChatColor.GRAY + "|"); bar = ("|" + ChatColor.GRAY + bar);}

		bar = bar + ChatColor.GRAY + "]";
		bar = ChatColor.GRAY + "[" + bar;

		p.sendTitle(ChatColor.GRAY + "", bar, 0, 6, 0);
		p.playSound(p.getLocation(), "block.note_block.hat", 1, charge/2f);
		if (charge >= 3) {
			charge = 0;
			p.setCooldown(i.getType(), 40);
			p.getWorld().playSound(p.getEyeLocation(), "entity.ender_dragon.flap", 1f, 0.5f);
			p.getWorld().playSound(p.getEyeLocation(), "entity.ender_dragon.flap", 1f, 1f);
			p.getWorld().playSound(p.getEyeLocation(), "entity.ender_dragon.flap", 1f, 2f);

			Location loc = p.getEyeLocation();
			dont = true;
			for (int a = 0; a < 200; a++) {

				p.getWorld().spawnParticle(Particle.CRIT, loc, 1, 0, 0, 0, 0);
				loc.add(loc.getDirection().multiply(new Vector(0.5, 0.5, 0.5)));
				if (loc.getBlock().getType().isSolid()) break;
				boolean bbreak = false;

				for (LivingEntity loopentity : Sieged.hitbox(p, loc)) { if (loopentity != p) {
					System.out.println(damage + " | " + loopentity.getNoDamageTicks() + " | " + loopentity.getName());
					loopentity.damage(damage, p);
					bbreak = true;
				}} if (bbreak) break;


			}
			dont = false;

		}

		p.setMetadata("spearcharge", new FixedMetadataValue(getPlugin(), charge+1));


	}*/



}
