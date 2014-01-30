package org.zephyrion.cta;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import thaumcraft.api.aspects.Aspect;

/**
 * This class processes and collates all config from the other mods, this mod, 
 * and aspect config files.
 * 
 * @author Zymus
 * @version 0.3
 * @since 0.1
 */
public class ConfigurationCollator {

    /**
     * Constructs a new ConfigurationCollator with the specified Minecraft 
     * configuration directory, and the Configurable Thaumcraft Aspects config 
     * file.
     * 
     * @param configRoot the Minecraft config root
     * @param ctaConfigFile the Configurable Thaumcraft Aspects config file.
     */
    public ConfigurationCollator(
        final String configRoot, final File ctaConfigFile) {
        if (configRoot == null) {
            throw new IllegalArgumentException("configRoot must not be null");
        }
        if (ctaConfigFile == null) {
            throw new IllegalArgumentException(
                "ctaConfigFile must not be null");
        }
        this.configRoot = configRoot;
        this.ctaConfigFile = ctaConfigFile;
    }

    /**
     * Collates the CTA Config File, with the Mod config files, with the 
     * configurable aspects files.
     */
    public void collate() {
        try {
            final Set<ModEntry> modEntries = parseModEntries(ctaConfigFile);
            
            for (final ModEntry modEntry : modEntries) {
                final String aspectConfigFilePath = 
                    getAspectConfigFile(modEntry.getName());
                final Set<ModAspectEntry> aspectEntries = 
                    parseAspectEntries(aspectConfigFilePath);
                // So now we have all aspect entries
                // now we just need to collate them with the information 
                // in the mod config files
                final File modConfigFile =
                    new File(configRoot + modEntry.getModConfigFilePath());
                final Configuration modConfig =
                    new Configuration(modConfigFile);
                modConfig.load();
                final ConfigurationAdapter configAdapter =
                    new ConfigurationAdapter(modConfig);
                collateModConfigWithAspectEntries(configAdapter, aspectEntries);
            }

        }
        catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private Set<ModEntry> parseModEntries(final File ctaConfigFile)
        throws SAXException, IOException {
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(ctaConfigFile);
            final NodeList modList = doc.getElementsByTagName(MOD_TAG);
            final Set<ModEntry> entries = new HashSet<>();

            for (int i = 0; i < modList.getLength(); i++) {
                final Node mod = modList.item(i);
                final NamedNodeMap attributes = mod.getAttributes();

                final String name =
                    attributes.getNamedItem(NAME).getNodeValue();
                final String modConfigFilePath =
                    attributes.getNamedItem(MOD_CONFIG_FILE_PATH)
                        .getNodeValue();

                final ModEntry entry = new ModEntry(name, modConfigFilePath);
                entries.add(entry);
            }
            return entries;
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        throw new IOException("Unable to parse mod entries in config file: "
            + ctaConfigFile.getAbsolutePath());
    }
    
    private Set<ModAspectEntry> parseAspectEntries(
        final String aspectConfigFilePath) throws SAXException, IOException {
        final Set<ModAspectEntry> modAspectEntries = new HashSet<>();
        try {
            CTALogger.log("Parsing Aspect Entries in: " + aspectConfigFilePath);
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final File aspectConfigFile = new File(aspectConfigFilePath);
            final Document doc = builder.parse(aspectConfigFile);
            final NodeList modList = doc.getElementsByTagName(OBJECT_TAG);

            for (int i = 0; i < modList.getLength(); i++) {
                final Node mod = modList.item(i);
                final NamedNodeMap attributes = mod.getAttributes();
                
                validateObjectNode(mod, attributes);
                final String objectID =
                    attributes.getNamedItem(OBJECT_ID).getNodeValue();
                final String metaValuesString = 
                    attributes.getNamedItem(META_VALUES).getNodeValue();
                
                final String[] metaValuesArray = metaValuesString.split(",");
                validateMetaValues(objectID, metaValuesArray);
                
                final Set<Integer> metaValues =
                    convertMetaValues(metaValuesArray);
                final Set<AspectEntry> aspectEntries = new HashSet<>();
                final NodeList aspects = mod.getChildNodes();
                for (int j = 0; j < aspects.getLength(); j++) {
                    final Node aspect = aspects.item(j);
                    if (aspect.getNodeName().equals(TEXT)) {
                        continue;
                    }
                    final NamedNodeMap aspectAttributes =
                        aspect.getAttributes();
                    validateAspectNode(aspect, aspectAttributes);
                    final Aspect aspectType =
                        parseAspectType(aspect, aspectAttributes);
                    final int researchPoints =
                        parseResearchPoints(aspect, aspectAttributes);
                    final AspectEntry entry = new AspectEntry(
                        aspectType, researchPoints);
                    CTALogger.log("Entry found: " + entry.toString());
                    aspectEntries.add(entry);
                }
                final ModAspectEntry entry =
                    new ModAspectEntry(objectID, metaValues, aspectEntries);
                modAspectEntries.add(entry);
            }
            return modAspectEntries;
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        throw new IOException(
            "Unable to parse mod entries in config file: "
                + aspectConfigFilePath);
    }
    
    private final String getAspectConfigFile(final String modName) {
        final String aspectConfigFile =
            configRoot + ASPECT_CONFIG_DIRECTORY + modName + ".xml";
        return aspectConfigFile;
    }
    
    private final void validateMetaValues(
        final String objectID, final String[] metaValues) {
        for (final String metaValue : metaValues) {
            if (!metaValue.matches("-?\\d+")) {
                throw new NumberFormatException(
                    objectID + " contains invalid metaValue: " + metaValue);
            }
        }
    }
    
    private final void validateResearchPoints(
        final String aspectType, final String researchPoints) {
        if (!researchPoints.matches("-?\\d+")) {
            throw new NumberFormatException(
                String.format(
                    "\"%s\" contains invalid researchPoints value: \"%s\"",
                    aspectType, researchPoints));
        }
    }
    
    private final void validateObjectNode(
        final Node node, final NamedNodeMap attributes) {
        if (node == null) {
            throw new IllegalArgumentException("node must not be null");
        }
        if (attributes == null) {
            throw new IllegalArgumentException("attributes must not be null");
        }
        final String nodeName = node.getNodeName();
        if (nodeName != OBJECT_TAG) {
            throw new IllegalArgumentException(
                "invalid tag name - nodeName: " + nodeName
                + ", expected: " + OBJECT_TAG);
        }
        final String objectID =
            attributes.getNamedItem(OBJECT_ID).getNodeValue();
        if (objectID == null) {
            throwMissingAttributeException(node, OBJECT_ID);
        }
        final String metaValues =
            attributes.getNamedItem(META_VALUES).getNodeValue();
        if (metaValues == null) {
            throwMissingAttributeException(node, META_VALUES);
        }
    }
    
    private final void validateAspectNode(
        final Node node, final NamedNodeMap attributes) {
        if (node == null) {
            throw new IllegalArgumentException("node must not be null");
        }
        if (attributes == null) {
            throw new IllegalArgumentException("attributes must not be null");
        }
        final String nodeName = node.getNodeName();
        if (nodeName != ASPECT_TAG) {
            throw new IllegalArgumentException(
                "invalid tag name - nodeName: " + nodeName
                + ", expected: " + ASPECT_TAG);
        }
        final String type = attributes.getNamedItem(ASPECT_TYPE).getNodeValue();
        if (type == null) {
            throwMissingAttributeException(node, ASPECT_TYPE);
        }
        final String researchPoints =
            attributes.getNamedItem(RESEARCH_POINTS).getNodeValue();
        if (researchPoints == null) {
            throwMissingAttributeException(node, RESEARCH_POINTS);
        }
        validateResearchPoints(type, researchPoints);
    }
    
    private Set<Integer> convertMetaValues(final String[] metaValues) {
        final Set<Integer> result = new HashSet<>();
        for (final String metaValue : metaValues) {
            final int value = Integer.parseInt(metaValue);
            result.add(value);
        }
        return result;
    }
    
    private Aspect parseAspectType(
        final Node node, final NamedNodeMap attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException("attributes must not be null");
        }
        final String aspectType =
            attributes.getNamedItem(ASPECT_TYPE).getNodeValue();
        if (aspectType == null) {
            throwMissingAttributeException(node, ASPECT_TYPE);
        }
        final Aspect aspect = Aspect.getAspect(aspectType);
        if (aspect == null) {
            throw new IllegalArgumentException(aspectType + " has null value?");
        }
        return aspect;
    }
    
    private int parseResearchPoints(
        final Node node, final NamedNodeMap attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException("attributes must not be null");
        }
        final String researchPointsString =
            attributes.getNamedItem(RESEARCH_POINTS).getNodeValue();
        if (researchPointsString == null) {
            throwMissingAttributeException(node, RESEARCH_POINTS);
        }
        final int result = Integer.parseInt(researchPointsString);
        return result;
        
    }
    
