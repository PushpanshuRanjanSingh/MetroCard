package com.example.geektrust.service.impl;

import com.example.geektrust.constant.PassengerType;
import com.example.geektrust.constant.Station;
import com.example.geektrust.model.CheckIn;
import com.example.geektrust.service.CheckInService;
import com.example.geektrust.service.MetroCardService;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckInServiceImplTest {

    private final static MetroCardService metroCardService = new MetroCardServiceImpl();
    private final CheckInService checkInService = new CheckInServiceImpl(metroCardService);

    @BeforeAll
    public void setup() {
        metroCardService.addCard("MC1", 101);
        metroCardService.addCard("MC2", 501);
        metroCardService.addCard("MC3", 1001);
    }

    @Order(1)
    @Test
    public void testAdultCheckIn() {
        CheckIn checkIn = new CheckIn("MC3", PassengerType.ADULT.name(), Station.CENTRAL.name());
        checkInService.checkInPassenger(checkIn);
        Assertions.assertNotEquals(0, checkInService.getAmount().get(Station.CENTRAL.name()));
    }

    @Order(2)
    @Test
    public void testSeniorCheckIn() {
        CheckIn checkIn = new CheckIn("MC2", PassengerType.SENIOR_CITIZEN.name(), Station.CENTRAL.name());
        checkInService.checkInPassenger(checkIn);
        Assertions.assertNotEquals(0, checkInService.getAmount().get(Station.CENTRAL.name()));
    }

    @Order(3)
    @Test
    public void testKidCheckIn() {
        CheckIn checkIn = new CheckIn("MC1", PassengerType.KID.name(), Station.CENTRAL.name());
        checkInService.checkInPassenger(checkIn);
        Assertions.assertNotEquals(0, checkInService.getAmount().get(Station.CENTRAL.name()));
    }

    @Order(4)
    @Test
    public void testAdultCheckInReturn() {
        CheckIn checkIn = new CheckIn("MC3", PassengerType.ADULT.name(), Station.CENTRAL.name());
        checkInService.checkInPassenger(checkIn);
        Assertions.assertEquals(0, checkInService.getDiscount().get(Station.AIRPORT.name()));
        Assertions.assertNotEquals(0, checkInService.getAmount().get(Station.CENTRAL.name()));
    }
}
