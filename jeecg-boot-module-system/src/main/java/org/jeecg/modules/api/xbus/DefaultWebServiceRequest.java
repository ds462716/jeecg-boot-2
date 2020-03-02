
package org.jeecg.modules.api.xbus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DefaultWebServiceRequest complex type�� Java �ࡣ
 *
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType name="DefaultWebServiceRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="msgBody" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="msgSendTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sourceAppCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefaultWebServiceRequest", propOrder = {
        "msgBody",
        "msgSendTime",
        "password",
        "serviceId",
        "sourceAppCode"
})
public class DefaultWebServiceRequest {

    protected String msgBody;
    protected String msgSendTime;
    protected String password;
    protected String serviceId;
    protected String sourceAppCode;

    /**
     * ��ȡmsgBody���Ե�ֵ��
     *
     * @return possible object is
     * {@link String }
     */
    public String getMsgBody() {
        return msgBody;
    }

    /**
     * ����msgBody���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMsgBody(String value) {
        this.msgBody = value;
    }

    /**
     * ��ȡmsgSendTime���Ե�ֵ��
     *
     * @return possible object is
     * {@link String }
     */
    public String getMsgSendTime() {
        return msgSendTime;
    }

    /**
     * ����msgSendTime���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMsgSendTime(String value) {
        this.msgSendTime = value;
    }

    /**
     * ��ȡpassword���Ե�ֵ��
     *
     * @return possible object is
     * {@link String }
     */
    public String getPassword() {
        return password;
    }

    /**
     * ����password���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * ��ȡserviceId���Ե�ֵ��
     *
     * @return possible object is
     * {@link String }
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * ����serviceId���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setServiceId(String value) {
        this.serviceId = value;
    }

    /**
     * ��ȡsourceAppCode���Ե�ֵ��
     *
     * @return possible object is
     * {@link String }
     */
    public String getSourceAppCode() {
        return sourceAppCode;
    }

    /**
     * ����sourceAppCode���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSourceAppCode(String value) {
        this.sourceAppCode = value;
    }

}
