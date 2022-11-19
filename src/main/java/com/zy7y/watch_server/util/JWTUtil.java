package com.zy7y.watch_server.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Calendar;
import java.util.Map;

public class JWTUtil {

    private static Integer EFFECTIVE_TIME = 3 * 24 * 60;

    private static String SECRET = "secret_github.com/zy7y_springboot@#$%^&*!()";

    /**
     * @Param: 传入需要设置的payload信息
     * @return: 返回token
     */
    public static String generateToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        // 将map内的信息传入JWT的payload中
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        // 设置JWT令牌的过期时间为60
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, EFFECTIVE_TIME);
        builder.withExpiresAt(instance.getTime());

        // 设置签名并返回token
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * @Param: 传入token
     * @return:
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * @Param: 传入token
     * @return: 解密的token信息
     */
    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

}

