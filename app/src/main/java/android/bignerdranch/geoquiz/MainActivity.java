package android.bignerdranch.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_BUTTON = "button";
    private static final String KEY_CHEATER = "cheater";
    private static final String KEY_REMAIN = "remain";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private int mCountRemainingCheat = 3;
    private boolean mIsCheater = false;
    private boolean mIsButtonsEnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mIsButtonsEnable = savedInstanceState.getBoolean(KEY_BUTTON);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER);
            mCountRemainingCheat = savedInstanceState.getInt(KEY_REMAIN);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsButtonsEnable = true;
                mIsCheater = false;
                updateQuestion();
            }
        });
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
                mCountRemainingCheat--;
                if(mCountRemainingCheat == 0) {
                    mCheatButton.setEnabled(false);
                }
            }
        });



        updateQuestion();
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mIsCheater = mQuestionBank[mCurrentIndex].isWasCheat();
        mFalseButton.setEnabled(mIsButtonsEnable);
        mTrueButton.setEnabled(mIsButtonsEnable);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId;
        if (mIsCheater){
            messageResId = R.string.judgment_toast;
        }else{
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        mIsButtonsEnable = false;
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onREsume called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onCPause called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "Leave ");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putInt(KEY_REMAIN,mCountRemainingCheat);
        savedInstanceState.putBoolean(KEY_BUTTON,mIsButtonsEnable);
        savedInstanceState.putBoolean(KEY_CHEATER,mIsCheater);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if (data == null)
            {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShonw(data);
            mQuestionBank[mCurrentIndex].setWasCheat(mIsCheater);
        }
    }
}
