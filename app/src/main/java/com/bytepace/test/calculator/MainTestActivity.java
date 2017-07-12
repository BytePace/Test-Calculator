package com.bytepace.test.calculator;

import android.support.annotation.VisibleForTesting;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vikto on 12-Jul-17.
 */

public class MainTestActivity extends MainActivity {

    private MainActivityCallBack mMainActivityCallBack;

    @VisibleForTesting
    public void setMainActivityCallBack(MainActivityCallBack callBack) {
        mMainActivityCallBack = callBack;
    }

    @Override
    protected void calculate() {
        super.calculate();

        if(mMainActivityCallBack != null)
            mMainActivityCallBack.calculateIsDone();
    }

    @VisibleForTesting
    public String getTextResult() {
        return mResultTextView.getText().toString();
    }

    @VisibleForTesting
    public Button getAddButton() {
        return mAddButton;
    }

    @VisibleForTesting
    public EditText getInputDataEditText() {
        return mInputDateEditText;
    }

    @VisibleForTesting
    public Button getCleanButton() {
        return mCleanButton;
    }

    @VisibleForTesting
    public Button getCalculateButton() {
        return mCalculateButton;
    }
}
