package com.test.flipkart;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.flipkart.utilities.GeneralMethods;
import com.page.classes.HomePage;
import com.page.classes.ItemPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class OrderItem {
DesiredCapabilities capabilities;
	
	ExtentReports report;
	ExtentTest test;
	String startTimeString = null;
	WebDriver driver;
	GeneralMethods gm;
	HomePage hp;
	ItemPage ip;
	
	@BeforeClass
	public void beforeclass() {
		try {
			// Set Properties
			gm = new GeneralMethods();
			long starttime = System.currentTimeMillis();
			startTimeString = String.valueOf(starttime);
			report = new ExtentReports("src/test/resources/reports"+"/FlipkartTestWeb" + startTimeString + "/" + "ExtentReport.html");
			//System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
			//driver = new RemoteWebDriver(new URL("http://192.168.43.228:9515/wd/hub"), DesiredCapabilities.chrome());
			driver = new RemoteWebDriver(new URL("http://jenkins.mydomain:4444/wd/hub"), DesiredCapabilities.chrome());
			driver.manage().window().maximize();
			hp = new HomePage(driver);
			ip = new ItemPage(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public void afterclass() {
		try {
			report.flush();
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void openFlipkart() {
		try {
			test = report.startTest("Open Flipkart");
			driver.get("https://www.flipkart.com");
			hp.closeLoginPopup();
			test.log(LogStatus.PASS, "Opened Flipkart");
			test.log(LogStatus.PASS, "Snapshot beside", test.addScreenCapture(
					gm.captureScreenShot("openFlipkart", "FlipkartTestWeb" + startTimeString,driver)));
		} catch(Exception e) {
			test.log(LogStatus.FAIL, "Could not open Flipkart",e);
		} finally {
			report.endTest(test);
		}
	}
	
	@Test
	public void searchItem() {
		try {
			test = report.startTest("Search Item");
			String item = "Android";
			int itemNumber = 1;
			hp.searchItem(item);
			test.log(LogStatus.PASS, "Searched "+item);
			test.log(LogStatus.PASS, "Snapshot beside", test.addScreenCapture(
					gm.captureScreenShot("searchItem", "FlipkartTestWeb" + startTimeString,driver)));
			String currentWindow = driver.getWindowHandle();
			Thread.sleep(2000);
			hp.openItem(itemNumber);
			Thread.sleep(2000);
			gm.switchToLastTab(driver);
			test.log(LogStatus.PASS, "Opened item");
			test.log(LogStatus.PASS, "Snapshot beside", test.addScreenCapture(
					gm.captureScreenShot("searchItem", "FlipkartTestWeb" + startTimeString,driver)));
			Thread.sleep(2000);
			System.out.println(driver.getTitle());
		} catch(Exception e) {
			test.log(LogStatus.FAIL, "Could not open Flipkart",e);
		} finally {
			report.endTest(test);
		}
	}
	
	@Test
	public void buyItem() {
		try {
			test = report.startTest("Buy Item");
			String mobileNumber ="9804100193";
			Thread.sleep(2000);
			ip.addToCart(driver);
			test.log(LogStatus.PASS, "Added to cart");
			test.log(LogStatus.PASS, "Snapshot beside", test.addScreenCapture(
					gm.captureScreenShot("buyItem", "FlipkartTestWeb" + startTimeString,driver)));
			ip.clickPlaceOrderButton();
			test.log(LogStatus.PASS, "Clicked on place order button");
			test.log(LogStatus.PASS, "Snapshot beside", test.addScreenCapture(
					gm.captureScreenShot("buyItem", "FlipkartTestWeb" + startTimeString,driver)));
			ip.enterMobileNumber(mobileNumber);
			test.log(LogStatus.PASS, "Entered MobileNumber");
			test.log(LogStatus.PASS, "Snapshot beside", test.addScreenCapture(
					gm.captureScreenShot("buyItem", "FlipkartTestWeb" + startTimeString,driver)));
			Thread.sleep(7000);
		} catch(Exception e) {
			test.log(LogStatus.FAIL, "Could not open Flipkart",e);
		} finally {
			report.endTest(test);
		}
	}
	
	

	
}
