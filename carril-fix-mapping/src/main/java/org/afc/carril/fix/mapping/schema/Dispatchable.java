
package org.afc.carril.fix.mapping.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dispatchable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dispatchable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="condition" type="{http://www.afc.org/ferrocarril/fix}dispatchable" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="source" use="required" type="{http://www.afc.org/ferrocarril/fix}reference" />
 *       &lt;attribute name="source-index" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="source-expression" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dispatchable", propOrder = {
    "condition"
})
@XmlSeeAlso({
    MsgMap.class
})
public class Dispatchable {

    protected List<Dispatchable> condition;
    @XmlAttribute
    protected String name;
    @XmlAttribute(required = true)
    protected Reference source;
    @XmlAttribute(name = "source-index", required = true)
    protected String sourceIndex;
    @XmlAttribute(name = "source-expression", required = true)
    protected String sourceExpression;

    /**
     * Gets the value of the condition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the condition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dispatchable }
     * 
     * 
     */
    public List<Dispatchable> getCondition() {
        if (condition == null) {
            condition = new ArrayList<Dispatchable>();
        }
        return this.condition;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setSource(Reference value) {
        this.source = value;
    }

    /**
     * Gets the value of the sourceIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceIndex() {
        return sourceIndex;
    }

    /**
     * Sets the value of the sourceIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceIndex(String value) {
        this.sourceIndex = value;
    }

    /**
     * Gets the value of the sourceExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceExpression() {
        return sourceExpression;
    }

    /**
     * Sets the value of the sourceExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceExpression(String value) {
        this.sourceExpression = value;
    }

}
