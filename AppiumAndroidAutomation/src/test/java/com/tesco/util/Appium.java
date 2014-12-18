package com.tesco.util;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import com.tesco.grocery.pages.DashBoardPage;
import com.tesco.grocery.pages.HomePage;
import com.tesco.grocery.pages.TourPage;

import static com.tesco.util.Helpers.driver;
public class Appium implements SauceOnDemandSessionIdProvider {

	static {
		// Disable annoying cookie warnings.
		// WARNING: Invalid cookie header
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
	}

	/** Page object references. Allows using 'home' instead of 'HomePage' **/
	protected HomePage home;

	/** Page object reference. Allows using 'tour' instead of 'TourPage' **/

	protected TourPage tour = new TourPage();

	protected DashBoardPage dashboard = new DashBoardPage();

	Properties CONFIG = null;

	/** wait wraps Helpers.wait **/
	public static WebElement wait(By locator) {
		return Helpers.wait(locator);
	}

	private boolean runOnSauce = System.getProperty("sauce") != null;

	/**
	 * Authenticate to Sauce with environment variables SAUCE_USER_NAME and
	 * SAUCE_API_KEY
	 **/
	private SauceOnDemandAuthentication auth = new SauceOnDemandAuthentication();

	/** Report pass/fail to Sauce Labs **/
	// false to silence Sauce connect messages.
	public @Rule SauceOnDemandTestWatcher reportToSauce = new SauceOnDemandTestWatcher(
			this, auth, false);

	@Rule
	public TestRule printTests = new TestWatcher() {
		protected void starting(Description description) {
			System.out.print("  test: " + description.getMethodName());
		}

		protected void finished(Description description) {
			final String session = getSessionId();

			if (session != null) {
				System.out.println(" " + "https://saucelabs.com/tests/"
						+ session);
			} else {
				System.out.println();
			}
		}
	};

	private String sessionId;

	/** Keep the same date prefix to identify job sets. **/
	private static Date date = new Date();

	/** Run before each test **/
	@Before
	public void setUp() throws Exception {
		// Initialize CONFIG
		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(
				"src/test/java/com/tesco/config/env.properties");
		CONFIG.load(fs);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("appium-version",
				CONFIG.getProperty("appiumVersionEnv"));
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformVersion", "4.4");

		// Set job name on Sauce Labs
		capabilities.setCapability("name", "Java Android tutorial " + date);
		String userDir = System.getProperty("user.dir");

		URL serverAddress;
		String localApp = "Android_Phone_12th_Nov_7_4.apk";
		if (runOnSauce) {
			String user = auth.getUsername();
			String key = auth.getAccessKey();

			// Upload app to Sauce Labs
			SauceREST rest = new SauceREST(user, key);

			rest.uploadFile(new File(userDir, localApp), localApp);

			capabilities.setCapability("app", "sauce-storage:" + localApp);
			serverAddress = new URL("http://" + user + ":" + key
					+ "@ondemand.saucelabs.com:80/wd/hub");
			driver = new AppiumDriver(serverAddress, capabilities);
		} else {
			String appPath = Paths.get(userDir, localApp).toAbsolutePath()
					.toString();
			capabilities.setCapability("app", appPath);
			serverAddress = new URL("http://127.0.0.1:4723/wd/hub");
			driver = new AppiumDriver(serverAddress, capabilities);
		}

		sessionId = driver.getSessionId().toString();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Helpers.init(driver, serverAddress);
	}

	/** Run after each test **/
	@After
	public void tearDown() throws Exception {
		if (driver != null)
			driver.quit();
	}

	/**
	 * If we're not on Sauce then return null otherwise SauceOnDemandTestWatcher
	 * will error.
	 **/
	public String getSessionId() {
		return runOnSauce ? sessionId : null;
	}
}