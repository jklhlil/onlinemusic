package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.Comment;
import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.impl.CommentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //@RestController 相当于 @Controller+@ResponseBody
@RequestMapping("/comment")
@Api(tags = "评论相关接口")
public class CommentControl {
    @Autowired
    CommentServiceImpl commentService;

    //根据id删除
    @DeleteMapping("/deleteById/{commentId}")  //根据id删除 评论
    @ApiOperation("删除评论")
    @ApiImplicitParam(name = "参数： 1.评论Id 。 ")
    public ResultVO deleteById(@PathVariable("commentId") String commentId) throws Exception {
        Integer commentId1 = Integer.parseInt(commentId);
        //删除并返回结果
        if (commentService.deleteById(commentId1) == 1) {
            return new ResultVO(ResultEnum.delete_success);
        } else {
            return new ResultVO(ResultEnum.delete_failed);
        }
    }

    @PostMapping("/comment")  // 添加评论， 进行数据验证
    @ApiOperation("添加评论，没通过审核")
    @ApiImplicitParam(name = "参数： 1.评论对象 。 ")
    public ResultVO addComment(@RequestBody @Validated(insert.class) Comment comment) throws Exception {
        //添加并返回结果
        if (commentService.insert(comment) == 1) {
            return new ResultVO(ResultEnum.insert_success, comment);
        } else {
            return new ResultVO(ResultEnum.insert_failed);
        }
    }

    @PutMapping("/batchCheck") //通过评论id数组 批量通过审核
    @ApiOperation("批量通过审核 通过id")
    @ApiImplicitParam(name = "参数： 1.data:评论id数组 。")
    public ResultVO batchCheckByids(@RequestBody List<Integer> idList) throws Exception {
        //返回数据
        if (commentService.batchCheckByids(idList) == 1) {
            return new ResultVO(ResultEnum.update_success);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }

    @PutMapping("/update")  //更新评论， 进行数据验证
    @ApiOperation("编辑评论信息")
    @ApiImplicitParam(name = "参数： 1.评论对象 。 ")
    public ResultVO updateComment(@RequestBody @Validated(update.class) Comment comment) throws Exception {
        //更新并返回结果
        if (commentService.update(comment) == 1) {
            return new ResultVO(ResultEnum.update_success, comment);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }

    @PostMapping("/findByPage")  //查询评论
    @ApiOperation("查找评论，分页")
    @ApiImplicitParam(name = "参数： 1.data:评论对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByPage(@RequestBody @Validated(select.class) RequestBean<Comment> requestBean) throws Exception {

        RespPageBean respPageBean = commentService.selectByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    // 多表
    @PostMapping("/findConsumerById")  //通过审核的评论
    @ApiOperation("查看音乐的评论，分页")
    @ApiImplicitParam(name = "参数： 1.data:音乐Id 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findConsumerByMusicId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
        RespPageBean respPageBean = commentService.findConsumerByMusicId(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findConsumerNumById")  //通过审核的评论
    @ApiOperation("查看音乐的评论数，分页")
    @ApiImplicitParam(name = "参数： 1.data:音乐Id 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findConsumerByMusicId(@RequestBody Integer musicId) throws Exception {
        Integer commentNum = commentService.findConsumerNumByMusicId(musicId);
        //返回数据
        return new ResultVO(ResultEnum.select_success, commentNum);

    }

    @PostMapping("/findMusicById")  //通过审核的评论
    @ApiOperation("查看用户的评论，分页")
    @ApiImplicitParam(name = "参数： 1.data:用户Id 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findMusicByConsumerId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
        RespPageBean respPageBean = commentService.findMusicByConsumerId(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findNoCheck")  //查询没通过审核的评论
    @ApiOperation("查看没通过审核的评论，分页")
    @ApiImplicitParam(name = "参数： 1.data:评论对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findNoCheck(@RequestBody @Validated(select.class) RequestBean<Comment> requestBean) throws Exception {
        RespPageBean respPageBean = commentService.findNoCheck(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/comRankBypage")  //查询查看评论排行榜音乐
    @ApiOperation("查看评论排行榜，分页")
    @ApiImplicitParam(name = "参数： 1.page:第几页 。 2.pageSize：一页大小 。")
    public ResultVO queryComRankBypage(@RequestBody @Validated(select.class) PageBean pageBean) throws Exception {
        RespPageBean respPageBean = commentService.queryComRankBypage(pageBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }



// 通过对象查询用户
//    @PostMapping("/findConsumerByPage")  //查询用户
//    @ApiOperation("查看活动参加的人，分页。")
//    public ResultVO findConsumerByPage(@RequestBody @Validated(select.class) RequestBean<Comment> requestBean) throws Exception {
//        System.err.println(requestBean);
//
//
//        RespPageBean respPageBean = commentService.selectConsumerByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
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
//    public ResultVO findActivityByPage(@RequestBody @Validated(select.class) RequestBean<Comment> requestBean) throws Exception {
//        // 构建评论，后查询
//        RespPageBean respPageBean = commentService.selectActivityByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }

}