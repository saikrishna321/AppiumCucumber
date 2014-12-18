package com.tesco.grocery.pages;

import static com.tesco.util.Helpers.*;
/** Page object for the accessibility page **/
public abstract class AccessibilityPage {

    /** Verify the accessibility page has loaded **/
    public static void loaded() {
        find("Accessibility Node Provider");
    }
}