import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.ks.RunConfigurationModifier
import com.kazurayam.ks.WebDriverPathHelper
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.constants.StringConstants
import com.kms.katalon.core.webui.driver.DriverFactory

// add a few methods into the RunConfiguraiton object dynamically
RunConfigurationModifier.apply()

// inject the path information of ChromeDriver, FirefoxDriver and EdgeChromiumDriver for WebUI testing into the RunConfiguration
RunConfigurationModifier.injectWebDriverPaths()

// serialize the modified RunConfigurtion into JSON
String json = RunConfiguration.toJson()

// write the JSON into a file for view
Path buildDir = Paths.get(RunConfiguration.getProjectDir()).resolve('build')
Files.createDirectories(buildDir)
Path outFile = buildDir.resolve("execution.properties.json")
outFile.toFile().text = json

