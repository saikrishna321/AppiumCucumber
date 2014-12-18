package com.tesco.grocery.pages;

import org.openqa.selenium.By;

import com.tesco.util.Appium;
import com.tesco.util.CommonId;
import static com.tesco.util.Helpers.*;

public class DashBoardPage {

	public void login() {
		find("Sign in").click();
		Appium.wait(By.name(CommonId.username));
		touchElement(CommonId.username).sendKeys("auto2@gmail.com");
		touchElement(CommonId.password).sendKeys("Tesco@123");
		touchElement(CommonId.signin).click();
		waitInvisible(for_find(CommonId.loadingprogress));
	}

	public void open_side_nav() {
		waitAll(for_find(CommonId.LHN));
		find(CommonId.LHN).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
