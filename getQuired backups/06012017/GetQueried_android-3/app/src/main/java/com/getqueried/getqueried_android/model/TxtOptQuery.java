package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class TxtOptQuery {

    public String type;
    public String questionText;
    public Boolean questionImage;
    public String sliderType;
    public Integer choiceMin;
    public Integer choiceMax;
    public List<AnswerOptionList> answerOptionList = new ArrayList<AnswerOptionList>();

    /**
     * No args constructor for use in serialization
     */
    public TxtOptQuery() {
    }

    /**
     * @param sliderType
     * @param answerOptionList
     * @param choiceMax
     * @param questionText
     * @param questionImage
     * @param type
     * @param choiceMin
     */
    public TxtOptQuery(String type, String questionText, Boolean questionImage, String sliderType, Integer choiceMin, Integer choiceMax, List<AnswerOptionList> answerOptionList) {
        this.type = type;
        this.questionText = questionText;
        this.questionImage = questionImage;
        this.sliderType = sliderType;
        this.choiceMin = choiceMin;
        this.choiceMax = choiceMax;
        this.answerOptionList = answerOptionList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(questionText).append(questionImage).append(sliderType).append(choiceMin).append(choiceMax).append(answerOptionList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TxtOptQuery) == false) {
            return false;
        }
        TxtOptQuery rhs = ((TxtOptQuery) other);
        return new EqualsBuilder().append(type, rhs.type).append(questionText, rhs.questionText).append(questionImage, rhs.questionImage).append(sliderType, rhs.sliderType).append(choiceMin, rhs.choiceMin).append(choiceMax, rhs.choiceMax).append(answerOptionList, rhs.answerOptionList).isEquals();
    }

}