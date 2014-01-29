package org.zephyrion.cta;

import java.util.HashMap;
import java.util.Map;

/**
 * The ModEntry class is used to specify the name of a mod to load, and that 
 * mod's config file path.
 * 
 * @author Zymus
 * @version 0.1
 * @since 0.1
 */
public final class ModEntry {

    /**
     * Constructs a ModEntry with the specified name and mod config file path
     * 
     * @param name the mod name
     * @param modConfigFilePath the mod config file path
     */
    public ModEntry(final String name, final String modConfigFilePath) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (modConfigFilePath == null) {
            throw new IllegalArgumentException(
                "modConfigFilePath must not be null");
        }
        this.name = name;
        this.modConfigFilePath = modConfigFilePath;
    }

    /**
     * Returns the Mod's name
     * 
     * @return the mod's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the mod config's file path
     * 
     * @return the mod config's file path
     */
    public String getModConfigFilePath() {
        return modConfigFilePath;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[ModEntry name=\"").append(name)
            .append("\", modConfigFilePath=\"").append(modConfigFilePath)
            .append("\"]");
        return builder.toString();
    }

    private final String name;
    private final String modConfigFilePath;
    private final Map<String, Integer> objectIDs = new HashMap<>();
}
