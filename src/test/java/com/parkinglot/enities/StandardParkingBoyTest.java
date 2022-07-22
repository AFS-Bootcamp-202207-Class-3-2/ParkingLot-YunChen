package com.parkinglot.enities;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.StandardParkingBoy;
import com.parkinglot.entities.Ticket;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StandardParkingBoyTest {
    @Test
    void should_parking_first_parking_lot_when_have_two_parking_lot_given_a_car_and_standard_parking_boy() throws UnRecognizedException, UnAvailablePositionException {
        //given
        StandardParkingBoy standardParkingBoy = new StandardParkingBoy(new ParkingLot(1), new ParkingLot());
        //when
        Car car = new Car("测试的车牌定制化license");
        Ticket ticket = standardParkingBoy.park(car);
        //then
        Car fetchCar = standardParkingBoy.getParkingLots().get(0).fetch(ticket);
        assertThat(fetchCar).isEqualTo(car);
    }
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

    @Test
    void should_return_right_tickets_of_two_car_when_fetch_each_car_given_two_ticket()throws Exception {
        //given
        StandardParkingBoy standardParkingBoy = new StandardParkingBoy(new ParkingLot(1), new ParkingLot());
        //when
        Car firstCar = new Car();
        Car secondCar = new Car();
        Ticket firstTicket = standardParkingBoy.park(firstCar);
        Ticket secondTicket = standardParkingBoy.park(secondCar);
        Car fetchSecondCar = standardParkingBoy.fetch(secondTicket);
        Car fetchFirstCar = standardParkingBoy.fetch(firstTicket);
        assertThat(firstCar).isEqualTo(fetchFirstCar);
        assertThat(fetchSecondCar).isEqualTo(secondCar);
    }

    @Test
    void should_throw_unrecognized_except_when_fetch_given_unrecognized_ticket()throws Exception {
        //given
        StandardParkingBoy standardParkingBoy = new StandardParkingBoy(new ParkingLot(1), new ParkingLot());
        //when
        //自己生成的ticket是无效的
        Ticket ticket = new Ticket();

        //then
        assertThatThrownBy(() -> standardParkingBoy.fetch(ticket)).isInstanceOf(UnRecognizedException.class)
        .hasMessageContaining(Constant.UnRecognizedTicketException);
    }


    @Test
    void should_throw_unrecognized_except_when_fetch_given_a_used_ticked()throws Exception {
        //given
        StandardParkingBoy standardParkingBoy = new StandardParkingBoy(new ParkingLot(1), new ParkingLot());
        //when
        Car car = new Car();
        Ticket ticket = standardParkingBoy.park(car);
        standardParkingBoy.fetch(ticket);

        //then
        UnRecognizedException unRecognizedException = assertThrows(UnRecognizedException.class, () -> standardParkingBoy.fetch(ticket));
        assertThat(unRecognizedException.getMessage()).isEqualTo(Constant.UnRecognizedTicketException);
    }

    @Test
    void should_throw_UnAvailable_when_park_given_two_full_parking_lot()throws Exception {
        //given
        StandardParkingBoy standardParkingBoy = new StandardParkingBoy(new ParkingLot(1), new ParkingLot(1));
        //when
        standardParkingBoy.park(new Car());
        standardParkingBoy.park(new Car());

        //then
        UnAvailablePositionException unAvailablePositionException = assertThrows(UnAvailablePositionException.class, () -> standardParkingBoy.park(new Car()));
        assertEquals(Constant.UnAvailablePositionException,unAvailablePositionException.getMessage());
    }
}
