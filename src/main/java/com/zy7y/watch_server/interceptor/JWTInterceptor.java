package com.zy7y.watch_server.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy7y.watch_server.dao.UserDao;
import com.zy7y.watch_server.pojo.User;
import com.zy7y.watch_server.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURI());
        Map<String, String> map = new HashMap<>();

        //获取请求头中的token令牌
        String token = request.getHeader("Authorization");
        try {
            DecodedJWT claims = JWTUtil.getTokenInfo(token);
            User user = new User();
            user.setUsername(claims.getClaim("username").asString());
            user.setId(Integer.valueOf(claims.getClaim("id").asString()));
            User obj = userDao.getUserByFilter(user);
            if (obj != null){
                return true;
            }
            map.put("message", "无效签名-用户不存在!");
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("message", "无效签名!");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("message", "token过期!");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("message", "token算法不一致!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "token无效!!");
        }
        map.put("code", "403");//设置状态
        map.put("data", null);
        //将 map 转为json  jackson
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
