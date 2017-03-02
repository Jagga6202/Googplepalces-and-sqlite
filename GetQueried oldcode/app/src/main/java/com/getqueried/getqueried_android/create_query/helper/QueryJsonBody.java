package com.getqueried.getqueried_android.create_query.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class QueryJsonBody {

    @SerializedName("jsonBody")
    @Expose
    private String key;

    public JSONObject getValue() {
        return value;
    }

    public void setValue(JSONObject value) {
        this.value = value;
    }

    @SerializedName("value")
    @Expose
    private JSONObject value;

    /**
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    public void setKey(String key) {
        this.key = key;
    }

}