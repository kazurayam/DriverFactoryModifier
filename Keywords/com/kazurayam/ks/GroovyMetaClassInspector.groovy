package com.kazurayam.ks

import groovy.json.JsonOutput;
import java.util.stream.Collectors;

public class GroovyMetaClassInspector {
	
	private int methodNameColumnWidth = 36;
	
	public GroovyMetaClassInspector() {}
	
	public void setMethodNameColumnWidth(int width) {
		this.methodNameColumnWidth = width
	}

	/**
	 *
	 * @param metaClass
	 */
	public void inspect(MetaClass metaClass) {
		Objects.requireNonNull(metaClass)
		String json = toJson(metaClass)
	}
	
	public String toJson(MetaClass metaClass) {
		Objects.requireNonNull(metaClass)
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
		Map<String, Object> mp = new HashMap<>()
		//mp.put("metaMethods", metaMethodMaps);
		mp.put("methods", methodMaps);
		return JsonOutput.prettyPrint(JsonOutput.toJson(mp))
	}
	
	public String toSignature(MetaMethod metaMethod) {
		StringBuilder sb = new StringBuilder()
		sb.append(metaMethod.getName())
		sb.append(getFillerWhitespace(metaMethod.getName()))
		sb.append(metaMethod.toString())
		return sb.toString()
	}
	
	public String getFillerWhitespace(String item) {
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
