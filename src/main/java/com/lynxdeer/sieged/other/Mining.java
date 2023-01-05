package com.lynxdeer.sieged.other;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.lynxdeer.sieged.items.Items.getMaterial;

public class Mining implements Listener {

	@EventHandler
	public void onDiamondMine(BlockBreakEvent event) {

		if (event.getBlock().getType() != Material.DIAMOND_ORE) return;
		if (event.isCancelled()) return;
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		event.setDropItems(false);
		Player p = event.getPlayer();
		ItemStack tool = p.getInventory().getItemInMainHand();
		int fortunelevel = (tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) ? tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) : 0;
		int itemsdropped = 1;
		float chance = Math.round(Math.random()*100f)/100f;
		float multiplier = Math.round(Math.random()*200f)/100f + 1;

		switch (fortunelevel) {
			case 1: if (chance < 0.34) itemsdropped = 2;
			case 2:
				if (chance >= 0.5 && chance <= 0.75) itemsdropped = 2;
				if (chance > 0.75) itemsdropped = 3;
			case 3:
				if (chance >= 0.4 && chance <= 0.6) itemsdropped = 2;
				if (chance >= 0.6 && chance <= 0.8) itemsdropped = 3;
				if (chance > 0.8) itemsdropped = 4;
		}

		itemsdropped*=multiplier;

		ItemStack item = getMaterial("diamond_chunk");

		for (int i = 0; i < itemsdropped; i++) {
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
		}

	}

}
