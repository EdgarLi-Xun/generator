package com.edgarli.generator.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 表的数据信息
 *
 * @author auto
 * @date 2019-01-02
 */
@Data
@Accessors(chain = true)
public class TableInfo {

    // 表名称
    private Object tableName;

    // 创建日期
    private Object createTime;

    // 数据库引擎
    private Object engine;

    // 编码集
    private Object coding;

    // 备注
    private Object remark;


}
