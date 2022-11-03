package com.ecjtu.onlinemusic.service.impl;


import com.ecjtu.onlinemusic.dao.ConsumerDao;
import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.ConsumerService;
import com.ecjtu.onlinemusic.util.Md5Change;
import com.ecjtu.onlinemusic.util.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


//   @Transactional(propagation=Propagation.NOT_SUPPORTED)
//   @Transactional(readOnly = true)

@Service
@Transactional
//@Transactional  //加事务，加事务会增加操作时间。尽量不加。
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    ConsumerDao consumerDao;

    // 根据 用户名 查询用户
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findByConsumerName(RequestBean<String> requestBean) throws Exception {
        // 构建用户，后查询
        Consumer consumer = new Consumer();
        consumer.setName(requestBean.getData());
        RespPageBean respPageBean = selectByPage(consumer, requestBean.getPage(), requestBean.getPageSize());

        return respPageBean;
    }

    // 查看 一个 用户，根据Id
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Consumer findByConsumerId(Integer consumerId) throws Exception {
        // 构建用户，后查询
        Consumer consumer = new Consumer();
        consumer.setId(consumerId);
        Consumer consumer1 = selectOne(consumer);
        return consumer1;
    }

    //   注册
    public Integer register(Consumer consumer) throws Exception {
        //  查询是否存在
        Consumer consumer1 = new Consumer();
        consumer1.setMail(consumer.getMail());
        List<Consumer> consumerList = consumerDao.selectByPage(consumer1, null);
        if (consumerList.size() != 0) {
            //  邮箱已存在
            return 2;
        } else {
            // 默认头像,昵称,积分
            if (consumer.getPictureUrl() == null) {
                consumer.setPictureUrl("https://s1.ax1x.com/2022/06/22/jpCUUO.jpg");
            }
            if (consumer.getName() == null) {
                consumer.setName("第十四小组用户");
            }
            if (consumer.getScore() == null) {
                consumer.setScore(30);
            }

            if (consumer.getPassword() != null  && consumer.getPassword()!="") {
                //密码加密
                String password = consumer.getPassword();
                String encode = Md5Change.encryption(password);//加密返回密文密码
                consumer.setPassword(encode);
            }

            return consumerDao.insert(consumer);
        }

    }

    // 批量删除用户
    public Integer batchDeleteByids(List<Integer> idList) throws Exception {
        for (Integer id : idList) {
            // 有一个删除失败，就失败
            if (consumerDao.deleteById(id) == 0) {
                return 0;
            }
        }
        return 1;
    }

    //    更新用户照片
    public HashMap updatePicture(Integer userId, MultipartFile file, HttpServletRequest servletRequest) throws Exception {

        // 删除旧照片
//        String oldPictureUrl = consumerDao.selectById(userId).getPictureUrl();
//        String fileName=oldPictureUrl.substring( oldPictureUrl.indexOf("/files/")+7 );
//        Upload.deleteFile(fileName);

        HashMap<String, String> map = new HashMap();

        // 1. 将照片保存到服务器
        String path = Upload.upload(file);

        //2.获取 协议，主机名，端口
        String origin = servletRequest.getScheme() + "://" + servletRequest.getServerName() + ":" + servletRequest.getLocalPort();
        String pictureUrl = origin + path;
        System.err.println(pictureUrl);

        //3.将生成的照片url保存到数据库
        Consumer consumer = new Consumer();
        consumer.setId(userId);
        consumer.setPictureUrl(pictureUrl);

        // 下面的map不是很重要
        if (consumerDao.update(consumer) == 1) {
            map.put("stat", "1");
            map.put("pictureUrl", pictureUrl);
            return map;
        } else {
            map.put("stat", "0");
            return map;
        }
    }

    @Override
    public Integer deleteById(Integer consumerId) throws Exception {
        return consumerDao.deleteById(consumerId);
    }

    @Override
    public Integer insert(Consumer consumer) throws Exception {

        if (consumer.getPassword() != null  && consumer.getPassword()!="") {
            //密码加密
            String password = consumer.getPassword();
            String encode = Md5Change.encryption(password);//加密返回密文密码
            consumer.setPassword(encode);
        }

        return consumerDao.insert(consumer);
    }

    @Override
    public Integer update(Consumer consumer) throws Exception {
        System.err.println(consumer+"update");


        if (consumer.getPassword() != null  && consumer.getPassword()!="") {
            //密码加密
            String password = consumer.getPassword();
            String encode = Md5Change.encryption(password);//加密返回密文密码
            consumer.setPassword(encode);
        }

        return consumerDao.update(consumer);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectByPage(Consumer consumer, Integer page, Integer pageSize) throws Exception {

        if (consumer.getPassword() != null  && consumer.getPassword()!="") {
            //密码加密
            String password = consumer.getPassword();
            String encode = Md5Change.encryption(password);//加密返回密文密码
            consumer.setPassword(encode);
        }

        //  总数
        int total = consumerDao.selectByPage(consumer, null).size();
        //  一页的
        List<Consumer> consumerList = consumerDao.selectByPage(consumer, new PageBean(page, pageSize));

        return new RespPageBean(total, consumerList);
    }

    //查找一个
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Consumer selectOne(Consumer consumer) throws Exception {
        if (consumer.getPassword() != null && consumer.getPassword()!="") {
            //密码加密
            String password = consumer.getPassword();
            String encode = Md5Change.encryption(password);//加密返回密文密码
            consumer.setPassword(encode);
        }
        List<Consumer> consumerList = consumerDao.selectByPage(consumer, new PageBean(1, 1));
        return consumerList.size() == 0 ? null : consumerList.get(0);
    }

//    //  实现  UserDetailsService  无
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Consumer userDetails = null;
//        System.err.println(username+"loadUserByUsername");
//        try {
//            Consumer consumer = new Consumer();
//            consumer.setMail(username);
//            userDetails = selectOne(consumer);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new UsernameNotFoundException(ResultEnum.ERROR.getMsg());
//        }
//        if (userDetails == null) {
//            throw new UsernameNotFoundException(ResultEnum.USER_NOT_EXIST.getMsg());
//        }
//        return userDetails;
//    }

}
