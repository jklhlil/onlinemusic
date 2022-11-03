//package com.ecjtu.onlinemusic.handler;
//
//import com.ecjtu.onlinemusic.entity.vo.ResultEnum;
//import com.ecjtu.onlinemusic.entity.vo.ResultVO;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
//    /**
//     * 退出登录处理方法
//     * @param request
//     * @param response
//     * @param authentication
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        //清除登录信息
//        SecurityContextHolder.clearContext();
//        ResultVO resultVO = new ResultVO(ResultEnum.USER_NOT_LOGIN);
//        resultVO.out(response);
//    }
//}
