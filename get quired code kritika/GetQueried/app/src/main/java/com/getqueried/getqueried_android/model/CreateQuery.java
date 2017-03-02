package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class CreateQuery {

    public String queryTitle;
    public Boolean anonymous;
    public String target;
    public List<TxtOptQuery> questionList = new ArrayList<TxtOptQuery>();

    /**
     * No args constructor for use in serialization
     */
    public CreateQuery() {
    }

    /**
     * @param anonymous
     * @param queryTitle
     * @param questionList
     * @param target
     */
    public CreateQuery(String queryTitle, Boolean anonymous, String target, List<TxtOptQuery> questionList) {
        this.queryTitle = queryTitle;
        this.anonymous = anonymous;
        this.target = target;
        this.questionList = questionList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(queryTitle).append(anonymous).append(target).append(questionList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CreateQuery) == false) {
            return false;
        }
        CreateQuery rhs = ((CreateQuery) other);
        return new EqualsBuilder().append(queryTitle, rhs.queryTitle).append(anonymous, rhs.anonymous).append(target, rhs.target).append(questionList, rhs.questionList).isEquals();
    }
}