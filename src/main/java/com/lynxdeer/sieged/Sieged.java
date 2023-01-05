package com.lynxdeer.sieged;

import com.lynxdeer.sieged.items.CGive;
import com.lynxdeer.sieged.items.Weapons;
import com.lynxdeer.sieged.other.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

import static com.lynxdeer.sieged.items.Items.getMaterial;
import static org.bukkit.Bukkit.addRecipe;

public final class Sieged extends JavaPlugin implements Listener {


	private static Sieged plugin;

	@Override
	public void onEnable() {

		plugin = this;

		Objects.requireNonNull(this.getCommand("cgive")).setExecutor(new CGive());
		Objects.requireNonNull(this.getCommand("map")).setExecutor(new MapCommand());

		Objects.requireNonNull(this.getCommand("tpa")).setExecutor(new TPA());
		Objects.requireNonNull(this.getCommand("tpaccept")).setExecutor(new TPA());

		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new Weapons(), this);
		getServer().getPluginManager().registerEvents(new Mining(), this);
		getServer().getPluginManager().registerEvents(new MapBorder(), this);
		getServer().getPluginManager().registerEvents(new Chat(), this);





		// add spears and flame/freezeblade later

		ShapedRecipe blazerod = new ShapedRecipe(new NamespacedKey(getPlugin(), "blazerod"), new ItemStack(Material.BLAZE_ROD));
		blazerod.shape("CGC", "FSF", "CIC");
		blazerod.setIngredient('G', Material.GUNPOWDER);
		blazerod.setIngredient('F', Material.FLINT);
		blazerod.setIngredient('I', Material.IRON_INGOT);
		blazerod.setIngredient('C', Material.COAL);
		blazerod.setIngredient('S', Material.STICK);
		addRecipe(blazerod);

		ShapedRecipe godapple = new ShapedRecipe(new NamespacedKey(getPlugin(), "godapple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
		godapple.shape("GGG", "GAG", "GGG");
		godapple.setIngredient('G', Material.GOLD_BLOCK);
		godapple.setIngredient('A', Material.APPLE);
		addRecipe(godapple);

		ShapelessRecipe xpbottle = new ShapelessRecipe(new NamespacedKey(getPlugin(), "xpbottle"), new ItemStack(Material.EXPERIENCE_BOTTLE));
		xpbottle.addIngredient(Material.LAPIS_LAZULI);
		xpbottle.addIngredient(Material.GLASS_BOTTLE);
		addRecipe(xpbottle);


		ShapelessRecipe diamond = new ShapelessRecipe(new NamespacedKey(getPlugin(), "diamond"), new ItemStack(Material.DIAMOND));
		diamond.addIngredient(new RecipeChoice.ExactChoice(getMaterial("DIAMOND_CHUNK")));
		diamond.addIngredient(new RecipeChoice.ExactChoice(getMaterial("DIAMOND_CHUNK")));
		diamond.addIngredient(new RecipeChoice.ExactChoice(getMaterial("DIAMOND_CHUNK")));
		diamond.addIngredient(new RecipeChoice.ExactChoice(getMaterial("DIAMOND_CHUNK")));
		addRecipe(diamond);

		ShapelessRecipe soulsand = new ShapelessRecipe(new NamespacedKey(getPlugin(), "soulsand"), new ItemStack(Material.SOUL_SAND));
		soulsand.addIngredient(Material.ROTTEN_FLESH);
		soulsand.addIngredient(Material.ROTTEN_FLESH);
		soulsand.addIngredient(Material.ROTTEN_FLESH);
		soulsand.addIngredient(Material.ROTTEN_FLESH);
		addRecipe(soulsand);

		ShapelessRecipe nw = new ShapelessRecipe(new NamespacedKey(getPlugin(), "nw"), new ItemStack(Material.NETHER_WART));
		nw.addIngredient(Material.BLAZE_POWDER);
		nw.addIngredient(Material.WHEAT);
		addRecipe(nw);

		ShapelessRecipe quartz = new ShapelessRecipe(new NamespacedKey(getPlugin(), "quartz"), new ItemStack(Material.QUARTZ));
		quartz.addIngredient(Material.IRON_NUGGET);
		quartz.addIngredient(Material.IRON_NUGGET);
		quartz.addIngredient(Material.IRON_NUGGET);
		quartz.addIngredient(Material.IRON_NUGGET);
		addRecipe(quartz);

		ShapelessRecipe glowstone = new ShapelessRecipe(new NamespacedKey(getPlugin(), "glowstone"), new ItemStack(Material.GLOWSTONE_DUST));
		glowstone.addIngredient(Material.GOLD_NUGGET);
		glowstone.addIngredient(Material.GOLD_NUGGET);
		glowstone.addIngredient(Material.REDSTONE);
		glowstone.addIngredient(Material.GOLD_NUGGET);
		glowstone.addIngredient(Material.GOLD_NUGGET);

		addRecipe(glowstone);

		ShapelessRecipe slime = new ShapelessRecipe(new NamespacedKey(getPlugin(), "slime"), new ItemStack(Material.SLIME_BALL));
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);
		slime.addIngredient(Material.MOSS_BLOCK);

		addRecipe(slime);


	}

