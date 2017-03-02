package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Options {

    public List<String> location = new ArrayList<String>();
    public Integer locationLevel;
    public Object specificUsers;

    /**
     * No args constructor for use in serialization
     */
    public Options() {
    }

    /**
     * @param locationLevel
     * @param location
     */
    public Options(List<String> location, Integer locationLevel) {
        this.location = location;
        this.locationLevel = locationLevel;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(location).append(locationLevel).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Options) == false) {
            return false;
        }
        Options rhs = ((Options) other);
        return new EqualsBuilder().append(location, rhs.location).append(locationLevel, rhs.locationLevel).isEquals();
    }

}