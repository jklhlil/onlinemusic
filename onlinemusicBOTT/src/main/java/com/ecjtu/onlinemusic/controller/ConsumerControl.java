package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.impl.ConsumerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;


@RestController //@RestController 相当于 @Controller+@ResponseBody
@RequestMapping("/consumer")
@Api(tags = "用户相关接口")
public class ConsumerControl {
    @Autowired
    ConsumerServiceImpl consumerService;

    @PostMapping("/login")  //用户登录
    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "参数： 1.用户对象 。")
    public ResultVO login(@RequestBody @Validated(insert.class) Consumer consumer, Model model) throws Exception {
        RespPageBean respPageBean = consumerService.selectByPage(consumer, 1, 1);
        //返回数据
        if (respPageBean.getDataList().size() != 0) {
            return new ResultVO(ResultEnum.LOGIN_SUCCESS, respPageBean.getDataList().get(0));
        } else {
            System.err.println("登陆失败"+respPageBean.getDataList().toString());
            return new ResultVO(ResultEnum.USER_LOGIN_FAIL);
        }
    }

    @PostMapping("/register")  // 添加用户，进行数据验证
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "参数： 1.用户对象 。")
    public ResultVO registerConsumer(@RequestBody @Validated(insert.class) Consumer consumer) throws Exception {
        //添加并返回结果
        Integer stat = consumerService.register(consumer);
        if (stat == 1) {
            return new ResultVO(ResultEnum.insert_success, consumer);
        } else if (stat == 2) {
            return new ResultVO(ResultEnum.insert_failed, "邮箱已被注册");
        } else {
            return new ResultVO(ResultEnum.insert_failed, "未知错误");
        }
    }



    @DeleteMapping("/deleteById/{consumerId}")  //根据id删除用户
    @ApiOperation("注销用户")
    @ApiImplicitParam(name = "参数： 1.用户Id 。")
    public ResultVO deleteById(@PathVariable("consumerId") String consumerId, HttpServletRequest servletRequest) throws Exception {

        System.err.println(servletRequest.getScheme() + "://" + servletRequest.getServerName() + ":" + servletRequest.getLocalPort());

        Integer consumerId1 = Integer.parseInt(consumerId);
        //删除并返回结果
        if (consumerService.deleteById(consumerId1) == 1) {
            return new ResultVO(ResultEnum.delete_success);
        } else {
            return new ResultVO(ResultEnum.delete_failed);
        }
    }

    @DeleteMapping("/batchDelete") //通过音乐id数组批量删除
    @ApiOperation("批量删除 通过id")
    @ApiImplicitParam(name = "参数： 1.data:音乐id数组 。")
    public ResultVO batchDeleteByids(@RequestBody List<Integer> idList) throws Exception {

        //返回数据
        if (consumerService.batchDeleteByids(idList) == 1) {
            return new ResultVO(ResultEnum.delete_success);
        } else {
            return new ResultVO(ResultEnum.delete_failed);
        }
    }


    @PutMapping("/edit")  //更新用户，进行数据验证
    @ApiOperation("编辑用户信息")
    @ApiImplicitParam(name = "参数： 1.用户对象 。")
    public ResultVO updateConsumer(@RequestBody @Validated(update.class) Consumer consumer, Model model) throws Exception {
        //更新并返回结果
        if (consumerService.update(consumer) == 1) {
            return new ResultVO(ResultEnum.update_success, consumer);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }

    @PutMapping("/updatePicture")  //更新用户，进行数据验证
    @ApiOperation("更新头像")
    @ApiImplicitParam(name = "参数： 1.用户id 。 2.picture:照片文件。")
    public ResultVO updatePicture(Integer userId, MultipartFile file, HttpServletRequest servletRequest) throws Exception {
        // 数据验证
//        System.err.println(userId);
//        System.err.println(file);
        if (userId == null || file == null) {
            return new ResultVO(ResultEnum.VALIDATE_FAILED, "用户id和头像文件，不能为空");
        }

        //更新并返回结果
        HashMap map = consumerService.updatePicture(userId, file, servletRequest);
        if (map.get("stat") == "1") {
            return new ResultVO(ResultEnum.update_success, "照片链接为：" + map.get("pictureUrl"));
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }

    }


    @PostMapping("/findByPage")  //查询用户
    @ApiOperation("查找用户，分页")
    @ApiImplicitParam(name = "参数： 1.data:用户对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByPage(@RequestBody @Validated(select.class) RequestBean<Consumer> requestBean) throws Exception {
        // 构建用户，后查询
        RespPageBean respPageBean = consumerService.selectByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }


    @PostMapping("/findByName")  //通过用户名查询用户
    @ApiOperation("根据 用户名 查询用户，分页")
    @ApiImplicitParam(name = "参数： 1.data:用户名 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByConsumerName(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
        RespPageBean respPageBean = consumerService.findByConsumerName(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findById")  //通过用户名查询用户
    @ApiOperation("查看 一个 用户，根据Id")
    @ApiImplicitParam(name = "参数： 1.用户Id 。")
    public ResultVO findByConsumerId(@RequestBody Integer consumerId) throws Exception {

        Consumer consumer1 = consumerService.findByConsumerId(consumerId);

        //返回数据
        if (consumer1 != null) {
            return new ResultVO(ResultEnum.select_success, consumer1);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }


//    @PostMapping("/findConsumerById")  //查询用户
//    @ApiOperation("根据用户id查看参加的用户，分页。")
//    public ResultVO findConsumerByConsumerId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
//
//        // 构建用户，后查询
//        Consumer consumer = new Consumer();
//        consumer.setConsumerId(requestBean.getData());
//        RespPageBean respPageBean = consumerService.selectConsumerByPage(consumer, requestBean.getPage(), requestBean.getPageSize());
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }


}