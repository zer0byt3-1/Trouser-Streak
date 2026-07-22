package pwn.noobs.trouserstreak.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.patchy.BlockedServers;

@Mixin(BlockedServers.class)
public class BlockedServersMixin {
	@Inject(method="isBlockedServerHostName", at = @At("RETURN"), cancellable = true, remap = false)
	public void isBlockedServerHostName(String server, CallbackInfoReturnable<Boolean> clr) {
		// Boolean returnValue = clr.getReturnValue();
		clr.setReturnValue(false);
	}
}
