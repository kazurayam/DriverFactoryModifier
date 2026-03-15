package com.kazurayam.ks

import com.kms.katalon.core.webui.driver.DriverFactory
import static org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@RunWith(JUnit4.class)
public class DriverFactoryModifierTest {
	
	@Test
	public void testSmoke() {
		boolean b = DriverFactoryModifier.runWith("Chrome")
		assertTrue(b)
		WebUI.openBrowser('')
		WebUI.delay(1)
		WebUI.closeBrowser()
	}
}
