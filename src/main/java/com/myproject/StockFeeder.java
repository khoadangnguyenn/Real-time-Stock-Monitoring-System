package com.myproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockFeeder {
    private List<Stock> stockList = new ArrayList<>();
    private Map<String, List<StockViewer>> viewers = new HashMap<>();
    private static StockFeeder instance = null;

    private StockFeeder() {
        this.stockList = new ArrayList<>(); 
        this.viewers = new HashMap<>();
    }

    public static StockFeeder getInstance() {
        if (instance == null) {
            instance = new StockFeeder();
            }
        return instance;
    }

    public void addStock(Stock stock) {
        if (!stockList.contains(stock)) {
            stockList.add(stock);
        }  
    }

    public void registerViewer(String code, StockViewer stockViewer) {
        List<StockViewer> trackingList = viewers.get(code);
        boolean stockExists = false;
        for (Stock stock : stockList) {
            if (stock.getCode().equals(code)) {
                stockExists = true;
                break;
            }
        }
        if (!stockExists) {
            com.myproject.Logger.errorRegister(code);
            return;
        }
        if (trackingList == null) {
            trackingList = new ArrayList<>();
            viewers.put(code, trackingList);
        } else {
            for (StockViewer existingViewer : trackingList) {
                if (existingViewer.getClass().equals(stockViewer.getClass())) {
                    // Viewer exist, log warning
                    com.myproject.Logger.errorRegister(code);
                    return;
                }
            }
        }
        trackingList.add(stockViewer);
    }    

    public void unregisterViewer(String code, StockViewer stockViewer) {
        if (viewers.containsKey(code)) {
            viewers.get(code).remove(stockViewer);
            if (viewers.get(code).isEmpty()) {
                viewers.remove(code);
            }
        }
        else {
            com.myproject.Logger.errorUnregister(code);
        }
    }

    public void notify(StockPrice stockPrice) {
        // Notifying registered viewers about price updates
        String stockCode = stockPrice.getCode();
        if (viewers.containsKey(stockCode)) {
            for (StockViewer viewer : viewers.get(stockCode)) {
                viewer.onUpdate(stockPrice);
            }
        }
    }
}
