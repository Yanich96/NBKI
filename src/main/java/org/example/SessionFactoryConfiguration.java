package org.example;

import org.example.databases.Cat;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class SessionFactoryConfiguration {

    @Bean
    SessionFactory sessionFactory() {
        var configFile = new File("./hibernate.cfg.xml");
        if (!configFile.exists())
            return new org.hibernate.cfg.Configuration()
                    .addAnnotatedClass(Cat.class)
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        else
            return new org.hibernate.cfg.Configuration()
                    .addAnnotatedClass(Cat.class)
                    .configure(configFile)
                    .buildSessionFactory();
    }
}
