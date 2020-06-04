package cn.itsource;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Mail {
   @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void test1() throws Exception {
        //创建一个简单的邮箱信息
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置发件人
        simpleMailMessage.setFrom("528532329@qq.com");
        //设置主题
        simpleMailMessage.setSubject("你好哇");
        //设置正文
        simpleMailMessage.setText("泡面！！！");
        //设置收件人
        simpleMailMessage.setTo("1669524673@qq.com");
        //发送邮件
        javaMailSender.send(simpleMailMessage);
    }


}
