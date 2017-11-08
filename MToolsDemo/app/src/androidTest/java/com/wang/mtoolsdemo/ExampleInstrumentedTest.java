package com.wang.mtoolsdemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.wang.mtoolsdemo.common.util.ChineseCharToEnUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.wang.mtoolsdemo", appContext.getPackageName());
    }

    @Test
    public static void testChineseCharToEnUtil(){
        String actonName = ChineseCharToEnUtil.getAllFirstLetter("ÕÅÈý");
        System.out.println("" + actonName);
    }

    public static void main(String[] args){
        testChineseCharToEnUtil();
    }
}
