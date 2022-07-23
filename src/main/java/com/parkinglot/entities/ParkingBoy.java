package com.parkinglot.entities;

import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Setter
@Getter
public abstract class ParkingBoy {
    /**
     * 所管理的停车场
     */
    private List<ParkingLot> parkingLots = new ArrayList<>();


    private Map<String, ParkingLot> mapToParkingLot = new HashMap<>();


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

    public abstract void watchParkingLots(ParkingLot parkingLot,String action);

    public void clean() {
        this.getMapToParkingLot().clear();
        this.getParkingLots().clear();
    }
}
