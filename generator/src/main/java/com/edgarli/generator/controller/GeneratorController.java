package com.edgarli.generator.controller;

import com.edgarli.generator.entity.Generator;
import com.edgarli.generator.entity.vo.ColumnInfo;
import com.edgarli.generator.service.GeneratorServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GeneratorController
 * @Description
 * @Author lixun
 * @Date 2020/9/1 11:01
 * @Version 1.0
 **/
@Controller
@RequestMapping("/api/generator")
public class GeneratorController {
    @Autowired
    private GeneratorServer generatorServer;

    @GetMapping("/getTables")
    public ResponseEntity getTables(@RequestParam(defaultValue = "") String name, @RequestParam int start, @RequestParam int limit) {
        return new ResponseEntity(generatorServer.queryPage(name, start, limit), HttpStatus.OK);
    }

    @GetMapping("/getConfig")
    public ResponseEntity getConfig() {
        return new ResponseEntity(generatorServer.getById(), HttpStatus.OK);
    }

    @GetMapping("/getColums")
    public ResponseEntity getColums(@RequestParam String tableName) {
        return new ResponseEntity(generatorServer.getColums(tableName), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Generator generator) {
        generatorServer.updateGenerator(generator);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/generator")
    public ResponseEntity generator(@RequestBody List<ColumnInfo> columnInfos, @RequestParam String tableName) {
        generatorServer.generator(columnInfos, tableName);
        return new ResponseEntity("生成成功", HttpStatus.OK);
    }
}
