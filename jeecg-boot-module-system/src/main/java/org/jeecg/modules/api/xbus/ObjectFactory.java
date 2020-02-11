
package org.jeecg.modules.api.xbus;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cxf package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cxf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReceiveMessage_Type }
     * 
     */
    public ReceiveMessage_Type createReceiveMessage_Type() {
        return new ReceiveMessage_Type();
    }

    /**
     * Create an instance of {@link DefaultWebServiceRequest }
     * 
     */
    public DefaultWebServiceRequest createDefaultWebServiceRequest() {
        return new DefaultWebServiceRequest();
    }

    /**
     * Create an instance of {@link ReceiveMessageResponse }
     * 
     */
    public ReceiveMessageResponse createReceiveMessageResponse() {
        return new ReceiveMessageResponse();
    }

    /**
     * Create an instance of {@link DefaultWebServiceResponse }
     * 
     */
    public DefaultWebServiceResponse createDefaultWebServiceResponse() {
        return new DefaultWebServiceResponse();
    }

}
