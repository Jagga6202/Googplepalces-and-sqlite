package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AnswerOptionList {

    public Boolean answerOptionImage;
    public String answerOptionText;

    /**
     * No args constructor for use in serialization
     */
    public AnswerOptionList() {
    }

    /**
     * @param answerOptionText
     * @param answerOptionImage
     */
    public AnswerOptionList(Boolean answerOptionImage, String answerOptionText) {
        this.answerOptionImage = answerOptionImage;
        this.answerOptionText = answerOptionText;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(answerOptionImage).append(answerOptionText).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AnswerOptionList) == false) {
            return false;
        }
        AnswerOptionList rhs = ((AnswerOptionList) other);
        return new EqualsBuilder().append(answerOptionImage, rhs.answerOptionImage).append(answerOptionText, rhs.answerOptionText).isEquals();
    }

}