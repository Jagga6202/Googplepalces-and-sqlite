package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class CreateQueryRandom {

    public String queryTitle;
    public Boolean anonymous;
    public String target;
    public Options options;
    public List<TxtInpQuery> questionList = new ArrayList<TxtInpQuery>();

    /**
     * No args constructor for use in serialization
     */
    public CreateQueryRandom() {
    }

    /**
     * @param anonymous
     * @param queryTitle
     * @param questionList
     * @param target
     * @param options
     */
    public CreateQueryRandom(String queryTitle, Boolean anonymous, String target, Options options, List<TxtInpQuery> questionList) {
        this.queryTitle = queryTitle;
        this.anonymous = anonymous;
        this.target = target;
        this.options = options;
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
        if ((other instanceof CreateQueryRandom) == false) {
            return false;
        }
        CreateQueryRandom rhs = ((CreateQueryRandom) other);
        return new EqualsBuilder().append(queryTitle, rhs.queryTitle).append(anonymous, rhs.anonymous).append(target, rhs.target).append(options, rhs.options).append(questionList, rhs.questionList).isEquals();
    }

}