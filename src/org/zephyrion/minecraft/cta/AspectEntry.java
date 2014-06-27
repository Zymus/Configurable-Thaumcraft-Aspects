package org.zephyrion.minecraft.cta;

import thaumcraft.api.aspects.Aspect;

/**
 * This class is a container for an aspect, and the number of research points 
 * that will be gained upon scanning the object.
 * 
 * @author Zymus
 * @version 0.1
 * @since 0.1
 */
public class AspectEntry {

    /**
     * Constructs a new AspectEntry with the specified Aspect and research 
     * points gained upon scanning.
     * 
     * @param aspect the aspect
     * @param researchPoints the research points gained
     */
    public AspectEntry(final Aspect aspect, final int researchPoints) {
        if (aspect == null) {
            throw new IllegalArgumentException("aspect must not be null");
        }
        if (researchPoints < 1) {
            throw new IllegalArgumentException(
                "researchPoints must be positive integer: " + researchPoints);
        }
        this.aspect = aspect;
        this.researchPoints = researchPoints;
    }

    /**
     * Returns the Thaumcraft Aspect for this AspectEntry
     * 
     * @return the aspect
     */
    public Aspect getAspect() {
        return aspect;
    }

    /**
     * Returns the number of research points gained upon scanning
     * 
     * @return the research points gained.
     */
    public int getResearchPoints() {
        return researchPoints;
    }

    @Override
    public String toString() {
        return String.format(FORMAT_STRING, aspect.toString(), researchPoints);
    }

    private static final String FORMAT_STRING = 
        "[AspectEntry aspect=\"%s\", researchPoints=%d]";
    private final Aspect aspect;
    private final int researchPoints;
}
