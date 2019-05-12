package com.cit.micro.data;

import com.cit.micro.data.service.ServerListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class DataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
        ServerListener.serverRun();
    }
}
