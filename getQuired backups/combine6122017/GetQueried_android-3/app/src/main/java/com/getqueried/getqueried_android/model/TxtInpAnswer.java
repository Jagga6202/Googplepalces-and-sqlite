package com.getqueried.getqueried_android.model;

import java.io.Serializable;

public class TxtInpAnswer implements Serializable {

    private String answerText;
    private String answerTime;
    private String userID;

    /**
     * No args constructor for use in serialization
     */
    public TxtInpAnswer() {
    }

    /**
     * @param userID
     * @param answerTime
     * @param answerText
     */
    public TxtInpAnswer(String answerText, String answerTime, String userID) {
        this.answerText = answerText;
        this.answerTime = answerTime;
        this.userID = userID;
    }

    /**
     * @return The answerText
     */
    public String getAnswerText() {
        return answerText;
    }

    /**
     * @param answerText The answerText
     */
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    /**
     * @return The answerTime
     */
    public String getAnswerTime() {
        return answerTime;
    }

    /**
     * @param answerTime The answerTime
     */
    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    /**
     * @return The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

}