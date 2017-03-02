package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Metadata {

    public String userID;
    public String name;
    public String description;
    public String imagePath;
    public String image_path;

    /**
     * No args constructor for use in serialization
     */
    public Metadata() {
    }

    /**
     * @param userID
     * @param imagePath
     * @param description
     * @param name
     * @param image_path
     */
    public Metadata(String userID, String name, String description, String imagePath, String image_path) {
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.image_path = image_path;
    }

    public Metadata(String userID, String name, String description, String imagePath) {
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userID).append(name).append(description).append(imagePath).append(image_path).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metadata) == false) {
            return false;
        }
        Metadata rhs = ((Metadata) other);
        return new EqualsBuilder().append(userID, rhs.userID).append(name, rhs.name).append(description, rhs.description).append(imagePath, rhs.imagePath).append(image_path, rhs.image_path).isEquals();
    }

}