	@EventHandler
	public void onTotemUse(EntityResurrectEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(true);
	}

	public static int rand(int min, int max) {
		return (int) Math.floor(Math.random()*(max-min+1)+min);
	}

	public static Location getRandomLocation(World w) {
		Location loc = new Location(w, rand(-12287, 12287), 256, rand(-6143, 6143));
		while (loc.getBlock().getType() == Material.AIR) {
			loc.subtract(0, 1, 0);
			if (loc.getY() <= 0) break;
		}
		return loc;
	}

	public static void randomlySpawn(Player p) {
		if (p.getBedSpawnLocation() != null) return;
		Location loc = getRandomLocation(p.getWorld());
		while (loc.getBlock().getType() == Material.WATER) {loc = getRandomLocation(p.getWorld());}
		Bukkit.getLogger().log(Level.INFO, "Spawned " + p + " at " + loc.getX() + loc.getY() + loc.getZ());
		p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ()));
		p.sendMessage(ChatColor.GRAY + "You were spawned at a random spot because you did not have a bed set.");
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 40, 1));
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 10, true, false));
		if (event.getPlayer().getBedSpawnLocation() != null) return;
		Bukkit.getScheduler().runTaskLater(this, () -> {randomlySpawn(event.getPlayer());}, 1L);

	}

	public static Sieged getPlugin() {return plugin;}

	public static boolean entityisproper(LivingEntity ent) {

		return !(ent instanceof Painting) && !(ent instanceof Boat) &&
				!(ent instanceof EnderCrystal) && !(ent instanceof Minecart) &&
				!(ent instanceof ItemFrame) && !(ent instanceof GlowItemFrame) &&
				!(ent instanceof Item) && !(ent instanceof SmallFireball) &&
				!(ent instanceof DragonFireball) && !(ent instanceof Arrow) &&
				!(ent instanceof ArmorStand) && !(ent instanceof Egg) && !(ent instanceof Snowball)
			;
	}

	public static ArrayList<LivingEntity> hitbox(Player p, Location loc) {

		ArrayList<LivingEntity> ret = new ArrayList<>();

		for (LivingEntity loopentity : p.getWorld().getLivingEntities()) {

			boolean a = false;

			if (loc.distance(loopentity.getEyeLocation()) < 0.9) a = true;
			if (loc.distance(loopentity.getLocation().add(new Vector(0, 1, 0))) < 0.9) a = true;
			if (loc.distance(loopentity.getLocation()) < 0.9) a = true;

			if (Sieged.entityisproper(loopentity) && loopentity != p) {
				if (a) ret.add(loopentity);
			}

		}

		return ret;

	}

}
