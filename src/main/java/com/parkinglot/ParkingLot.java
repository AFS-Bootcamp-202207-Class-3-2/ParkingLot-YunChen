package com.parkinglot;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLot {

    private int capacity = 10;

    /**
     * 当前剩余容量
     */
    private int locals = capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }
    public Ticket park(Car car) {
        if (locals <= 0) {
            return null;
        }
        locals-=locals;
        return new Ticket();
    }
}
