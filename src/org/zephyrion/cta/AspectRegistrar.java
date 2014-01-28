package org.zephyrion.cta;

import java.util.Set;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;

/**
 * This class is responsible for registering objects with aspects.
 * 
 * @author Zymus
 * @version 0.1
 * @since 0.1
 */
public final class AspectRegistrar {

    /**
     * Prevents instantiation of the class.
     */
    private AspectRegistrar() {
        throw new AssertionError("AspectRegistrar cannot be instantiated");
    }

    /**
     * Registers the object with the specified aspects. This method allows finer
     * control over which specific object is being registered by using the
     * object's meta value.
     * 
     * @param id
     *        the object id
     * @param metaValue
     *        the meta value to apply the aspects to
     * @param entries
     *        the aspects that are being registered
     */
    public static void registerObject(final int id, final int metaValue,
        final Set<AspectEntry> entries) {
        final AspectList aspectList = convertAspectEntrySet(entries);
        ThaumcraftApi.registerObjectTag(id, metaValue, aspectList);
    }

    /**
     * Registers the object with the specified aspects. This method allows finer
     * control over which specific object is being registered by using the
     * object's meta value. This method differs from 
     * registerObject(id, metaValue, entries), by allowing a group of
     * metavalues to have the same aspects registered to them.
     * 
     * @param id
     *        the object id
     * @param metaValues
     *        the meta values to apply the aspects to
     * @param entries
     *        the aspects that are being registered
     */
    public static void registerObject(final int id, final int[] metaValues,
        final Set<AspectEntry> entries) {
        final AspectList aspectList = convertAspectEntrySet(entries);
        ThaumcraftApi.registerObjectTag(id, metaValues, aspectList);
    }

    /**
     * Converts a Set<AspectEntry> into an AspectList to be used by Thaumcraft.
     * 
     * @param entries the entries to be converted
     * @return the Thaumcraft-compatible AspectList
     */
    private static AspectList convertAspectEntrySet(
        final Set<AspectEntry> entries) {
        final AspectList list = new AspectList();
        for (final AspectEntry entry : entries) {
            list.add(entry.getAspect(), entry.getResearchPoints());
        }
        return list;
    }
}
