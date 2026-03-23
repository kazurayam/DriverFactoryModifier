import com.kazurayam.ks.DriverFactoryModifier
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// modify the DriverFactory so that WebUI.openBrowser() opens Chrome (headless) browser
DriverFactoryModifier.apply(WebUIDriverType.HEADLESS_DRIVER)

WebUI.comment("DriverFactory is now configured to open  " + WebUIDriverType.HEADLESS_DRIVER)
