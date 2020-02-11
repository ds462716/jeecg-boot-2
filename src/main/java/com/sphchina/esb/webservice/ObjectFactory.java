
package com.sphchina.esb.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sphchina.esb.webservice package. 
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

    private final static QName _XBusTest_QNAME = new QName("http://webservice.esb.sphchina.com", "xBusTest");
    private final static QName _XBusTestResponse_QNAME = new QName("http://webservice.esb.sphchina.com", "xBusTestResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sphchina.esb.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XBusTest }
     * 
     */
    public XBusTest createXBusTest() {
        return new XBusTest();
    }

    /**
     * Create an instance of {@link XBusTestResponse }
     * 
     */
    public XBusTestResponse createXBusTestResponse() {
        return new XBusTestResponse();
    }

    /**
     * Create an instance of {@link DefaultWebServiceRequest }
     * 
     */
    public DefaultWebServiceRequest createDefaultWebServiceRequest() {
        return new DefaultWebServiceRequest();
    }

    /**
     * Create an instance of {@link DefaultWebServiceResponse }
     * 
     */
    public DefaultWebServiceResponse createDefaultWebServiceResponse() {
        return new DefaultWebServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XBusTest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.esb.sphchina.com", name = "xBusTest")
    public JAXBElement<XBusTest> createXBusTest(XBusTest value) {
        return new JAXBElement<XBusTest>(_XBusTest_QNAME, XBusTest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XBusTestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.esb.sphchina.com", name = "xBusTestResponse")
    public JAXBElement<XBusTestResponse> createXBusTestResponse(XBusTestResponse value) {
        return new JAXBElement<XBusTestResponse>(_XBusTestResponse_QNAME, XBusTestResponse.class, null, value);
    }

}
