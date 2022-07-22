package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    private static final String LICENSE = "license";
    private boolean isValid = false;
    private String token = "";

    public Ticket(String token) {
        this.token = token;
    }
    private Car car;
    public Ticket(Car car) {
        token = JWT.create()
                .setPayload(LICENSE, car.getLicensePlate())
                .setSigner(JWTSignerUtil.none())
                .sign();
        this.car = car;
        isValid = true;
    }


    public boolean isUsed() {
        return !isValid;
    }

}
