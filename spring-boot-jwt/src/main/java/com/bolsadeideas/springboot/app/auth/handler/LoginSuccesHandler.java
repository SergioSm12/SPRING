package com.bolsadeideas.springboot.app.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;
import java.util.Locale;

@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();

        FlashMap flashMap = new FlashMap();

        Locale locale =localeResolver.resolveLocale(request);
        String mensaje = String.format(messageSource.getMessage("text.login.success",null,locale), authentication.getName());

        flashMap.put("success", mensaje);

        if(authentication != null){
            //Envia el name a consola
            logger.info("El usuario '" + authentication.getName()+ "' ha iniciado sesión con éxito! " );
        }

        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}