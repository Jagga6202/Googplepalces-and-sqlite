package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class LocationOptionsDep {

    private String location;
    private List<String> gender = new ArrayList<String>();
    private Integer locationLevel;

    /**
     * No args constructor for use in serialization
     */
    public LocationOptionsDep() {
    }

    /**
     * @param locationLevel
     * @param location
     * @param gender
     */
    public LocationOptionsDep(String location, List<String> gender, Integer locationLevel) {
        this.location = location;
        this.gender = gender;
        this.locationLevel = locationLevel;
    }

    /**
     * @return The location
     */
    public String getLocation() {
        return location ;
    }

    /**
     * @param location The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return The gender
     */
    public List<String> getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    /**
     * @return The locationLevel
     */
    public Integer getLocationLevel() {
        return locationLevel;
    }

    /**
     * @param locationLevel The locationLevel
     */
    public void setLocationLevel(Integer locationLevel) {
        this.locationLevel = locationLevel;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(location).append(gender).append(locationLevel).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LocationOptionsDep) == false) {
            return false;
        }
        LocationOptionsDep rhs = ((LocationOptionsDep) other);
        return new EqualsBuilder().append(location, rhs.location).append(gender, rhs.gender).append(locationLevel, rhs.locationLevel).isEquals();
    }

}