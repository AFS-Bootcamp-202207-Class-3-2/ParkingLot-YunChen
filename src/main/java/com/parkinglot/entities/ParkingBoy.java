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

    public void addManageNewParkingLot(ParkingLot newParkingLot) {
        parkingLots.add(newParkingLot);
    }


    public Ticket park(Car car) throws UnAvailablePositionException, UnRecognizedException {
        if (car == null) {
            throw new UnRecognizedException(Constant.UnRecognizedCarException);
        }
        ParkingLot hasPositionParkingLot = selectByStrategy();
        if (hasPositionParkingLot == null) {
            throw new UnAvailablePositionException();
        }
        return hasPositionParkingLot.park(car);
    }

    public abstract ParkingLot  selectByStrategy();

    public Car fetch(Ticket ticket) throws UnRecognizedException {
        if (ticket == null || ticket.isUsed() || !ticket.isValid()) {
            throw new UnRecognizedException(Constant.UnRecognizedTicketException);
        }
        int idx = (int) JWT.of(ticket.getToken()).getPayload("id");
        return parkingLots.get(idx).fetch(ticket);
    }
}
