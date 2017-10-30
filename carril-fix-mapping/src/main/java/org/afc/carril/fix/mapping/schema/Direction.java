
package org.afc.carril.fix.mapping.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for direction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="direction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="msg-map" type="{http://www.afc.org/carril/fix}msg-map" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "direction", propOrder = {
    "msgMap"
})
public class Direction {

    @XmlElement(name = "msg-map")
    protected List<MsgMap> msgMap;

    /**
     * Gets the value of the msgMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msgMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMsgMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MsgMap }
     * 
     * 
     */
    public List<MsgMap> getMsgMap() {
        if (msgMap == null) {
            msgMap = new ArrayList<MsgMap>();
        }
        return this.msgMap;
    }

}
