package com.parkinglot.enities;

import com.parkinglot.entities.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextTest {
    @Test
    void should_return_right_car_when_change_strategy_given_base_rate()throws Exception {
        //given
        Context context = new Context();
        Car firstCar = new Car();
        Car secondCar = new Car();
        context.addParkingLotStrategy("rate", new SuperSmartParkingBoy());
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        context.addParkingLot(firstParkingLot);
        context.addParkingLot(secondParkingLot);
        Ticket firstCarTicket = context.parkCar(firstCar);
        Ticket secondCarTicket = context.parkCar(secondCar);
        context.changeParkingStrategy("rate");
        //when
        Car fetchFirstCar = context.fetchCar(firstCarTicket);
        Car fetchSecondCar =context.fetchCar(secondCarTicket);
        //then
        assertThat(fetchFirstCar).isEqualTo(firstCar);
        assertThat(fetchSecondCar).isEqualTo(secondCar);
    }
}
