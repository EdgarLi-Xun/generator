package com.edgarli.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edgarli.generator.entity.Generator;
import com.edgarli.generator.entity.vo.ColumnInfo;
import com.edgarli.generator.entity.vo.TableInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName GeneratorServer
 * @Description
 * @Author lixun
 * @Date 2020/8/31 17:47
 * @Version 1.0
 **/
@Repository
public interface GeneratorMapper extends BaseMapper<Generator> {

    /**
     * 获取当前数据库中的所有表
     *
     * @param name
     * @param page
     * @return
     */
    IPage<TableInfo> queryPage(@Param("name") String name, Page<TableInfo> page);

    TableInfo getByName(@Param("name") String name);

    /**
     * 根据表名获取当前表中的所有的列数据
     *
     * @param name
     * @return
     */
    List<ColumnInfo> getColums(@Param("name") String name);

    String haveGenerator();
}
