package pwn.noobs.trouserstreak.mixin;

import net.minecraft.client.multiplayer.resolver.AddressCheck;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AddressCheck.class)
public interface AddressCheckMixin {
	@Overwrite
	public default boolean isAllowed(ResolvedServerAddress address) {
		return true;
	}
	
	@Overwrite
	public default boolean isAllowed(ServerAddress address) {
		return true;
	}
}
