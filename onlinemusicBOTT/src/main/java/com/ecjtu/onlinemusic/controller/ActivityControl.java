package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.Activity;
import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import com.ecjtu.onlinemusic.entity.vo.RequestBean;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;
import com.ecjtu.onlinemusic.entity.vo.ResultEnum;
import com.ecjtu.onlinemusic.entity.vo.ResultVO;
import com.ecjtu.onlinemusic.service.impl.ActivityServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController //@RestController 相当于 @Controller+@ResponseBody
@RequestMapping("/activity")
@Api(tags = "活动相关接口")
public class ActivityControl {
    @Autowired
    ActivityServiceImpl activityService;

    //根据id删除
    @DeleteMapping("/deleteById/{activityId}")  //根据id删除活动
    @ApiOperation("删除活动")
    @ApiImplicitParam(name = "参数： 1.活动Id 。 ")
    public ResultVO deleteById(@PathVariable("activityId") Integer activityId) throws Exception {
        //删除并返回结果
        if (activityService.deleteById(activityId) == 1) {
            return new ResultVO(ResultEnum.delete_success);
        } else {
            return new ResultVO(ResultEnum.delete_failed);
        }
    }

    @PostMapping("/publish")  // 添加活动，进行数据验证
    @ApiOperation("发布活动")
    @ApiImplicitParam(name = "参数： 1.活动对象 。 ")
    public ResultVO addActivity(@RequestBody @Validated(insert.class) Activity activity) throws Exception {
        //添加并返回结果
        if (activityService.insert(activity) == 1) {
            return new ResultVO(ResultEnum.insert_success, activity);
        } else {
            return new ResultVO(ResultEnum.insert_failed);
        }
    }

    @PutMapping("/edit")  //更新活动，进行数据验证
    @ApiOperation("编辑活动信息")
    @ApiImplicitParam(name = "参数： 1.活动对象 。 ")
    public ResultVO updateActivity(@RequestBody @Validated(update.class) Activity activity) throws Exception {
        //更新并返回结果
        if (activityService.update(activity) == 1) {
            return new ResultVO(ResultEnum.update_success, activity);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }


    @PostMapping("/findByPage")  //查询活动
    @ApiOperation("查找活动，分页")
    @ApiImplicitParam(name = "参数： 1.data:活动对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByPage(@RequestBody @Validated(select.class) RequestBean<Activity> requestBean) throws Exception {
        // 构建活动，后查询
        RespPageBean respPageBean = activityService.selectByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findAll")  //查询活动
    @ApiOperation("查找活动，分页")
    @ApiImplicitParam(name = "参数： 1.data:活动对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByPage() throws Exception {
        // 构建活动，后查询
        RespPageBean respPageBean = activityService.selectByPage(null,null,null);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findByName")  //通过活动名查询活动
    @ApiOperation("查找活动，根据 活动名,分页")
    @ApiImplicitParam(name = "参数： 1.data:活动名 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByActivityName(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
        RespPageBean respPageBean = activityService.findByActivityName(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findById")  //通过活动名查询活动
    @ApiOperation("查找 一个 活动，根据Id")
    @ApiImplicitParam(name = "参数： 1.活动Id 。 ")
    public ResultVO findByActivityId(@RequestBody Integer activityId) throws Exception {
        Activity activity = activityService.findByActivityId(activityId);
        //返回数据
        if (activity!=null) {
            return new ResultVO(ResultEnum.select_success,activity);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }


//    @PostMapping("/findActivityById")  //查询活动
//    @ApiOperation("根据用户id查看参加的活动，分页。")
//    public ResultVO findActivityByConsumerId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
//
//        // 构建活动，后查询
//        Activity activity = new Activity();
//        activity.setConsumerId(requestBean.getData());
//        RespPageBean respPageBean = activityService.selectActivityByPage(activity, requestBean.getPage(), requestBean.getPageSize());
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }


}