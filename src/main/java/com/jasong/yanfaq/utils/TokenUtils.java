package com.jasong.yanfaq.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.jasong.yanfaq.entity.User;
import com.jasong.yanfaq.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static IUserService staticiUserService;
    @Resource
    private IUserService iUserService;

    @PostConstruct
    public void setiUserService() {
        staticiUserService = iUserService;
    }

    public static String genToken(String userId, String password) {
        return JWT.create().withAudience(userId)//userId保存到token里面作为载荷
                .withExpiresAt(DateUtil.offsetDay(new Date(), 7)) //七天后过期
                .sign(Algorithm.HMAC256(password)); //password作为token密钥
    }

    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        if (StrUtil.isNotBlank(token)) {
            try {
                String aud = JWT.decode(token).getAudience().get(0);
                return staticiUserService.getById(Integer.valueOf(aud));
            } catch (JWTDecodeException e) {
                return null;
            }
        }
        return null;
    }
}

