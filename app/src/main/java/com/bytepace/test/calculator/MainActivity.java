package com.bytepace.test.calculator;

import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mInputDateEditText;
    private Button mCalculateButton;
    private Button mAddButton;
    private Button mSubtractButton;
    private Button mDivideButton;
    private Button mMultiplyButton;
    private Button mCleanButton;
    private TextView mResultTextView;

    @VisibleForTesting
    public float mFirstValue = 0, mSecondValue = 0;
    @VisibleForTesting
    public Operation mOperation = null;

    public String ADD;
    public String SUBTRACT;
    public String DIVIDE;
    public String MULTIPLY;

    private enum Operation {
        ADD,
        SUBTRACT,
        DIVIDE,
        MULTIPLY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputDateEditText = (EditText) findViewById(R.id.et_input_data);
        mCalculateButton = (Button) findViewById(R.id.btn_calculate);
        mAddButton = (Button) findViewById(R.id.btn_add);
        mSubtractButton = (Button) findViewById(R.id.btn_subtract);
        mDivideButton = (Button) findViewById(R.id.btn_divide);
        mMultiplyButton = (Button) findViewById(R.id.btn_multiply);
        mCleanButton = (Button) findViewById(R.id.btn_clean);
        mResultTextView = (TextView) findViewById(R.id.tv_result);

        mCalculateButton.setOnClickListener(this);
        mAddButton.setOnClickListener(this);
        mSubtractButton.setOnClickListener(this);
        mDivideButton.setOnClickListener(this);
        mMultiplyButton.setOnClickListener(this);
        mCleanButton.setOnClickListener(this);

        ADD = getString(R.string.operation_add);
        SUBTRACT = getString(R.string.operation_add);
        DIVIDE = getString(R.string.operation_add);
        MULTIPLY = getString(R.string.operation_add);
    }

    @Override
    public void onClick(View v) {
        String strValue =  mInputDateEditText.getText().toString();
        if(strValue.equals("") && v.getId() != R.id.btn_clean)
            return;
        else if(v.getId() == R.id.btn_clean) {
            cleanData();
            return;
        }

        float currentValue = Float.valueOf(strValue);
        switch (v.getId()) {
            case R.id.btn_add :
                setValues(currentValue, Operation.ADD);
                break;
            case R.id.btn_subtract :
                setValues(currentValue, Operation.SUBTRACT);
                break;
            case R.id.btn_divide :
                setValues(currentValue, Operation.DIVIDE);
                break;
            case R.id.btn_multiply :
                setValues(currentValue, Operation.MULTIPLY);
                break;
            case R.id.btn_calculate :
                if(mOperation != null) {
                    mSecondValue = currentValue;
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                Thread.sleep(4000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        calculate();
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();
                }
                break;
        }
    }

    private void calculate() {
        switch (mOperation) {
            case ADD:
                setResult(mFirstValue + mSecondValue, ADD);
                break;
            case SUBTRACT:
                setResult(mFirstValue - mSecondValue, SUBTRACT);
                break;
            case DIVIDE:
                setResult(mFirstValue / mSecondValue, DIVIDE);
                break;
            case MULTIPLY:
                setResult(mFirstValue * mSecondValue, MULTIPLY);
                break;
        }
    }

    private void cleanData() {
        mInputDateEditText.setText("");
        mResultTextView.setText("");
        mFirstValue = 0;
        mSecondValue = 0;
        mOperation = null;
    }

    private void setValues(float value, Operation operation) {
        mFirstValue = value;
        mOperation = operation;
        mResultTextView.setText(mFirstValue + " " + getStringFromOperation(operation));
        mInputDateEditText.setText("");
    }

    private void setResult(float result, String operation) {
        mResultTextView.setText(mFirstValue + " " + operation + " " + mSecondValue + " = " + result);
        mInputDateEditText.setText("");
        mOperation = null;
    }

    private String getStringFromOperation(Operation operation) {
        switch (operation) {
            case ADD:
                return ADD;
            case SUBTRACT:
                return SUBTRACT;
            case DIVIDE:
                return DIVIDE;
            case MULTIPLY:
                return MULTIPLY;
            default:
                return "";
        }
    }

    @VisibleForTesting
    public String getTextInputData() {
        return mInputDateEditText.getText().toString();
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
