package com.parkinglot.entities;

import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class ParkingLot {

    private int id = 0;

    private int capacity = 10;

    private Map<String,Car> correspondTicket;

    public ParkingLot(){
        correspondTicket = new HashMap<>(capacity);
    }
    /**
     * 当前剩余容量
     */
    private int currCapacity = capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        currCapacity = capacity;
        correspondTicket = new HashMap<>(capacity);
    }
    public Ticket park(Car car) throws UnAvailablePositionException {
        if (currCapacity <= 0) {
            throw new UnAvailablePositionException();
        }
        //只有parkLot颁发的ticket才是有效的
        Ticket ticket = new Ticket(car);
        correspondTicket.put(ticket.getToken(), car);
        this.currCapacity--;
        return ticket;
    }

    /**
     * 用于给parkingBoyFetch操作的映射
     * @param car
     * @param id
     * @return
     * @throws UnAvailablePositionException
     */
    public Ticket park(Car car,int id) throws UnAvailablePositionException {
        if (currCapacity <= 0) {
            throw new UnAvailablePositionException();
        }
        //只有parkLot颁发的ticket才是有效的
        Ticket ticket = new Ticket(car,id);
        correspondTicket.put(ticket.getToken(), car);
        this.currCapacity--;
        return ticket;
    }

    public Car fetch(Ticket ticket) throws UnRecognizedException {
        if (!ticket.isValid() ||ticket.isUsed()) {
            throw new UnRecognizedException();
        }
        String token = ticket.getToken();
        if (correspondTicket.containsKey(token)) {
            Car car = correspondTicket.get(token);
            correspondTicket.remove(token);
            //被使用设置为无效
            ticket.setValid(false);
            return car;
        } else {
            return null;
        }
    }

    private boolean valid(Ticket ticket) {
        return !StringUtils.isEmpty(ticket.getToken())||correspondTicket.containsKey(ticket.getToken());
    }

    public boolean isFull() {
        return currCapacity >= capacity;
    }


}
