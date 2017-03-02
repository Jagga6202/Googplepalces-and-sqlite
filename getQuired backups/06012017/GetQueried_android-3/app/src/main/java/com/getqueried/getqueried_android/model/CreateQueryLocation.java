package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONArray;

public class CreateQueryLocation {

    private String queryTitle;
    private Boolean anonymous;
    private String target;
    private LocationOptions options;
    private JSONArray questionList = new JSONArray();

    /**
     * No args constructor for use in serialization
     */
    public CreateQueryLocation() {
    }

    /**
     * @param anonymous
     * @param queryTitle
     * @param questionList
     * @param target
     * @param options
     */
    public CreateQueryLocation(String queryTitle, Boolean anonymous, String target, LocationOptions options, JSONArray questionList) {
        this.queryTitle = queryTitle;
        this.anonymous = anonymous;
        this.target = target;
        this.options = options;
        this.questionList = questionList;
    }

    /**
     * @return The queryTitle
     */
    public String getQueryTitle() {
        return queryTitle;
    }

    /**
     * @param queryTitle The queryTitle
     */
    public void setQueryTitle(String queryTitle) {
        this.queryTitle = queryTitle;
    }

    /**
     * @return The anonymous
     */
    public Boolean getAnonymous() {
        return anonymous;
    }

    /**
     * @param anonymous The anonymous
     */
    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * @return The target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target The target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return The options
     */
    public LocationOptions getOptions() {
        return options;
    }

    /**
     * @param options The options
     */
    public void setOptions(LocationOptions options) {
        this.options = options;
    }

    /**
     * @return The questionList
     */
    public JSONArray getQuestionList() {
        return questionList;
    }

    /**
     * @param questionList The questionList
     */
    public void setQuestionList(JSONArray questionList) {
        this.questionList = questionList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(queryTitle).append(anonymous).append(target).append(options).append(questionList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CreateQueryLocation) == false) {
            return false;
        }
        CreateQueryLocation rhs = ((CreateQueryLocation) other);
        return new EqualsBuilder().append(queryTitle, rhs.queryTitle).append(anonymous, rhs.anonymous).append(target, rhs.target).append(options, rhs.options).append(questionList, rhs.questionList).isEquals();
    }

}