package com.parkinglot.enities;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.SuperSmartParkingBoy;
import com.parkinglot.entities.Ticket;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void should_park_second_parking_lot_when_first_rate_large_second_given_car()throws Exception {
        //given
        ParkingLot firstParkingLot = new ParkingLot(2);
        firstParkingLot.park(new Car());
        ParkingLot secondParkingLot = new ParkingLot(2);
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
        superSmartParkingBoy.addManageNewParkingLot(firstParkingLot);
        superSmartParkingBoy.addManageNewParkingLot(secondParkingLot);
        //when
        Car car = new Car();
        Ticket ticket = superSmartParkingBoy.park(car);
        Car fetchCar = secondParkingLot.fetch(ticket);
        //then
        assertThat(fetchCar).isEqualTo(car);
    }

    @Test
    void should_return_each_right_when_fetch_twice_given_two_ticket()throws Exception {
        //given
        ParkingLot firstParkingLot = new ParkingLot(2);
        Car firstCar = new Car();
        Ticket firstCarTicket = firstParkingLot.park(firstCar);
        ParkingLot secondParkingLot = new ParkingLot(2);
        Car secondCar = new Car();
        Ticket secondCarTicket = secondParkingLot.park(secondCar);
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
        superSmartParkingBoy.addManageNewParkingLot(firstParkingLot);
        superSmartParkingBoy.addManageNewParkingLot(secondParkingLot);
        //when
        Car fetchFirstCar = superSmartParkingBoy.fetch(firstCarTicket);
        Car fetchSecondCar = superSmartParkingBoy.fetch(secondCarTicket);
        //then
        assertThat(firstCar).isEqualTo(fetchFirstCar);
        assertThat(secondCar).isEqualTo(fetchSecondCar);
    }
    
    @Test
    void should_throws_unrecognized_exception_when_fetch_given_a_wrong_ticket()throws Exception {
        //given
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot(),new ParkingLot());
        Ticket ticket = new Ticket();
        //when
        UnRecognizedException unRecognizedException = assertThrows(UnRecognizedException.class, () -> superSmartParkingBoy.fetch(ticket));
        //then
        assertThat(Constant.UnRecognizedTicketException).isEqualTo(unRecognizedException.getMessage());
    }

    @Test
    void should_throws_unrecognized_exception_when_fetch_given_a_used_ticket()throws Exception {
        //given
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot(),new ParkingLot());
        Car car = new Car();
        Ticket ticket = superSmartParkingBoy.park(car);
        superSmartParkingBoy.fetch(ticket);
        //when
        UnRecognizedException unRecognizedException = assertThrows(UnRecognizedException.class, () -> superSmartParkingBoy.fetch(ticket));
        //then
        assertThat(Constant.UnRecognizedTicketException).isEqualTo(unRecognizedException.getMessage());
    }
}
