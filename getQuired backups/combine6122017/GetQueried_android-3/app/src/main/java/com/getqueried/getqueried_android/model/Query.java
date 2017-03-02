package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by serverpc on 27/9/16.
 */
public class Query {

    private long id;
    private String question;
    private String description;
    private String distance;
    private String time;
    private String userId;
    private String userName;
    private String avatarPath;


    private String queryPath;
    private String tag;
    private double lat;
    private double lng;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /**
     * No args constructor for use in serialization
     */
    public Query() {
    }

    public Query(String question, String description, String avatarPath,String queryPath) {
        this.question = question;
        this.description = description;
        this.avatarPath = avatarPath;
        this.queryPath=queryPath;
    }

  /*  public Query(String question, String description, String distance, String time,
                 String userName, String avatarPath, String tag,String queryPath) {
        this.question = question;
        this.description = description;
        this.distance = distance;
        this.time = time;
        this.userName = userName;
        this.avatarPath = avatarPath;
        this.tag = tag;
        this.queryPath=queryPath;
    }
*/
    /**
     * @param id
     * @param time
     * @param distance
     * @param tag
     * @param description
     * @param userId
     * @param userName
     * @param lng
     * @param question
     * @param avatarPath
     * @param lat
     */
   /* public Query(Integer id, String question, String description, String distance, String time,
                 String userId, String userName, String avatarPath, String tag, Float lat, Float lng,String queryPath) {
        this.id = id;
        this.question = question;
        this.description = description;
        this.distance = distance;
        this.time = time;
        this.userId = userId;
        this.userName = userName;
        this.avatarPath = avatarPath;
        this.tag = tag;
        this.lat = lat;
        this.lng = lng;
        this.queryPath=queryPath;
    }*/


    /**
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(long id) {
        this.id = id;
    }

    public Query withId(long id) {
        this.id = id;
        return this;
    }
    public String getQueryPath() {
        return queryPath;
    }

    public void setQueryPath(String queryPath) {
        this.queryPath = queryPath;
    }

    /**
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    public Query withQuestion(String question) {
        this.question = question;
        return this;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Query withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Query withDistance(String distance) {
        this.distance = distance;
        return this;
    }

    /**
     * @return The time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    public Query withTime(String time) {
        this.time = time;
        return this;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Query withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * @return The user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Query withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * @return The avatarPath
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * @param avatarPath The avatar_path
     */
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Query withAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
        return this;
    }

    /**
     * @return The tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag The tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    public Query withTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * @return The lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat The lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    public Query withLat(double lat) {
        this.lat = lat;
        return this;
    }

    /**
     * @return The lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * @param lng The lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    public Query withLng(double lng) {
        this.lng = lng;
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

    public Query withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        // return new HashCodeBuilder().append(id).append(question).append(description).append(distance).append(time).append(userId).append(userName).append(avatarPath).append(queryPath).append(tag).append(lat).append(lng).append(additionalProperties).toHashCode();
        return new HashCodeBuilder().append(question).append(description).append(avatarPath).append(queryPath).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Query) == false) {
            return false;
        }
        Query rhs = ((Query) other);
        //return new EqualsBuilder().append(id, rhs.id).append(question, rhs.question).append(description, rhs.description).append(distance, rhs.distance).append(queryPath,rhs.queryPath).append(time, rhs.time).append(userId, rhs.userId).append(userName, rhs.userName).append(avatarPath, rhs.avatarPath).append(tag, rhs.tag).append(lat, rhs.lat).append(lng, rhs.lng).append(additionalProperties, rhs.additionalProperties).isEquals();
        return new EqualsBuilder().append(id, rhs.id).append(question, rhs.question).append(description, rhs.description).append(avatarPath, rhs.avatarPath).append(queryPath,rhs.queryPath).isEquals();
    }
}
