package com.myproject;

import java.util.HashMap;
import java.util.Map;

public class StockAlertView implements StockViewer {
    private double alertThresholdHigh;
    private double alertThresholdLow;
    private Map<String, Double> lastAlertedPrices = new HashMap<>(); // TODO: Stores last alerted price per stock

    public StockAlertView(double highThreshold, double lowThreshold) {
        this.alertThresholdHigh = highThreshold;
        this.alertThresholdLow = lowThreshold;
    }

    @Override
    public void onUpdate(StockPrice stockPrice) {
        String stockCode = stockPrice.getCode();
        Double currentPrice = stockPrice.getAvgPrice();
        Double lastPrice = lastAlertedPrices.get(stockCode);
        //First time log the price
        if (lastPrice == null) {
            if (currentPrice >= alertThresholdHigh) {
                alertAbove(stockCode, currentPrice);
            } 
            else if (currentPrice <= alertThresholdLow) {
                alertBelow(stockCode, currentPrice);
            }
            lastAlertedPrices.put(stockCode, currentPrice);
            return;
        }
        if (currentPrice >= alertThresholdHigh && lastPrice <= alertThresholdHigh) {
            alertAbove(stockCode, currentPrice);
        } 
        if (currentPrice <= alertThresholdLow && lastPrice >= alertThresholdLow) {
            alertBelow(stockCode, currentPrice);
        }
        lastAlertedPrices.put(stockCode, currentPrice);
    }

    private void alertAbove(String stockCode, double price) {
        com.myproject.Logger.logAlert(stockCode, price);
    }

    private void alertBelow(String stockCode, double price) {
        com.myproject.Logger.logAlert(stockCode, price);
    }
}
