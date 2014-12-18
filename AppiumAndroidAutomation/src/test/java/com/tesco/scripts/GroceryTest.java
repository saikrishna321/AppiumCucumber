package com.tesco.scripts;

import com.tesco.util.Helpers;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class GroceryTest extends Helpers {

	@Before
	public void startServer() throws Exception {
		setUp();
	}

	@Given("^i am on application landing page$")
	public void pageTour() throws Exception {
		tour.touchpage();
	}

	@When("^i login to the application if the user is not logged in$")
	public void login() {
		dashboard.login();
		dashboard.open_side_nav();
	}

	@After
	public void killServer() throws Exception {
		tearDown();
	}
}
