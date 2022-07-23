package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperSmartParkingBoy extends ParkingBoy {

    private double calculateLoadFactor(ParkingLot parkingLot) {
        int currCapacity = parkingLot.getCurrCapacity();
        int totalCapacity = parkingLot.getCapacity();
        double positions = totalCapacity - currCapacity+ 0.0;
        return positions  / totalCapacity;
    }

    public SuperSmartParkingBoy(ParkingLot... parkingLots) {
        Arrays.sort(parkingLots, (beforeParkingLot, behindParkingLot) -> {
            double compareLoadFactor = calculateLoadFactor(behindParkingLot) - calculateLoadFactor(beforeParkingLot);
            return compareLoadFactor > 0 ? -1 : 1;
        });
        List<ParkingLot> parkingLotsList = getParkingLots();
        for (int idx = 0; idx < parkingLots.length; idx++) {
            parkingLotsList.add(parkingLots[idx]);
            parkingLots[idx].setParkingBoy(this);
            this.getMapToParkingLot().put(parkingLots[idx].getKey(), parkingLots[idx]);
        }
    }
    @Override
    public void addManageNewParkingLot(ParkingLot newParkingLot) {
        int index = this.getParkingLots().size();
        this.getParkingLots().add(newParkingLot);
        updateUp(index);
        newParkingLot.setParkingBoy(this);
        this.getMapToParkingLot().put(newParkingLot.getKey(), newParkingLot);
    }

    @Override
    public Ticket park(Car car) throws UnAvailablePositionException, UnRecognizedException {
        if (car == null) {
            throw new UnRecognizedException(Constant.UnRecognizedCarException);
        }
        if (!this.getParkingLots().isEmpty()) {
            ParkingLot parkingLot = this.getParkingLots().get(0);
            Ticket ticket = parkingLot.park(car);
            updateDown(0);
            return ticket;
        } else {
            return null;
        }
    }

    @Override
    public Car fetch(Ticket ticket) throws UnRecognizedException {
        if (ticket == null || ticket.isUsed() || !ticket.isValid()) {
            throw new UnRecognizedException(Constant.UnRecognizedTicketException);
        }
        String parkingLotKey = (String) JWT.of(ticket.getToken()).getPayload("parkingLotKey");
        ParkingLot parkingLot = this.getMapToParkingLot().get(parkingLotKey);
        Car car = parkingLot.fetch(ticket);
        updateUp(findFetchParkingLotIndex(parkingLot));
        return car;
    }

    @Override
    public void watchParkingLots(ParkingLot parkingLot, String action) {
        if (action.equals("park")) {
            updateDown(findFetchParkingLotIndex(parkingLot));
        } else if (action.equals("fetch")) {
            updateUp(findFetchParkingLotIndex(parkingLot));
        }
    }


    private int findFetchParkingLotIndex(ParkingLot parkingLot) {
        for (int idx = 0; idx < this.getParkingLots().size(); idx++) {
            if (parkingLot.getKey().equals(this.getParkingLots().get(idx).getKey())) {
                return idx;
            }
        }
        return -1;
    }
    /**
     * 新添加元素就对大顶堆进行更新,上提
     *
     * @param index
     */
    private int updateUp(int index) {
        while (index > 0) {
            int parIndex = ((index + 1) >> 1) - 1;
            if (swap(parIndex, index)) {
                index = parIndex;
            } else {
                break;
            }
        }
        return index;
    }

    private int updateDown(int index) {
        int threshold = this.getParkingLots().size() - 1;
        List<ParkingLot> parkingLots = this.getParkingLots();
        while (index <= threshold) {
            int childLeftIndex = (index << 1) + 1;
            if (childLeftIndex >= this.getParkingLots().size()) {
                break;
            }
            //找到子节点最小
            int childIndex;
            if (childLeftIndex +1 < this.getParkingLots().size()) {
                childIndex =  parkingLots.get(childLeftIndex).getCurrCapacity() >
                        parkingLots.get(childLeftIndex + 1).getCurrCapacity() ?
                        childLeftIndex :
                        childLeftIndex + 1;
            }else {
                childIndex = childLeftIndex;
            }
            if (swap(index, childIndex)) {
                index = childIndex;
            } else {
                break;
            }
        }
        return index;
    }

    /**
     * 当前的负载因子比父节点更小就进行更换
     * @param parIdx
     * @param currIdx
     * @return
     */
    private boolean swap(int parIdx, int currIdx) {
        List<ParkingLot> parkingLots = this.getParkingLots();
        if (calculateLoadFactor(parkingLots.get(parIdx)) > calculateLoadFactor(parkingLots.get(currIdx))) {
            ParkingLot temporaryParkingLot = parkingLots.get(currIdx);
            parkingLots.set(currIdx, parkingLots.get(parIdx));
            parkingLots.set(parIdx, temporaryParkingLot);
            return true;
        } else {
            return false;
        }
    }

}
