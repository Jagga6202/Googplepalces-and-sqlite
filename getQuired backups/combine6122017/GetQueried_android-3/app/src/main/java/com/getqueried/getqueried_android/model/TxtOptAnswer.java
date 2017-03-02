package com.getqueried.getqueried_android.model;

import java.io.Serializable;

/**
 * Created by just_me on 05.11.16.
 */
public class TxtOptAnswer implements Serializable {
    private String optionText;
    private int numberOfVoted;

    public TxtOptAnswer(String optionText, int numberOfVoted) {
        this.optionText = optionText;
        this.numberOfVoted = numberOfVoted;
    }

    public TxtOptAnswer() {
    }

    public String getOptionText() {
        return optionText;
    }

    public int getNumberOfVoted() {
        return numberOfVoted;
    }
}
