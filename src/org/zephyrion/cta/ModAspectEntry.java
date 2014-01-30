package org.zephyrion.cta;

import java.util.Collections;
import java.util.Set;

import thaumcraft.api.aspects.Aspect;

/**
 * This class represents the object name, the meta values, and the aspects to 
 * be registered with Thaumcraft.
 * 
 * @author Zymus
 * @version 0.3
 * @since 0.1
 */
public class ModAspectEntry {

    /**
     * Constructs a new AspectEntry with the specified Aspect and research
     * points.
     * 
     * @param aspect
     * @param researchPoints
     */
    public ModAspectEntry(
        final String objectID, final Set<Integer> metaValues,
        final Set<AspectEntry> aspects) {
        if (objectID == null) {
            throw new IllegalArgumentException("objectID must not be null");
        }
        if (metaValues == null) {
            throw new IllegalArgumentException(
                "metaValues must not be null");
        }
        if (aspects == null) {
            throw new IllegalArgumentException("aspects must not be null");
        }
        this.objectID = objectID;
        this.metaValues = metaValues;
        this.aspects = aspects;
    }

    /**
     * Returns the object identifier
     * 
     * @return the object id
     */
    public String getObjectID() {
        return objectID;
    }

    /**
     * Returns a Set of all meta values that this entry will be configured for.
     * 
     * @return the meta values
     */
    public Set<Integer> getMetaValues() {
        return Collections.unmodifiableSet(metaValues);
    }

    /**
     * Returns the type of Aspect of this AspectEntry.
     * 
     * @return
     */
    public Set<AspectEntry> getAspects() {
        return Collections.unmodifiableSet(aspects);
    }
    
    /**
     * Adds a meta value to the entry
     * 
     * @param metaValue
     */
    void addMetaValue(final int metaValue) {
        metaValues.add(metaValue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[AspectEntry objectID=\"").append(objectID)
            .append("\", metaValues=").append(metaValues).append(", aspects=")
            .append(aspects).append("]");
        return builder.toString();
    }

    private final String objectID;
    private final Set<Integer> metaValues;
    private final Set<AspectEntry> aspects;
}
