package com.getqueried.getqueried_android.model;

import java.util.ArrayList;
import java.util.List;

public class AnswerTxtInpList {

    private List<TxtInpAnswer> answersDetailsList = new ArrayList<TxtInpAnswer>();

    /**
     * No args constructor for use in serialization
     */
    public AnswerTxtInpList() {
    }

    /**
     * @param answersDetailsList
     */
    public AnswerTxtInpList(List<TxtInpAnswer> answersDetailsList) {
        this.answersDetailsList = answersDetailsList;
    }

    /**
     * @return The answersDetailsList
     */
    public List<TxtInpAnswer> getAnswersDetailsList() {
        return answersDetailsList;
    }

    /**
     * @param answersDetailsList The answersDetailsList
     */
    public void setAnswersDetailsList(List<TxtInpAnswer> answersDetailsList) {
        this.answersDetailsList = answersDetailsList;
    }

}