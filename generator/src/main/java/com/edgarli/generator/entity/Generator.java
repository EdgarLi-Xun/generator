package com.edgarli.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 代码生成配置
 *
 * @author auto
 * @date 2019-01-03
 */
@Data
@Accessors(chain = true)
public class Generator implements Serializable {

    @TableId
    private String id;

    // 包路径
    private String pack;

    // 模块名
    private String moduleName;

    // 前端文件路径
    private String path;

    // 作者
    private String author;

    // 表前缀
    private String prefix;

    // 是否覆盖
    private Boolean cover;

    // 项目路径
    private String projectPath;
}
