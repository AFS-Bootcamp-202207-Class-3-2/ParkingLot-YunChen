package com.parkinglot.entities;

import java.util.ArrayList;
import java.util.List;

public class StandardParkingBoy {
    private List<ParkingLot> parkingLots = new ArrayList<>();


    public StandardParkingBoy(ParkingLot... parkingLots) {
        for (int idx = 0; idx < parkingLots.length; idx++) {
            this.parkingLots.add(parkingLots[idx]);
        }
    }

    public void addManageNewParkingLot(ParkingLot newParkingLot) {
        parkingLots.add(newParkingLot);
    }


}
