package com.parkinglot.enities;

import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.Car;
import com.parkinglot.entities.Ticket;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Fail;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {

    @Test
    void should_give_ticket_when_park_given_car()throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Ticket ticket = parkingLot.park(new Car());
        //then
        assertThat(ticket).isNotNull();
    }


    @Test
    void should_return_car_when_fetch_given_ticket()throws Exception {
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
    void should_return_each_ticket_when_park_two_car_given_two_car()throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Ticket ticketFirst = parkingLot.park(new Car());
        Ticket ticketSecond = parkingLot.park(new Car());
        //then
        Assertions.assertThatObject(new LinkedList<>(Arrays.asList(ticketFirst, ticketSecond))).
                matches(Objects::nonNull);
    }



    @Test
    void should_throw_error_message_unrecognized_ticket_when_fetch_given_a_used_ticket()throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Ticket ticket = parkingLot.park(new Car());
        parkingLot.fetch(ticket);
        //then
        UnRecognizedException unrecognizedException = assertThrows(UnRecognizedException.class,
                () -> parkingLot.fetch(ticket));

        assertEquals("Unrecognized parking ticket",unrecognizedException.getMessage());
    }

    
    @Test
    void should_throw_error_message_unrecognized_ticket_when_fetch_car_given_unrecognized_ticket() {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Ticket ticket = new Ticket();
        try {
            parkingLot.fetch(ticket);
            Fail.fail("Unrecognized parking ticket");
        } catch (UnRecognizedException e) {
            //then
            Assertions.assertThat(e).hasMessageContaining("Unrecognized parking ticket");
        }

    }


    @Test
    void should_throw_error_message_no_available_when_park_given_a_full_parking_lot() throws Exception{
        //given
        ParkingLot fullParkingLot = new ParkingLot(1);
        //when
        fullParkingLot.park(new Car());
        UnAvailablePositionException unAvailablePositionException = assertThrows(UnAvailablePositionException.class,
                () -> fullParkingLot.park(new Car()));
        //then
        assertEquals("No available position",unAvailablePositionException.getMessage());
    }
    

}
