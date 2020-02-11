
package com.sphchina.esb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>xBusTest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="xBusTest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="defaultWebServiceRequest" type="{http://webservice.esb.sphchina.com}defaultWebServiceRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xBusTest", propOrder = {
    "defaultWebServiceRequest"
})
public class XBusTest {

    protected DefaultWebServiceRequest defaultWebServiceRequest;

    /**
     * 获取defaultWebServiceRequest属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DefaultWebServiceRequest }
     *     
     */
    public DefaultWebServiceRequest getDefaultWebServiceRequest() {
        return defaultWebServiceRequest;
    }

    /**
     * 设置defaultWebServiceRequest属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DefaultWebServiceRequest }
     *     
     */
    public void setDefaultWebServiceRequest(DefaultWebServiceRequest value) {
        this.defaultWebServiceRequest = value;
    }

}
