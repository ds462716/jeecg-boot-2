
package org.jeecg.modules.api.xbus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 *
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DefaultWebServiceRequest" type="{http://webservice.esb.sphchina.com}DefaultWebServiceRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "defaultWebServiceRequest"
})
@XmlRootElement(name = "receiveMessage")
public class ReceiveMessage_Type {

    @XmlElement(name = "DefaultWebServiceRequest", required = true)
    protected DefaultWebServiceRequest defaultWebServiceRequest;

    /**
     * ��ȡdefaultWebServiceRequest���Ե�ֵ��
     *
     * @return possible object is
     * {@link DefaultWebServiceRequest }
     */
    public DefaultWebServiceRequest getDefaultWebServiceRequest() {
        return defaultWebServiceRequest;
    }

    /**
     * ����defaultWebServiceRequest���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link DefaultWebServiceRequest }
     */
    public void setDefaultWebServiceRequest(DefaultWebServiceRequest value) {
        this.defaultWebServiceRequest = value;
    }

}
