package org.zephyrion.cta;

import java.util.Set;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * This class adapts a Minecraft Forge Configuration object to more simply 
 * access only the functions necessary for the mod.
 * 
 * @author Zymus
 * @version 0.3
 * @since 0.1
 */
public final class ConfigurationAdapter {

    /**
     * Constructs a ConfigurationAdapter with the specified Minecraft Forge 
     * config.
     * 
     * @param config the Minecraft Forge config
     */
    public ConfigurationAdapter(final Configuration config) {
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        this.config = config;
        this.categoryNames = config.getCategoryNames();
    }

    /**
     * Returns the Minecraft Forge Configuration that was passed in to the 
     * constructor.
     * 
     * @return the Minecraft Forge Configuration
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Returns whether or not the Minecraft Forge Configuration contains the 
     * specified key.
     * 
     * @param key the key name
     * @return true if the key is found; false otherwise
     */
    public boolean containsKey(final String key) {
        for(final String categoryName : categoryNames) {
            final ConfigCategory category = config.getCategory(categoryName);
            if (config.hasKey(categoryName, key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value from the Minecraft Forge Configuration with the 
     * specified key.
     * 
     * @param key the key name
     * @return the value of the key
     */
    public String getValue(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        for(final String categoryName : categoryNames) {
            if (config.hasKey(categoryName, key)) {
                final Property property = config.get(categoryName, key, "");
                return property.getString();
            }
        }
        throw new UnsupportedOperationException(
            "no key found with name \"" + key + "\"");
    }
    
    
    private final Configuration config;
    private final Set<String> categoryNames;
}
