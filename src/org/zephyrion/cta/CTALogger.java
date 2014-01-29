package org.zephyrion.cta;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;

/**
 * A simple logger class
 * 
 * @author Zymus
 * @version 0.1
 * @since 0.1
 */
public final class CTALogger {

    private CTALogger() {
        throw new AssertionError("cannot instantiate CTALogger class");
    }

    /**
     * Logs the specified message, with any extra data
     * @param message the message to log
     * @param data additional data
     */
    public static void log(final String message, final Object... data) {
        log(Level.INFO, String.format(
            "[%s] %s", CTAMod.MOD_NAME, message, data));
    }

    /**
     * Logs the specified message, with any extra data
     * 
     * @param level the logging level
     * @param message the message to log
     * @param data additional data
     */
    public static void log(
        final Level level, final String message, final Object... data) {
        FMLLog.log(level, message, data);
    }
}
