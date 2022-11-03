package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.impl.SubscribeCollectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController //@RestController 相当于 @Controller+@ResponseBody
@RequestMapping("/subCol")
@Api(tags = "收藏订阅相关接口")
public class SubscribeCollectControl {
    @Autowired
    SubscribeCollectServiceImpl subColService;

    @PostMapping("/update")  //更新收藏订阅， 进行数据验证
    @ApiOperation("添加或取消收藏订阅")
    @ApiImplicitParams({@ApiImplicitParam(name = "参数： 1.收藏订阅对象 。"),
            @ApiImplicitParam(name = "isCollect=0 为取消收藏，isCollect=1为添加收藏 。 "),
            @ApiImplicitParam(name = "isSubscribe 订阅同理 。 "),
    })
    public ResultVO modifySubCol(@RequestBody @Validated(update.class) SubscribeCollect subscribeCollect) throws Exception {
        //更新并返回结果
        if (subColService.modifySubCol(subscribeCollect) == 1) {
            return new ResultVO(ResultEnum.update_success, subscribeCollect);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }

    // 多表
    @PostMapping("/findColMusicById")  //查询音乐
    @ApiOperation("查看用户收藏的音乐，分页")
    @ApiImplicitParam(name = "参数： 1.data:用户id 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findCollectMusicByConsumerId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
        RespPageBean respPageBean = subColService.findColMusicByConsumerId(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findSubMusicById")  //查询音乐
    @ApiOperation("查看用户订阅的音乐，分页")
    @ApiImplicitParam(name = "参数： 1.data:用户id 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findSubMusicConsumerId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
        RespPageBean respPageBean = subColService.findSubMusicConsumerId(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/colRankBypage")  //查询查看收藏排行榜音乐
    @ApiOperation("查看收藏排行榜，分页")
    @ApiImplicitParam(name = "参数： 1.page:第几页 。 2.pageSize：一页大小 。")
    public ResultVO queryColRankBypage(@RequestBody @Validated(select.class) PageBean pageBean) throws Exception {
        RespPageBean respPageBean = subColService.queryColRankBypage(pageBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }
    @PostMapping("/subRankBypage")  //查询查看订阅排行榜音乐
    @ApiOperation("查看订阅排行榜，分页")
    @ApiImplicitParam(name = "参数： 1.page:第几页 。 2.pageSize：一页大小 。")
    public ResultVO querySubRankBypage(@RequestBody @Validated(select.class) PageBean pageBean) throws Exception {
        RespPageBean respPageBean = subColService.querySubRankBypage(pageBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }



//    // 多表
//    @PostMapping("/findColUserById")  //查询用户
//    @ApiOperation("查看音乐被哪些人收藏，分页")
//    @ApiImplicitParam(name = "参数： 1.data:音乐id 。 2.page:第几页 。 3.pageSize：一页大小 。")
//    public ResultVO findCollectByMusicId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
//
//        // 构建收藏订阅，后查询
//        SubscribeCollect subscribeCollect = new SubscribeCollect();
//        subscribeCollect.setMusicId(requestBean.getData());
//        subscribeCollect.setIsCollect(1);
//        RespPageBean respPageBean = subColService.selectConsumerByPage(subscribeCollect, requestBean.getPage(), requestBean.getPageSize());
//
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }

//    @PostMapping("/findSubUserById")  //查询用户
//    @ApiOperation("查看音乐被哪些人收藏，分页")
//    @ApiImplicitParam(name = "参数： 1.data:音乐id 。 2.page:第几页 。 3.pageSize：一页大小 。")
//    public ResultVO findSubscribeByMusicId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
//
//        // 构建收藏订阅，后查询
//        SubscribeCollect subscribeCollect = new SubscribeCollect();
//        subscribeCollect.setMusicId(requestBean.getData());
//        subscribeCollect.setIsSubscribe(1);
//        RespPageBean respPageBean = subColService.selectConsumerByPage(subscribeCollect, requestBean.getPage(), requestBean.getPageSize());
//
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }




}