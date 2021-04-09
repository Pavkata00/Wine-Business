package com.business.project.config.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LogoutInterceptor implements HandlerInterceptor {

    //logout na 5 min!
    private static final long MAX_INACTIVE_SESSION_TIME = 1000 * 300;

    private HttpSession session;

    public LogoutInterceptor(HttpSession session) {
        this.session = session;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isUserLogged()) {
            session = request.getSession();
            request.getSession().getLastAccessedTime();
            if (System.currentTimeMillis() - session.getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
                SecurityContextHolder.clearContext();
                request.logout();
                response.sendRedirect("/users/login");
            }
        }
        return true;
    }

    private static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication()
                    .getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }
}
