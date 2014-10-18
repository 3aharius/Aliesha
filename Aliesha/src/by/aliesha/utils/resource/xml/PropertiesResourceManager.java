package by.aliesha.utils.resource.xml;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.aliesha.utils.AppConstants;
import by.aliesha.utils.resource.xml.model.FileLocales;
import by.aliesha.utils.resource.xml.model.Properties;
import by.aliesha.utils.resource.xml.model.PropertyFile;

public final class PropertiesResourceManager {
	
	private static final String RESOURCES_SETTINGS_FILE_NAME = "resources-config.xml";
	private static final Logger logger = LoggerFactory.getLogger(AppConstants.LOGGER_NAME);
	private static volatile PropertiesResourceManager resoureManager;
	private Map<String, Map<Locale, ResourceBundle>> resources;
	
	static {
	    logger.debug("Start app resourses initialize ....");
		try {
			InputStream configFileIs = getFileAsStreamFromClassPath(RESOURCES_SETTINGS_FILE_NAME);
			if(configFileIs != null) {
    			JAXBContext context = JAXBContext.newInstance(Properties.class);
    			Unmarshaller unmarshaller = context.createUnmarshaller();
    			Properties properties = (Properties) unmarshaller.unmarshal(configFileIs);
    			Map<String, Map<Locale, ResourceBundle>> resourcesMap = new ConcurrentHashMap<String, Map<Locale, ResourceBundle>>();
    			for(PropertyFile file : properties.getProperties()){
    			    logger.debug("file name: " + file.getId());
    			    Map<Locale, ResourceBundle> propsByLocale = new ConcurrentHashMap<Locale, ResourceBundle>();
    			    FileLocales fileLocales = file.getLocales();
    			    logger.debug("fileLocales: " + fileLocales);
    			    if(fileLocales != null) {
        			    for(String loc : fileLocales.getLocales()) {
        			        try {
        			            Locale fileLocale = Locale.forLanguageTag(loc);
        			            if(fileLocale != Locale.ROOT) {
                                    ResourceBundle bundle = ResourceBundle.getBundle(file.getPath(), fileLocale);
                                    propsByLocale.put(fileLocale, bundle);
        			            }
                            } catch (MissingResourceException ex) {
                                logger.error("Properties file " + file.getPath() + " not found...", ex);
                            }
        			    }
    			    }
    			    if(!propsByLocale.containsKey(Locale.getDefault())) {
			            try {
                            ResourceBundle bundle = ResourceBundle.getBundle(file.getPath(), Locale.getDefault());
                            propsByLocale.put(Locale.getDefault(), bundle);
                        } catch (MissingResourceException ex) {
                            logger.error("Properties file " + file.getPath() + " not found...", ex);
                        }
    			    }
    			    if(propsByLocale.size() > 0) {
    			        resourcesMap.put(file.getId(), propsByLocale);
    			    }
    			}
    			resoureManager = new PropertiesResourceManager(resourcesMap);
			} else {
			    logger.error("Config file " + RESOURCES_SETTINGS_FILE_NAME + " not found...");
			}
		} catch (JAXBException e) {
		    logger.error("Invalid file format:" + RESOURCES_SETTINGS_FILE_NAME, e);
		}
		logger.debug("Stop app resourses initialize ....");
	}
	
	private PropertiesResourceManager(){}
	
	private PropertiesResourceManager(Map<String, Map<Locale, ResourceBundle>> resources){
		this.resources = resources;
	}
	
	public static PropertiesResourceManager getInstance() {
		return resoureManager;
	}
	
	private static InputStream getFileAsStreamFromClassPath(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}
	
	public ResourceBundle getBundle(String id, Locale userLocale) {
	    if(!resources.containsKey(id)) {
	        return null;
	    }
	    Map<Locale, ResourceBundle> propsByLocale = resources.get(id);
	    return propsByLocale.containsKey(userLocale) ? propsByLocale.get(userLocale) : propsByLocale.get(Locale.getDefault());
	}

}
