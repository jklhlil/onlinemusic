package com.ecjtu.onlinemusic.service.impl;


import com.ecjtu.onlinemusic.dao.MusicDao;
import com.ecjtu.onlinemusic.dao.SubscribeCollectDao;
import com.ecjtu.onlinemusic.entity.Comment;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.MusicService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MusicServiceImpl implements MusicService {

    @Autowired
    MusicDao musicDao;
    @Autowired
    SubscribeCollectDao subColDao;
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    SubscribeCollectServiceImpl subscribeCollectService;

    //搜索音乐，根据 歌曲Id
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Music findByMusicId(Integer musicId) throws Exception {
        // 构建音乐，后查询
        return musicDao.selectById(musicId);
    }

    //搜索音乐，根据 歌名
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findByMusicName(RequestBean<String> requestBean) throws Exception {
        // 构建音乐，后查询
        Music music = new Music();
        music.setName(requestBean.getData());
        music.setIsCheck(1);
        RespPageBean respPageBean = selectByPage(music, requestBean.getPage(), requestBean.getPageSize());
        return respPageBean;
    }

    //    搜索音乐，根据 歌手名
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findBySinger(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
        Music music = new Music();
        music.setSinger(requestBean.getData());
        music.setIsCheck(1);
        RespPageBean respPageBean = selectByPage(music, requestBean.getPage(), requestBean.getPageSize());
        return respPageBean;
    }

    //    搜索音乐，根据 歌名   管理员
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findByAdmin(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
        Music music = new Music();
        music.setName(requestBean.getData());
        RespPageBean respPageBean = selectByPage(music, requestBean.getPage(), requestBean.getPageSize());
        return respPageBean;
    }

//    //    搜索所有音乐，管理员
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    public RespPageBean findByString(@RequestBody @Validated(select.class) RequestBean<String> requestBean) throws Exception {
//        Music music = new Music();
//        music.setName(requestBean.getData());
//        RespPageBean respPageBean = selectByPage(music, requestBean.getPage(), requestBean.getPageSize());
//        return respPageBean;
//    }

    //    搜索没通过检查的音乐
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findNoCheck(@RequestBody @Validated(select.class) RequestBean<Music> requestBean) throws Exception {
        // 将isCheck设为0
        if (requestBean.getData() == null) {
            requestBean.setData(new Music());
        }
        requestBean.getData().setIsCheck(0);
        RespPageBean respPageBean = selectByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
        return respPageBean;
    }

    //  查找 一个 音乐，根据Id，含收藏订阅信息
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SubscribeCollect findSubColMusicById(@RequestBody MusicConsumer musicConsumer) throws Exception {

        //查看收藏订阅行是否存在
        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setMusicId(musicConsumer.getMusicId());
        subscribeCollect.setConsumerId(musicConsumer.getConsumerId());
        List<SubscribeCollect> subscribeCollectList = subColDao.selectMusicByPage(subscribeCollect, new PageBean(1, 1));

        if (subscribeCollectList.size() > 0) {
            subscribeCollectList.get(0).setConsumer(null);
            return subscribeCollectList.get(0);
        } else {
            // 不存在，添加收藏订阅，再查询返回音乐
            subColDao.insert(subscribeCollect);

            Music music = new Music();
            music.setId(musicConsumer.getMusicId());
            subscribeCollect.setMusic(selectOne(music));
            subscribeCollect.setConsumer(null);

            return subscribeCollect;
        }
    }

    // 批量删除音乐
    public Integer batchDeleteByids(List<Integer> idList) throws Exception {
        for (Integer id : idList) {
            // 有一个删除失败，就失败
            if (musicDao.deleteById(id) == 0) {
                return 0;
            }
        }
        return 1;
    }

    //    查看推荐
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean queryRecommend(PageBean pageBean) throws Exception {

        RespPageBean respPageBean = new RespPageBean();
        MusicConsumer musicConsumer = new MusicConsumer();
        //根据收藏订阅热评进行推荐
        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setMusicId(musicConsumer.getMusicId());
        // 收藏订阅热评各选几个
        Integer pagesize = pageBean.getPageSize() / 3;
        Integer remainder = pageBean.getPageSize() % 3;
        subscribeCollect.setConsumerId(musicConsumer.getConsumerId());
        subscribeCollect.setIsCollect(1);
        RespPageBean respCol = subscribeCollectService.queryColRankBypage(new PageBean(pageBean.getPage(), pagesize));
        RespPageBean respSub = subscribeCollectService.querySubRankBypage(new PageBean(pageBean.getPage(), pagesize));
        respPageBean.setTotal(respSub.getTotal()*2);
        subscribeCollect.setIsSubscribe(0);
        subscribeCollect.setIsSubscribe(1);
        RespPageBean respCom = commentService.queryComRankBypage(new PageBean(pageBean.getPage(), pagesize + remainder));
        List<Music> musicList = new ArrayList<Music>();
        // 将得到的音乐列表拼接
        musicList.addAll(respCol.getDataList());
        musicList.addAll(respSub.getDataList());
        musicList.addAll(respCom.getDataList());
        respPageBean.setDataList(musicList);
        return respPageBean;
    }

    @Override
    public Integer deleteById(Integer musicId) throws Exception {
        return musicDao.deleteById(musicId);
    }

    @Override
    public Integer insert(Music music) throws Exception {
        // 无论谁新增music，都要审核
        music.setIsCheck(0);
//        System.err.println(music);
        return musicDao.insert(music);
    }

    @Override
    public Integer update(Music music) {
        return musicDao.update(music);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectByPage(Music music, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total = musicDao.selectByPage(music, null).size();
        //  一页的
//        System.err.println(page);
//        System.err.println(pageSize);
        List<Music> musicList = musicDao.selectByPage(music, new PageBean(page, pageSize));

        return new RespPageBean(total, musicList);
    }

    //查找一个
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Music selectOne(Music music) throws Exception {
        List<Music> musicList = musicDao.selectByPage(music, new PageBean(1, 1));
        return musicList.size() == 0 ? null : musicList.get(0);
    }
}
