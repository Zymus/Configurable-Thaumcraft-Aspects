package org.zephyrion.cta;

import java.io.File;
import java.io.IOException;
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
        initConfig(minecraftConfigDir);
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
    
    private void initConfig(final String minecraftConfigDir) {
        final File configFile =
            new File(minecraftConfigDir + "/" + MOD_ID + ".xml");
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            final File ctaConfigDir = new File(minecraftConfigDir + "/" + MOD_ID);
            if (!ctaConfigDir.exists()) {
                ctaConfigDir.mkdir();
            }
        }
        catch (final IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static final String MOD_ID = "ConfigurableThaumcraftAspects";
    public static final String MOD_NAME = "Configurable Thaumcraft Aspects";
    public static final String VERSION = "0.1";
    @Instance
    public static CTAMod INSTANCE;
    private ConfigurationCollator collator;
}
