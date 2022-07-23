package com.parkinglot.entities;

import cn.hutool.jwt.JWT;
import com.parkinglot.exception.UnAvailablePositionException;
import com.parkinglot.exception.UnRecognizedException;
import com.parkinglot.util.Constant;

import java.util.*;

public class SmartParkingBoy extends ParkingBoy {

    private Map<String, ParkingLot> mapToParkingLot = new HashMap<>();

    public SmartParkingBoy(ParkingLot... parkingLots) {
        Arrays.sort(parkingLots, (beforeParkingLot, behindParkingLot) -> behindParkingLot.getCurrCapacity() -  beforeParkingLot.getCurrCapacity() );
        List<ParkingLot> parkingLotsList = getParkingLots();
        for (int idx = 0; idx < parkingLots.length; idx++) {
            parkingLotsList.add(parkingLots[idx]);
            parkingLots[idx].setParkingBoy(this);
            mapToParkingLot.put(parkingLots[idx].getKey(), parkingLots[idx]);
        }
    }

    @Override
    public void addManageNewParkingLot(ParkingLot newParkingLot) {
        int index = this.getParkingLots().size();
        this.getParkingLots().add(newParkingLot);
        updateUp(index);
        newParkingLot.setParkingBoy(this);
        mapToParkingLot.put(newParkingLot.getKey(), newParkingLot);
    }


    private void buildBigTopPile() {
        List<ParkingLot> parkingLots = this.getParkingLots();
        for (int idx = parkingLots.size() - 1; idx > 0; idx--) {
            updateUp(idx);
        }
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
     * 比较父节点和字节点的剩余容量大小，确定是否进行交换
     * @param parIdx
     * @param currIdx
     * @return
     */
    private boolean swap(int parIdx, int currIdx) {
        List<ParkingLot> parkingLots = this.getParkingLots();
        int compareCapacity = parkingLots.get(parIdx).getCurrCapacity();
        if (parkingLots.get(currIdx).getCurrCapacity() > compareCapacity) {
            ParkingLot temporaryParkingLot = parkingLots.get(currIdx);
            parkingLots.set(currIdx, parkingLots.get(parIdx));
            parkingLots.set(parIdx, temporaryParkingLot);
            return true;
        } else {
            return false;
        }
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
        ParkingLot parkingLot = mapToParkingLot.get(parkingLotKey);
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
}
