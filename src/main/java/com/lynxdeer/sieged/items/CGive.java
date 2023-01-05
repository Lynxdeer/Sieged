package com.lynxdeer.sieged.items;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CGive implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("cgive")) {
		if (args.length == 0) {
			sender.sendMessage("§cPlease supply an item to give!");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cYou cannot execute this command as console!");
			return true;
		}
		sender.sendMessage("§aGave you an item with the id " + args[0]);

		((Player) sender).getInventory().addItem((args[0].startsWith("mat_")) ? Items.getMaterial(args[0].replaceAll("mat_", "")) : Items.getItem(args[0]));
		return true;
	}return false;}
}
