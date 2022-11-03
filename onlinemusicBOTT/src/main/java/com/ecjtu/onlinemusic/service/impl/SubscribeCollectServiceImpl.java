package com.ecjtu.onlinemusic.service.impl;


import com.ecjtu.onlinemusic.dao.ConsumerDao;
import com.ecjtu.onlinemusic.dao.MusicDao;
import com.ecjtu.onlinemusic.dao.SubscribeCollectDao;
import com.ecjtu.onlinemusic.entity.*;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.SubscribeCollectService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

//   @Transactional(propagation=Propagation.NOT_SUPPORTED)
//   @Transactional(readOnly = true)
@Transactional
@Service
public class SubscribeCollectServiceImpl implements SubscribeCollectService {

    @Autowired
    SubscribeCollectDao subColDao;
    @Autowired
    ConsumerDao consumerDao;
    @Autowired
    MusicDao musicDao;

    // 更新：插入或修改
    public Integer modifySubCol(SubscribeCollect subscribeCollect) throws Exception {
        //查看收藏订阅行是否存在
        SubscribeCollect subscribeCollect2 = new SubscribeCollect();
        subscribeCollect2.setMusicId(subscribeCollect.getMusicId());
        subscribeCollect2.setConsumerId(subscribeCollect.getConsumerId());
        List<SubscribeCollect> subscribeCollectList = subColDao.selectMusicByPage(subscribeCollect2, new PageBean(1, 1));

        //订阅
        if (subscribeCollect.getIsSubscribe() != null) {
            //查看积分是否足够
            Consumer consumer = consumerDao.selectById(subscribeCollect.getConsumerId());
            Music music = musicDao.selectById(subscribeCollect.getMusicId());
            if (consumer == null || music == null || consumer.getScore() < music.getScore()) {
                //没查到用户，音乐。或者积分不够
                return 0;
            } else {
                //更新积分
                consumer.setScore(consumer.getScore() - music.getScore());
                consumerDao.update(consumer);
                if (subscribeCollectList.size() > 0) {
                    //  更新订阅情况
                    subscribeCollect.setId(subscribeCollectList.get(0).getId());
                    return subColDao.update(subscribeCollect);
                } else {
                    //  不存在，新增订阅行
                    return subColDao.insert(subscribeCollect);
                }
            }
        }

        // 收藏
        if (subscribeCollectList.size() > 0) {
            //  更新收藏情况
            subscribeCollect.setId(subscribeCollectList.get(0).getId());
            return subColDao.update(subscribeCollect);
        } else {
            //  不存在，新增收藏行
            return subColDao.insert(subscribeCollect);
        }

    }

    //    查看收藏的音乐
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findColMusicByConsumerId(RequestBean<Integer> requestBean) throws Exception {
        // 构建对象
        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setConsumerId(requestBean.getData());
        subscribeCollect.setIsCollect(1);
        //查询
        RespPageBean<SubscribeCollect> respPageBean1 = selectMusicByPage(subscribeCollect, requestBean.getPage(), requestBean.getPageSize());
        //  净化数据
        List<Music> list = new ArrayList();
        for (SubscribeCollect subscribeCollect1 : respPageBean1.getDataList()) {
            list.add(subscribeCollect1.getMusic());
        }
        return new RespPageBean(respPageBean1.getTotal(), list);
    }

    //    查看订阅的音乐
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findSubMusicConsumerId(RequestBean<Integer> requestBean) throws Exception {
        // 构建收藏订阅，后查询
        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setConsumerId(requestBean.getData());
        subscribeCollect.setIsSubscribe(1);
        //查询
        RespPageBean<SubscribeCollect> respPageBean1 = selectMusicByPage(subscribeCollect, requestBean.getPage(), requestBean.getPageSize());
        //  净化数据
        List<Music> list = new ArrayList();
        for (SubscribeCollect subscribeCollect1 : respPageBean1.getDataList()) {
            list.add(subscribeCollect1.getMusic());
        }
        return new RespPageBean(respPageBean1.getTotal(), list);
    }

    //    查看收藏排行榜
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean queryColRankBypage(PageBean pageBean) throws Exception {
        // 总数 和 数据
        Integer total = subColDao.queryColRankBypage(null).size();
        List<Music> musicList = subColDao.queryColRankBypage(pageBean);

        return new RespPageBean( total,musicList);
    }
    //    查看订阅排行榜
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean querySubRankBypage(PageBean pageBean) throws Exception {
        // 总数 和 数据
        Integer total = subColDao.querySubRankBypage(null).size();
        List<Music> musicList = subColDao.querySubRankBypage(pageBean);

        return new RespPageBean( total,musicList);
    }

    @Override
    public Integer deleteById(Integer subscribeCollectId) throws Exception {
        return subColDao.deleteById(subscribeCollectId);
    }

    @Override
    public Integer insert(SubscribeCollect subscribeCollect) throws Exception {
        subscribeCollect.setIsSubscribe(0);
        subscribeCollect.setIsCollect(0);
        return subColDao.insert(subscribeCollect);
    }

    @Override
    public Integer update(SubscribeCollect subscribeCollect) {
        return subColDao.update(subscribeCollect);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectByPage(SubscribeCollect subscribeCollect, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total = subColDao.selectByPage(subscribeCollect, null).size();
        //  一页的
        List<SubscribeCollect> subColList = subColDao.selectByPage(subscribeCollect, new PageBean(page, pageSize));

        for (SubscribeCollect subscribeCollect1 : subColList) {
            subscribeCollect1.setConsumer(null);
            subscribeCollect1.setMusic(null);
        }
        return new RespPageBean(total, subColList);
    }

    //查找一个
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SubscribeCollect selectOne(SubscribeCollect subscribeCollect) throws Exception {
        List<SubscribeCollect> subscribeCollectList = subColDao.selectByPage(subscribeCollect, new PageBean(1, 1));
        return subscribeCollectList.size() == 0 ? null : subscribeCollectList.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectMusicByPage(SubscribeCollect subscribeCollect, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total = subColDao.selectMusicByPage(subscribeCollect, null).size();
        //  一页的
        List<SubscribeCollect> subColList = subColDao.selectMusicByPage(subscribeCollect, new PageBean(page, pageSize));

        //  得到订阅和音乐列表
        for (SubscribeCollect subscribeCollect1 : subColList) {
            subscribeCollect1.setConsumer(null);
        }
        return new RespPageBean(total, subColList);
    }

    //查找一个音乐
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SubscribeCollect selectOneMusic(SubscribeCollect subscribeCollect) throws Exception {
        List<SubscribeCollect> subscribeCollectList = subColDao.selectMusicByPage(subscribeCollect, new PageBean(1, 1));
        return subscribeCollectList.size() == 0 ? null : subscribeCollectList.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectConsumerByPage(SubscribeCollect subscribeCollect, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total = subColDao.selectConsumerByPage(subscribeCollect, null).size();
        //  一页的
        List<SubscribeCollect> subColList = subColDao.selectConsumerByPage(subscribeCollect, new PageBean(page, pageSize));

        //  得到订阅和用户列表
        for (SubscribeCollect subscribeCollect1 : subColList) {
            subscribeCollect1.setMusic(null);
        }

        return new RespPageBean(total, subColList);
    }

    //查找一个用户
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SubscribeCollect selectOneConsumer(SubscribeCollect subscribeCollect) throws Exception {
        List<SubscribeCollect> subscribeCollectList = subColDao.selectConsumerByPage(subscribeCollect, new PageBean(1, 1));
        return subscribeCollectList.size() == 0 ? null : subscribeCollectList.get(0);
    }

}
