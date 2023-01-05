package com.lynxdeer.sieged.other;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("map")) {

			TextComponent message = new TextComponent("Click §nhere §fto copy! After copying, paste it into your browser's search bar and hit enter.");
			message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "144.217.10.134:8107"));
			Player p = (Player)sender;
			p.spigot().sendMessage(message);

			return true;
		}return false;}
}
