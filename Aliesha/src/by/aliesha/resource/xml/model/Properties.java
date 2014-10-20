package by.aliesha.resource.xml.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "properties" })
@XmlRootElement(name = "Properties")
public class Properties {

    @XmlElement(name = "Property", required = true)
    private List<PropertyFile> properties;

    public List<PropertyFile> getProperties() {
        return properties == null ? new ArrayList<PropertyFile>() : properties;
    }

    public void setProperties(List<PropertyFile> resources) {
        this.properties = resources;
    }

}
