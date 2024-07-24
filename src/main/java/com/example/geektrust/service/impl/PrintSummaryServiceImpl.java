package com.example.geektrust.service.impl;

import com.example.geektrust.constant.PassengerType;
import com.example.geektrust.constant.Station;
import com.example.geektrust.model.PassengerCount;
import com.example.geektrust.service.CheckInService;
import com.example.geektrust.service.PrintSummaryService;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class PrintSummaryServiceImpl implements PrintSummaryService {

    CheckInService checkInService;

    public PrintSummaryServiceImpl(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @Override
    public void printSummary() {
        Map<String, Integer> amount = checkInService.getAmount();
        Map<String, Integer> discount = checkInService.getDiscount();
        Map<Station, Map<PassengerType, Integer>> typeCount = checkInService.getTypeCount();

        for (Station station : Station.values()) {
            System.out.printf("TOTAL_COLLECTION %s %d %d%n", station.name(), amount.get(station.name()), discount.get(station.name()));
            System.out.println("PASSENGER_TYPE_SUMMARY");

            PriorityQueue<PassengerCount> sortedCount = convertToQueue(typeCount.get(station));
            while (!sortedCount.isEmpty()) {
                PassengerCount passengerCount = sortedCount.poll();
                System.out.printf("%s %d%n", passengerCount.getPassengerType().name(), passengerCount.getCount());
            }
        }
    }

    private PriorityQueue<PassengerCount> convertToQueue(Map<PassengerType, Integer> map) {
        return map.entrySet().stream()
                .map(entry -> new PassengerCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(PriorityQueue::new));
    }

}
