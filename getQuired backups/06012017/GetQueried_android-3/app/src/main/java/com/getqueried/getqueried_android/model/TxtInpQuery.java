package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TxtInpQuery {

    public String type;
    public String questionText;
    public Boolean questionImage;

    /**
     * No args constructor for use in serialization
     */
    public TxtInpQuery() {
    }

    /**
     * @param questionText
     * @param questionImage
     * @param type
     */
    public TxtInpQuery(String type, String questionText, Boolean questionImage) {
        this.type = type;
        this.questionText = questionText;
        this.questionImage = questionImage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(questionText).append(questionImage).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TxtInpQuery) == false) {
            return false;
        }
        TxtInpQuery rhs = ((TxtInpQuery) other);
        return new EqualsBuilder().append(type, rhs.type).append(questionText, rhs.questionText).append(questionImage, rhs.questionImage).isEquals();
    }

}