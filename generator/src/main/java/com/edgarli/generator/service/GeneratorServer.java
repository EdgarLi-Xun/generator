package com.edgarli.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edgarli.generator.entity.Generator;
import com.edgarli.generator.entity.vo.ColumnInfo;
import com.edgarli.generator.entity.vo.TableInfo;

import java.util.List;

/**
 * @ClassName GeneratorServer
 * @Description
 * @Author lixun
 * @Date 2020/8/31 17:49
 * @Version 1.0
 **/
public interface GeneratorServer extends IService<Generator> {

    /**
     * 获取当前数据库中所有的表
     *
     * @param name
     * @param start
     * @param limit
     * @return
     */
    IPage<TableInfo> queryPage(String name, long start, long limit);

    /**
     * 根据表名获取当前表中所有的列数据
     *
     * @param name
     * @return
     */
    List<ColumnInfo> getColums(String name);

    /**
     * 查询当前配置文件
     *
     * @return
     */
    Generator getById();

    /**
     * 修改当前配置文件
     *
     * @param generator
     */
    void updateGenerator(Generator generator);

    /**
     * 生成代码
     *
     * @param columnInfos
     * @param tableName
     */
    void generator(List<ColumnInfo> columnInfos, String tableName);
}
