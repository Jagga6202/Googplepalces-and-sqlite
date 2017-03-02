package com.example.ideafoundation.meetaapp.model;

import java.io.Serializable;

/**
 * Created by ideafoundation on 01/03/17.
 */

public class DefaultNeed implements Serializable {
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;
    int id;

    public DefaultNeed(int id, String places_default) {
        this.id = id;
        this.places_default = places_default;
    }

    public DefaultNeed() {
    }

    public DefaultNeed(String places_default) {
        this.places_default = places_default;
    }

    public String getPlaces_default() {
        return places_default;
    }

    public void setPlaces_default(String places_default) {
        this.places_default = places_default;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String places_default;
}
