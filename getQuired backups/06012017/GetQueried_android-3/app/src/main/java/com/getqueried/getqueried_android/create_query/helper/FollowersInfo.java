package com.getqueried.getqueried_android.create_query.helper;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class represent basic information about some person
 * {@link FollowersInfo#name} and {@link FollowersInfo#number} can't be set to null
 * {@link FollowersInfo#icon} can be null, in such case should be used default image from res
 */
public class FollowersInfo implements Comparable<FollowersInfo> {

    @Nullable
    public Bitmap icon;
    public String name;
    public String number;

    public FollowersInfo(String name, String number, Bitmap icon) {
        this.icon = icon;
        this.name = nullToEmpty(name);
        this.number = nullToEmpty(number);
    }

    public FollowersInfo() {
        name = "";
        number = "";
        icon = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != FollowersInfo.class) {
            return false;
        }
        FollowersInfo second = (FollowersInfo) o;
        return (number.equals(second.number) && name.equals(second.name));
    }

    @Override
    public int compareTo(@NonNull FollowersInfo another) {
        return name.compareTo(another.name);
    }

    @Nullable
    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = nullToEmpty(name);
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@Nullable String number) {
        this.number = nullToEmpty(number);
    }

    private String nullToEmpty(String string) {
        return (string == null) ? "" : string;
    }
}