package com.yyx.house.expro.web;


import com.yyx.core.entity.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by zhangya on 2017/4/24.
 */
@Api(value = "contacts-api", description = "用户登录", position = 5)
@RestController
public class IndexController {




    @ApiOperation(value = "用户登录", httpMethod = "GET", response = ResponseEntity.class, notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ResponseEntity login(String userName, String password) throws Exception{
//        UcUser ucUser = ucUserManager.getUserByName(name);

//        if(ucUser != null) {
//            ApiResult<UcUser> result = new ApiResult<UcUser>();
//            result.setCode(ResultCode.SUCCESS.getCode());
//            result.setData(ucUser);
//            return result;
//        } else {
//            throw new BusinessException("根据{name=" + name + "}获取不到UcUser对象");
//        }
        ResponseEntity testEntity = new ResponseEntity(new Integer(1),"用户登录成功");
        return testEntity;
    }
}
