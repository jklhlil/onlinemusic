package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import com.ecjtu.onlinemusic.entity.vo.RequestBean;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;
import com.ecjtu.onlinemusic.entity.vo.ResultEnum;
import com.ecjtu.onlinemusic.entity.vo.ResultVO;
import com.ecjtu.onlinemusic.service.impl.AdminServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController //@RestController 相当于 @Controller+@ResponseBody
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
public class AdminControl {
    @Autowired
    AdminServiceImpl adminService;
    
    @PostMapping("/login")  //管理员登录
    @ApiOperation("管理员登录")
    @ApiImplicitParam(name = "参数： 1.管理员对象 。 ")
    public ResultVO login(@RequestBody @Validated(insert.class) Admin admin) throws Exception {
        Admin admin1 = adminService.selectOne(admin);
        if (admin1!=null) {
            return new ResultVO(ResultEnum.LOGIN_SUCCESS,admin1);
        } else {
            return new ResultVO(ResultEnum.ADMIN_LOGIN_FAIL);
        }
    }

    @PostMapping("/put")  // 添加管理员，进行数据验证
    @ApiOperation("添加管理员")
    @ApiImplicitParam(name = "参数： 1.管理员对象 。 ")
    public ResultVO addAdmin(@RequestBody @Validated(insert.class) Admin admin) throws Exception {
        //添加并返回结果
        if (adminService.insert(admin) == 1) {
            return new ResultVO(ResultEnum.insert_success, admin);
        } else {
            return new ResultVO(ResultEnum.insert_failed);
        }
    }

    @DeleteMapping("/deleteById/{adminId}")  //根据id删除管理员
    @ApiOperation("删除管理员")
    @ApiImplicitParam(name = "参数： 1.管理员Id 。 ")
    public ResultVO deleteById(@PathVariable("adminId") Integer adminId) throws Exception {
        //删除并返回结果
        if (adminService.deleteById(adminId) == 1) {
            return new ResultVO(ResultEnum.delete_success);
        } else {
            return new ResultVO(ResultEnum.delete_failed);
        }
    }


    @PutMapping("/edit")  //更新管理员，进行数据验证
    @ApiOperation("编辑管理员信息")
    @ApiImplicitParam(name = "参数： 1.管理员对象 。 ")
    public ResultVO updateAdmin(@RequestBody @Validated(update.class) Admin admin) throws Exception {
        //更新并返回结果
        if (adminService.update(admin) == 1) {
            return new ResultVO(ResultEnum.update_success, admin);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }


    @PostMapping("/findByPage")  //查询管理员
    @ApiOperation("查找管理员，分页")
    @ApiImplicitParam(name = "参数： 1.data:管理员对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByPage(@RequestBody @Validated(select.class) RequestBean<Admin> requestBean) throws Exception {

        RespPageBean respPageBean = adminService.selectByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }









//
//    @PostMapping("/findByName")  //通过管理员名查询管理员
//    @ApiOperation("根据 管理员名 查找管理员，分页")
//    @ApiImplicitParam(name = "参数： 1.data:管理员名 。 2.page:第几页 。 3.pageSize：一页大小 。")
//    public ResultVO findByAdminName(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
//
//        // 构建管理员，后查询
//        Admin admin = new Admin();
//        admin.setName(requestBean.getData());
//        RespPageBean respPageBean = adminService.selectByPage(admin, requestBean.getPage(), requestBean.getPageSize());
//
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success,respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }
//
//    @PostMapping("/findById")  //通过管理员名查询管理员
//    @ApiOperation("查找 一个 管理员，根据Id")
//    @ApiImplicitParam(name = "参数： 1.管理员Id 。 ")
//    public ResultVO findByAdminId(@RequestBody Integer adminId) throws Exception {
//
//        // 构建管理员，后查询
//        Admin admin = new Admin();
//        admin.setId(adminId);
//        Admin Admin1 = adminService.selectOne(admin);
//
//        //返回数据
//        if (Admin1!=null) {
//            return new ResultVO(ResultEnum.select_success,Admin1);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }



}