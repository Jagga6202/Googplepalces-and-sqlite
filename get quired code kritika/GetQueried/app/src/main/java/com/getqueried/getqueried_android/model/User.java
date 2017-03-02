package com.getqueried.getqueried_android.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

    public static String first_name;
    public static String last_name;
    public static String email;
    public static String fb_id;
    public static String fb_token;
    public static Boolean terms_accepted;
    public static String service_type;

    /**
     * No args constructor for use in serialization
     */
    public User() {
    }

    /**
     * @param fb_id
     * @param first_name
     * @param email
     * @param service_type
     * @param last_name
     * @param terms_accepted
     * @param fb_token
     */
    public User(String first_name, String last_name, String email, String fb_id, String fb_token, Boolean terms_accepted, String service_type) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.fb_id = fb_id;
        this.fb_token = fb_token;
        this.terms_accepted = terms_accepted;
        this.service_type = service_type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(first_name).append(last_name).append(email).append(fb_id).append(fb_token).append(terms_accepted).append(service_type).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
        User rhs = ((User) other);
        return new EqualsBuilder().append(first_name, rhs.first_name).append(last_name, rhs.last_name).append(email, rhs.email).append(fb_id, rhs.fb_id).append(fb_token, rhs.fb_token).append(terms_accepted, rhs.terms_accepted).append(service_type, rhs.service_type).isEquals();
    }

}