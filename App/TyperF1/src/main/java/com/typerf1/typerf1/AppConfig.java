package com.typerf1.typerf1;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Configure XML converter
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2XmlHttpMessageConverter(new XmlMapper()));

        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
