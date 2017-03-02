
package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryList {

    private List<Query> queries = new ArrayList<Query>();
    private long count;

    /**
     * No args constructor for use in serialization
     */
    public QueryList() {
    }

    /**
     * @param count
     * @param queries
     */
    public QueryList(List<Query> queries, Integer count) {
        this.queries = queries;
        this.count = count;
    }

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The queries
     */
    public List<Query> getQueries() {
        return queries;
    }

    /**
     * @param queries The queries
     */
    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public void addQuery(Query query) {
        if(this.queries != null){
            this.queries.add(query);
        }
    }

    public QueryList withQueries(List<Query> queries) {
        this.queries = queries;
        return this;
    }

    /**
     * @return The count
     */
    public long getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(long count) {
        this.count = count;
    }

    public QueryList withCount(long count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public QueryList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(queries).append(count).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof QueryList) == false) {
            return false;
        }
        QueryList rhs = ((QueryList) other);
        return new EqualsBuilder().append(queries, rhs.queries).append(count, rhs.count).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
