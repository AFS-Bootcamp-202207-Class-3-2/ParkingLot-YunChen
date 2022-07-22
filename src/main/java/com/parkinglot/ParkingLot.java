package com.parkinglot;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.parkinglot.entities.Car;
import com.parkinglot.entities.Ticket;
import com.parkinglot.exception.UnrecognizedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class ParkingLot {

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
    public Ticket park(Car car) {
        if (currCapacity <= 0) {
            return null;
        }
        Ticket ticket = new Ticket();
        correspondTicket.put(ticket.getToken(), car);
        this.currCapacity--;
        return ticket;
    }

    public Car fetch(Ticket ticket) throws Exception {
        if (!valid(ticket)) {
            throw new UnrecognizedException();
        }
        String token = ticket.getToken();
        if (correspondTicket.containsKey(token)) {
            Car car = correspondTicket.get(token);
            correspondTicket.remove(token);
            return car;
        } else {
            return null;
        }
    }

    private boolean valid(Ticket ticket) {
        return !StringUtils.isEmpty(ticket.getToken())||correspondTicket.containsKey(ticket.getToken());
    }

    public static void main(String[] args) {
//        byte[] key = "1234567890".getBytes();
//        String token = JWT.create().setPayload("license", "12").setKey(key).sign();
//        JWT jwt = JWT.of(token).setKey(key);
//        System.out.println(jwt.getPayload("license"));
    }


}
