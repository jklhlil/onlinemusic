//package com.ecjtu.onlinemusic.handler;
//
//import com.ecjtu.onlinemusic.entity.Consumer;
//import com.ecjtu.onlinemusic.entity.vo.ResultVO;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    /**
//     * 登录成功后处理
//     * @param request
//     * @param response
//     * @param authentication
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        //从身份证中获取用户信息
//        Consumer consumer = (Consumer) authentication.getPrincipal();
//        //把用户信息存入 安全上下文中
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        System.err.println(consumer);
//        System.err.println(authentication);
//
//        //返回当前登录用户的信息
//        ResultVO resultVO = new ResultVO(consumer);
//        resultVO.out(response);
//    }
//
//}
