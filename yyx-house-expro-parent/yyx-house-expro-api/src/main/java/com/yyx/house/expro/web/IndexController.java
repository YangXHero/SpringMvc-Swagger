package com.yyx.house.expro.web;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yyx.core.entity.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangya on 2017/4/24.
 */
@RestController
public class IndexController {



    @RequestMapping(value="/login", method = RequestMethod.GET)
    @ApiOperation(value = "用户登录", httpMethod = "GET", response = ResponseEntity.class, notes = "用户登录")
    public ResponseEntity login(@ApiParam(required = true, name = "userName", value = "用户名")  String userName,String password) throws Exception{
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
