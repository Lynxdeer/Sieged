package com.lynxdeer.sieged.other;

import com.lynxdeer.sieged.Sieged;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class TPA implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		Sieged plugin = Sieged.getPlugin();

		if (label.equalsIgnoreCase("tpa")) {

			if (!(sender instanceof Player p)) return true;
			if (args.length == 0) {sender.sendMessage(ChatColor.RED + "Please specify who to tpa to!");}

			else {

				if (p.getGameMode() == GameMode.SPECTATOR) {sender.sendMessage(ChatColor.RED + "You are dead!");return true;}
				int combat = (p.hasMetadata("combat"))? p.getMetadata("combat").get(0).asInt():0;
				if (combat > 0) {p.sendMessage(ChatColor.RED + "You are in combat!"); return true;}
				if (Bukkit.getPlayer(args[0]) == null) {sender.sendMessage(ChatColor.RED + args[0] + " is not online or does not exist!");return true;}

				Player arg = Bukkit.getPlayer(args[0]);
				assert arg != null;

				p.setMetadata("tpato", new FixedMetadataValue(plugin, args[0]));
				p.setMetadata("tpatimestamp", new FixedMetadataValue(plugin, System.currentTimeMillis()));
				//p.sendMessage("(Debug) Sent request at " + System.currentTimeMillis() + " ms system time, recieved metadata as " + p.getMetadata("tpatimestamp").get(0).asInt());
				p.sendMessage(ChatColor.YELLOW + "Sent a TPA request to " + args[0]);
				arg.sendMessage(ChatColor.YELLOW + "TPA request from " + p.getName());
				arg.sendMessage(ChatColor.GOLD + "Accept it by doing /tpaccept " + p.getName() + " (Expires in 30 seconds)");

			}}

		if (label.equalsIgnoreCase("tpaccept")) {

			if (!(sender instanceof Player p)) return true;
			if (args.length == 0) {sender.sendMessage(ChatColor.RED + "Please specify who to tpaccept!");}

			else {

				if (p.getGameMode() == GameMode.SPECTATOR) {sender.sendMessage(ChatColor.RED + "You are dead!");return true;}
				if (Bukkit.getPlayer(args[0]) == null) {sender.sendMessage(ChatColor.RED + args[0] + " is not online or does not exist!");return true;}

				Player arg = Bukkit.getPlayer(args[0]);
				assert arg != null;

				int combat = (arg.hasMetadata("combat"))? arg.getMetadata("combat").get(0).asInt():0;
				if (combat > 0) {p.sendMessage(ChatColor.RED + "This person is in combat!"); return true;}
				if (!arg.getMetadata("tpato").get(0).asString().equals(p.getName())) {p.sendMessage(ChatColor.RED + "This person has not sent you a TPA request!"); return true;}
				//arg.sendMessage("(debug) " + (System.currentTimeMillis() - arg.getMetadata("tpatimestamp").get(0).asInt()) + " | " + (arg.getMetadata("tpatimestamp").get(0).asInt()) + " | " + System.currentTimeMillis());

				if ((System.currentTimeMillis() - arg.getMetadata("tpatimestamp").get(0).asInt()) < 30000) {p.sendMessage(ChatColor.RED + "This TPA request expired!"); return true;}
				p.sendMessage(ChatColor.YELLOW + "Accepted the TPA request of " + args[0]);
				arg.sendMessage(ChatColor.YELLOW + "Your tpa request to " + p.getName() + " was accepted.");
				arg.teleport(p.getLocation());
				arg.removeMetadata("tpato", plugin);
				arg.removeMetadata("tpatimestamp", plugin);

			}}
		return false;
	}
}
