package com.edgarli.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author EdgarLi
 */
@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class
        },
        scanBasePackages = {"com.edgarli.generator"}
)
@MapperScan("com.edgarli.generator.dao")
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
