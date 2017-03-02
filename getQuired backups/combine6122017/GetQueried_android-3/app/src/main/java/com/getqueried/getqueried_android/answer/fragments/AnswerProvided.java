package com.getqueried.getqueried_android.answer.fragments;

/**
 * Created by just_me on 06.11.16.
 */
public interface AnswerProvided {
    void answerStateChanged(boolean isAnswerChosen, int optionNumber, String answersText);
}
