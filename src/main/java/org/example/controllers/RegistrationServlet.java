package org.example.controllers;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.services.RegistrationService;

import org.example.model.User;

import java.io.IOException;


@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    private final RegistrationService registrationService = new RegistrationService();
    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String ip = req.getRemoteAddr();
        String name = StringUtils.isEmpty(req.getParameter("username")) ? "DEFAULT" : req.getParameter("username");
        User user = registrationService.register(ip, name);
        logger.info("User [{}] successfully registered with name : {} , attempts : {}", user.getId(), user.getName(), user.getAttempts());
        redirecting(resp, session, user);
    }

    private void redirecting(HttpServletResponse resp, HttpSession session, User user) throws IOException {
        session.setAttribute("user", user);
        session.setAttribute("id", 0);
        resp.sendRedirect("/questions");
    }


}
