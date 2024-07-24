package com.example.geektrust.service.impl;

import com.example.geektrust.constant.PassengerType;
import com.example.geektrust.constant.Station;
import com.example.geektrust.model.CheckIn;
import com.example.geektrust.service.CheckInService;
import com.example.geektrust.service.MetroCardService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CheckInServiceImpl implements CheckInService {

    Map<String, Integer> amount;
    Map<String, Integer> discount;
    Map<Station, Map<PassengerType, Integer>> typeCount;
    Map<String, Station> passenger;

    MetroCardService metroCardService;

    public CheckInServiceImpl(MetroCardService metroCardService) {
        this.metroCardService = metroCardService;
        amount = new HashMap<>();
        discount = new HashMap<>();
        typeCount = new HashMap<>();
        passenger = new HashMap<>();

        Arrays.stream(Station.values()).forEach(station -> {
            amount.put(station.name(), 0);
            discount.put(station.name(), 0);
            typeCount.put(station, new HashMap<>());
        });
    }


    @Override
    public void checkInPassenger(CheckIn checkIn) {
        String cardId = checkIn.getCardId();
        String stationName = checkIn.getFromStation().name();
        int amount = checkIn.getPassengerType().getVal();
        int discount = 0;

        if (passenger.containsKey(cardId)) {
            amount /= 2;
            discount = amount;
            passenger.remove(cardId);
        } else {
            passenger.put(cardId, checkIn.getFromStation());
        }

        int finalAmount = amount;
        int remaining = metroCardService.transactCard(cardId, finalAmount);

        amount += (int) (remaining * 0.02f);

        this.amount.merge(stationName, amount, Integer::sum);
        this.discount.merge(stationName, discount, Integer::sum);

        updatePassengerCount(typeCount.get(checkIn.getFromStation()), checkIn.getPassengerType(), checkIn.getFromStation());
    }

    private void updatePassengerCount(Map<PassengerType, Integer> tempMap, PassengerType passengerType, Station station) {
        tempMap.merge(passengerType, 1, Integer::sum);
        typeCount.put(station, tempMap);
    }


    @Override
    public Map<Station, Map<PassengerType, Integer>> getTypeCount() {
        return this.typeCount;
    }

    @Override
    public Map<String, Integer> getAmount() {
        return this.amount;
    }

    @Override
    public Map<String, Integer> getDiscount() {
        return this.discount;
    }
}
