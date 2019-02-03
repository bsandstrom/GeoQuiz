package com.brentsandstrom.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.brentsandstrom.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.brentsandstrom.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mApiLevelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mApiLevelTextView = (TextView) findViewById(R.id.api_level_text_view);
        mApiLevelTextView.setText(getString(R.string.api_level, Build.VERSION.SDK_INT));

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);

                // This animation feature is supported in version 21 and up. Only run if supported.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth();
                    int cy = mShowAnswerButton.getHeight();
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }
                else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    /**
     * Create a new intent that will open a CheatActivity.
     *
     * @param packageContext The ActivityContext. Not ApplicationContext. Ex: QuizActivity.this is
     *                       good, getApplicationContext() bad.
     * @param answerIsTrue   The answer to the selected question. Determines what the user sees if
     *                       they cheat.
     * @return The Intent you'll need to start CheatActivity
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    /**
     * After running startActivityForResult on this activity, you'll get an intent
     * that contains a boolean extra that is true if the user cheated. This method
     * gets that boolean from the intent for you.
     *
     * @param result The result of startActivityForResult
     * @return True if the user cheated
     */
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

}
