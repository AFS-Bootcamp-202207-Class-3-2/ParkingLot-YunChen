package com.parkinglot.entities;

import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {


    private Map<String,ParkingBoy> strategies = new HashMap<>();

    private String strategy = "default";

    public Context()  {
        strategies.put("default", new StandardParkingBoy());
    }


    public void addParkingLot(ParkingLot parkingLot) {
        this.strategies.get(strategy).addManageNewParkingLot(parkingLot);
    }
    public void addParkingLotStrategy(String command,ParkingBoy strategy) {
        this.strategies.put(command, strategy);
    }

    public Ticket parkCar(Car car) throws UnRecognizedException, UnAvailablePositionException {
        return strategies.get(strategy).park(car);
    }
    public Car fetchCar(Ticket ticket) throws UnRecognizedException {
        return strategies.get(strategy).fetch(ticket);
    }

    public void changeParkingStrategy(String strategy) throws Exception {
        if (StringUtils.isEmpty(strategy) || !strategies.containsKey(strategy)) {
            throw new Exception("not the strategy");
        }
        ParkingBoy originExecutor = strategies.get(this.strategy);
        List<ParkingLot> parkingLots = originExecutor.getParkingLots();
        ParkingBoy orderExecutor = strategies.get(strategy);
        this.strategy = strategy;
        for (int idx = 0; idx < parkingLots.size(); idx++) {
            ParkingLot parkingLot = parkingLots.get(idx);
            orderExecutor.addManageNewParkingLot(parkingLot);
        }
        originExecutor.clean();
    }
}
