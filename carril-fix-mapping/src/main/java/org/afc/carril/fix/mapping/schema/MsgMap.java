
package org.afc.carril.fix.mapping.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for msg-map complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="msg-map">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.afc.org/carril/fix}dispatchable">
 *       &lt;sequence>
 *         &lt;element name="conditions" type="{http://www.afc.org/carril/fix}dispatchable" minOccurs="0"/>
 *         &lt;element name="tags" type="{http://www.afc.org/carril/fix}tag"/>
 *       &lt;/sequence>
 *       &lt;attribute name="target-type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "msg-map", propOrder = {
    "conditions",
    "tags"
})
public class MsgMap
    extends Dispatchable
{

    protected Dispatchable conditions;
    @XmlElement(required = true)
    protected Tag tags;
    @XmlAttribute(name = "target-type", required = true)
    protected String targetType;

    /**
     * Gets the value of the conditions property.
     * 
     * @return
     *     possible object is
     *     {@link Dispatchable }
     *     
     */
    public Dispatchable getConditions() {
        return conditions;
    }

    /**
     * Sets the value of the conditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dispatchable }
     *     
     */
    public void setConditions(Dispatchable value) {
        this.conditions = value;
    }

    /**
     * Gets the value of the tags property.
     * 
     * @return
     *     possible object is
     *     {@link Tag }
     *     
     */
    public Tag getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tag }
     *     
     */
    public void setTags(Tag value) {
        this.tags = value;
    }

    /**
     * Gets the value of the targetType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * Sets the value of the targetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetType(String value) {
        this.targetType = value;
    }

}
