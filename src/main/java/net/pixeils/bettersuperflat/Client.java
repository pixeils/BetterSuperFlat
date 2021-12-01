package net.pixeils.bettersuperflat;

import net.fabricmc.api.ClientModInitializer;
import net.pixeils.bettersuperflat.gen.GeneratorTypes;
import net.pixeils.bettersuperflat.mixin.GeneratorTypeAccessor;

public class Client implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    GeneratorTypeAccessor.getValues().add(GeneratorTypes.BETTERSUPERFLAT);
  }
}
