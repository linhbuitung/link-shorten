package com.example.linkshorten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;

@SpringBootApplication
public class LinkShortenApplication {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        ConfigurableApplicationContext context = SpringApplication.run(com.example.linkshorten.LinkShortenApplication.class, args);
    }
}
