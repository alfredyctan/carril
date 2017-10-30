
package org.afc.carril.fix.mapping.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reference.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="reference">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CONST"/>
 *     &lt;enumeration value="STATE"/>
 *     &lt;enumeration value="OBJ"/>
 *     &lt;enumeration value="FIX"/>
 *     &lt;enumeration value="SCHEMA"/>
 *     &lt;enumeration value="FIX-HEADER"/>
 *     &lt;enumeration value="FIX-TRAILER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "reference")
@XmlEnum
public enum Reference {

    CONST("CONST"),
    STATE("STATE"),
    OBJ("OBJ"),
    FIX("FIX"),
    SCHEMA("SCHEMA"),
    @XmlEnumValue("FIX-HEADER")
    FIX_HEADER("FIX-HEADER"),
    @XmlEnumValue("FIX-TRAILER")
    FIX_TRAILER("FIX-TRAILER");
    private final String value;

    Reference(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Reference fromValue(String v) {
        for (Reference c: Reference.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
