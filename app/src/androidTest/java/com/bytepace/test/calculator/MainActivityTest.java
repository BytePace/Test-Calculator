package com.bytepace.test.calculator;

import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.EditText;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by vikto on 10-Jul-17.
 */
public class MainActivityTest extends Assert {

    private MainTestActivity mMainActivity = null;

    @Rule
    public ActivityTestRule<MainTestActivity> mActivityTestRule =
            new ActivityTestRule<>(MainTestActivity.class);

    @Before
    public void setUp() throws Exception {
        mMainActivity = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mMainActivity = null;
    }

    @Test
    public void testCleanData_input2plus2_allObjectsCleaned() {
        assertNotNull(mMainActivity);

        final Button addButton = mMainActivity.getAddButton();
        final EditText inputDataEditText = mMainActivity.getInputDataEditText();

        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                inputDataEditText.setText(String.valueOf(2));
                addButton.performClick();
                inputDataEditText.setText(String.valueOf(2));
            }
        });

        getInstrumentation().waitForIdleSync();

        assertEquals(inputDataEditText.getText().toString(), "2");
        assertEquals(mMainActivity.getTextResult(), "2.0 +");
        assertTrue(mMainActivity.mFirstValue == 2);
        assertTrue(mMainActivity.mSecondValue == 0);

        final Button cleanButton = mMainActivity.getCleanButton();

        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cleanButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        assertEquals(mMainActivity.getInputDataEditText().getText().toString(), "");
        assertEquals(mMainActivity.getTextResult(), "");
        assertTrue(mMainActivity.mFirstValue == 0);
        assertTrue(mMainActivity.mSecondValue == 0);
    }

    @Test
    public void testCalculate_2plus2_4() throws InterruptedException {
        assertNotNull(mMainActivity);

        final Button addButton = mMainActivity.getAddButton();
        final EditText inputDataEditText = mMainActivity.getInputDataEditText();

        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                inputDataEditText.setText(String.valueOf(2));
                addButton.performClick();
                inputDataEditText.setText(String.valueOf(2));
            }
        });

        getInstrumentation().waitForIdleSync();

        assertEquals(inputDataEditText.getText().toString(), "2");
        assertEquals(mMainActivity.getTextResult(), "2.0 +");
        assertTrue(mMainActivity.mFirstValue == 2);
        assertTrue(mMainActivity.mSecondValue == 0);

        final Button calculateButton = mMainActivity.getCalculateButton();

        final Object syncObject = new Object();

        mMainActivity.setMainActivityCallBack(new MainActivityCallBack() {
            @Override
            public void calculateIsDone() {
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        });

        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calculateButton.performClick();
            }
        });

        synchronized (syncObject) {
            syncObject.wait();
        }

        getInstrumentation().waitForIdleSync();

        assertEquals(mMainActivity.getTextResult(), "2.0 + 2.0 = 4.0");
    }
}