import com.kazurayam.ks.DriverFactoryModifier
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')   // open Chrome broser
WebUI.navigateToUrl('https://www.google.co.jp/chrome/')
WebUI.delay(1)
WebUI.closeBrowser()

DriverFactoryModifier.runWith("Edge Chromium")
WebUI.openBrowser('') // open Edge browser
WebUI.navigateToUrl('https://www.microsoft.com/edge/download/') 
WebUI.delay(1)
WebUI.closeBrowser()

DriverFactoryModifier.runWith("Firefox")
WebUI.openBrowser('') // open Firefox browser
WebUI.navigateToUrl('https://www.firefox.com/') 
WebUI.delay(1)
WebUI.closeBrowser()
