package pwn.noobs.trouserstreak.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

// Imported from WURST Client (https://github.com/Wurst-Imperium/Wurst7/blob/master/src/main/java/net/wurstclient/mixin/TitleScreenMixin.java)
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
	private TitleScreenMixin(Component title) {
		super(title);
	}
	
	@Inject(method = "getMultiplayerDisabledReason()Lnet/minecraft/network/chat/Component;", at = @At("HEAD"), cancellable = true)
	private void getMultiplayerDisabledReason(CallbackInfoReturnable<Component> cir) {
		cir.setReturnValue(null);
	}
}
