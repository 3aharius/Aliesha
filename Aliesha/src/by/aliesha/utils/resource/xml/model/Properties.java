package by.aliesha.utils.resource.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Properties")
@XmlAccessorType(XmlAccessType.FIELD)
public class Properties {
	
	@XmlElement(name="Property", required=true)
	private List<PropertyFile> properties;
	
	public Properties(){}

	public List<PropertyFile> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyFile> resources) {
		this.properties = resources;
	}

}
