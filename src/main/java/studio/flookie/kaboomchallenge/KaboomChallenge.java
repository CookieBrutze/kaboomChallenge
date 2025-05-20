package studio.flookie.kaboomchallenge;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KaboomChallenge implements ModInitializer {
	public static final String MOD_ID = "kaboomchallenge";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private int tickCounter = 0;
	private boolean ticking = false;

	@Override
	public void onInitialize() {
		ServerPlayConnectionEvents.JOIN.register((handler, server, sender) -> {
			ServerPlayerEntity player = handler.getPlayer();
			if (player.interactionManager.getGameMode() == GameMode.SURVIVAL && !ticking) {
				ticking = true;
				System.out.println("Started tick counter!");

				ServerTickEvents.START_SERVER_TICK.register(server1 -> {
					tickCounter++;
					if (tickCounter >= 140) {
						System.out.println("140 ticks have passed!");
						loadExplosion.explosion(player, player.getWorld());
						tickCounter = 0;
					}
				});
			}
		});

		// Optional: stop ticking when last player leaves (if needed)
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			if (server.getPlayerManager().getPlayerList().isEmpty()) {
				ticking = false;
				System.out.println("All players left. Tick counter paused.");
				// Note: This doesn't unregister the callback, but you could design around that if needed
			}
		});
	}
}
