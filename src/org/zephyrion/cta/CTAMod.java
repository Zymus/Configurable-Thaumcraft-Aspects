package org.zephyrion.cta;

import java.io.File;
import java.util.logging.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
    modid=CTAMod.MOD_ID,
    name=CTAMod.MOD_NAME,
    version=CTAMod.VERSION
)
@NetworkMod(clientSideRequired=true)
public final class CTAMod {

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        final String minecraftConfigDir =
            event.getModConfigurationDirectory().getAbsolutePath();
        final String ctaConfigFilePath = getConfigFilePath(minecraftConfigDir);
        final File ctaConfigFile = new File(ctaConfigFilePath);
        this.collator = new ConfigurationCollator(
            minecraftConfigDir, ctaConfigFile);
    }
    
    @EventHandler
    public void load(final FMLInitializationEvent event) throws Exception {
        collator.collate();
    }
    
    private String getConfigFilePath(final String minecraftConfigDir) {
        final String ctaConfigFilePath =
            minecraftConfigDir + "/" + MOD_ID + ".xml";
        return ctaConfigFilePath;
    }

    public static final String MOD_ID = "ConfigurableThaumcraftAspects";
    public static final String MOD_NAME = "Configurable Thaumcraft Aspects";
    public static final String VERSION = "0.1";
    @Instance
    public static CTAMod INSTANCE;
    private ConfigurationCollator collator;
}
