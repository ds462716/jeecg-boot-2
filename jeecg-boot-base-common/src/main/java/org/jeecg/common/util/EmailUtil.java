package org.jeecg.common.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author laowang
 */
public class EmailUtil {
    private static final String SENDER_NAME="17766076503@163.com";//发送者邮箱
    private static final String SENDER_PASSWORD="aA362944234";//邮箱密码
    private static final String SUBJECT="接口内容";//主题
    /**

     *send email method

     *@param accepter who you want to send

     *@param content email content

     *@throws MessagingException

     *@throws AddressException

     */

    public synchronized static void sendEmail(String accepter , String content) throws AddressException, MessagingException {

        /*

         * 1、创建连接对象

         *         设置邮件发送的协议

         *         设置发送邮件的服务器

         *         填写自己的密钥

         *

         * 2、创建邮件对象

         *         设置发件人

         *         设置收件人

         *         设置抄送者

         *         设置邮件主题

         *         设置邮件内容

         *

         * 3、发送邮件

         */

//            1、创建连接对象

        Properties props = new Properties();

        //1.1设置邮件发送的协议

        props.put("mail.transport.protocol" , "smtp");

        //1.2设置发送邮件的服务器

        props.put("mail.smtp.host" , "smtp.163.com");

        //1.3需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）

        props.put("mail.smtp.auth" , "true");

        //1.4下面一串是发送邮件用465端口，如果不写就是以25端口发送，阿里云已经关闭了25端口

        props.setProperty("mail.smtp.socketFactory.class" , "javax.net.ssl.SSLSocketFactory");

        props.setProperty("mail.smtp.socketFactory.fallback" , "false");

        props.setProperty("mail.smtp.port" , "465");

        props.setProperty("mail.smtp.socketFactory.port" , "465");

        //1.5认证信息

        Session session= Session.getInstance(props , new Authenticator() {

            protected PasswordAuthentication

            getPasswordAuthentication(){

                return new PasswordAuthentication(SENDER_NAME , SENDER_PASSWORD);

            }

        });



//        2、创建邮件对象

        Message message = new MimeMessage( session );

        //2.1设置发件人

        message.setFrom( new InternetAddress(SENDER_NAME) );

        //2.2设置收件人

        message.setRecipient(MimeMessage.RecipientType.TO , new InternetAddress(accepter));

        //2.3设置抄送者（PS:没有这一条网易会认为这是一条垃圾短信，而发不出去）

        message.setRecipient(MimeMessage.RecipientType.CC , new InternetAddress(SENDER_NAME));

        //2.4设置邮件的主题

        message.setSubject(SUBJECT);

        //2.5设置邮件的内容

        message.setContent(" "+content+" ", "text/html;charset=utf-8");

       //  3、发送邮件

        Transport.send(message);

    }

}
