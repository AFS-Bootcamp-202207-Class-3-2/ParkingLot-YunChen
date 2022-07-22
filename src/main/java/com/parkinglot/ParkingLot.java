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
    private int currCapacity = capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        currCapacity = capacity;
    }
    public Ticket park(Car car) {
        if (currCapacity <= 0) {
            return null;
        }
        this.currCapacity--;
        return new Ticket();
    }

    public Car fetch(Ticket ticket) {
        return new Car();
    }


}
