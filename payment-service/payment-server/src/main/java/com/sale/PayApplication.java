package com.sale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings("all")
// 这里扫描两个包，一个是自己写的mapper，一个是uid-generator的mapper
@MapperScan({"com.sale.mapper",
        "com.baidu.fsg.uid"})
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
