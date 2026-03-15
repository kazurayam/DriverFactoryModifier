package com.kazurayam.ks

import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory

public class DriverFactoryModifier {

	public static boolean runWith(String browser) {
		println "run with " + browser
		return true
	}
}
