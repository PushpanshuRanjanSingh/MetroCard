package com.example.geektrust;

import com.example.geektrust.model.CheckIn;
import com.example.geektrust.service.CheckInService;
import com.example.geektrust.service.MetroCardService;
import com.example.geektrust.service.PrintSummaryService;
import com.example.geektrust.service.impl.CheckInServiceImpl;
import com.example.geektrust.service.impl.MetroCardServiceImpl;
import com.example.geektrust.service.impl.PrintSummaryServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MetroStationService {

    MetroCardService metroCardService = new MetroCardServiceImpl();
    CheckInService checkInService = new CheckInServiceImpl(metroCardService);
    PrintSummaryService printSummaryService = new PrintSummaryServiceImpl(checkInService);

    public void start(String[] args) throws IOException{

        try {
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String[] input = sc.nextLine().split(" ", 2);
                switch (input[0]) {
                    case "BALANCE":
                        String[] cardProperties = input[1].split(" ", 2);
                        metroCardService.addCard(cardProperties[0], Integer.parseInt(cardProperties[1]));
                        break;
                    case "CHECK_IN":
                        String[] checkInDetails = input[1].split(" ", 3);
                        checkInService.checkInPassenger(new CheckIn(checkInDetails[0], checkInDetails[1], checkInDetails[2]));
                        break;
                    case "PRINT_SUMMARY":
                        printSummaryService.printSummary();
                        break;
                    default:
                        break;
                }

            }
            sc.close();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
