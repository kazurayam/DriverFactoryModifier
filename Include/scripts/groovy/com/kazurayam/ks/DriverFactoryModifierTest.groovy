package com.kazurayam.ks

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@RunWith(JUnit4.class)
public class DriverFactoryModifierTest {
	
	@Test
	public void test_runWith() {
		String jsonBefore = new GroovyMetaClassInspector().toJson(DriverFactory.metaClass)
		println "BEFORE:\n" + jsonBefore
		//println DriverFactory.hello("People")
		DriverFactoryModifier.runWith(WebUIDriverType.FIREFOX_DRIVER)
		String jsonAfter = new GroovyMetaClassInspector().toJson(DriverFactory.metaClass)
		println "AFTER:\n" + jsonAfter
		println DriverFactory.hello("World")
	}
	
	/*
	@Test
	public void testFirefox() {
		DriverFactoryModifier.runWith(WebUIDriverType.FIREFOX_DRIVER)
		WebUI.openBrowser('')
		WebUI.delay(1)
		WebUI.closeBrowser()
	}
	
	@Test
	public void testFirefoxHeadless() {
		DriverFactoryModifier.runWith(WebUIDriverType.FIREFOX_HEADLESS_DRIVER)
		WebUI.openBrowser('')
		WebUI.delay(1)
		WebUI.closeBrowser()
	}

	@Test
	public void testChrome() {
		DriverFactoryModifier.runWith(WebUIDriverType.CHROME_DRIVER)
		WebUI.openBrowser('')
		WebUI.delay(1)
		WebUI.closeBrowser()
	}
		 
	 @Test
	 public void testChromeHeadless() {
		 DriverFactoryModifier.runWith(WebUIDriverType.HEADLESS_DRIVER)
		 WebUI.openBrowser('')
		 WebUI.delay(1)
		 WebUI.closeBrowser()
	 }
	 
	 @Test
	 public void testEdgeChromium() {
		 DriverFactoryModifier.runWith(WebUIDriverType.EDGE_CHROMIUM_DRIVER)
		 WebUI.openBrowser('')
		 WebUI.delay(1)
		 WebUI.closeBrowser()
	 }
	 */
}
