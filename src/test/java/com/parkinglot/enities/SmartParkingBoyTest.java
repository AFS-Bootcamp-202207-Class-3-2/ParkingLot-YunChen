package com.parkinglot.enities;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.SmartParkingBoy;
import com.parkinglot.entities.Ticket;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void should_throw_unrecognized_exception_when_fetch_given_a_wrong_ticket()throws Exception {
        //given
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot(),new ParkingLot());
        Ticket wrongTicket = new Ticket();
        //when
        UnRecognizedException unRecognizedException = assertThrows(UnRecognizedException.class, () -> smartParkingBoy.fetch(wrongTicket));
        //then
        assertThat(Constant.UnRecognizedTicketException).isEqualTo(unRecognizedException.getMessage());
    }

    @Test
    void should_throw_unrecognized_exception_when_fetch_given_a_used_ticket()throws Exception {
        //given
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot(),new ParkingLot());
        Car car = new Car();
        Ticket ticket = smartParkingBoy.park(car);
        smartParkingBoy.fetch(ticket);
        //when
        UnRecognizedException unRecognizedException = assertThrows(UnRecognizedException.class, () -> smartParkingBoy.fetch(ticket));
        //then
        assertThat(Constant.UnRecognizedTicketException).isEqualTo(unRecognizedException.getMessage());
    }

    @Test
    void should_throw_unavailable_position_exception_when_park_car_given_two_full_parking_lot_to_smart_parking_boy()throws Exception {
        //given
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot(1),new ParkingLot(1));
        smartParkingBoy.park(new Car());
        smartParkingBoy.park(new Car());
        //when
        UnAvailablePositionException unAvailablePositionException = assertThrows(UnAvailablePositionException.class, () -> smartParkingBoy.park(new Car()));
        //then
        assertThat(Constant.UnAvailablePositionException).isEqualTo(unAvailablePositionException.getMessage());
    }
}
