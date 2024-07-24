package com.example.geektrust.service.impl;

import com.example.geektrust.model.MetroCard;
import com.example.geektrust.service.MetroCardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MetroCardServiceImpl implements MetroCardService {

    private final Map<String, MetroCard> metroCards = new HashMap<>();

    @Override
    public void addCard(String cardId, int balance) {
        MetroCard metroCard = new MetroCard(cardId, balance);
        metroCards.put(cardId, metroCard);
    }

    @Override
    public List<MetroCard> getCards() {
        List<MetroCard> list = new ArrayList<>();
        for (Map.Entry<String, MetroCard> entry : metroCards.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public int transactCard(String cardId, int amount) {
        MetroCard metroCard = metroCards.get(cardId);
        int balance = metroCard.getBalance();
        int diff = balance - amount;

        if (diff < 0) {
            metroCard.setBalance(0);
            metroCards.put(cardId, metroCard);
            return -diff;
        } else {
            metroCard.setBalance(diff);
            metroCards.put(cardId, metroCard);
            return 0;
        }
    }

}
