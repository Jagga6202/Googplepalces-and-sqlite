package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

import java.util.ArrayList;

public class RatSliQuery {

    private String questionType;
    private String text;
    private Boolean hasImage;
    private String choiceMin;
    private String choiceMax;

    public ArrayList<JSONObject> getAnswerOptionList() {
        return answerOptionList;
    }

    public void setAnswerOptionList(ArrayList<JSONObject> answerOptionList) {
        this.answerOptionList = answerOptionList;
    }

    private ArrayList<JSONObject> answerOptionList;

    /**
     * No args constructor for use in serialization
     */
    public RatSliQuery() {
    }

    /**
     * @param choiceMax
     * @param hasImage
     * @param text
     * @param questionType
     * @param choiceMin
     */
    public RatSliQuery(String questionType, String text, Boolean hasImage, String choiceMin, String choiceMax,ArrayList<JSONObject> answerOptionList) {
        this.questionType = questionType;
        this.text = text;
        this.hasImage = hasImage;
        this.choiceMin = choiceMin;
        this.choiceMax = choiceMax;
        this.answerOptionList=answerOptionList;
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
    public String getChoiceMin() {
        return choiceMin;
    }

    /**
     * @param choiceMin The choiceMin
     */
    public void setChoiceMin(String choiceMin) {
        this.choiceMin = choiceMin;
    }

    /**
     * @return The choiceMax
     */
    public String getChoiceMax() {
        return choiceMax;
    }

    /**
     * @param choiceMax The choiceMax
     */
    public void setChoiceMax(String choiceMax) {
        this.choiceMax = choiceMax;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(questionType).append(text).append(hasImage).append(choiceMin).append(choiceMax).append(answerOptionList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RatSliQuery) == false) {
            return false;
        }
        RatSliQuery rhs = ((RatSliQuery) other);
        return new EqualsBuilder().append(questionType, rhs.questionType).append(text, rhs.text).append(hasImage, rhs.hasImage).append(choiceMin, rhs.choiceMin).append(choiceMax, rhs.choiceMax).append(answerOptionList,rhs.answerOptionList).isEquals();
    }

}