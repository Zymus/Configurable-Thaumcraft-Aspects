package org.zephyrion.cta;

import java.util.Set;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
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
     * Registers the object with the Thaumcraft Aspects
     * 
     * @param objectIDValue the object id
     * @param entry the details of the aspects that will be registered
     */
    public static void register(
        final int objectIDValue, final ModAspectEntry entry) {
        CTALogger.log(String.format("Object %d registered with entry %s",
            objectIDValue, entry.toString()));
        final int[] metaValues = convertMetaValues(entry.getMetaValues());
        final AspectList aspectList = convertAspectSet(entry.getAspects());
        if (metaValues.length == 1) {
            final int metaValue = metaValues[0];
            ThaumcraftApi.registerObjectTag(objectIDValue, metaValue, aspectList);
        }
        else {
            
            ThaumcraftApi.registerObjectTag(objectIDValue, metaValues, aspectList);
        }
    }

    /**
     * Converts a Set of integer values into a Thaumcraft-compatible int[]
     * 
     * @param metaValuesSet the Set of meta values
     * @return an int[] containing the meta values
     */
    private static int[] convertMetaValues(final Set<Integer> metaValuesSet) {
        final int metaValueCount = metaValuesSet.size();
        final int[] metaValues = new int[metaValueCount];
        int index = 0;
        for (final Integer value : metaValuesSet) {
            metaValues[index++] = value;
        }
        return metaValues;
    }
    
    /**
     * Converts a Set<AspectEntry> into an AspectList to be used by Thaumcraft.
     * 
     * @param entries the entries to be converted
     * @return the Thaumcraft-compatible AspectList
     */
    private static AspectList convertAspectSet(
        final Set<AspectEntry> aspects) {
        final AspectList list = new AspectList();
        for (final AspectEntry entry : aspects) {
            list.add(entry.getAspect(), entry.getResearchPoints());
        }
        return list;
    }
}
