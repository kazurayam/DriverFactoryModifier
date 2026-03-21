import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.constants.StringConstants;
import com.kms.katalon.core.webui.driver.DriverFactory

def executedBrowser = RunConfiguration.getDriverSystemProperty(DriverFactory.WEB_UI_DRIVER_PROPERTY, DriverFactory.EXECUTED_BROWSER_PROPERTY)
def chromeDriverPath = RunConfiguration.getDriverSystemProperty(DriverFactory.WEB_UI_DRIVER_PROPERTY, DriverFactory.CHROME_DRIVER_PATH_PROPERTY)
def edgeChromiumDriverPath = RunConfiguration.getDriverSystemProperty(DriverFactory.WEB_UI_DRIVER_PROPERTY, DriverFactory.EDGE_CHROMIUM_DRIVER_PATH_PROPERTY)
def firefoxDriverPath = RunConfiguration.getDriverSystemProperty(DriverFactory.WEB_UI_DRIVER_PROPERTY,StringConstants.CONF_PROPERTY_GECKO_DRIVER_PATH)

println "executedBrowser:        " + executedBrowser
println "chromeDriverPath:       " + chromeDriverPath
println "edgeChromiumDriverPath: " + edgeChromiumDriverPath
println "firefoxDriverPath:      " + firefoxDriverPath