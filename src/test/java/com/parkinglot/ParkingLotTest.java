package com.parkinglot;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.Ticket;
import com.parkinglot.exception.UnrecognizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Fail;
import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void should_throw_error_message_unrecognized_ticket_when_fetch_given_a_used_ticket()throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot();
        //when
        Ticket ticket = parkingLot.park(new Car());
        parkingLot.fetch(ticket);
        //then
        UnrecognizedException unrecognizedException = assertThrows(UnrecognizedException.class,
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
        } catch (Exception e) {
            //then
            Assertions.assertThat(e).hasMessageContaining("Unrecognized parking ticket");
        }

    }


    @Test
    void should_throw_error_message_no_available_when_park_given_a_full_parking_lot() {
        //given
        
        //when
        
        //then
    }
    

}
