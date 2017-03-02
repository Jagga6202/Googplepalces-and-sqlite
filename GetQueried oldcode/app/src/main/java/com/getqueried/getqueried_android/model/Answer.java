package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

public class Answer<T> {

    public String queryID;
    public ArrayList<T> answerList;

    /**
     * No args constructor for use in serialization
     */
    public Answer() {
    }

    /**
     * @param answerTxtInpList
     * @param queryID
     */
    public Answer(String queryID, ArrayList<T> answerTxtInpList) {
        this.queryID = queryID;
        this.answerList = answerTxtInpList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(queryID).append(answerList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (! (other instanceof Answer)) {
            return false;
        }
        Answer rhs = ((Answer) other);
        return new EqualsBuilder().append(queryID, rhs.queryID).append(answerList, rhs.answerList).isEquals();
    }

}