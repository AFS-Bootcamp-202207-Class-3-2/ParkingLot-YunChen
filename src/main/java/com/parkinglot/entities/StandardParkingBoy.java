package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class StandardParkingBoy extends ParkingBoy{




    public StandardParkingBoy(ParkingLot... parkingLots) {
        super(parkingLots);
    }

    @Override
    public ParkingLot selectByStrategy() {
        ParkingLot hasPositionParkingLot = null;
        for (int idx = 0; idx < this.getParkingLots().size(); idx++) {
            if (!this.getParkingLots().get(idx).isFull()) {
                hasPositionParkingLot = this.getParkingLots().get(idx);
                break;
            }
        }
        return hasPositionParkingLot;
    }


//    public Ticket park(Car car) throws UnAvailablePositionException, UnRecognizedException {
//        if (car == null) {
//            throw new UnRecognizedException(Constant.UnRecognizedCarException);
//        }
//        ParkingLot hasPositionParkingLot = null;
//        for (int idx = 0; idx < parkingLots.size(); idx++) {
//            if (!parkingLots.get(idx).isFull()) {
//                hasPositionParkingLot = parkingLots.get(idx);
//                break;
//            }
//        }
//        if (hasPositionParkingLot == null) {
//            throw new UnAvailablePositionException();
//        }
//        return hasPositionParkingLot.park(car);
//    }


}
