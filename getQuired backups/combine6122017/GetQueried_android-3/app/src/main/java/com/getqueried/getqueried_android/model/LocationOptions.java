package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class LocationOptions {

    private String location;
    private Integer locationLevel;
    private List<String> gender = new ArrayList<String>();
    private List<List<Integer>> age = new ArrayList<List<Integer>>();
    private List<String> education = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     */
    public LocationOptions() {
    }

    /**
     * @param locationLevel
     * @param location
     * @param age
     * @param gender
     * @param education
     */
    public LocationOptions(String location, Integer locationLevel, List<String> gender, List<List<Integer>> age, List<String> education) {
        this.location = location;
        this.locationLevel = locationLevel;
        this.gender = gender;
        this.age = age;
        this.education = education;
    }

    /**
     * @return The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(String location) {
        this.location = location;
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
     * @return The age
     */
    public List<List<Integer>> getAge() {
        return age;
    }

    /**
     * @param age The age
     */
    public void setAge(List<List<Integer>> age) {
        this.age = age;
    }

    /**
     * @return The education
     */
    public List<String> getEducation() {
        return education;
    }

    /**
     * @param education The education
     */
    public void setEducation(List<String> education) {
        this.education = education;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(location).append(locationLevel).append(gender).append(age).append(education).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LocationOptions) == false) {
            return false;
        }
        LocationOptions rhs = ((LocationOptions) other);
        return new EqualsBuilder().append(location, rhs.location).append(locationLevel, rhs.locationLevel).append(gender, rhs.gender).append(age, rhs.age).append(education, rhs.education).isEquals();
    }

}