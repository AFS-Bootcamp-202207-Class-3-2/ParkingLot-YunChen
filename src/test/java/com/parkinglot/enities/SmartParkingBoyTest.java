package com.parkinglot.enities;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.SmartParkingBoy;
import com.parkinglot.entities.Ticket;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmartParkingBoyTest {
    
    @Test
    void should_park_first_empty_parking_lot_when_park_a_car_given_a_car_and_two_empty_parking_lot()throws Exception {
        //given
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
        //when
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        smartParkingBoy.addManageNewParkingLot(firstParkingLot);
        smartParkingBoy.addManageNewParkingLot(secondParkingLot);
        Car car = new Car();
        Ticket ticket = smartParkingBoy.park(car);
        //then
        Car fetchCar = firstParkingLot.fetch(ticket);
        assertThat(fetchCar).isEqualTo(car);
    }

    @Test
    void should_park_to_second_empty_parking_lot_when_first_parking_lot_not_empty_given_a_car_and_two_parking_lot()throws Exception {
        //given
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
        //when
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        smartParkingBoy.addManageNewParkingLot(firstParkingLot);
        smartParkingBoy.addManageNewParkingLot(secondParkingLot);
        firstParkingLot.park(new Car());
        Car checkCar = new Car();
        Ticket ticket = smartParkingBoy.park(checkCar);
        //then
        Car fetchCar = secondParkingLot.fetch(ticket);
        assertThat(checkCar).isEqualTo(fetchCar);
    }
}
