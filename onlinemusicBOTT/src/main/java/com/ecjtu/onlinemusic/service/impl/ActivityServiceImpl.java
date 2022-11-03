package com.ecjtu.onlinemusic.service.impl;


import com.ecjtu.onlinemusic.dao.ActivityDao;
import com.ecjtu.onlinemusic.entity.Activity;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;

    //   通过 活动名 查找活动
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespPageBean findByActivityName(RequestBean<String> requestBean) throws Exception {
        // 构建活动，后查询
        Activity activity = new Activity();
        activity.setName(requestBean.getData());
        RespPageBean respPageBean = selectByPage(activity, requestBean.getPage(), requestBean.getPageSize());

        return respPageBean;
    }

    //   查找 一个 活动，根据Id
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public Activity findByActivityId(@RequestBody Integer activityId) throws Exception {
        // 构建活动，后查询
        Activity activity = new Activity();
        activity.setId(activityId);

        Activity activity1 = selectOne(activity);
        return activity1;
    }

    @Override
    public Integer deleteById(Integer activityId) throws Exception {
        return activityDao.deleteById(activityId);
    }

    @Override
    public Integer insert(Activity activity) throws Exception {
        // 如果没设置积分，设为0
        return activityDao.insert(activity);
    }

    @Override
    public Integer update(Activity activity) throws Exception {

        return activityDao.update(activity);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectByPage(Activity activity, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total = activityDao.selectByPage(activity, null).size();
        //  一页的
        List<Activity> activityList = activityDao.selectByPage(activity, new PageBean(page, pageSize));

        return new RespPageBean(total, activityList);
    }

    //查找一个
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Activity selectOne(Activity activity) throws Exception {
        List<Activity> activityList = activityDao.selectByPage(activity, new PageBean(1, 1));
        return activityList.size() == 0 ? null : activityList.get(0);
    }
}
