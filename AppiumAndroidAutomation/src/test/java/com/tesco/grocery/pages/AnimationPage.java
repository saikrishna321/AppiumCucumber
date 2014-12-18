package com.tesco.grocery.pages;

import static com.tesco.util.Helpers.find;

/** Page object for the animation page **/
public abstract class AnimationPage {

    /** Verify the animation page has loaded **/
    public static void loaded() {
        find("Bouncing Balls");
    }
}