package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public abstract class ParkingBoy {
    /**
     * 所管理的停车场
     */
    private List<ParkingLot> parkingLots = new ArrayList<>();




    public ParkingBoy(ParkingLot... parkingLots) {
        for (int idx = 0; idx < parkingLots.length; idx++) {
            ParkingLot parkingLot = parkingLots[idx];
            parkingLot.setId(idx);
            this.parkingLots.add(parkingLot);
        }
    }

    public abstract void addManageNewParkingLot(ParkingLot newParkingLot) ;


    public abstract Ticket park(Car car) throws UnAvailablePositionException, UnRecognizedException;


    public abstract Car fetch(Ticket ticket) throws UnRecognizedException;
}
