package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class ImgOptQuery {

    private String queryTitle;
    private LocationOptions options;
    private List<ImgOptQuestionList> imgOptQuestionList = new ArrayList<ImgOptQuestionList>();
    private String target;
    private Boolean anonymous;

    /**
     * No args constructor for use in serialization
     */
    public ImgOptQuery() {
    }

    /**
     * @param anonymous
     * @param queryTitle
     * @param imgOptQuestionList
     * @param target
     * @param options
     */
    public ImgOptQuery(String queryTitle, LocationOptions options, List<ImgOptQuestionList> imgOptQuestionList, String target, Boolean anonymous) {
        this.queryTitle = queryTitle;
        this.options = options;
        this.imgOptQuestionList = imgOptQuestionList;
        this.target = target;
        this.anonymous = anonymous;
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
     * @return The imgOptQuestionList
     */
    public List<ImgOptQuestionList> getImgOptQuestionList() {
        return imgOptQuestionList;
    }

    /**
     * @param imgOptQuestionList The imgOptQuestionList
     */
    public void setImgOptQuestionList(List<ImgOptQuestionList> imgOptQuestionList) {
        this.imgOptQuestionList = imgOptQuestionList;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(queryTitle).append(options).append(imgOptQuestionList).append(target).append(anonymous).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ImgOptQuery) == false) {
            return false;
        }
        ImgOptQuery rhs = ((ImgOptQuery) other);
        return new EqualsBuilder().append(queryTitle, rhs.queryTitle).append(options, rhs.options).append(imgOptQuestionList, rhs.imgOptQuestionList).append(target, rhs.target).append(anonymous, rhs.anonymous).isEquals();
    }

}