package com.lynxdeer.sieged.other;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import static com.lynxdeer.sieged.Sieged.getPlugin;
import static com.lynxdeer.sieged.items.Items.getItem;
import static com.lynxdeer.sieged.items.Items.getMaterial;

public class Recipes {

	public static void getShapedRecipe(String s) {

		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), s), getItem(s));

		String type = s.toLowerCase().replaceAll
			("_spear", "").replaceAll
			("_battleaxe", "");


		String weapon;
		weapon = s.toLowerCase().replaceAll(type + "_", "");

		if (s.toLowerCase().endsWith("spear") || s.toLowerCase().endsWith("battleaxe")) {

			switch (weapon){
				case "battleaxe": recipe.shape("HHH", "HPH", " P ");
				case "spear": recipe.shape("  H", " P ", "P  ");
			}

			recipe.setIngredient('P', new RecipeChoice.ExactChoice(getMaterial("POLE")));

			if (s.startsWith("wooden")) {
				String woodmat = type.toUpperCase().replaceFirst("WOODEN", "") + "_PLANKS";
				recipe.setIngredient('H', Material.valueOf(woodmat));

			}

			switch (type) {

				case "stoneheaded":  recipe.setIngredient('H', Material.COBBLESTONE);
				case "stoneheadeddeepslate": recipe.setIngredient('H', Material.DEEPSLATE);
				case "stoneheadedblackstone":recipe.setIngredient('H', Material.BLACKSTONE);
				case "golden":       recipe.setIngredient('H', Material.GOLD_INGOT);

				case "iron":         recipe.setIngredient('H', Material.IRON_INGOT); recipe.setIngredient('P', new RecipeChoice.ExactChoice(getMaterial("METAL_POLE"))); break;
				case "diamond":      recipe.setIngredient('H', Material.DIAMOND);    recipe.setIngredient('P', new RecipeChoice.ExactChoice(getMaterial("METAL_POLE"))); break;
			}


		}

		if (s.toLowerCase().endsWith("pole")) {

			type = s.toLowerCase().replaceAll("pole", "");

			recipe.shape(" S ", " ST", " S ");

			switch (type) {

				case "":
					recipe.setIngredient('S', Material.STICK);
					recipe.setIngredient('T', Material.STRING);
				case "metal_":
					recipe.setIngredient('S', Material.IRON_INGOT);
					recipe.setIngredient('T', new RecipeChoice.ExactChoice(getMaterial("POLE")));
			}

		}

		//return recipe;
		System.out.println(s + " | " + type + " |" + String.join(":", recipe.getShape()) + "| " + weapon);
		Bukkit.getServer().addRecipe(recipe);

	}

}
