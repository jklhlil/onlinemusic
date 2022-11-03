package com.ecjtu.onlinemusic.service.impl;


import com.ecjtu.onlinemusic.dao.AdminDao;
import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;
import com.ecjtu.onlinemusic.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    //    根据管理员id删除管理员
    @Override
    public Integer deleteById(Integer adminId) throws Exception {
        return adminDao.deleteById(adminId);
    }

    //    添加管理员
    @Override
    public Integer insert(Admin admin) throws Exception {
        return adminDao.insert(admin);
    }

    //    修改管理员的信息
    @Override
    public Integer update(Admin admin) throws Exception {
        return adminDao.update(admin);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public RespPageBean selectByPage(Admin admin, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total=adminDao.selectByPage(admin,null).size();
        //  一页的
        List<Admin> adminList=adminDao.selectByPage(admin,new PageBean(page,pageSize));

        return new RespPageBean(total,adminList);
    }

    //查找一个
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public Admin selectOne(Admin admin) throws Exception {
        List<Admin> adminList=adminDao.selectByPage(admin,new PageBean(1,1));
        return adminList.size()==0?null:adminList.get(0);
    }


}
