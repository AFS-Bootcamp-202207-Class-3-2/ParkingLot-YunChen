package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    private static final String LICENSE = "license";
    private boolean isUsed = false;
    private String token = "";
    private ParkingLot parkingLot;

    public Ticket(String token) {
        this.token = token;
    }
    public Ticket(Car car,ParkingLot parkingLot) {
        token = JWT.create()
                .setPayload(LICENSE, car.getLicensePlate())
                .setPayload("id", parkingLot.getId())
                .setPayload("parkingLotKey", parkingLot.getKey())
                .setSigner(JWTSignerUtil.none())
                .sign();
        this.parkingLot = parkingLot;
    }

    public boolean isValid(){
        try {
            return JWT.of(token).getPayload("parkingLotKey").equals(parkingLot.getKey()) && !isUsed;
        } catch (JWTException e) {
            return false;
        }
    }


    public boolean isUsed() {
        return isUsed;
    }

}
