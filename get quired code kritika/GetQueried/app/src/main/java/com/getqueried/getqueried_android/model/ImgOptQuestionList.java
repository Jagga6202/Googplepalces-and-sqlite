package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONArray;

public class ImgOptQuestionList {

    private String questionText;
    private JSONArray answerOptionList;
    private Integer choiceMax;
    private String type;
    private Boolean questionImage;
    private Integer choiceMin;

    /**
     * No args constructor for use in serialization
     */
    public ImgOptQuestionList() {
    }

    /**
     * @param choiceMax
     * @param answerOptionList
     * @param questionText
     * @param questionImage
     * @param type
     * @param choiceMin
     */
    public ImgOptQuestionList(String questionText, JSONArray answerOptionList, Boolean questionImage, Integer choiceMin, Integer choiceMax, String type) {
        this.questionText = questionText;
        this.answerOptionList = answerOptionList;
        this.choiceMax = choiceMax;
        this.type = type;
        this.questionImage = questionImage;
        this.choiceMin = choiceMin;
    }

    /**
     * @return The questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * @param questionText The questionText
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * @return The answerOptionList
     */
    public JSONArray getAnswerOptionList() {
        return answerOptionList;
    }

    /**
     * @param answerOptionList The answerOptionList
     */
    public void setAnswerOptionList(JSONArray answerOptionList) {
        this.answerOptionList = answerOptionList;
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

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The questionImage
     */
    public Boolean getQuestionImage() {
        return questionImage;
    }

    /**
     * @param questionImage The questionImage
     */
    public void setQuestionImage(Boolean questionImage) {
        this.questionImage = questionImage;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(questionText).append(answerOptionList).append(choiceMax).append(type).append(questionImage).append(choiceMin).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ImgOptQuestionList) == false) {
            return false;
        }
        ImgOptQuestionList rhs = ((ImgOptQuestionList) other);
        return new EqualsBuilder().append(questionText, rhs.questionText).append(answerOptionList, rhs.answerOptionList).append(choiceMax, rhs.choiceMax).append(type, rhs.type).append(questionImage, rhs.questionImage).append(choiceMin, rhs.choiceMin).isEquals();
    }

}