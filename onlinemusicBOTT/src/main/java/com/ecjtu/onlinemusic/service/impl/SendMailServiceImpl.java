package com.ecjtu.onlinemusic.service.impl;

import com.ecjtu.onlinemusic.entity.vo.MailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/*
 * @description: 发送邮件的业务工具
 * @author: 何江涛
 * @create: 2022-06-27
 * @version: 1.0
 */
@Service
public class SendMailServiceImpl {

    //注入邮件工具类javaMailSender
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;
    @Value("${spring.mail.fromName}")
    private String fromName;

    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

    //数据有效性验证，跟@Valid ated  差不多
    public void checkMail(MailRequest mailRequest) {
        Assert.notNull(mailRequest, "邮件请求不能为空");
        Assert.notNull(mailRequest.getSendTo(), "邮件收件人不能为空");
        Assert.notNull(mailRequest.getSubject(), "邮件主题不能为空");
        Assert.notNull(mailRequest.getText(), "邮件收件人不能为空");
    }


    // 发送验证码
    public void SendVerifyCode(MailRequest mailRequest) {
        //标题
        mailRequest.setSubject("第十四组在线音频");
        //内容
        mailRequest.setText("<h2 style=\"text-align: center;\">" +
                "【在线音频APP】             </h2> <div style=\"text-align: center;font-size: 17px;height: 80px;\">\n" +
                "验证码：                   <span style=\"color: #0000ffdb;font-size: 21px;\">" +
                mailRequest.getText() + "</span> ; " +
                "请勿转发，本验证码5分钟有效。如非本人操作，请忽略此信息。    </div>" +

                "<div style=\"text-align: center;\">" +
                "    <img src=\"https://s1.ax1x.com/2022/06/29/jnj93t.png\" width=\"60%\" style=\"border-radius:12px ;\">" +
                "</div>");
        sendHtmlMail(mailRequest);

    }


    public void sendSimpleMail(MailRequest mailRequest) {

        SimpleMailMessage message = new SimpleMailMessage();
        checkMail(mailRequest);
        //邮件发件人
        message.setFrom(fromName + '<' + sendMailer + '>');
        //邮件收件人 1或多个
        message.setTo(mailRequest.getSendTo().split(","));
        //邮件主题
        message.setSubject(mailRequest.getSubject());
        //邮件内容
        message.setText(mailRequest.getText());
        //邮件发送时间
        message.setSentDate(new Date());

        javaMailSender.send(message);

        logger.info("发送邮件成功:{}->{}", sendMailer, mailRequest.getSendTo());
    }


    public void sendHtmlMail(MailRequest mailRequest) {
        MimeMessage message = javaMailSender.createMimeMessage();
        checkMail(mailRequest);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //邮件发件人
            message.setFrom(fromName + '<' + sendMailer + '>');
            //邮件收件人 1或多个
            helper.setTo(mailRequest.getSendTo().split(","));
            //邮件主题
            helper.setSubject(mailRequest.getSubject());
            //邮件内容
            helper.setText(mailRequest.getText(), true);
            //邮件发送时间
            helper.setSentDate(new Date());

            String filePath = mailRequest.getFilePath();
            if (StringUtils.hasText(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
            }
            javaMailSender.send(message);
            logger.info("发送邮件成功:{}->{}", sendMailer, mailRequest.getSendTo());
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }
}

