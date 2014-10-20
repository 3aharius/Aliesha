package by.aliesha.frontcontroller.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "controllerScanPacks", "viewDir" })
@XmlRootElement(name = "Config")
public class Config {

    @XmlElement(name = "ControllerScanPacks", required = true)
    protected ControllerScanPacks controllerScanPacks;
    
    @XmlElement(name = "ViewDir", required = true)
    protected String viewDir;

    public ControllerScanPacks getControllerScanPacks() {
        return controllerScanPacks;
    }

    public void setControllerScanPacks(ControllerScanPacks value) {
        this.controllerScanPacks = value;
    }

    public String getViewDir() {
        return viewDir;
    }

    public void setViewDir(String value) {
        this.viewDir = value;
    }

}
