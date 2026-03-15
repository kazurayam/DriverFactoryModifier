package com.kazurayam.ks

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.logging.LogLevel;
import com.kms.katalon.core.webui.driver.DriverFactory;
import com.kms.katalon.core.driver.IDriverType;
import com.kms.katalon.core.exception.StepFailedException;

public class DriverFactoryModifier {

	public static boolean runWith(String browser) {
		println "[DriverFactoryModifier#runWith] " + browser
		DriverFactory.metaClass.static.openWebDriver = { ->
			println "[DriverFactory#openWebDriver] "
			/**
			 * Open a new WebDriver based on the RunConfiguration
			 */
			try {
				WebDriver webDriver;
				if (DriverFactory.isUsingExistingDriver()) {
					webDriver = DriverFactory.startExistingBrowser();
				} else {
					String remoteWebDriverUrl = DriverFactory.getRemoteWebDriverServerUrl();
					if (StringUtils.isNotEmpty(remoteWebDriverUrl())) {
						webDriver = DriverFactory.startRemoteBrowser();
					} else {
						IDriverType executedBrowser = DriverFactory.getExecutedBrowser();
						println "[DriverFactory++] executedBrowser: " + executedBrowser.getName();
						webDriver = DriverFactory.startNewBrowser(executedBrowser);
					}
					DriverFactory.saveWebDriverSessionData(webDriver);
					DriverFactory.changeWebDriver(webDriver);
				}
				return webDriver
			} catch (Error e) {
				DriverFactory.logger.logMessage(LogLevel.WARNING, e.getMessage(), e);
				throw new StepFailedException(e);
			}
		}
		println "[DriverFactoryModifier] modified"
		return true
	}
}
