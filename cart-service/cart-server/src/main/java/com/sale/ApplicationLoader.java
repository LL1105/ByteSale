package com.sale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sale.mapper")
public class ApplicationLoader {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationLoader.class, args) ;
    }

}