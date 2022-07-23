package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    private static final String LICENSE = "license";
    private boolean isUsed = false;
    private String token = "";
    private String parkingLotKey;

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
        this.parkingLotKey = parkingLot.getKey();
    }

    public boolean isValid(){
        try {
            return JWT.of(token).getPayload("parkingLotKey").equals(parkingLotKey) && !isUsed;
        } catch (JWTException e) {
            return false;
        }
    }


    public boolean isUsed() {
        return isUsed;
    }

}
