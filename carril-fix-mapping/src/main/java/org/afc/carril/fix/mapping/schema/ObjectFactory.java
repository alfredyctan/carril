
package org.afc.carril.fix.mapping.schema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.afc.ferrocarril.fix.mapping.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.afc.ferrocarril.fix.mapping.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StateInit }
     * 
     */
    public StateInit createStateInit() {
        return new StateInit();
    }

    /**
     * Create an instance of {@link Dispatchable }
     * 
     */
    public Dispatchable createDispatchable() {
        return new Dispatchable();
    }

    /**
     * Create an instance of {@link FixConv }
     * 
     */
    public FixConv createFixConv() {
        return new FixConv();
    }

    /**
     * Create an instance of {@link Direction }
     * 
     */
    public Direction createDirection() {
        return new Direction();
    }

    /**
     * Create an instance of {@link Tag }
     * 
     */
    public Tag createTag() {
        return new Tag();
    }

    /**
     * Create an instance of {@link OnError }
     * 
     */
    public OnError createOnError() {
        return new OnError();
    }

    /**
     * Create an instance of {@link MsgMap }
     * 
     */
    public MsgMap createMsgMap() {
        return new MsgMap();
    }

}
