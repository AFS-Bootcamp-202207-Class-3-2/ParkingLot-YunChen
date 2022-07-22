package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    private static final String LICENSE = "license";
    private String token = "";

    public Ticket(String token) {
        this.token = token;
    }
    private Car car;
    public Ticket(Car car) {
        token = JWT.create()
                .setPayload(LICENSE, car.getLicensePlate())
                .sign();
        this.car = car;
    }

}
