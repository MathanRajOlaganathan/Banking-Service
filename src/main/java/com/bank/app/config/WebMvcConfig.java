package com.bank.app.config;

import com.bank.app.converter.String2LocalDateConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 14/01/2021
 */
public class WebMvcConfig  extends WebMvcConfigurationSupport {

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new String2LocalDateConverter());
    }


}
