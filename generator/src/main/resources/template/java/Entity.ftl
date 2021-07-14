package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
* @author ${author}
* @date ${date}
* ${remark}管理
*/
@Data
@Accessors(chain = true)
public class ${className} implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if column.columnComment != ''>
    /**
    * ${column.columnComment}
    */
    </#if>
    <#if column.columnKey = 'PRI'>
    @TableId
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}
