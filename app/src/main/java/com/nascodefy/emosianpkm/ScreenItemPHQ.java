package com.nascodefy.emosianpkm;

import android.widget.RadioGroup;

public class ScreenItemPHQ {
    String Question;
    RadioGroup radioGroup;

    public ScreenItemPHQ(String question) {
        Question = question;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}
