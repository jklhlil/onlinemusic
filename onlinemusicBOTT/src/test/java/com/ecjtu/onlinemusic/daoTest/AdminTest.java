package com.ecjtu.onlinemusic.daoTest;

import com.ecjtu.onlinemusic.dao.AdminDao;
import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AdminTest {

    @Autowired
    AdminDao adminDao;

    @Test
    void deleteByAdminId() {
        System.out.println(adminDao.deleteById(5));
        //  输出红色显眼
        System.err.println("1. delete成功");
    }

    @Test
    void insertByAdmin() {
        Admin admin = new Admin();
        admin.setMail("11");
        admin.setPassword("11");

        System.out.println(adminDao.insert(admin));
        System.err.println("2. insert成功");

    }

    @Test
    void updateByAdmin() {
        Admin admin = new Admin();
        admin.setName("fdasss");

        System.out.println(adminDao.update(admin));
        System.err.println("3. update成功");

    }

//    分页查询
    @Test
    void selectAdminByPage() {
        Admin admin = new Admin();

        System.out.println(adminDao.selectByPage(admin, null));
        System.err.println("4.1 select实体 成功");

        System.out.println(adminDao.selectByPage(null, new PageBean(3, 5)));
        System.err.println("4.2 select分页 成功");

        List<Admin> admins = adminDao.selectByPage(admin, new PageBean(1, 5));
//  for( Admin admin1:admins ){
//      System.out.println(admin1);
//
//  }
        System.out.println(admins);
        System.err.println("4.3 select实体+分页 成功");
    }


}
