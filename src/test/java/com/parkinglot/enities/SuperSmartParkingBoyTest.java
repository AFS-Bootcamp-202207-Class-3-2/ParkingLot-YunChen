package com.parkinglot.enities;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.SuperSmartParkingBoy;
import com.parkinglot.entities.Ticket;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperSmartParkingBoyTest {
    @Test
    void should_park_first_parking_lot_when_have_two_rate_parking_lot_given_car()throws Exception {
        //given
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
        superSmartParkingBoy.addManageNewParkingLot(firstParkingLot);
        superSmartParkingBoy.addManageNewParkingLot(secondParkingLot);
        //when
        Car car = new Car();
        Ticket ticket = superSmartParkingBoy.park(car);
        Car fetchCar = firstParkingLot.fetch(ticket);
        //then
        assertThat(fetchCar).isEqualTo(car);
    }
}
