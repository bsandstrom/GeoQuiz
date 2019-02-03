package com.brentsandstrom.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.brentsandstrom.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.brentsandstrom.geoquiz.answer_shown";

    private static final String KEY_ANSWER_SHOWN = "answer_shown";

    private boolean mAnswerIsTrue;
    private boolean mAnswerShown;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the UI to the cheat activity xml
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerShown = false;
        if (savedInstanceState != null) {
            mAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false);
            setAnswerShownResult(mAnswerShown);
        }

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mAnswerShown = true;
                setAnswerShownResult(true);
            }
        });

    }

    // Override this, so that we can save the instance state. For example, if the user rotates the
    // screen, this will be called. The values we care about will be added to the savedInstanceState,
    // the activity destroyed and the savedInstanceState passed to the onCreate method where we can
    // take the values out of the savedInstanceState and store them.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ANSWER_SHOWN, mAnswerShown);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
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
