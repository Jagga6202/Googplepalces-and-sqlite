package com.getqueried.getqueried_android.model;

import java.io.Serializable;

/**
 * Created by just_me on 05.11.16.
 */
public class ImgOptAnswer implements Serializable {

    String answerOptionImage;
    int numberOfVoted;

    public ImgOptAnswer(String answerOptionImage, int numberOfVoted) {
        this.answerOptionImage = answerOptionImage;
        this.numberOfVoted = numberOfVoted;
    }

    // used when number of voted is not provided
    public ImgOptAnswer(String answerOptionImage) {
        this.answerOptionImage = answerOptionImage;
    }

    public ImgOptAnswer() {
    }

    public String getAnswerOptionImage() {
        return answerOptionImage;
    }

    public int getNumberOfVoted() {
        return numberOfVoted;
    }
}
