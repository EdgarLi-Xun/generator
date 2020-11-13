package com.edgarli.generator.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.*;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.edgarli.generator.entity.Generator;
import com.edgarli.generator.entity.vo.ColumnInfo;
import com.edgarli.generator.entity.vo.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 *
 * @author auto
 * @date 2019-01-02
 */
@Slf4j
public class GenUtil {

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    private static final String PK = "PRI";

    private static final String EXTRA = "auto_increment";

    /**
     * 获取后端代码模板名称
     *
     * @return List
     */
    private static List<String> getAdminTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity");
        templateNames.add("Mapper");
        templateNames.add("Service");
        templateNames.add("ServiceImpl");
        templateNames.add("Controller");
        return templateNames;
    }

    /**
     * 获取后端代码模板名称
     *
     * @return List
     */
    private static List<String> getMapperTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Mapper");
        return templateNames;
    }

    /**
     * 获取前端代码模板名称
     *
     * @return List
     */
    private static List<String> getFrontTemplateNames() {
        List<String> templateNames = new ArrayList<>();
//        templateNames.add("api");
//        templateNames.add("index");
//        templateNames.add("eForm");
        return templateNames;
    }

    /**
     * 生成代码
     *  @param columnInfos 表元数据
     * @param generator   生成代码的参数配置，如包路径，作者
     * @param tableInfo
     */
    public static void generatorCode(List<ColumnInfo> columnInfos, Generator generator, String tableName, TableInfo tableInfo) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("package", generator.getPack());
        map.put("remark", tableInfo.getRemark());
        map.put("moduleName", generator.getModuleName());
        map.put("author", generator.getAuthor());
        map.put("date", LocalDate.now().toString());
        map.put("tableName", tableName);
        String className = Utils.toCapitalizeCamelCase(tableName);
        String changeClassName = Utils.toCamelCase(tableName);

        // 判断是否去除表前缀
        if (StringUtils.isNotEmpty(generator.getPrefix())) {
            className = Utils.toCapitalizeCamelCase(StrUtil.removePrefix(tableName, generator.getPrefix()));
            changeClassName = Utils.toCamelCase(StrUtil.removePrefix(tableName, generator.getPrefix()));
        }
        map.put("className", className);
        map.put("upperCaseClassName", className.toUpperCase());
        map.put("changeClassName", changeClassName);
        map.put("hasTimestamp", false);
        map.put("queryHasTimestamp", false);
        map.put("queryHasBigDecimal", false);
        map.put("hasBigDecimal", false);
        map.put("hasQuery", false);
        map.put("auto", false);

        List<Map<String, Object>> columns = new ArrayList<>();
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        for (ColumnInfo column : columnInfos) {
            Map<String, Object> listMap = new HashMap<>();
            listMap.put("columnComment", column.getColumnComment());
            listMap.put("columnKey", column.getColumnKey());

            String colType = ColUtil.cloToJava(column.getColumnType().toString());
            String changeColumnName = Utils.toCamelCase(column.getColumnName().toString());
            String capitalColumnName = Utils.toCapitalizeCamelCase(column.getColumnName().toString());
            if (PK.equals(column.getColumnKey())) {
                map.put("pkColumnType", colType);
                map.put("pkChangeColName", changeColumnName);
                map.put("pkCapitalColName", capitalColumnName);
            }
            if (TIMESTAMP.equals(colType)) {
                map.put("hasTimestamp", true);
            }
            if (BIGDECIMAL.equals(colType)) {
                map.put("hasBigDecimal", true);
            }
            if (EXTRA.equals(column.getExtra())) {
                map.put("auto", true);
            }
            listMap.put("columnType", colType);
            listMap.put("columnName", column.getColumnName());
            listMap.put("isNullable", column.getIsNullable());
            listMap.put("columnShow", column.getColumnShow());
            listMap.put("changeColumnName", changeColumnName);
            listMap.put("capitalColumnName", capitalColumnName);

            // 判断是否有查询，如有则把查询的字段set进columnQuery
            if (!StringUtils.isBlank(column.getColumnQuery())) {
                listMap.put("columnQuery", column.getColumnQuery());
                map.put("hasQuery", true);
                if (TIMESTAMP.equals(colType)) {
                    map.put("queryHasTimestamp", true);
                }
                if (BIGDECIMAL.equals(colType)) {
                    map.put("queryHasBigDecimal", true);
                }
                queryColumns.add(listMap);
            }
            columns.add(listMap);
        }
        map.put("columns", columns);
        map.put("queryColumns", queryColumns);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));

        // 生成后端代码
        List<String> templates = getAdminTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("java/" + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, generator, className);

            assert filePath != null;
            File file = new File(filePath);

            // 如果非覆盖生成
            if (!generator.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // 生成代码
            genFile(file, template, map);
        }

        // 生成Mapper
        templates = getMapperTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("mapper/" + templateName + ".ftl");
            String filePath = getMapperFilePath(templateName, generator, className);

            assert filePath != null;
            File file = new File(filePath);

            // 如果非覆盖生成
            if (!generator.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // 生成代码
            genFile(file, template, map);
        }

        // 生成前端代码
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("html/" + templateName + ".ftl");
            String filePath = getFrontFilePath(templateName, generator, map.get("changeClassName").toString());

            assert filePath != null;
            File file = new File(filePath);

            // 如果非覆盖生成
            if (!generator.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // 生成代码
            genFile(file, template, map);
        }
    }

    /**
     * 定义后端文件路径以及名称
     */
    private static String getAdminFilePath(String templateName, Generator generator, String className) {
        String projectPath = generator.getProjectPath() + File.separator + generator.getModuleName();
        String packagePath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!ObjectUtils.isEmpty(generator.getPack())) {
            packagePath += generator.getPack().replace(".", File.separator) + File.separator;
        }

        if ("Entity".equals(templateName)) {
            return packagePath + "entity" + File.separator + className + ".java";
        }

        if ("Controller".equals(templateName)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if ("Service".equals(templateName)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if ("ServiceImpl".equals(templateName)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if ("Mapper".equals(templateName)) {
            return packagePath + "dao" + File.separator + className + "Mapper.java";
        }

        return null;
    }

    /**
     * 定义后端文件路径以及名称
     */
    private static String getMapperFilePath(String templateName, Generator generator, String className) {
        String projectPath = generator.getProjectPath() + File.separator + generator.getModuleName();
        if ("Mapper".equals(templateName)) {
            return projectPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "mappers" + File.separator + className + "Mapper.xml";
        }
        return null;
    }

    /**
     * 定义前端文件路径以及名称
     */
    private static String getFrontFilePath(String templateName, Generator generator, String apiName) {
        String path = generator.getPath();

        if ("index".equals(templateName)) {
            return path + File.separator + "index.vue";
        }

        if ("eForm".equals(templateName)) {
            return path + File.separator + File.separator + "form.vue";
        }
        return null;
    }

    private static void genFile(File file, Template template, Map<String, Object> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(map, writer);
        } catch (TemplateException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            assert writer != null;
            writer.close();
        }
    }
}
