package by.aliesha.frontcontroller;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.aliesha.exception.PageNotFoundException;
import by.aliesha.exception.UnsupportedHttpMethodException;
import by.aliesha.frontcontroller.annotation.Action;
import by.aliesha.frontcontroller.annotation.Controller;
import by.aliesha.frontcontroller.xml.model.Config;
import by.aliesha.url.ParsedUrl;
import by.aliesha.url.URLParser;
import by.aliesha.utils.AppConstants;
import by.aliesha.utils.FileUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Frontcontroller {
    
    private static final String FRONTCONTROLLER_SETTINGS_FILE_NAME = "frontcontroller-config.xml";
    private static final String CLASS_EXTENSION = ".class";
    private static final Logger logger = LoggerFactory.getLogger(AppConstants.LOGGER_NAME);
    private static Map<String, ControllerEntity> controllerEntries;
    private static volatile Frontcontroller frontcontroller;
    private static ControllerActionInvoker invoker;

    static {
        logger.debug("Start frontcontroller initialize ....");
        InputStream configFileIs = FileUtils.getFileAsStreamFromClassPath(FRONTCONTROLLER_SETTINGS_FILE_NAME);
        try {
            if (configFileIs != null) {
                JAXBContext context = JAXBContext.newInstance(Config.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Config frontCtrlConfig = (Config) unmarshaller.unmarshal(configFileIs);
                String classPath = FileUtils.getClassLoaderPath();
                controllerEntries = new ConcurrentHashMap<String, ControllerEntity>();
                for(String pack : frontCtrlConfig.getControllerScanPacks().getPackages()) {
                    String packPath = pack.replace(".", "/");
                    for(String file : new File(classPath + "/" + packPath).list()) {
                        if(file.endsWith(CLASS_EXTENSION)) {
                            String className = pack + "." + file.substring(0, file.length() - CLASS_EXTENSION.length());
                            try {
                                Class loadClass = Class.forName(className);
                                if(loadClass.isAnnotationPresent(Controller.class)) {
                                    Controller controllerAnno = (Controller) loadClass.getAnnotation(Controller.class);
                                    Object ctrl = loadClass.newInstance();
                                    ControllerEntity ctrlEntry = new ControllerEntity(ctrl);
                                    String mappingUrl = controllerAnno.urlPattern().toLowerCase();
                                    controllerEntries.put(mappingUrl, ctrlEntry);
                                    System.out.println(className + " " + mappingUrl);
                                    for(Method method : loadClass.getMethods()) {
                                        if(method.isAnnotationPresent(Action.class)) {
                                            Action actionAnno = method.getAnnotation(Action.class);
                                            ctrlEntry.addMethodBinding(actionAnno.urlPattern().toLowerCase(), actionAnno.method().name().toUpperCase(), method);
                                            System.out.println(method.getName() + " " + actionAnno.urlPattern() + "-" + actionAnno.method());
                                        }
                                    }
                                }
                                
                            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                                logger.error("Error loading class:" + className, e);
                            }
                        }
                    }
                }
                String viewLocation = frontCtrlConfig.getViewDir();
                invoker = new ControllerActionInvoker(viewLocation);
            } else {
                logger.error("Config file " + FRONTCONTROLLER_SETTINGS_FILE_NAME + " not found...");
            }
        } catch (JAXBException e) {
            logger.error("Invalid file format:" + FRONTCONTROLLER_SETTINGS_FILE_NAME, e);
        }
        
        frontcontroller = new Frontcontroller();
        logger.debug("Finish frontcontroller initialize ....");
    }
    
    private Frontcontroller() {}
    
    public static Frontcontroller getInstance() {
        return frontcontroller;
    }
    
    public String invokeAction(HttpServletRequest request) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, PageNotFoundException, UnsupportedHttpMethodException {
        logger.debug("ENTER");
        logger.debug("PathInfo: " + request.getPathInfo());
        ParsedUrl parsedUrl = URLParser.parseUrl(request.getPathInfo());
        logger.debug("Parsed controller: " + parsedUrl.getController());
        logger.debug("Parsed action: " + parsedUrl.getAction());
        ControllerEntity ctrlEntry = controllerEntries.get(parsedUrl.getController().toLowerCase());
        if(ctrlEntry == null) {
            throw new PageNotFoundException("Controller " + parsedUrl.getController() + " not found.");
        }
        ctrlEntry.checkActionRequest(request.getMethod().toUpperCase(), parsedUrl.getAction(), parsedUrl.getController());
        String view = invoker.invoke(ctrlEntry, parsedUrl.getAction(), request);
        logger.debug("EXIT");
        return view;
    }
    
    public ControllerEntity getControllerEntity(String ctrlUrl) {
        return controllerEntries.get(ctrlUrl.toLowerCase());
    }
    
}
