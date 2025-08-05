package com.depinx.data;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * web容器中进行部署
 * 
 * @author ruoyi
 */
public class DataServletInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(DataProjectsApplication.class);
    }
}
