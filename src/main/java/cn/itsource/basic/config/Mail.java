package cn.itsource.basic.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;




@Configuration
public class Mail {
   @Autowired
    private JavaMailSender mailSender;
    public void sigh() throws Exception {
        //创建复杂邮件对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        helper.setFrom("528532329@qq.com");
        helper.setSubject("有新伙伴加入");
        helper.setText(" <h1>有新伙伴加入啦！！！</h1>\n" +
                "        <span>快去激活他的账户吧</span>\n" +
                "        <a href='http://192.168.124.15:8081/#/activation'>点我去激活</a>",true);
        helper.setTo("528532329@qq.com");
        mailSender.send(mimeMessage);
    }
    public void Activation(String email) throws Exception {
        //创建复杂邮件对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        helper.setFrom("528532329@qq.com");
        helper.setSubject("审核情况");
        helper.setText(" <h1>恭喜你审核通过</h1>\n" +
                "        <span>快去账户看看吧</span>\n" +
                "        <a href='http://192.168.124.15:8081/#/login'>点我去登录</a>",true);
        helper.setTo(email);
        mailSender.send(mimeMessage);
    }
    //email注册
    public void registeremail(String email,String code) throws Exception {
        //创建复杂邮件对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        helper.setFrom("528532329@qq.com");
        helper.setSubject("注册通知");
        helper.setText(" <h1>欢迎注册宠物之家</h1>\n" +
                "        <span>你的注册码是</span>\n"+ code,true);
        helper.setTo(email);
        mailSender.send(mimeMessage);
    }


}
