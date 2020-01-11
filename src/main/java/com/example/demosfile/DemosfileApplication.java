package com.example.demosfile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.demosfile.dao"})
public class DemosfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemosfileApplication.class, args);
    }

}
