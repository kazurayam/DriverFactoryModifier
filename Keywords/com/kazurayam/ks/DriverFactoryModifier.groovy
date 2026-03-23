package com.kazurayam.ks

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration;
import com.kms.katalon.core.driver.IDriverType;
import com.kms.katalon.core.exception.StepFailedException;
import com.kms.katalon.core.logging.LogLevel;
import com.kms.katalon.core.webui.driver.DriverFactory;
import com.kms.katalon.core.webui.driver.WebMobileDriverFactory;
import com.kms.katalon.core.webui.driver.WebUIDriverType

/**
 * Modify the `com.kms.katalon.core.webui.driver.DriverFactory` class 
 * dynamically using Groovy's Meta-programming technique.
 */
public class DriverFactoryModifier {
	
	/**
	 * Will modify DriverFactory.openWebDriver() method dynamically so that it opens a browser 
	 * of the driver type specified.
	 * 
	 * @param driverName one of "Chrome", "Chrome (headless)", "Firefox", "Firefox (headless)", "Edge Chromium"
	 */
	@Keyword
	public static void apply(String driverName) {
		Objects.requireNonNull(driverName)
		WebUIDriverTypeModifier.apply()
		if (WebUIDriverType.isDefinedDriverName(driverName)) {
			WebUIDriverType driverType = WebUIDriverType.valueOfByDriverName(driverName)
			apply(driverType)
		} else {
			throw new IllegalArgumentException(driverName + " is not a valid WebUIDriverType")
		}
	}

	/**
	 * Will modify DriverFactory.openWebDriver() method dynamically so that it opens a browser 
	 * of the driver type specified.
	 * 
	 * @param driverType 
	 */
	public static void apply(WebUIDriverType driverType) {
		Objects.requireNonNull(driverType)
		//
		DriverFactory.metaClass.'static'.openWebDriver = { ->
			/* the following closure code is just the same as the original. No difference. 
			 * However I needed to create this meta method.
			 * Why? 
			 * I want to let the DriverFactory#openWebDriver() to call the meta method 
			 * getExecutedBrowser() defined bellow. In order to do that, I needed to make 
			 * this meta method openWebDriver(). Unless I create the meta method openWebDriver(),
			 * then the normal openWebDriver() will never call the meta method getExecutedBrowser().
			 * I found this behavior by experiment. No document explains this. 
			 * It is very strange. But this is given. I just accept it.
			 */
			try {
				WebDriver webDriver;
				if (DriverFactory.isUsingExistingDriver()) {
					webDriver = DriverFactory.startExistingBrowser();
				} else {
					String remoteWebDriverUrl = DriverFactory.getRemoteWebDriverServerUrl();
					if (StringUtils.isNotEmpty(remoteWebDriverUrl)) {
						webDriver = DriverFactory.startRemoteBrowser();
					} else {
						webDriver = DriverFactory.startNewBrowser(DriverFactory.getExecutedBrowser());
					}
	
					DriverFactory.saveWebDriverSessionData(webDriver);
					DriverFactory.changeWebDriver(webDriver);
				}
	
				return webDriver;
			} catch (Error e) {
				DriverFactory.logger.logMessage(LogLevel.WARNING, e.getMessage(), e);
				throw new StepFailedException(e);
			}
		}

		//
		DriverFactory.metaClass.'static'.getExecutedBrowser = { ->
			IDriverType webDriverType = null;
			if (DriverFactory.isUsingExistingDriver()) {
				webDriverType = WebUIDriverType.fromStringValue(RunConfiguration.getExistingSessionDriverType());
			}
	
			if (webDriverType != null) {
				return webDriverType;
			}
	
			String remoteWebDriverUrl = DriverFactory.getRemoteWebDriverServerUrl();
			
			String driverConnectorProperty = null;
			String driverTypeString = null
			if (StringUtils.isNotBlank(remoteWebDriverUrl)) {
				driverConnectorProperty = RunConfiguration.REMOTE_DRIVER_PROPERTY  // Remote
				driverTypeString = RunConfiguration.getDriverSystemProperty(driverConnectorProperty, DriverFactory.EXECUTED_BROWSER_PROPERTY)
			} else {
				driverConnectorProperty = DriverFactory.WEB_UI_DRIVER_PROPERTY  // WebUI
				/* 
				 * Here is a great hack!
				 * Will force `DriverFactory.openWebDriver()` to disregard the browser type selected in the GUI.
				 * Will tell `DriverFactory.openWebDriver()` to open the browser type specified by `DriverFactoryModifier.apply(driverType)`.
				 */
				//driverTypeString = RunConfiguration.getDriverSystemProperty(driverConnectorProperty, DriverFactory.EXECUTED_BROWSER_PROPERTY)
				driverTypeString = driverType.name()
			}
			if (driverTypeString != null) {
				webDriverType = WebUIDriverType.valueOf(driverTypeString);
			}
	
			if (webDriverType == null && RunConfiguration.getDriverSystemProperty(DriverFactory.MOBILE_DRIVER_PROPERTY,
					WebMobileDriverFactory.EXECUTED_MOBILE_PLATFORM) != null) {
				webDriverType = WebUIDriverType.valueOf(RunConfiguration.getDriverSystemProperty(DriverFactory.MOBILE_DRIVER_PROPERTY,
						WebMobileDriverFactory.EXECUTED_MOBILE_PLATFORM));
			}
	
			return webDriverType;
		}
	}
}
