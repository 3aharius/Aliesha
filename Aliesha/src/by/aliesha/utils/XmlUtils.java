package by.aliesha.utils;

import java.io.File;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class XmlUtils {
    
    @SuppressWarnings("unchecked")
    public static <T> T unmarshallXmlFile(InputStream configFileIs, String xsdLocation, Class<T> tClass) throws SAXException, JAXBException{
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdLocation));
        JAXBContext context = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        T value = (T) unmarshaller.unmarshal(configFileIs);
        return value;
    }

}
