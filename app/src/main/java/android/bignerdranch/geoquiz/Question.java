package android.bignerdranch.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mWasCheat = false;

    public boolean isWasCheat() {
        return mWasCheat;
    }

    public void setWasCheat(boolean wasCheat) {
        mWasCheat = wasCheat;
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

    public Question(int textResId, boolean answeTrue){
        mTextResId = textResId;
        mAnswerTrue = answeTrue;
    }
}
