package com.tesco.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tesco.grocery.pages.DashBoardPage;
import com.tesco.grocery.pages.HomePage;
import com.tesco.grocery.pages.TourPage;

public class Helpers {

	public static AppiumDriver driver;
	public static URL serverAddress;
	private static WebDriverWait driverWait;

	/** Page object references. Allows using 'home' instead of 'HomePage' **/
	protected HomePage home;

	/** Page object reference. Allows using 'tour' instead of 'TourPage' **/

	protected TourPage tour = new TourPage();

	protected DashBoardPage dashboard = new DashBoardPage();

	Properties CONFIG = null;

	/** Run before each test **/

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

		String userDir = System.getProperty("user.dir");

		String localApp = "Android_Phone_12th_Nov_7_4.apk";

		String appPath = Paths.get(userDir, localApp).toAbsolutePath()
				.toString();
		capabilities.setCapability("app", appPath);
		serverAddress = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AppiumDriver(serverAddress, capabilities);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		init(driver, serverAddress);
	}

	/** Run after each test **/

	public void tearDown() throws Exception {
		if (driver != null)
			driver.quit();
	}

	/**
	 * Initialize the webdriver. Must be called before using any helper methods.
	 * *
	 */
	public static void init(AppiumDriver webDriver, URL driverServerAddress) {
		driver = webDriver;
		serverAddress = driverServerAddress;
		int timeoutInSeconds = 60;
		// must wait at least 60 seconds for running on Sauce.
		// waiting for 30 seconds works locally however it fails on Sauce.
		driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
	}

	/**
	 * Wrap WebElement in MobileElement *
	 */
	private static MobileElement w(WebElement element) {
		return new MobileElement((RemoteWebElement) element, driver);
	}

	/**
	 * Wrap WebElement in MobileElement *
	 */
	private static List<MobileElement> w(List<WebElement> elements) {
		List list = new ArrayList(elements.size());
		for (WebElement element : elements) {
			list.add(w(element));
		}

		return list;
	}

	/**
	 * Set implicit wait in seconds *
	 */
	public static void setWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Return an element by locator *
	 */
	public static MobileElement element(By locator) {
		return w(driver.findElement(locator));
	}

	/**
	 * Return a list of elements by locator *
	 */
	public static List<MobileElement> elements(By locator) {
		return w(driver.findElements(locator));
	}

	/**
	 * Press the back button *
	 */
	public static void back() {
		driver.navigate().back();
	}

	/**
	 * Return a list of elements by tag name *
	 */
	public static List<MobileElement> tags(String tagName) {
		return elements(for_tags(tagName));
	}

	/**
	 * Return a tag name locator *
	 */
	public static By for_tags(String tagName) {
		return By.className(tagName);
	}

	/**
	 * Return a static text element by xpath index *
	 */
	public static MobileElement s_text(int xpathIndex) {
		return element(for_text(xpathIndex));
	}

	/**
	 * Return a static text locator by xpath index *
	 */
	public static By for_text(int xpathIndex) {
		return By.xpath("//android.widget.TextView[" + xpathIndex + "]");
	}

	/**
	 * Return a static text element that contains text *
	 */
	public static MobileElement text(String text) {
		return element(for_text(text));
	}

	/**
	 * Return a static text locator that contains text *
	 */
	public static By for_text(String text) {
		return By.xpath("//*[contains(@text, '" + text + "')]");
	}

	/**
	 * Return a static text element by exact text *
	 */
	public static MobileElement text_exact(String text) {
		return element(for_text_exact(text));
	}

	/**
	 * Return a static text locator by exact text *
	 */
	public static By for_text_exact(String text) {
		return By.xpath("//*[@text='" + text + "']");
	}

	public static By for_find(String value) {
		return By.xpath("//*[@content-desc=\"" + value
				+ "\" or @resource-id=\"" + value + "\" or @text=\"" + value
				+ "\"] | //*[contains(translate(@content-desc,\"" + value
				+ "\",\"" + value + "\"), \"" + value
				+ "\") or contains(translate(@text,\"" + value + "\",\""
				+ value + "\"), \"" + value + "\") or @resource-id=\"" + value
				+ "\"]");
	}

	public static MobileElement find(String value) {
		return element(for_find(value));
	}

	/**
	 * Wait 30 seconds for locator to find an element *
	 */
	public static MobileElement wait(By locator) {
		return w(driverWait.until(ExpectedConditions
				.visibilityOfElementLocated(locator)));
	}

	/**
	 * Wait 60 seconds for locator to find all elements *
	 */
	public static List<MobileElement> waitAll(By locator) {
		return w(driverWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(locator)));
	}

	/**
	 * Wait 60 seconds for locator to not find a visible element *
	 */
	public static boolean waitInvisible(By locator) {
		return driverWait.until(ExpectedConditions
				.invisibilityOfElementLocated(locator));
	}

	/**
	 * Return an element that contains name or text *
	 */
	public static MobileElement scroll_to(String value) {
		return driver.scrollTo(value);
	}

	/**
	 * Return an element that exactly matches name or text *
	 */
	public static MobileElement scroll_to_exact(String value) {
		return driver.scrollToExact(value);
	}

	/**
	 * Returns True if element is present
	 */
	public static boolean isElementPresent(final By by)
			throws InterruptedException {
		boolean isPresent = true;
		wait(by);
		// search for elements and check if list is empty
		if (driver.findElements(by).isEmpty()) {
			isPresent = false;
		}
		// rise back implicitly wait time
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return isPresent;
	}

	/**
	 * 
	 */
	public static WebElement touchElement(String value) {
		return driver.findElement(for_find(value));
	}

}