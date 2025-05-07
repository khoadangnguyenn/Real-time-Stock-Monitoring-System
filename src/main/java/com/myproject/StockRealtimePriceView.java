package com.myproject;

import java.util.HashMap;
import java.util.Map;

public class StockRealtimePriceView implements StockViewer {
    private final Map<String, Double> lastPrices = new HashMap<>();

    @Override
    public void onUpdate(StockPrice stockPrice) {
        // Check if price has changed and log it
        Double currentPrice = stockPrice.getAvgPrice();
        String stockCode = stockPrice.getCode();
        Double lastPrice = lastPrices.get(stockCode);
        if (lastPrices.containsKey(stockCode)) {
            if (!lastPrice.equals(currentPrice)) {
                lastPrices.put(stockCode, currentPrice); 
                com.myproject.Logger.logRealtime(stockCode, currentPrice);
            }
        }
        else {
            lastPrices.put(stockCode, currentPrice);
        }
    }
}
