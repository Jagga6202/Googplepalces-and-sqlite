package com.getqueried.getqueried_android.model;

import java.io.Serializable;

/**
 * Created by ideafoundation on 23/12/16.
 */

public class DefaultAnswerItem implements Serializable {
    public String answerOptionNum;
    public String answerOptionImage;
    public String answerOptionText;

    public DefaultAnswerItem(String answerOptionNum,String answerOptionImage, String answerOptionText) {
        this.answerOptionNum=answerOptionNum;
        this.answerOptionImage = answerOptionImage;
        this.answerOptionText = answerOptionText;
    }

    public DefaultAnswerItem() {
        answerOptionNum="";
        answerOptionImage = "";
        answerOptionText = "";

    }
}