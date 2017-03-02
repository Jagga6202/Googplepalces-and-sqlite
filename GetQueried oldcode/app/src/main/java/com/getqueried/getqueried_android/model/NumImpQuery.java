package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NumImpQuery {

    private String questionType;
    private String text;
    private Boolean hasImage;
    private Integer choiceMin;
    private Integer choiceMax;

    /**
     * No args constructor for use in serialization
     */
    public NumImpQuery() {
    }

    /**
     * @param choiceMax
     * @param hasImage
     * @param text
     * @param questionType
     * @param choiceMin
     */
    public NumImpQuery(String questionType, String text, Boolean hasImage, Integer choiceMin, Integer choiceMax) {
        this.questionType = questionType;
        this.text = text;
        this.hasImage = hasImage;
        this.choiceMin = choiceMin;
        this.choiceMax = choiceMax;
    }

    /**
     * @return The questionType
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * @param questionType The questionType
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The hasImage
     */
    public Boolean getHasImage() {
        return hasImage;
    }

    /**
     * @param hasImage The hasImage
     */
    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    /**
     * @return The choiceMin
     */
    public Integer getChoiceMin() {
        return choiceMin;
    }

    /**
     * @param choiceMin The choiceMin
     */
    public void setChoiceMin(Integer choiceMin) {
        this.choiceMin = choiceMin;
    }

    /**
     * @return The choiceMax
     */
    public Integer getChoiceMax() {
        return choiceMax;
    }

    /**
     * @param choiceMax The choiceMax
     */
    public void setChoiceMax(Integer choiceMax) {
        this.choiceMax = choiceMax;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(questionType).append(text).append(hasImage).append(choiceMin).append(choiceMax).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NumImpQuery) == false) {
            return false;
        }
        NumImpQuery rhs = ((NumImpQuery) other);
        return new EqualsBuilder().append(questionType, rhs.questionType).append(text, rhs.text).append(hasImage, rhs.hasImage).append(choiceMin, rhs.choiceMin).append(choiceMax, rhs.choiceMax).isEquals();
    }

}