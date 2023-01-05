package com.lynxdeer.sieged.other;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MapBorder implements Listener {


	@EventHandler
	public void onMapBorder(PlayerMoveEvent event) {

		Player p = event.getPlayer();
		if (Math.abs(event.getTo().getX()) > 12287) {
			event.setCancelled(true);
			p.teleport(new Location(p.getWorld(), p.getLocation().getX() + ((p.getLocation().getX() < 0) ? 1 : -1), p.getLocation().getY(), p.getLocation().getX() + ((p.getLocation().getZ() < 0) ? 1 : -1)));
		}
		if (Math.abs(event.getTo().getZ()) > 6143) {
			event.setCancelled(true);
			p.teleport(new Location(p.getWorld(), p.getLocation().getX() + ((p.getLocation().getX() < 0) ? 1 : -1), p.getLocation().getY(), p.getLocation().getX() + ((p.getLocation().getZ() < 0) ? 1 : -1)));
		}

	}


}
