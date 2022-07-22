package com.parkinglot.enities;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.StandardParkingBoy;
import com.parkinglot.entities.Ticket;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StandardParkingBoyTest {

    @Test
    void should_parking_lot_first_full_and_parking_second_parking_log_second_when_parking_given_two_parking_lot() throws UnRecognizedException, UnAvailablePositionException {
        //given
        StandardParkingBoy standardParkingBoy = new StandardParkingBoy(new ParkingLot(1), new ParkingLot());
        //when
        standardParkingBoy.park(new Car());
        Car car = new Car("测试的车牌定制化license");
        Ticket ticket = standardParkingBoy.park(car);
        //then
        Car fetchCar = standardParkingBoy.getParkingLots().get(1).fetch(ticket);
        assertThat(fetchCar).isEqualTo(car);
    }

}
