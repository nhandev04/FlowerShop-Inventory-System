package com.flowershop.view.observer;

import java.util.ArrayList;
import java.util.List;

public class ShopEventManager {

    private static ShopEventManager instance;
    private final List<ShopObserver> observers;

    private ShopEventManager() {
        observers = new ArrayList<>();
    }

    public static synchronized ShopEventManager getInstance() {
        if (instance == null) {
            instance = new ShopEventManager();
        }
        return instance;
    }

    public void subscribe(ShopObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(ShopObserver observer) {
        observers.remove(observer);
    }

    public void notify(String eventType) {
        for (ShopObserver observer : observers) {
            observer.updateData(eventType);
        }
    }
}