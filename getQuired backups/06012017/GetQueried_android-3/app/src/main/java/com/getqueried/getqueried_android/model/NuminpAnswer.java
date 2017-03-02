package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ideafoundation on 04/01/17.
 */

public class NuminpAnswer {
    private String answer;

    /**
     * No args constructor for use in serialization
     */
    public NuminpAnswer() {
    }


    public NuminpAnswer(String answer) {
        this.answer = answer;


    }

    /**
     * @return The questionType
     */
    public String getanswer() {
        return answer;
    }

    /**
     * @param
     */
    public void setanswer(String answer) {
        this.answer = answer;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(answer).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NumSliQuery) == false) {
            return false;
        }
        NuminpAnswer rhs = ((NuminpAnswer) other);
        return new EqualsBuilder().append(answer, rhs.answer).isEquals();
    }
}
