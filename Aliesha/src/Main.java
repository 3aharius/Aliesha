import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;


import javax.xml.bind.Marshaller;

import by.aliesha.utils.resource.xml.PropertiesResourceManager;
import by.aliesha.utils.resource.xml.model.FileLocales;
import by.aliesha.utils.resource.xml.model.PropertyFile;
import by.aliesha.utils.resource.xml.model.Properties;


public class Main {

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		
	    /*for(Locale loc : Locale.getAvailableLocales()) {
	        System.out.println(loc);
	    }*/
	    //generateTestFile();
	    System.out.println(Locale.forLanguageTag("ru"));
	    PropertiesResourceManager manager = PropertiesResourceManager.getInstance();
	    
	    ResourceBundle bundle1 = manager.getBundle("connection", Locale.getDefault());
	    System.out.println("bundle1 locale = "+bundle1.getLocale());
	    for(String key : bundle1.keySet()){
	        System.out.printf("%s=%s\n", key, bundle1.getString(key));
	    }
	    ResourceBundle bundle2 = manager.getBundle("label", Locale.forLanguageTag("ru"));
	    System.out.println("bundle2 locale = "+bundle2.getLocale());
	    for(String key : bundle2.keySet()){
            System.out.printf("%s=%s\n", key, bundle2.getString(key));
        }
	    ResourceBundle bundle3 = manager.getBundle("label", Locale.JAPAN);
	    System.out.println("bundle3 locale = "+bundle3.getLocale());
        for(String key : bundle3.keySet()){
            System.out.printf("%s=%s\n", key, bundle3.getString(key));
        }
	}
	
	private static void generateTestFile() throws JAXBException, FileNotFoundException {
	    List<String> locales = new ArrayList<String>();
	    locales.add("ru");
	    locales.add("en_Us");
	    PropertyFile resource1 = new PropertyFile();
        resource1.setId("hz");
        resource1.setPath("/Web-INF");
        FileLocales fLocales = new FileLocales();
        fLocales.setLocales(locales);
        resource1.setLocales(fLocales);
        PropertyFile resource2 = new PropertyFile();
        resource2.setId("res");
        resource2.setPath("/Web-INF.prop");
        List<PropertyFile> resourceList = new ArrayList<PropertyFile>();
        resourceList.add(resource1);
        resourceList.add(resource2);
        Properties resoures = new Properties();
        resoures.setProperties(resourceList);
        
        JAXBContext context = JAXBContext.newInstance(Properties.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(resoures, new FileOutputStream("resources/resources-test.xml"));
        marshaller.marshal(resoures, System.out);
        
	}

}
