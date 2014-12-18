package com.tesco.scripts;

import com.tesco.util.Appium;

import static com.tesco.util.Helpers.back;

public class PageObjectPatternTest extends Appium {

    @org.junit.Test
    public void pageObject() throws Exception {
        home.accessibilityClick();
        back();

        home.animationClick();
        back();
    }
}