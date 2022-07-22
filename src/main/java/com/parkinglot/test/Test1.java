package com.parkinglot.test;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test1 {

    public static void main(String[] args) {
        Snowflake snowflake1 = IdUtil.getSnowflake();
        Snowflake snowflake2 = IdUtil.getSnowflake();
        System.out.println(snowflake1 == snowflake2);

        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", Integer.parseInt("123"));
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

//        System.out.println(JWTUtil.createToken(map, "1234".getBytes()));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEyMywiZXhwaXJlX3RpbWUiOjE2NTk3NjQ5Mzc2MTd9.OHvp6egR5H5XsNeDuM4UXXRRVirCiqH1R1bNI67Ns0A";

        final JWT jwt = JWTUtil.parseToken(token);

        jwt.getHeader(JWTHeader.TYPE);
        System.out.println(new Date((Long) jwt.getPayload("expire_time")));
    }

}
