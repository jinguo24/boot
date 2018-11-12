package ${controllerPackageName!};

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.simple.common.rest.Result;
import com.simple.common.rest.ResultData;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;
import ${entityPackageName}.${entityName};
import ${servicePackageName}.${entityName}Service;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("${entityInstanceName}")
public class ${entityName!}Controller extends BaseController
{
	@Autowired
    private ${entityName!}Service ${entityInstanceName}Service;

    private Logger logger = Logger.getLogger(${entityName!}Controller.class);

    @GetMapping("list")
        @ApiImplicitParams({
    	  @ApiImplicitParam(name="pageNum",value="页数",dataType="int", paramType = "query",required=true),
    	  @ApiImplicitParam(name="pageSize",value="每页条数",dataType="int", paramType = "query",required=true)})
    public ResultData list(@ModelAttribute ${entityName!} ${entityInstanceName},Integer pageNum, Integer pageSize) {
    	if (null == ${entityInstanceName}) ${entityInstanceName} = new ${entityName!}();
        final PageInfo<${entityName!}> page = ${entityInstanceName}Service.listAsPage(${entityInstanceName}, pageNum, pageSize);
        return new ResultData(page);
    }

    @PostMapping("add")
    public ResultData add(@RequestBody ${entityName!} ${entityInstanceName}) {
        //Assert.notNull(${entityInstanceName}.getName(), "角色名不能为空");
        //Assert.isTrue(!checkUnique(sysRole.getName(), null), "重复的角色名");
        ${entityInstanceName}Service.saveOrUpdate(${entityInstanceName});
        return new ResultData();
    }

    @PostMapping("update")
    public ResultData update(@RequestBody ${entityName!} ${entityInstanceName}) {
        ${entityInstanceName}Service.saveOrUpdate(${entityInstanceName});
        return new ResultData();
    }

    @GetMapping("/del")
     @ApiImplicitParam(name="id",value="id",dataType="String", paramType = "query",required=true)
    public ResultData delete(String id) {
        ${entityInstanceName}Service.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }
    
     @GetMapping("/findById")
     @ApiImplicitParam(name="id",value="id",dataType="String", paramType = "query",required=true)
    public ResultData findById(String id) {
    	return new ResultData(Result.SUCCESS,"查询成功",${entityInstanceName}Service.getById(id));
    }
	
	${ignoreText}
	
}
