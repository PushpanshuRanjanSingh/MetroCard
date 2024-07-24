package com.example.geektrust.service.impl;

import com.example.geektrust.service.CheckInService;
import com.example.geektrust.service.MetroCardService;
import com.example.geektrust.service.PrintSummaryService;
import org.junit.jupiter.api.Test;

public class PrintSummaryImplTest {

    private final MetroCardService metroCardService = new MetroCardServiceImpl();
    private final CheckInService checkInService = new CheckInServiceImpl(metroCardService);
    private final PrintSummaryService printSummaryService = new PrintSummaryServiceImpl(checkInService);

    @Test
    public void testPrintSummaryCall () {
        printSummaryService.printSummary();
    }
}
