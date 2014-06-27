package org.zephyrion.minecraft.cta;

/**
 * 
 * @author Zyle Moore
 * @version 0.1
 * @since 0.4
 */
public final class AspectRegistrar {

    /**
     * Private constructor to prevent instantiation.
     */
    private AspectRegistrar() {
       throw new AssertionError(
           "cannot instantiate " + AspectRegistrar.class.getName()); 
    }

    /**
     * 
     * @param modId the mod id for the item
     * @param unlocalisedName the unlocalised name of the block or item
     * @param aspectEntry 
     */
    public static void registerAspectEntry(
        final String modId, final String unlocalisedName,
        final AspectEntry aspectEntry) {
        if (modId == null) {
            throw new IllegalArgumentException("modId must not be null");
        }
        if (unlocalisedName == null) {
            throw new IllegalArgumentException(
                "unlocalisedName must not be null");
        }
        if (aspectEntry == null) {
            throw new IllegalArgumentException("aspectEntry must not be null");
        }
        // Register item with thaumcraft
    }
}
