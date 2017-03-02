package com.getqueried.getqueried_android.profile.helper;

import java.util.ArrayList;
import java.util.List;

public class InterfaceMediator {
    protected static List<AddFollowerInterface> observers = new ArrayList<AddFollowerInterface>();

    public static void addObserver(AddFollowerInterface observer) {
        observers.add(observer);
    }

    public static void removeObserver(Object observer) {
        int pos = observers.indexOf(observer);
        if (pos > -1)
            observers.remove(pos);
    }

    public static void notifyObservers(Object sender, Object data) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onFollowerAdded(sender, data);
        }
    }

}