package org.zephyrion.cta;

/**
 * 
 * @author Zymus
 * @version 0.1
 * @since 0.1
 */
public class AspectEntry {

    /**
     * Constructs a new AspectEntry with the specified Aspect and research
     * points.
     * 
     * @param aspect
     * @param researchPoints
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

    public Aspect getAspect() {
        return aspect;
    }

    public int getResearchPoints() {
        return researchPoints;
    }

    private final Aspect aspect;
    private final int researchPoints;
}