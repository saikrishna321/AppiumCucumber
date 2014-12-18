package com.tesco.grocery.pages;

import org.openqa.selenium.By;

import com.tesco.util.Appium;
import static com.tesco.util.Helpers.*;
public class TourPage {
	public void touchpage() throws InterruptedException {
		final boolean tour_page = isElementPresent(By.name("Next"));
		if (tour_page) {
			loaded();
			find("Next").click();
			Appium.wait(for_text_exact("Ok, got it"));
			find("Ok, got it").click();
		}

	}

	private static void loaded() {
		find("Next");
	}
}
