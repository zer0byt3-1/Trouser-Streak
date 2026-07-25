package pwn.noobs.trouserstreak.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import meteordevelopment.meteorclient.utils.player.ChatUtils;

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
	
	@Inject(method="handleParticleEvent", at=@At("HEAD"), cancellable = true)
	private void handleParticleEvent(ClientboundLevelParticlesPacket packet, CallbackInfo ci) {
		int count = packet.getCount();		
		if (count > 100000) {
			ChatUtils.sendMsg(Component.nullToEmpty(String.format("§l§cWARNING: Attempt to use ThouserStreak's Crash. Cancel this packet.")));
			ci.cancel();
			return;
		}
	}
	
	@Inject(method="handleSoundEvent", at=@At("HEAD"), cancellable = true)
	private void handleSoundEvent(ClientboundSoundPacket packet, CallbackInfo ci) {
		float volume = packet.getVolume();		
		if (Math.abs(volume - 25.0f) < 0.0001f) {
			ChatUtils.sendMsg(Component.nullToEmpty(String.format("§l§cWARNING: Attempt to use zer0byt3.'s Sound Screamer. Cancel this packet.")));
			ci.cancel();
			return;
		}
	}
}
