package com.getqueried.getqueried_android;

/**
 * Created by user on 12/24/2016.
 */

public class Country {
    private String thumbnailUrl;
    String code = null;
    String name = null;
    boolean selected = false;

    public Country(String code, String name,String thumbnailUrl, boolean selected) {
        super();
        this.code = code;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.selected = selected;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

