package com.example.geektrust.service;

import com.example.geektrust.constant.PassengerType;
import com.example.geektrust.constant.Station;
import com.example.geektrust.model.CheckIn;

import java.util.Map;

public interface CheckInService {

    /**
        Checks-in a passenger
        @CheckIn object
    */
    void checkInPassenger(CheckIn checkIn);

    /**
     * return station type along with count
     * @return map - amount left to pay
     */
    Map<Station, Map<PassengerType, Integer>> getTypeCount();

    /**
        returns station vs amount map
        @return map - amount paid mapped to station
    */
    Map<String, Integer> getAmount();

    /**
        returns station vs amount map
        @return map - amount paid mapped to station
    */
    Map<String, Integer> getDiscount();

}
