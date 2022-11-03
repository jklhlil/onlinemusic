package com.ecjtu.onlinemusic.controller;

import com.ecjtu.onlinemusic.entity.vo.MailRequest;
import com.ecjtu.onlinemusic.service.impl.SendMailServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @description: 发送邮件control
 * @author: 何江涛
 * @create: 2022-06-29
 * @version: 1.0
 */

@RestController
@RequestMapping("/sendMail")
@Api(value = "发送邮件接口", tags = {"发送邮件接口"})
public class SendMailController {
    @Autowired
    private SendMailServiceImpl sendMailService;

    @PostMapping("/verifyCode")
    @ApiOperation("发送验证码")
    public void SendVerifyCode(@RequestBody MailRequest mailRequest) {
        sendMailService.SendVerifyCode(mailRequest);
    }

    @PostMapping("/simple")
    @ApiOperation("发送普通邮件")
    public void SendSimpleMessage(@RequestBody MailRequest mailRequest) {
        sendMailService.sendSimpleMail(mailRequest);
    }

    @PostMapping("/html")
    @ApiOperation("发送 html格式，可带附件 邮箱")
    public void SendHtmlMessage(@RequestBody MailRequest mailRequest) {
        sendMailService.sendHtmlMail(mailRequest);
    }
}

