package com.edgarli.generator;

import com.edgarli.generator.dao.GeneratorMapper;
import com.edgarli.generator.service.GeneratorServer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class GeneratorApplicationTests {

    @Autowired
    private GeneratorServer generatorServer;

    @Autowired
    private GeneratorMapper generatorMapper;

    @Test
    void contextLoads() {
    }

}
