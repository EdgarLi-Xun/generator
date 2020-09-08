package ${package}.controller;

import sega.app.aop.log.Log;
import ${package}.entity.${className};
import ${package}.service.${className}Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author ${author}
* @date ${date}
*/
@Api(tags = "${className}管理")
@RestController
@RequestMapping("/api/${changeClassName}")
public class ${className}Controller {


@Autowired
private ${className}Service ${changeClassName}Service;


    @PostMapping("/save")
    @Log("保存或者修改${className}")
    @ApiOperation("保存或者修改${className}")
    public ResponseEntity saveOrUpdates(@Validated @RequestBody ${className} ${changeClassName}){
        return new ResponseEntity<>(${changeClassName}Service.saveOrUpdates(${changeClassName}),HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    @Log("获取${className}")
    @ApiOperation("获取${className}")
    public ResponseEntity getById(@PathVariable("id") String id){
    return new ResponseEntity<>(${changeClassName}Service.getById(id),HttpStatus.OK);
    }

    @getMapping("/delete/{id}")
    @Log("删除${className}")
    @ApiOperation("删除${className}")
    public ResponseEntity delete(@PathVariable("id") String id){
        ${changeClassName}Service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
