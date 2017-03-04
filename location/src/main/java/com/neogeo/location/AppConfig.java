package com.neogeo.location;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = "com.neogeo.location")
public class AppConfig extends WebMvcConfigurerAdapter {
	
	@Bean
    public GeoJsonModule geoJsonModule(){
        return new GeoJsonModule();
    }

}
