package net.pixeils.bettersuperflat.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/*

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldMixin extends Screen {

    @Shadow private GameRules gameRules;

    protected CreateWorldMixin(Text title) { super(title); }

    @Inject(method = "createLevel", at = @At("HEAD"))
    private void createLevelMix(CallbackInfo ci) {
        //still needs if statement or will apply to all new worlds created
            gameRules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, null);
            gameRules.get(GameRules.DO_WEATHER_CYCLE).set(false, null);
            gameRules.get(GameRules.DO_MOB_SPAWNING).set(false, null);
            gameRules.get(GameRules.KEEP_INVENTORY).set(true, null);
    }
}
*/