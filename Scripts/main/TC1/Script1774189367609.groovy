import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.setViewPortSize(800, 400)
WebUI.navigateToUrl('https://example.com/')
WebUI.delay(1)
WebUI.closeBrowser()