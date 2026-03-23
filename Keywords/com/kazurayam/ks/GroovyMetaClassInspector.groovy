package com.kazurayam.ks

import groovy.json.JsonOutput;
import java.util.stream.Collectors;

/**
 * Groovy language provides "MetaClass" of all Java or Groovy classes accessible runtime,
 * which gives us the chances to modify the method implementation dynamically.
 * 
 * This GroovyMetaClassInspector provides a helper method that supports debugging "MetaClass".
 */
public class GroovyMetaClassInspector {
	
	private int methodNameColumnWidth = 36;
	
	private MetaClass targetMetaClass = null;
	
	public GroovyMetaClassInspector() {}
	
	public void setMethodNameColumnWidth(int width) {
		this.methodNameColumnWidth = width
	}
	
	/**
	 * returns a JSON string which includes all the methods implemented in the metaClass; 
	 * both genuine ones and the dynamically added ones. 
	 * The JSON output makes the list of methods visible.
	 * Useful for debugging the Metaprogramming.
	 *  
	 * @param metaClass
	 * @return a JSON representation of the methods of the metaClass
	 */
	public String toJson(MetaClass metaClass) {
		Objects.requireNonNull(metaClass)
		this.targetMetaClass = metaClass
		//
		List<Map> methodMaps = metaClass.getMethods()
			.stream()
			.map({ MetaMethod it ->
				return toSignature(it)
			})
			.sorted()
			.collect(Collectors.toList())
		assert methodMaps.size() > 0
		//
		List<Map> metaMethodMaps = metaClass.getMetaMethods()
			.stream()
			.map({ MetaMethod it -> 
				return toSignature(it)
			})
			.sorted()
			.collect(Collectors.toList())
		assert metaMethodMaps.size() > 0
		//
		Map<String, Object> mp = new HashMap<>()
		//mp.put("metaMethods", metaMethodMaps);
		mp.put("methods", methodMaps);
		return JsonOutput.prettyPrint(JsonOutput.toJson(mp))
	}
	
	/**
	 * Given the name of method "openWebDriver", then will return a string like:
	 * 
	 * "openWebDriver                       public static org.openqa.selenium.WebDriver com.kms.katalon.core.webui.driver.DriverFactory.openWebDriver() throws java.lang.Exception",
     * 
     * This string is good for sorting the lines by the method name alphabetically
     * 
	 * @param metaMethod 
	 */
	public String toSignature(MetaMethod metaMethod) {
		StringBuilder sb = new StringBuilder()
		sb.append(metaMethod.getName())
		sb.append(fillWhitespace(metaMethod.getName()))
		sb.append(metaMethod.toString())
		return sb.toString()
	}
	
	/**
	 * Given item is "123456789012345678901234567890" and the methodNameColumnWidth is set 36, 
	 * then will return a string of "123456789012345678901234567890      " appended 6 whitespace as filler.            
	 * @param item
	 * @return a string appended a sequence of whitespace
	 */
	public String fillWhitespace(String item) {
		StringBuilder sb = new StringBuilder()
		methodNameColumnWidth.times { sb.append(' ') }
		int filler = methodNameColumnWidth - item.length()
		if (filler > 0) {
			return sb.toString().substring(0, methodNameColumnWidth - item.length())
		} else {
			return ''
		}
	}
}
