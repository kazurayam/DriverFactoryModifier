package com.kazurayam.ks

import java.util.stream.Collectors

import com.kms.katalon.core.webui.driver.WebUIDriverType

/**
 * Modifies `com.kms.katalon.core.webui.driver.WebUIDriverType` class using Groovy's Metaprogramming technique.
 */
public class WebUIDriverTypeModifier {

	public static void apply() {
		/*
		 * Given 'Chrome' as dirverName, returns WebUIDriverType.CHROME_DRIVER
		 */
		WebUIDriverType.metaClass.static.valueOfByDriverName = { driverName ->
			List<WebUIDriverType> list = WebUIDriverType.values() as List
			List<WebUIDriverType> filtered = list.stream()
					.filter({ dt ->
						dt.getDriverName().equals(driverName)
					})
					.collect(Collectors.toList())
			if (filtered.size() > 0) {
				return filtered.get(0)
			} else {
				return null
			}
		}
		/*
		 * Given 'Chrome" as dirverName, returns true.
		 * Gievn 'undefined' as the driverName, returns false
		 */
		WebUIDriverType.metaClass.static.isDefinedDriverName = { driverName ->
			WebUIDriverType dt = WebUIDriverType.valueOfByDriverName(driverName)
			return (dt != null)
		}
		/*
		 * returns the list of driver names defined.
		 * 
		 * @returns `[Android, Chrome, Chrome (headless), Edge, Edge Chromium, Firefox, Firefox (headless), IE, Kobiton Device, Remote, Remote Chrome, Remote Firefox, Safari, TestCloud, iOS]`
		 */
		WebUIDriverType.metaClass.static.getDriverNames = { ->
			List<WebUIDriverType> list = WebUIDriverType.values() as List
			List<String> sorted = list.stream()
				.map({ dt -> dt.getDriverName() })
				.sorted()
				.collect(Collectors.toList())
			return sorted
		}
	}
}
