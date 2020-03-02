
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
 *         &lt;element name="DefaultWebServiceResponse" type="{http://webservice.esb.sphchina.com}DefaultWebServiceResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "defaultWebServiceResponse"
})
@XmlRootElement(name = "receiveMessageResponse")
public class ReceiveMessageResponse {

    @XmlElement(name = "DefaultWebServiceResponse", required = true)
    protected DefaultWebServiceResponse defaultWebServiceResponse;

    /**
     * ��ȡdefaultWebServiceResponse���Ե�ֵ��
     *
     * @return possible object is
     * {@link DefaultWebServiceResponse }
     */
    public DefaultWebServiceResponse getDefaultWebServiceResponse() {
        return defaultWebServiceResponse;
    }

    /**
     * ����defaultWebServiceResponse���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link DefaultWebServiceResponse }
     */
    public void setDefaultWebServiceResponse(DefaultWebServiceResponse value) {
        this.defaultWebServiceResponse = value;
    }

}
