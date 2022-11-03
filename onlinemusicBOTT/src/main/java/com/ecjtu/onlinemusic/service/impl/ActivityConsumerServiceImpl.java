package com.ecjtu.onlinemusic.service.impl;


import com.ecjtu.onlinemusic.dao.ActivityConsumerDao;
import com.ecjtu.onlinemusic.entity.ActivityConsumer;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.ActivityConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Transactional
public class ActivityConsumerServiceImpl implements ActivityConsumerService {

    @Autowired
    ActivityConsumerDao actiConsDao;

    //    查看活动参加的人
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespPageBean findConsumerByActivityId(RequestBean<Integer> requestBean) throws Exception {
        // 构建用户活动，后查询
        ActivityConsumer activityConsumer = new ActivityConsumer();
        activityConsumer.setActivityId(requestBean.getData());
        RespPageBean respPageBean = selectConsumerByPage(activityConsumer, requestBean.getPage(), requestBean.getPageSize());

        return respPageBean;
    }

    //    查看用户参加的活动
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespPageBean findActivityByConsumerId(RequestBean<Integer> requestBean) throws Exception {
        // 构建用户活动，后查询
        ActivityConsumer activityConsumer = new ActivityConsumer();
        activityConsumer.setConsumerId(requestBean.getData());
        RespPageBean respPageBean = selectActivityByPage(activityConsumer, requestBean.getPage(), requestBean.getPageSize());

        return respPageBean;
    }




    @Override
    public Integer deleteById(Integer activityConsumerId) throws Exception {
        int a = actiConsDao.deleteById(activityConsumerId);
        return a;
    }

    @Override
    public Integer insert(ActivityConsumer activityConsumer) throws Exception {
        if(actiConsDao.selectByPage(activityConsumer,null).size()>0){
            return 0;
        }else {
            return actiConsDao.insert(activityConsumer);
        }
    }

    @Override
    public Integer update(ActivityConsumer activityConsumer) throws Exception {
        return actiConsDao.update(activityConsumer);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectByPage(ActivityConsumer activityConsumer, Integer page, Integer pageSize) throws Exception {
        //  查总数
        int total = actiConsDao.selectByPage(activityConsumer, null).size();
        //  查一页列表
        List<ActivityConsumer> activityConsumerList = actiConsDao.selectByPage(activityConsumer, new PageBean(page, pageSize));

        for (ActivityConsumer activityConsumer1 : activityConsumerList) {
            activityConsumer1.setActivity(null);
            activityConsumer1.setConsumer(null);
        }
        return new RespPageBean(total, activityConsumerList);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectConsumerByPage(ActivityConsumer activityConsumer, Integer page, Integer pageSize) throws Exception {
        //  查总数
        int total = actiConsDao.selectConsumerByPage(activityConsumer, null).size();
        //  查一页列表
        List<ActivityConsumer> activityConsumerList = actiConsDao.selectConsumerByPage(activityConsumer, new PageBean(page, pageSize));
        for (ActivityConsumer activityConsumer1 : activityConsumerList) {
            activityConsumer1.setActivity(null);
        }
        return new RespPageBean(total, activityConsumerList);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectActivityByPage(ActivityConsumer activityConsumer, Integer page, Integer pageSize) throws Exception {
        //  查总数
        int total = actiConsDao.selectActivityByPage(activityConsumer, null).size();
        //  查一页列表
        List<ActivityConsumer> activityConsumerList = actiConsDao.selectActivityByPage(activityConsumer, new PageBean(page, pageSize));
        for (ActivityConsumer activityConsumer1 : activityConsumerList) {
            activityConsumer1.setConsumer(null);
        }
        return new RespPageBean(total, activityConsumerList);
    }


}