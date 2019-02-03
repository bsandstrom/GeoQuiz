package com.brentsandstrom.geoquiz;

/**
 * Created by Brent on 11/3/2018.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;

    private boolean mHasAnswer;
    private boolean mUserAnswer;
    private boolean mUserCheated;

    public Question(int textResId, boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mHasAnswer = false;
        mUserCheated = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public void setUserAnswer(boolean userAnswer) {
        this.mHasAnswer = true;
        this.mUserAnswer = userAnswer;
    }

    public boolean getHasAnswer() {
        return this.mHasAnswer;
    }

    public boolean getUserAnswerCorrect() {
        if(mHasAnswer){
            return mUserAnswer == mAnswerTrue;
        }
        else {
            return false;
        }
    }

    public void userCheated(){
        this.mUserCheated = true;
    }

    public boolean getUserCheated(){
        return this.mUserCheated;
    }
}
