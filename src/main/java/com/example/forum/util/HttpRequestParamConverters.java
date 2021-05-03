package com.example.forum.util;

import com.example.forum.dto.MessageId;
import com.example.forum.dto.MessageboardId;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

import javax.annotation.PostConstruct;

@Configuration
public class HttpRequestParamConverters {
    private final ApplicationContext context;
    public HttpRequestParamConverters(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void registerConverters() {
        GenericConversionService converter = mvcConversionService();
        converter.addConverter(String.class, MessageId.class, source -> new MessageId(Integer.parseInt(source)));
        converter.addConverter(String.class, MessageboardId.class, source -> new MessageboardId(Integer.parseInt(source)));
        converter.addConverter(MessageboardId.class, String.class, MessageboardId::toString);
    }

    public GenericConversionService mvcConversionService() {
        // Tämän toteutuksen taustalla on se, että WebEnvironment.NONE-tyyppisissä testeissä
        // ConversionService-beaniä ei löydy (ja tämä configuration kuitenkin luetaan),
        // joten varaudutaan luomalla bean "yksikkötesteissä" käsin, jos sitä ei ole.
        try {
            return context.getBean(GenericConversionService.class);
        } catch (NoSuchBeanDefinitionException ex) {
            return new DefaultFormattingConversionService();
        }
    }
}