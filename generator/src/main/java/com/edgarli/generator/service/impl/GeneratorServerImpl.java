package com.edgarli.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edgarli.generator.dao.GeneratorMapper;
import com.edgarli.generator.entity.Generator;
import com.edgarli.generator.entity.vo.ColumnInfo;
import com.edgarli.generator.entity.vo.TableInfo;
import com.edgarli.generator.service.GeneratorServer;
import com.edgarli.generator.utils.Constant;
import com.edgarli.generator.utils.EntityUtils;
import com.edgarli.generator.utils.GenUtil;
import com.edgarli.generator.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GeneratorServerImpl
 * @Description
 * @Author lixun
 * @Date 2020/8/31 17:50
 * @Version 1.0
 **/
@Service
public class GeneratorServerImpl extends ServiceImpl<GeneratorMapper, Generator> implements GeneratorServer {

    @Autowired
    private GeneratorMapper generatorMapper;

    @Override
    public IPage<TableInfo> queryPage(String name, long start, long limit) {
        return generatorMapper.queryPage(name, new Page<TableInfo>(start, limit));
    }

    @Override
    public TableInfo getByName(String name) {
        return generatorMapper.getByName(name);
    }

    @Override
    public List<ColumnInfo> getColums(String name) {
        return generatorMapper.getColums(name);
    }

    @Override
    public Generator getById() {

        String gen = generatorMapper.haveGenerator();
        Generator generator = new Generator();

        if (!StringUtils.isEmpty(gen)) {
            generator = generatorMapper.selectById(Constant.GENERATOR_ID);
        } else {
            generator = Utils.getProfileByPropertiesLoaderUtils();
        }

        if (generator == null) {
            generator = new Generator();
            generator.setCover(false);
        }
        if (generator.getCover() == null) {
            generator.setCover(false);
        }
        if (StringUtils.isEmpty(generator.getProjectPath())) {
            String path = System.getProperty("user.dir");
            generator.setProjectPath(path);
        }

        return generator;
    }

    @Override
    public void updateGenerator(Generator generator) {
        generator.setId(Constant.GENERATOR_ID);
        // 自动设置Api路径，注释掉前需要同步取消前端的注释
        String separator = File.separator;
        String[] paths;
        if (!StringUtils.isEmpty(generator.getPath())) {
            if (separator.equals("\\")) {
                paths = generator.getPath().split("\\\\");
            } else {
                paths = generator.getPath().split(File.separator);
            }
            StringBuilder api = new StringBuilder();
            for (String path : paths) {
                api.append(path);
                api.append(separator);
                if (path.equals("src")) {
                    api.append("api");
                    break;
                }
            }
        }
        if (!StringUtils.isEmpty(generator.getProjectPath())) {
            generator.setProjectPath(generator.getProjectPath().replace("/", "\\"));
        } else {
            generator.setProjectPath(System.getProperty("user.dir"));
        }
        String gen = generatorMapper.haveGenerator();

        if (StringUtils.isEmpty(gen)) {
            Map<String, Object> map = new HashMap<>();
            map = EntityUtils.entityToMap(generator);
            Utils.updateProperties(map);
        } else {
            this.saveOrUpdate(generator);
        }
    }

    @Override
    public void generator(List<ColumnInfo> columnInfos, String tableName) {
        Generator generator = this.getById();
        TableInfo tableInfo = this.getByName(tableName);
        try {
            GenUtil.generatorCode(columnInfos, generator, tableName, tableInfo);
        } catch (IOException e) {
            log.error(tableName + "生成失败" + e.getMessage());
        }
    }
}
