package com.kraynov.expenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
public class ExpensesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesApplication.class, args);
    }

//    @Bean
//    public ViewResolver internalResourceViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("/web/views/");
//        bean.setSuffix(".jsp");
//        return bean;
//    }
}
