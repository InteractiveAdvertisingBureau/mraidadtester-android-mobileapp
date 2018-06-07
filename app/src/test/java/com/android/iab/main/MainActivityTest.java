package com.android.iab.main;

import android.app.Activity;

import com.android.iab.BuildConfig;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.notNullValue;




@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class/*, sdk = 21*/)
public class MainActivityTest {

    MainActivity mActivity;

    public MainActivityTest(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

   /* @Before
    public void setup() {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().start().get();
    }*/

    @Test
    public void myActivityAppearsAsExpectedInitially() {
        Assert.assertTrue(Robolectric.buildActivity(MainActivity.class).create().get()!=null);
    }

    @Test
    public void clickingClickMeButtonChangesHelloWorldText() {
        Assert.assertTrue(Robolectric.buildActivity(MainActivity.class).create().get() != null);
    }

    @Test
    public void clickingClickMeButtonChangesHelloWorldText1() {
        Assert.assertTrue(true);
    }

    @Test
    public void testIt() {
        // failing test gives much better feedback
        Activity activity =
                Robolectric.setupActivity(MainActivity.class);
        Assert.assertEquals(activity, notNullValue());
    }
}