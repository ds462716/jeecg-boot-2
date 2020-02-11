package org.jeecg.modules.api.webservice;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.jeecg.modules.api.xbus.ReceiveMessage;
import org.jeecg.modules.api.xbus.ReceiveMessagePortImpl;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @SuppressWarnings("all")
    @Bean
    public ServletRegistrationBean disServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus()
    {
        return  new SpringBus();
    }

    @Bean
    public FileAndReportService fileAndReportService()
    {
        return  new FileAndReportServiceImpl();
    }
    @Bean
    public YpService ypService()
    {
        return  new YpServiceImpl();
    }
    @Bean
    public MedicinalService medicinalService()
    {
        return  new MedicinalServiceImpl();
    }
    @Bean
    public PlantService plantService()
    {
        return  new PlantServiceImpl();
    }
    @Bean
    public ToVoidService toVoidService()
    {
        return  new ToVoidServiceImpl();
    }
    @Bean
    public DataDisseminationService dataDisseminationService()
    {
        return  new DataDisseminationServiceImpl();
    }

    @Bean
    public ReceiveMessage getReceiveMessage()
    {
        return  new ReceiveMessagePortImpl();
    }

    /**
     * 饮片
     * @return
     */
    @Bean
    public Endpoint yp() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), ypService());
        endpoint.publish("/yp");
        return endpoint;
    }

    /**
     * 药材
     * @return
     */
    @Bean
    public Endpoint medicinal() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), medicinalService());
        endpoint.publish("/medicinal");
        return endpoint;
    }

    /**
     * 种植
     * @return
     */
    @Bean
    public Endpoint plant() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), plantService());
        endpoint.publish("/plant");
        return endpoint;
    }
    /**
     * 文件
     * @return
     */
    @Bean
    public Endpoint file() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), fileAndReportService());
        endpoint.publish("/file");
        return endpoint;
    }
    /**
     * 逆向作废
     * @return
     */
    @Bean
    public Endpoint tovoid() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), toVoidService());
        endpoint.publish("/toVoid");
        return endpoint;
    }
    /**
     * 数据下发
     * @return
     */
    @Bean
    public Endpoint dataDissemination() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), dataDisseminationService());
        endpoint.publish("/dataDissemination");
        return endpoint;
    }
/**
    *
     * 饮片-XBUS
     * @return
     */
    @Bean
    public Endpoint ypXBus() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), getReceiveMessage());
        endpoint.publish("/ypXBus");
        return endpoint;
    }
/**
    *
     * 药材-XBUS
     * @return
     */
    @Bean
    public Endpoint medicinalXBus() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), getReceiveMessage());
        endpoint.publish("/medicinalXBus");
        return endpoint;
    }
/**
    *
     * 种植-XBUS
     * @return
     */
    @Bean
    public Endpoint plantXBus() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), getReceiveMessage());
        endpoint.publish("/plantXBus");
        return endpoint;
    }
  /**  *
     * 文件-XBUS
     * @return
     */
    @Bean
    public Endpoint fileXBus() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), getReceiveMessage());
        endpoint.publish("/fileXBus");
        return endpoint;
    }
 /**   *
     * 逆向作废-XBUS
     * @return
     */
    @Bean
    public Endpoint tovoidXBus() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), getReceiveMessage());
        endpoint.publish("/toVoidXBus");
        return endpoint;
    }
  /**  *
     * 数据下发-XBUS
     * @return
     */
    @Bean
    public Endpoint dataDisseminationXBus() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), getReceiveMessage());
        endpoint.publish("/dataDisseminationXBus");
        return endpoint;
    }


}
