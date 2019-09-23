package com.stu.cx.star;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stu.cx.star.Dao")
public class StarApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarApplication.class, args);
    }

}
