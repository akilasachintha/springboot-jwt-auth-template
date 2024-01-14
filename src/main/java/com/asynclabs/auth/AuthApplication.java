package com.asynclabs.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@SpringBootApplication
public class AuthApplication {

    private static final Logger logger = LoggerFactory.getLogger(AuthApplication.class);

    @Value("${application.browser.url:}")
    private String browserUrl;

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    private static void openHomePage(String browserUrl) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI.create(browserUrl));
            } catch (IOException e) {
                logger.error("Failed to open browser using Desktop API", e);
                openBrowserFallback(browserUrl);
            }
        } else {
            logger.warn("Desktop API is not supported, falling back to Runtime.exec()");
            openBrowserFallback(browserUrl);
        }
    }

    private static void openBrowserFallback(String browserUrl) {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + browserUrl);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + browserUrl);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                Runtime.getRuntime().exec("xdg-open " + browserUrl);
            }
        } catch (IOException e) {
            logger.error("Failed to open browser using Runtime.exec()", e);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Profile("dev")
    public void openBrowser() {
        if (!browserUrl.isEmpty()) {
            openHomePage(browserUrl);
        }
    }
}
