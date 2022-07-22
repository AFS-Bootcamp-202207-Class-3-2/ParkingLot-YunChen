package com.parkinglot.enities;

import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.StandardParkingBoy;
import org.junit.jupiter.api.Test;

public class StandardParkingBoyTest {

    @Test
    void should_parking_lot_first_full_and_parking_second_parking_log_second_when_parking_given_two_parking_log() {
        //given
        StandardParkingBoy standardParkingBoy = new StandardParkingBoy(new ParkingLot(1), new ParkingLot());
        //when

        //then
    }

}
