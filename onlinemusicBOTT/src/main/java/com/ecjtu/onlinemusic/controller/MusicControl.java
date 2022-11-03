package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.impl.MusicServiceImpl;
import com.ecjtu.onlinemusic.service.impl.SubscribeCollectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //@RestController 相当于 @Controller+@ResponseBody
@RequestMapping("/music")
@Api(tags = "音乐相关接口")
public class MusicControl {
    @Autowired
    MusicServiceImpl musicService;
    @Autowired
    SubscribeCollectServiceImpl subColService;

    //根据id删除
    @DeleteMapping("/deleteById/{musicId}")  //根据id删除音乐
    @ApiOperation("删除音乐")
    @ApiImplicitParam(name = "参数： 1.音乐Id 。")
    public ResultVO deleteById(@PathVariable("musicId") Integer musicId) throws Exception {
        //删除并返回结果
        if (musicService.deleteById(musicId) == 1) {
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
        if (musicService.batchDeleteByids(idList) == 1) {
            return new ResultVO(ResultEnum.delete_success);
        } else {
            return new ResultVO(ResultEnum.delete_failed);
        }
    }

    @PostMapping("/upload")  // 添加音乐，进行数据验证
    @ApiOperation("上传音乐，默认没通过审核")
    @ApiImplicitParam(name = "参数： 1.音乐对象 。")
    public ResultVO addMusic(@RequestBody @Validated(insert.class) Music music) throws Exception {
        // 添加并返回结果
        if (musicService.insert(music) == 1) {
            return new ResultVO(ResultEnum.insert_success, music);
        } else {
            return new ResultVO(ResultEnum.insert_failed);
        }
    }

    @PutMapping("/edit")  //更新音乐，进行数据验证
    @ApiOperation("编辑音乐信息")
    @ApiImplicitParam(name = "参数： 1.音乐对象 。")
    public ResultVO updateMusic(@RequestBody @Validated(update.class) Music music) throws Exception {
        //更新并返回结果
        if (musicService.update(music) == 1) {
            return new ResultVO(ResultEnum.update_success, music);
        } else {
            return new ResultVO(ResultEnum.update_failed);
        }
    }


    @PostMapping("/findByPage")  //查询音乐
    @ApiOperation("查找音乐，分页")
    @ApiImplicitParam(name = "参数： 1.data:音乐对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByPage(@RequestBody @Validated(select.class) RequestBean<Music> requestBean) throws Exception {
        RespPageBean respPageBean = musicService.selectByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findById/{musicId}")  //通过音乐名查询音乐
    @ApiOperation("查找 一个 音乐，根据 Id")
    @ApiImplicitParam(name = "参数： 1.data:歌曲Id 。")
    public ResultVO findByMusicId(@PathVariable("musicId") String musicId) throws Exception {
        Integer musicId1=Integer.parseInt(musicId);
        Music music = musicService.findByMusicId(musicId1);
        if (music != null) {
            return new ResultVO(ResultEnum.select_success, music);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findByName")  //通过音乐名查询音乐
    @ApiOperation("搜索音乐，根据 歌名 ，分页")
    @ApiImplicitParam(name = "参数： 1.data:歌名 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByMusicName(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
        RespPageBean respPageBean = musicService.findByMusicName(requestBean);
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findBySinger")  //通过歌手名查询音乐
    @ApiOperation("搜索音乐，根据 歌手名 ，分页")
    @ApiImplicitParam(name = "参数： 1.data:歌手名 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findBySinger(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
        RespPageBean respPageBean = musicService.findBySinger(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }


    @PostMapping("/findByAdmin")  //通过音乐名查询音乐
    @ApiOperation("搜索所有音乐，管理员，分页")
    @ApiImplicitParam(name = "参数： 1.data:字符串 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findByAdmin(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
        RespPageBean respPageBean = musicService.findByAdmin(requestBean);
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }

    @PostMapping("/findNoCheck") //通过歌名查询音乐
    @ApiOperation("搜索没通过检查的音乐，分页")
    @ApiImplicitParam(name = "参数： 1.data:音乐对象 。 2.page:第几页 。 3.pageSize：一页大小 。")
    public ResultVO findNoCheck(@RequestBody @Validated(select.class) RequestBean<Music> requestBean) throws Exception {
        RespPageBean respPageBean = musicService.findNoCheck(requestBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }


    // TODO  这个方法耦合度高，应该移到收藏订阅控制层
    @PostMapping("/findSubColMusicById")  //通过音乐id查询音乐,含收藏订阅信息
    @ApiOperation("查找 一个 音乐，根据Id，含收藏订阅信息")
    @ApiImplicitParam(name = "参数： 1.音乐Id 。 2.用户Id 。")
    public ResultVO findSubColMusicById(@RequestBody @Validated(select.class) MusicConsumer musicConsumer) throws Exception {
        SubscribeCollect subscribeCollect = musicService.findSubColMusicById(musicConsumer);
        return new ResultVO(ResultEnum.select_success, subscribeCollect);
    }


    @PostMapping("/recommendMusic")  //查询查看评论排行榜音乐
    @ApiOperation("查看评论排行榜，分页")
    @ApiImplicitParam(name = "参数： 1.page:第几页 。 2.pageSize：一页大小 。")
    public ResultVO queryRecommend(@RequestBody @Validated(select.class) PageBean pageBean) throws Exception {
        RespPageBean respPageBean = musicService.queryRecommend(pageBean);
        //返回数据
        if (respPageBean.getTotal() > 0) {
            return new ResultVO(ResultEnum.select_success, respPageBean);
        } else {
            return new ResultVO(ResultEnum.select_failed);
        }
    }



//    @PostMapping("/findById")  //通过音乐id查询音乐
//    @ApiOperation("查找 一个 音乐，根据Id，不包含收藏订阅信息")
//    @ApiImplicitParam(name = "参数： 1.音乐Id 。")
//    public ResultVO findByMusicId(@RequestBody Integer musicId) throws Exception {
//
//        // 构建音乐，后查询
//        Music music = new Music();
//        music.setId(musicId);
//        RespPageBean respPageBean = musicService.selectByPage(music, 1, 1);
//
//        //返回数据
//        if (respPageBean.getDataList().size()!=0) {
//            return new ResultVO(ResultEnum.select_success,respPageBean.getDataList().get(0));
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }

//    @PostMapping("/findMusicById")  //查询音乐
//    @ApiOperation("根据用户id查看参加的音乐，分页。")
//    public ResultVO findMusicByConsumerId(@RequestBody @Validated(select.class) RequestBean<Integer> requestBean) throws Exception {
//
//        // 构建音乐，后查询
//        Music music = new Music();
//        music.setConsumerId(requestBean.getData());
//        RespPageBean respPageBean = musicService.selectMusicByPage(music, requestBean.getPage(), requestBean.getPageSize());
//        //返回数据
//        if (respPageBean.getTotal() > 0) {
//            return new ResultVO(ResultEnum.select_success, respPageBean);
//        } else {
//            return new ResultVO(ResultEnum.select_failed);
//        }
//    }


}