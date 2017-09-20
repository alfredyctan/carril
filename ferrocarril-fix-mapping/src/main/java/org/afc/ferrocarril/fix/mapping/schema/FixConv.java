
package org.afc.ferrocarril.fix.mapping.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="state-init" type="{http://www.afc.org/ferrocarril/fix}state-init"/>
 *         &lt;element name="obj-to-fix" type="{http://www.afc.org/ferrocarril/fix}direction"/>
 *         &lt;element name="fix-to-obj" type="{http://www.afc.org/ferrocarril/fix}direction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stateInit",
    "objToFix",
    "fixToObj"
})
@XmlRootElement(name = "fix-conv")
public class FixConv {

    @XmlElement(name = "state-init", required = true)
    protected StateInit stateInit;
    @XmlElement(name = "obj-to-fix", required = true)
    protected Direction objToFix;
    @XmlElement(name = "fix-to-obj", required = true)
    protected Direction fixToObj;

    /**
     * Gets the value of the stateInit property.
     * 
     * @return
     *     possible object is
     *     {@link StateInit }
     *     
     */
    public StateInit getStateInit() {
        return stateInit;
    }

    /**
     * Sets the value of the stateInit property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateInit }
     *     
     */
    public void setStateInit(StateInit value) {
        this.stateInit = value;
    }

    /**
     * Gets the value of the objToFix property.
     * 
     * @return
     *     possible object is
     *     {@link Direction }
     *     
     */
    public Direction getObjToFix() {
        return objToFix;
    }

    /**
     * Sets the value of the objToFix property.
     * 
     * @param value
     *     allowed object is
     *     {@link Direction }
     *     
     */
    public void setObjToFix(Direction value) {
        this.objToFix = value;
    }

    /**
     * Gets the value of the fixToObj property.
     * 
     * @return
     *     possible object is
     *     {@link Direction }
     *     
     */
    public Direction getFixToObj() {
        return fixToObj;
    }

    /**
     * Sets the value of the fixToObj property.
     * 
     * @param value
     *     allowed object is
     *     {@link Direction }
     *     
     */
    public void setFixToObj(Direction value) {
        this.fixToObj = value;
    }

}
