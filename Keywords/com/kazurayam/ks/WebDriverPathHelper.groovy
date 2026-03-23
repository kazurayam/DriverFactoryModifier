package com.kazurayam.ks

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;

import com.kms.katalon.core.webui.driver.DriverFactory;
import com.kms.katalon.core.webui.util.OSUtil;

/**
 * This class enables you to find out the path of WebDriver binaries bundled in the current Katalon Studio installation.
 * 
 */
public class WebDriverPathHelper {

	/**
	 * Only Mac is supported. Still TODO for Windows and Linux.
	 * 
	 * @return the path of ChromeDriver binary in the current Katalon Studio installation
	 */
	public static Path getChromeDriverPath() {
		if (OSUtil.isMac()) {
			// /Applications/Katalon Studio.app/Contents/Eclipse/configuration/resources/drivers/chromedriver_mac
			Path installationDir = getInstallationDir()
			return installationDir.resolve("Contents/Eclipse/configuration/resources/drivers/chromedriver_mac/chromedriver")
		} else {
			throw new UnsupportedOperationException("TODO")
		}
	}
	
	/**
	 * Only Mac is supported. Still TODO for Windows and Linux.
	 * 
	 * @return the path of FirefoxDriver binary in the current Katalon Studio installation
	 */
	public static Path getFirefoxDriverPath() {
		if (OSUtil.isMac()) {
			// firefox_mac/geckodriver
			Path installationDir = getInstallationDir()
			return installationDir.resolve("Contents/Eclipse/configuration/resources/drivers/firefox_mac/geckodriver")
		} else {
			throw new UnsupportedOperationException("TODO")
		}
	}
	
	/**
	 * Only Mac is supported. Still TODO for Windows and Linux.
	 * 
	 * @return the path of EdgeChromiumDriver binary in the current Katalon Studio installation
	 */
	public static Path getEdgeChromiumDriverPath() {
		if (OSUtil.isMac()) {
			// edgechromium_mac/msedgedriver
			Path installationDir = getInstallationDir()
			return installationDir.resolve("Contents/Eclipse/configuration/resources/drivers/edgechromium_mac/msedgedriver")
		} else {
			throw new UnsupportedOperationException("TODO")
		}
	}
	
	/**
	 * 
	 * @param clazz
	 * @return the path of the jar file which contains the class specified
	 */
	private static Path getCodeSourcePathOf(Class<?> clazz) {
		CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
		URL url = codeSource.getLocation();
		try {
			return Paths.get(url.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @return the path of the directory where Katalon Studio is installed.
	 *     e.g. on Mac, usually "/Applications/Katalon Studio.app"
	 */
	private static Path getInstallationDir() {
		Path webuiJar = getCodeSourcePathOf(DriverFactory.class)   // /Applications/Katalon Studio.app/Contents/Eclipse/plugins/com.kms.katalon.core.webui_1.0.0.202602260431.jar
		Path pluginsDir = webuiJar.getParent()
		Path eclipseDir = pluginsDir.getParent()
		Path contentsDir = eclipseDir.getParent()
		return contentsDir.getParent()            // /Applications/Katalon Studio.app
	}
	
}
