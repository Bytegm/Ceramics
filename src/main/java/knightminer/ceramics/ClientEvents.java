package knightminer.ceramics;

import knightminer.ceramics.client.gui.KilnScreen;
import knightminer.ceramics.client.model.ChannelModel;
import knightminer.ceramics.client.model.CisternModel;
import knightminer.ceramics.client.model.ClayBucketModel;
import knightminer.ceramics.client.model.CrackedFluidsModel;
import knightminer.ceramics.client.model.CrackedModel;
import knightminer.ceramics.client.renderer.ChannelTileEntityRenderer;
import knightminer.ceramics.client.renderer.CisternTileEntityRenderer;
import knightminer.ceramics.client.renderer.FaucetTileEntityRenderer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.mantle.client.model.FaucetFluidLoader;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid=Ceramics.MOD_ID, bus=Bus.MOD, value=Dist.CLIENT)
public class ClientEvents {
  public static void onConstructor() {
    FaucetFluidLoader.initialize();
  }

  @SubscribeEvent
  static void setupClient(FMLClientSetupEvent event) {
    RenderType cutout = RenderType.getCutout();
    RenderTypeLookup.setRenderLayer(Registration.TERRACOTTA_GAUGE.get(), cutout);
    RenderTypeLookup.setRenderLayer(Registration.PORCELAIN_GAUGE.get(), cutout);
    RenderTypeLookup.setRenderLayer(Registration.TERRACOTTA_CISTERN.get(), cutout);
    Registration.COLORED_CISTERN.forEach(cistern -> RenderTypeLookup.setRenderLayer(cistern, cutout));
    RenderTypeLookup.setRenderLayer(Registration.TERRACOTTA_FAUCET.get(), cutout);
    RenderTypeLookup.setRenderLayer(Registration.TERRACOTTA_CHANNEL.get(), cutout);

    ScreenManager.registerFactory(Registration.KILN_CONTAINER.get(), KilnScreen::new);
    ClientRegistry.bindTileEntityRenderer(Registration.CISTERN_TILE_ENTITY.get(), CisternTileEntityRenderer::new);
    ClientRegistry.bindTileEntityRenderer(Registration.FAUCET_TILE_ENTITY.get(), FaucetTileEntityRenderer::new);
    ClientRegistry.bindTileEntityRenderer(Registration.CHANNEL_TILE_ENTITY.get(), ChannelTileEntityRenderer::new);
  }

  @SubscribeEvent
  static void registerModels(ModelRegistryEvent event) {
    ModelLoaderRegistry.registerLoader(Ceramics.getResource("bucket"), ClayBucketModel.LOADER);
    ModelLoaderRegistry.registerLoader(Ceramics.getResource("cistern"), CisternModel.LOADER);
    ModelLoaderRegistry.registerLoader(Ceramics.getResource("channel"), ChannelModel.LOADER);
    // cracked models
    ModelLoaderRegistry.registerLoader(Ceramics.getResource("cracked"), CrackedModel.LOADER);
    ModelLoaderRegistry.registerLoader(Ceramics.getResource("cracked_fluids"), CrackedFluidsModel.LOADER);
    ModelLoaderRegistry.registerLoader(Ceramics.getResource("cracked_cistern"), CisternModel.CRACKED_LOADER);
    ModelLoaderRegistry.registerLoader(Ceramics.getResource("cracked_channel"), ChannelModel.CRACKED_LOADER);
  }
}
