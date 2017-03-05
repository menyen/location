package com.neogeo.location;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ng
 * 
 * Class used to add modules to the spring application
 */
@Configuration
@ComponentScan(basePackages = "com.neogeo.location")
public class AppConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * @return module to serialize and deserialize GeoJSON
	 */
	@Bean
    public GeoJsonModule geoJsonModule(){
        return new GeoJsonModule();
    }

}
