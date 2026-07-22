package pwn.noobs.trouserstreak.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddressResolver;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.client.multiplayer.resolver.ServerRedirectHandler;

// Imported from Wurst Client (https://github.com/Wurst-Imperium/Wurst7/blob/master/src/main/java/net/wurstclient/mixin/ServerNameResolverMixin.java)
@Mixin(ServerNameResolver.class)
public class ServerNameResolverMixin {
	@Shadow
	@Final
	private ServerAddressResolver resolver;
	
	@Shadow
	@Final
	private ServerRedirectHandler redirectHandler;
	
	@Inject(method = "resolveAddress(Lnet/minecraft/client/multiplayer/resolver/ServerAddress;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
	public void resolveAddress(ServerAddress address, CallbackInfoReturnable<Optional<ResolvedServerAddress>> cir) {
		Optional<ResolvedServerAddress> optionalAddress = resolver.resolve(address);
		Optional<ServerAddress> optionalRedirect = redirectHandler.lookupRedirect(address);
		
		if(optionalRedirect.isPresent())
			optionalAddress = resolver.resolve(optionalRedirect.get());
		
		cir.setReturnValue(optionalAddress);
	}
}
