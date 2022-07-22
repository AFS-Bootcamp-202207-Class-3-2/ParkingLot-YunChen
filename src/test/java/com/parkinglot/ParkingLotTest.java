package com.parkinglot;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.Ticket;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatObject;

public class ParkingLotTest {

    @Test
    void should_give_ticket_when_park_given_car() {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Ticket ticket = parkingLot.park(new Car());
        //then
        assertThat(ticket).isNotNull();
    }

    @Test
    void should_return_nothing_when_parkLot_full_given_car() {
        //given
        ParkingLot parkingLot = new ParkingLot(1);
        //when
        parkingLot.park(new Car());
        Ticket ticketSecond = parkingLot.park(new Car());
        //then
        assertThat(ticketSecond).isNull();
    }

    @Test
    void should_return_car_when_fetch_given_ticket() {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Car car = new Car();
        Ticket ticket = parkingLot.park(car);
        Car fetchCar = parkingLot.fetch(ticket);
        //then
        assertThat(fetchCar).isNotNull();
    }

    @Test
    void should_return_each_ticket_when_park_two_car_given_two_car() {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Ticket ticketFirst = parkingLot.park(new Car());
        Ticket ticketSecond = parkingLot.park(new Car());
        //then
        Assertions.assertThatObject(new LinkedList<>(Arrays.asList(ticketFirst, ticketSecond))).
                matches(tickets -> tickets != null);
    }

    @Test
    void should_return_nothing_when_fetch_given_wrong_ticket() {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Car car = parkingLot.fetch(new Ticket());
        //then
        assertThat(car).isNull();
    }

    @Test
    void should_return_nothing_when_fetch_given_a_used_ticket() {
        //given

        //when

        //then
    }


}
