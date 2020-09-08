package ${package}.service;

import ${package}.entity.${className};
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ${author}
* @date ${date}
*/
public interface ${className}Service  extends IService<${className}>{

    /**
     * 根据ID查询
     * @param id
     */
    ${className} getById(String id);

    /**
    * 保存或者修改
    * @param ${changeClassName}
    */
    void saveOrUpdates(${className} ${changeClassName});

    /**
    * 根据ID删除
    * @param id
    */
    void delete(String id));

}
