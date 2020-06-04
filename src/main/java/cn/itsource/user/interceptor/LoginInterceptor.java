package cn.itsource.user.interceptor;


import cn.itsource.basic.util.CollectUtils;
import cn.itsource.basic.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    //前置拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //配置放行资源
        String uri = request.getRequestURI();
        if (CollectUtils.contain(uri,Arrays.asList("/user/login","/logout","/dfs","/user/phoneRegister","/verifycode/sendSmsCode","/user/register/phone")))
            return true;
        //判断请求头里面是否携带U-TOKEN A-TOKEN,如果没有携带返回登录
        String uToken = request.getHeader("U-TOKEN");
        if(StringUtils.isBlank(uToken)){
            writeNoUserError(response);
            return false;
        }
        //判断redis中是否可以获取U-TOKEN A-TOKEN，如果获取不到代表没有用户登录
        String user = RedisUtils.INSTANCE.get(uToken);
        if( StringUtils.isBlank(user)){
            writeNoUserError(response);
            return false;
        }
        //刷新session过期时间-redis里面的一个值
        RedisUtils.INSTANCE.set(uToken,user,30*60);
        return true;
    }

    private void writeNoUserError(HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("utf-8"); //返回编码格式
            response.setContentType("application/json; charset=utf-8"); // json方式放回
            writer = response.getWriter();
            writer.write("{\"success\":false,\"message\":\"noUser\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
