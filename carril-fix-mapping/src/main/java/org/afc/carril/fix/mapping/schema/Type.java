
package org.afc.carril.fix.mapping.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INT"/>
 *     &lt;enumeration value="DOUBLE"/>
 *     &lt;enumeration value="BOOLEAN"/>
 *     &lt;enumeration value="BYTES"/>
 *     &lt;enumeration value="DECIMAL"/>
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="DATE"/>
 *     &lt;enumeration value="TIME"/>
 *     &lt;enumeration value="DATETIMESTAMP"/>
 *     &lt;enumeration value="DATETIME"/>
 *     &lt;enumeration value="CHAR"/>
 *     &lt;enumeration value="GROUP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "type")
@XmlEnum
public enum Type {

    INT,
    DOUBLE,
    BOOLEAN,
    BYTES,
    DECIMAL,
    STRING,
    DATE,
    TIME,
    DATETIMESTAMP,
    DATETIME,
    CHAR,
    GROUP;

    public String value() {
        return name();
    }

    public static Type fromValue(String v) {
        return valueOf(v);
    }

}
