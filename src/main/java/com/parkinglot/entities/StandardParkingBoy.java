package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StandardParkingBoy extends ParkingBoy{




    public StandardParkingBoy(ParkingLot... parkingLots) {
        super(parkingLots);
    }

    @Override
    public void addManageNewParkingLot(ParkingLot newParkingLot) {
        newParkingLot.setId(this.getParkingLots().size());
        this.getParkingLots().add(newParkingLot);
    }


    public Ticket park(Car car) throws UnAvailablePositionException, UnRecognizedException {
        if (car == null) {
            throw new UnRecognizedException(Constant.UnRecognizedCarException);
        }
        ParkingLot hasPositionParkingLot = null;
        for (int idx = 0; idx < this.getParkingLots().size(); idx++) {
            if (!this.getParkingLots().get(idx).isFull()) {
                hasPositionParkingLot = this.getParkingLots().get(idx);
                break;
            }
        }
        if (hasPositionParkingLot == null) {
            throw new UnAvailablePositionException();
        }
        return hasPositionParkingLot.park(car);
    }

    @Override
    public Car fetch(Ticket ticket) throws UnRecognizedException {
        if (ticket == null || ticket.isUsed() || !ticket.isValid()) {
            throw new UnRecognizedException(Constant.UnRecognizedTicketException);
        }
        int idx = (int) JWT.of(ticket.getToken()).getPayload("id");
        return this.getParkingLots().get(idx).fetch(ticket);
    }

    @Override
    public void watchParkingLots(ParkingLot parkingLot,String action) {

    }


}
