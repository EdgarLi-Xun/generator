package ${package}.service.impl;

import ${package}.entity.${className};
import ${package}.service.${className}Service;
import ${package}.mapper.${className}Mapper;
import org.springframework.stereotype.Service;

/**
* @author ${author}
* @date ${date}
*/
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Service {
    @Autowired
    private ${className}Mapper ${changeClassName}Mapper;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(${className} ${changeClassName}) {
        <#if columns??>
            <#list columns as column>
                <#if column.columnKey = 'PRI'>
                    if (StringUtils.isEmpty(hyff.getcolumn())) {
                    ${changeClassName}Mapper.insert(${changeClassName});
                    } else {
                    ${changeClassName}Mapper.updateById(${changeClassName});
                    }
                </#if>
            </#list>
        </#if>


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        ${changeClassName}Mapper.deleteById(id);
    }
    @Override
    public ${className} getById(String id) {
       return ${changeClassName}Mapper.selectById(id);
    }

}
