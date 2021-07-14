package ${package}.controller;

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
import org.springframework.beans.factory.annotation.Autowired;


/**
* @author ${author}
* @date ${date}
*/
@Api(tags = "${remark}管理")
@RestController
@RequestMapping("/api/${changeClassName}")
public class ${className}Controller {


@Autowired
private ${className}Service ${changeClassName}Service;


    @PostMapping("/save")
    @ApiOperation("保存或者修改${className}")
    public ResponseEntity saveOrUpdates(@Validated @RequestBody ${className} ${changeClassName}){
        return new ResponseEntity<>(${changeClassName}Service.saveOrUpdates(${changeClassName}),HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    @ApiOperation("获取${className}")
    public ResponseEntity getById(@PathVariable("id") String id){
    return new ResponseEntity<>(${changeClassName}Service.getById(id),HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除${className}")
    public ResponseEntity delete(@PathVariable("id") String id){
        ${changeClassName}Service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
