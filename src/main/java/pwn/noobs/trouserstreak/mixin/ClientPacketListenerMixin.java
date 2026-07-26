package pwn.noobs.trouserstreak.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.phys.Vec3;
import meteordevelopment.meteorclient.utils.player.ChatUtils;

import java.util.Optional;

import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {
	protected ClientPacketListenerMixin(Minecraft client, Connection connection, CommonListenerCookie connectionState) {
		super(client, connection, connectionState);
	}

	@Inject(method = "handleParticleEvent", at = @At("HEAD"), cancellable = true)
	private void handleParticleEvent(ClientboundLevelParticlesPacket packet, CallbackInfo ci) {
		int count = packet.getCount();
		if (count > 100000) {
			ChatUtils.sendMsg(Component.nullToEmpty(
					String.format("§c§lWARNING: Attempt to use ThouserStreak's Crash. Cancel this packet.")));
			ci.cancel();
			return;
		}
	}

	@Inject(method = "handleSoundEvent", at = @At("HEAD"), cancellable = true)
	private void handleSoundEvent(ClientboundSoundPacket packet, CallbackInfo ci) {
		float volume = packet.getVolume();
		if (Math.abs(volume - 25.0f) < 0.0001f) {
			ChatUtils.sendMsg(Component.nullToEmpty(
					String.format("§c§lWARNING: Attempt to use zer0byt3.'s Sound Screamer. Cancel this packet.")));
			ci.cancel();
			return;
		}
	}

	@Inject(method = "handleExplosion", at = @At("HEAD"), cancellable = true)
	private void handleExplosion(ClientboundExplodePacket packet, CallbackInfo ci) {
		try {
			Vec3 center = packet.center();
			Vec3 knockBack = new Vec3(0, 0, 0);
			WeightedList<ExplosionParticleInfo> particles = packet.blockParticles();
			if (packet.playerKnockback().isPresent()) {
				knockBack = packet.playerKnockback().get();
			}

			for (var item : particles.unwrap()) {
				var info = item.value();
				if (Math.abs(info.scaling()) > 10 || !Float.isFinite(info.scaling())) {
					ChatUtils.sendMsg(Component.nullToEmpty(String.format(
							"§c§lWARNING: Attempt to use ExplosionCrash (Invalid scaling). Cancel this packet.")));
					ci.cancel();
					return;
				}

				if (Math.abs(info.speed()) > 10 || !Float.isFinite(info.speed())) {
					ChatUtils.sendMsg(Component.nullToEmpty(String.format(
							"§c§lWARNING: Attempt to use ExplosionCrash (Invalid speed). Cancel this packet.")));
					ci.cancel();
					return;
				}
			}

			if (Math.abs(center.x) > 30_000_000 || Math.abs(center.y) > 30_000_000 || Math.abs(center.z) > 30_000_000
					|| !Double.isFinite(center.x) || !Double.isFinite(center.y) || !Double.isFinite(center.z)
					|| !Float.isFinite(packet.radius()) || packet.radius() > 10_000 || packet.blockCount() > 100_000
					|| Math.abs(knockBack.x) > 10_000 || Math.abs(knockBack.y) > 10_000
					|| Math.abs(knockBack.z) > 10_000) {
				ChatUtils.sendMsg(Component.nullToEmpty(String
						.format("§c§lWARNING: Attempt to use ExplosionCrash (Bad packet info). Cancel this packet.")));
				ci.cancel();
				return;
			}
		} catch (Exception e) {
			return;
		}
	}
}
