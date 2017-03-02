package com.getqueried.getqueried_android.model;

import java.io.Serializable;

/**
 * Created by just_me on 06.11.16.
 */
public class AnswerOptionItem implements Serializable {
    public String answerOptionImage;
    public String answerOptionText;

    public AnswerOptionItem(String answerOptionImage, String answerOptionText) {
        this.answerOptionImage = answerOptionImage;
        this.answerOptionText = answerOptionText;
    }

    public AnswerOptionItem() {
        answerOptionImage = "";
        answerOptionText = "";
    }
}
