package com.asynclabs.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @Value("${application.browser.url}")
    private String browserUrl;

    @GetMapping("/")
    public RedirectView redirectToSwaggerUi() {
        return new RedirectView(browserUrl);
    }
}
