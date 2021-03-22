package com.business.project.web;

import com.business.project.exception.FactoryNotFoundException;
import com.business.project.exception.WineNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalAdvice {


    @ExceptionHandler({WineNotFoundException.class})
    public ModelAndView handleWineNotFoundException(WineNotFoundException wineNotFoundException) {

        return new ModelAndView("wine-exception");
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ModelAndView handleUserNotFoundException(UsernameNotFoundException usernameNotFoundException) {

        return new ModelAndView("user-exception");
    }

    @ExceptionHandler({FactoryNotFoundException.class})
    public ModelAndView handleFactoryNotFoundException(FactoryNotFoundException factoryNotFoundException) {

        return new ModelAndView("factory-exception");
    }
}
