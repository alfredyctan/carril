
package org.afc.carril.fix.mapping.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for use.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="use">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REQ"/>
 *     &lt;enumeration value="OPT"/>
 *     &lt;enumeration value="BAN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "use")
@XmlEnum
public enum Use {

    REQ,
    OPT,
    BAN;

    public String value() {
        return name();
    }

    public static Use fromValue(String v) {
        return valueOf(v);
    }

}
