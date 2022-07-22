package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ticket {
    private static final String LICENSE = "license";
    private String token = "";

    public Ticket(Car car) {
        token = JWT.create()
                .setPayload(LICENSE, "1234567890")
                .sign();
    }
}
