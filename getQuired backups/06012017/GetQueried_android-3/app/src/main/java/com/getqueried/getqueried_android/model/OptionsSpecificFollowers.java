package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav on 10/10/2016.
 */
public class OptionsSpecificFollowers {

    public List<String> specificUsers = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     */
    public OptionsSpecificFollowers() {
    }

    /**
     * @param specificUsers
     */
    public OptionsSpecificFollowers(List<String> specificUsers) {
        this.specificUsers = specificUsers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(specificUsers).toHashCode();
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
        return new EqualsBuilder().append(specificUsers, rhs.specificUsers).isEquals();
    }
}
