package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.ActivityConsumer;
import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.impl.ActivityConsumerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController //@RestController 相当于 @Controller+@ResponseBody
@RequestMapping("/actiCons")
@Api(tags = "用户参加活动相关接口")
public class ActivityConsumerControl {
    @Autowired
    ActivityConsumerServiceImpl actiConsService;

    //根据id删除
    @DeleteMapping("/cancelById/{activityConsumerId}")  //根据id删除 用户活动
    @ApiOperation("取消参加活动")
    @ApiImplicitParam(name = "参数： 1.活动用户Id 。 ")
    public ResultVO deleteById(@PathVariable("activityConsumerId") Integer id) throws Exception {
        //删除并返回结果
        if (actiConsService.deleteById(id) == 1) {
            return new ResultVO(ResultEnum.delete_success);
        } else {
            return new ResultVO(ResultEnum.delete_failed);
        }
    }

    @PostMapping("/join")  // 添加用户活动， 进行数据验证
    @ApiOperation("参加活动")
    @ApiImplicitParam(name = "参数： 1.活动用户对象 。 ")
    public ResultVO addActiCons(@RequestBody @Validated(insert.class) ActivityConsumer activityConsumer) throws Exception {
        //添加并返回结果
        if (actiConsService.insert(activityConsumer) == 1) {
            return new ResultVO(ResultEnum.insert_success, activityConsumer);
        } else {
            return new ResultVO(ResultEnum.insert_failed);
        }
    }

    @PutMapping("/update")  //更新用户活动， 进行数据验证
    @ApiOperation("更新活动的信息")
    @ApiImplicitParam(name = "参数： 1.活动用户对象 。 ")
    public ResultVO updateActiCons(@RequestBody @Validated(update.class) ActivityConsumer activityConsumer) throws Exception {
        //更新并返回结果
        if (actiConsService.update(activityConsumer) == 1) {
            return new ResultVO(ResultEnum.update_success, activityConsumer);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }


    // 多表
    @PostMapping("/findConsumerById")  //查询用户
    @ApiOperation("查看活动参加的人，分页")
    @ApiImplicitParam(name = "参数： 1.data:活动Id 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findConsumerByActivityId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
        RespPageBean respPageBean = actiConsService.findConsumerByActivityId(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findActivityById")  //查询活动
    @ApiOperation("查看用户参加的活动，分页")
    @ApiImplicitParam(name = "参数： 1.data:用户Id 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findActivityByConsumerId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
        RespPageBean respPageBean = actiConsService.findActivityByConsumerId(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }







//    @PostMapping("/findByPage")  //查询用户活动
//    @ApiOperation("通过 对象的任何属性 查找。用不上。")
//    @ApiImplicitParam(name = "参数： 1.data:活动用户对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
//    public ResultVO findByPage(@RequestBody @Validated(select.class) RequestBean<ActivityConsumer> requestBean) throws Exception {
//
//        RespPageBean respPageBean = actiConsService.selectByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }




// 通过对象查询用户
//    @PostMapping("/findConsumerByPage")  //查询用户
//    @ApiOperation("查看活动参加的人，分页。")
//    public ResultVO findConsumerByPage(@RequestBody @Validated(select.class) RequestBean<ActivityConsumer> requestBean) throws Exception {
//        System.err.println(requestBean);
//
//
//        RespPageBean respPageBean = actiConsService.selectConsumerByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }
//
//    @PostMapping("/findActivityByPage")  //查询活动
//    @ApiOperation("查看用户参加的活动，分页。")
//    public ResultVO findActivityByPage(@RequestBody @Validated(select.class) RequestBean<ActivityConsumer> requestBean) throws Exception {
//        // 构建用户活动，后查询
//        RespPageBean respPageBean = actiConsService.selectActivityByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }


//    @GetMapping("/findConsumerByActiId/{activityId}")  //通过活动id查找用户,默认前20条。
//    @ApiOperation("通过活动id查找参加的用户，默认查找前20个人。")
//    public ResultVO findConsumerByActivityId(@PathVariable("activityId") @Validated(select.class) int activityId) throws Exception {
//        // 构建用户活动，后查询
//        ActivityConsumer activityConsumer = new ActivityConsumer();
//        activityConsumer.setActivityId(activityId);
//        RespPageBean respPageBean = actiConsService.selectConsumerByPage(activityConsumer,1,20);
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }
//
//    @GetMapping("/findActivityByUserId/{userId}")  //通过用户id查找活动,默认前10条。
//    @ApiOperation("通过用户id查找参加的用户，默认查找前20个人。")
//    public ResultVO findActivityByConsumerId(@PathVariable("userId")int consumerId) throws Exception {
//        // 构建用户活动，后查询
//        ActivityConsumer activityConsumer = new ActivityConsumer();
//        activityConsumer.setConsumerId(consumerId);
//        RespPageBean respPageBean = actiConsService.selectActivityByPage(activityConsumer,1,10);
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }


}