package ${package}.service.impl;

import ${package}.entity.${className};
import ${package}.service.${className}Service;
import ${package}.dao.${className}Mapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void saveOrUpdates(${className} ${changeClassName}) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        ${changeClassName}Mapper.deleteById(id);
    }
    @Override
    public ${className} getById(String id) {
       return ${changeClassName}Mapper.selectById(id);
    }

}