    private void throwMissingAttributeException(
        final Node node, final String value) {
        final String nodeName = node.getNodeName();
        throw new IllegalStateException(
            String.format("node \"%s\" does not have a \"%s\" attribute", 
                nodeName, value));
    }
    
    private void collateModConfigWithAspectEntries(
        final ConfigurationAdapter config, final Set<ModAspectEntry> entries) {
        for (final ModAspectEntry entry : entries) {
            final String objectID = entry.getObjectID();
            if (config.containsKey(objectID)) {
                final String value = config.getValue(objectID);
                int objectIDValue = -1;
                if (value.contains(":")) {
                    final String[] split = value.split(":");
                    objectIDValue = Integer.parseInt(split[0]);
                    final int metaValue = Integer.parseInt(split[1]);
                    entry.addMetaValue(metaValue);
                }
                else {
                    objectIDValue = Integer.parseInt(value);
                }
                AspectRegistrar.register(objectIDValue, entry);
            }
            else {
                CTALogger.log(
                    "Did not find matching config for Object ID: " + objectID);
            }
        }
    }

    private static final String MOD_TAG = "mod";
    private static final String NAME = "name";
    private static final String MOD_CONFIG_FILE_PATH = "modConfigFilePath";
    private static final String OBJECT_TAG = "object";
    private static final String ASPECT_TAG = "aspect";
    private static final String OBJECT_ID = "id";
    private static final String META_VALUES = "metaValues";
    private static final String ASPECT_TYPE = "type";
    private static final String RESEARCH_POINTS = "researchPoints";
    private static final String TEXT = "#text";
    private static final String ASPECT_CONFIG_DIRECTORY = 
        "/ConfigurableThaumcraftAspects/";
    private final DocumentBuilderFactory factory = DocumentBuilderFactory
        .newInstance();
    private final File ctaConfigFile;
    private final String configRoot;
}
