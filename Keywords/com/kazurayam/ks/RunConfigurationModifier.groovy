package com.kazurayam.ks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.constants.StringConstants
import com.kms.katalon.core.webui.driver.DriverFactory

/**
 * This class modifies the RunConfiguration class using Groovy's Metaprogramming techique.
 */
public class RunConfigurationModifier {
	
	public static void apply() {
		String wanted = """{
  "execution" : {
    "drivers" : {
      "system" : {
	    "WebUI" : {
          "broserType": "CHROME_DRIVER,
          "${DriverFactory.CHROME_DRIVER_PATH_PROPERTY}": "${WebDriverPathHelper.getChromeDriverPath().toString()}",
          "${StringConstants.CONF_PROPERTY_GECKO_DRIVER_PATH}": "${WebDriverPathHelper.getFirefoxDriverPath().toString()}",
          "${DriverFactory.EDGE_CHROMIUM_DRIVER_PATH_PROPERTY}": "${WebDriverPathHelper.getEdgeChromiumDriverPath().toString()}"
        }
      }
    }
  }
}
""";
		/*
		 * Katalon Studio generates a 'RunConfiguration' instance with the following information contained for example:
		 * ```
		 * { 
		 *   "execution" : {
		 *     "drivers" : {
		 *       "system" : {
		 *         "WebUI" : {
         *           "broserType": "CHROME_DRIVER,
         *           "chromeDriverPath" : "/Applications/Katalon Studio.app/Contents/Eclipse/configuration/resources/drivers/chromedriver_mac/chromedriver",
         *         }
         *       }
         *     }
		 * ```
		 * 
		 * I found a problem here. Katalon Studio generates a `RunConfiguration` object that contains only one driver path; 
		 * the path of the WebDriver that I selected in the GUI to run the test with. 
		 * If I chose Chrome in the GUI, then the 'RunConfiguration' object does not contain the path information of 
		 * FireFoxDriver and EdgeChromiumDriver. This is a shortage for me (kazurayam).
		 * 
		 * The `RunConfiguration.injectWebDriverPath(String key, String value)` method injects the path information 
		 * of WebDriver. A Test Case script can get the WebDriver's path information 
		 * using the `com.kazurayam.ks.WebDriverPathHelper` class.
		 */
		RunConfiguration.metaClass.'static'.injectWebDriverPath = { String key, String value ->
			Map<String, Object> execution = RunConfiguration.localExecutionSettingMapStorage.get("execution")
			assert execution != null
			Map<String, Object> drivers = execution.get("drivers")
			assert drivers != null
			Map<String, Object> system = drivers.get("system")
			assert system != null
			Map<String, Object> webui = system.get("WebUI")
			assert webui != null
			webui.put(key, value)
		}
		
		/*
		 * The `RunConfiguration.toJson()` returns a JSON string which serialize all of the settings information 
		 * of a project passed from Katalon Studio to user's test scripts. In the output JSON, the keys are sorted
		 * in alphabetical ascending order.
		 */
		RunConfiguration.metaClass.'static'.toJson = { ->
			ObjectMapper om = new ObjectMapper()
			om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
			om.configure(SerializationFeature.INDENT_OUTPUT, true)
			String json = om.writeValueAsString(RunConfiguration.localExecutionSettingMapStorage)
			return json
		}
	}

	/**
	 * inject the path information of the ChromeDriver binary, the FirefoxDriver binary and the EdgeChromiumDriver binary.
	 * This must be called after `RunConfigurationModifier.apply()` has been called.
	 * 
	 */
	@Keyword
	public static void injectWebDriverPaths() {
		RunConfigurationModifier.apply()  // to make sure that the injectWebDriverPath(String,String) method has been added
		RunConfiguration.injectWebDriverPath("${DriverFactory.CHROME_DRIVER_PATH_PROPERTY}", "${WebDriverPathHelper.getChromeDriverPath().toString()}")
		RunConfiguration.injectWebDriverPath("${StringConstants.CONF_PROPERTY_GECKO_DRIVER_PATH}", "${WebDriverPathHelper.getFirefoxDriverPath().toString()}")
		RunConfiguration.injectWebDriverPath("${DriverFactory.EDGE_CHROMIUM_DRIVER_PATH_PROPERTY}", "${WebDriverPathHelper.getEdgeChromiumDriverPath().toString()}")
	}
}
