package com.lynxdeer.sieged.items;

import com.lynxdeer.sieged.Sieged;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static org.bukkit.attribute.AttributeModifier.Operation.ADD_NUMBER;

public class Items {

	/*
	To do:
	- Ask in paper discord how to fix recipes
	*/

	public static ItemStack getItem(String s) {

		if (s.contains("pole")) {

			return getMaterial(s);

		}

		s = s.replaceAll("oak", "");    s = s.replaceAll("spruce", ""); s = s.replaceAll("birch", "");
		s = s.replaceAll("jungle", ""); s = s.replaceAll("acacia", ""); s = s.replaceAll("dark_", "");
		s = s.replaceAll("crimson", "");s = s.replaceAll("warped", ""); s = s.replaceAll("blackstone", "");
		s = s.replaceAll("deepslate", "");

		String[] lore = new String[0];
		ItemMeta itemmeta = null;
		ItemStack ret = new ItemStack(Material.AIR);
		float as = 420;




		// Spears

		/*if (s.equalsIgnoreCase("WOODEN_SPEAR")) {
			ret = new ItemStack(Material.WOODEN_SWORD); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.of("#9e7b3f") + "Wooden Spear");
			itemmeta.setCustomModelData(101); as = -2.75f;
			lore = new String[]{"§95 Damage", "§95 Range", "§9" + Float.toString(as).replaceAll(".0", "") + " Attack Speed", "§9Weight: 0%"};
		}

		if (s.equalsIgnoreCase("STONEHEADED_SPEAR")) {
			ret = new ItemStack(Material.STONE_SWORD); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.GRAY + "Stoneheaded Spear");
			itemmeta.setCustomModelData(101); as = -2.8f;
			lore = new String[]{"§96 Damage", "§95 Range", "§9" + Float.toString(as).replaceAll(".0", "") + " Attack Speed", "§9Weight: 10%"};
		}

		if (s.equalsIgnoreCase("IRON_SPEAR")) {
			ret = new ItemStack(Material.IRON_SWORD); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.WHITE + "Iron Spear");
			itemmeta.setCustomModelData(101); as = -2.85f;
			lore = new String[]{"§98 Damage", "§96 Range", "§9" + Float.toString(as).replaceAll(".0", "") + " Attack Speed", "§9Weight: 20%"};
		}

		if (s.equalsIgnoreCase("GOLDEN_SPEAR")) {
			ret = new ItemStack(Material.GOLDEN_SWORD); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.GOLD + "Golden Spear");
			itemmeta.setCustomModelData(101); as = -2.9f;
			lore = new String[]{"§95 Damage", "§97 Range", "§9" + Float.toString(as).replaceAll(".0", "") + " Attack Speed", "§9Weight: 30%"};
		}

		if (s.equalsIgnoreCase("DIAMOND_SPEAR")) {
			ret = new ItemStack(Material.DIAMOND_SWORD); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.AQUA + "Diamond Spear");
			itemmeta.setCustomModelData(101); as = -3;
			lore = new String[]{"§99 Damage", "§96 Range", "§9" + Float.toString(as).replaceAll(".0", "") + " Attack Speed", "§9Weight: 40%"};
		}*/

		/*if (s.equalsIgnoreCase("FLAME_BLADE")) {
			ret = new ItemStack(Material.DIAMOND_SWORD); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.of("#de882c") + "Flameblade");
			itemmeta.setCustomModelData(102); as = -4f;
			lore = new String[]{"§99 Damage", "§9" + Float.toString(as).replaceAll(".0", "") + " Attack Speed"};
		}*/



		if (itemmeta == null) return new ItemStack(Material.AIR);

		itemmeta.setLore(Arrays.asList(lore));
		itemmeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("GENERIC_ATTACK_SPEED", as, ADD_NUMBER));
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.getPersistentDataContainer().set(new NamespacedKey(Sieged.getPlugin(), "ci"), PersistentDataType.STRING, s.toUpperCase(Locale.ROOT));

		ret.setItemMeta(itemmeta);

		return ret;
	}

	public static ItemStack getMaterial(String s) {

		String[] lore = new String[0];
		ItemMeta itemmeta = null;
		ItemStack ret = new ItemStack(Material.AIR);





		if (s.equalsIgnoreCase("POLE")) {
			ret = new ItemStack(Material.STICK); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.of("#9e7b3f") + "Pole");
			itemmeta.setCustomModelData(1);
		}

		if (s.equalsIgnoreCase("METAL_POLE")) {
			ret = new ItemStack(Material.STICK); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName(ChatColor.of("#8a8a8a") + "Metal Pole");
			itemmeta.setCustomModelData(2);
		}

		if (s.equalsIgnoreCase("DIAMOND_CHUNK")) {
			ret = new ItemStack(Material.QUARTZ); itemmeta = ret.getItemMeta(); assert itemmeta != null;
			itemmeta.setDisplayName("§bDiamond Chunk");
			itemmeta.setLore(Collections.singletonList("§7Combine 4 in crafting to make a diamond!"));
			itemmeta.setCustomModelData(1);
		}


		if (itemmeta == null) return new ItemStack(Material.AIR);

		itemmeta.setLore(Arrays.asList(lore));
		itemmeta.getPersistentDataContainer().set(new NamespacedKey(Sieged.getPlugin(), "ci"), PersistentDataType.STRING, s.toUpperCase(Locale.ROOT));

		ret.setItemMeta(itemmeta);

		return ret;

	}


}
