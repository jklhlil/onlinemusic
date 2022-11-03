package com.ecjtu.onlinemusic.wan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @description: 这个类与项目为无关。
 * @author: 何江涛
 * @create: 2022-06-12
 * @version: 1.0
 */
@Controller
public class AA {

    @RequestMapping("/")
    public String test(){
        return "test.html";
    }

    @RequestMapping("/WanGuiFeng")
    public String wan(){
        return "redirect:WanGuiFeng/index.html";
    }


}
