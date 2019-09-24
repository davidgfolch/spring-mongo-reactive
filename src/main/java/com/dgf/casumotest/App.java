package com.dgf.casumotest;

import com.dgf.casumotest.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoAuditing
@Slf4j
public class App implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        ctx.getBean(RentService.class).initData();
    }

}
