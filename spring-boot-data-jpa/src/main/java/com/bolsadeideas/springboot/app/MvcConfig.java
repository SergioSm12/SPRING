package com.bolsadeideas.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.file.Paths;
import java.util.Locale;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final Logger log = LoggerFactory.getLogger(getClass());
/*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        log.info(resourcePath);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourcePath);
    }*/

    //Configurar controlador de vista, para acceso denegado
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_403").setViewName("error_403");
    }

    //Encoder para encriptar password
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Locale para internacionalizacion
    //resuleve donde se va guardar lo del interceptor en este caso en locale
    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es", "ES"));
        return localeResolver;
    }

    //Interceptor que cambia  el lenguaje.
    //metodo para cambiar interceptor Locale
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    //metodo para convertir vistas xml
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(new Class[]{com.bolsadeideas.springboot.app.view.xml.ClienteList.class});
        return marshaller;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
