package net.pixeils.bettersuperflat;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.registry.Registry;
import net.pixeils.bettersuperflat.gen.ChunkGenerator;
import net.pixeils.bettersuperflat.gen.GenerationSettings;
import static net.minecraft.util.registry.Registry.CHUNK_GENERATOR;


public class Main implements ModInitializer {

  @Override
  public void onInitialize() {
    Registry.register(
        CHUNK_GENERATOR, GenerationSettings.NAME, ChunkGenerator.CODEC);
  }
}
