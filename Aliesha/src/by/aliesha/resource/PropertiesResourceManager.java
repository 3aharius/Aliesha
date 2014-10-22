package by.aliesha.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import by.aliesha.resource.xml.model.FileLocales;
import by.aliesha.resource.xml.model.Properties;
import by.aliesha.resource.xml.model.PropertyFile;
import by.aliesha.utils.AppConstants;
import by.aliesha.utils.FileUtils;
import by.aliesha.utils.XmlUtils;

public final class PropertiesResourceManager {

    private static final String RESOURCES_SETTINGS_FILE_NAME = "resources-config.xml";
    private static final String XSD_FILE_LOCATION = "by/aliesha/resource/xml/xsd/ResourcesConfig.xsd";
    private static final Logger logger = LoggerFactory.getLogger(AppConstants.LOGGER_NAME);
    private static volatile PropertiesResourceManager resoureManager;
    private Map<String, Map<Locale, ResourceBundle>> resources;

    static {
        logger.debug("Start app resourses initialize ....");
        try {
            InputStream configFileIs = FileUtils.getFileAsStreamFromClassPath(RESOURCES_SETTINGS_FILE_NAME);
            if (configFileIs != null) {
                String xsdLocation = FileUtils.getClassLoaderPath(XSD_FILE_LOCATION);
                Properties properties = XmlUtils.unmarshallXmlFile(configFileIs, xsdLocation, Properties.class);
                Map<String, Map<Locale, ResourceBundle>> resourcesMap = new ConcurrentHashMap<String, Map<Locale, ResourceBundle>>();
                for (PropertyFile file : properties.getProperties()) {
                    logger.debug("file name: " + file.getId());
                    Map<Locale, ResourceBundle> propsByLocale = new ConcurrentHashMap<Locale, ResourceBundle>();
                    FileLocales fileLocales = file.getLocales();
                    logger.debug("fileLocales: " + fileLocales);
                    if (fileLocales != null) {
                        for (String loc : fileLocales.getLocales()) {
                            try {
                                Locale fileLocale = Locale.forLanguageTag(loc);
                                if (fileLocale != Locale.ROOT) {
                                    ResourceBundle bundle = ResourceBundle.getBundle(file.getPath(), fileLocale);
                                    propsByLocale.put(fileLocale, bundle);
                                }
                            } catch (MissingResourceException ex) {
                                logger.error("Properties file " + file.getPath() + " not found...", ex);
                                ex.printStackTrace();
                            }
                        }
                    }
                    if (!propsByLocale.containsKey(Locale.getDefault())) {
                        try {
                            ResourceBundle bundle = ResourceBundle.getBundle(file.getPath(), Locale.getDefault());
                            propsByLocale.put(Locale.getDefault(), bundle);
                        } catch (MissingResourceException ex) {
                            logger.error("Properties file " + file.getPath() + " not found...", ex);
                            ex.printStackTrace();
                        }
                    }
                    if (propsByLocale.size() > 0) {
                        resourcesMap.put(file.getId(), propsByLocale);
                    }
                }
                resoureManager = new PropertiesResourceManager(resourcesMap);
            } else {
                throw new FileNotFoundException("Config file " + RESOURCES_SETTINGS_FILE_NAME + " not found...");
            }
        } catch (JAXBException | SAXException ex) {
            logger.error("Invalid file format:" + RESOURCES_SETTINGS_FILE_NAME, ex);
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        logger.debug("Finish app resourses initialize ....");
    }

    private PropertiesResourceManager() {}

    private PropertiesResourceManager(Map<String, Map<Locale, ResourceBundle>> resources) {
        this.resources = resources;
    }

    public static PropertiesResourceManager getInstance() {
        return resoureManager;
    }

    public ResourceBundle getBundle(String id, Locale userLocale) {
        if (resources.containsKey(id)) {
            Map<Locale, ResourceBundle> propsByLocale = resources.get(id);
            return propsByLocale.containsKey(userLocale) ? propsByLocale
                    .get(userLocale) : propsByLocale.get(Locale.getDefault());
        }
        return null;
    }
    
}
