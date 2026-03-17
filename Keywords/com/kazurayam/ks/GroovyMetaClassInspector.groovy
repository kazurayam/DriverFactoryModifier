package com.kazurayam.ks

import groovy.json.JsonOutput;
import java.util.stream.Collectors;

public class GroovyMetaClassInspector {

	/**
	 *
	 * @param metaClass
	 */
	public static void inspect(MetaClass metaClass) {
		Objects.requireNonNull(metaClass)
		String json = toJson(metaClass)
	}
	
	public static String toJson(MetaClass metaClass) {
		Objects.requireNonNull(metaClass)
		//
		List<Map> metaMethodMaps = metaClass.getMetaMethods()
			.stream()
			.sorted(new MetaMethodComparator())
			.map({ MetaMethod it ->
				Map<String, Object> m = new HashMap<>()
				m.put("name", it.getName())
				return m
			})
			.collect(Collectors.toList())
		assert metaMethodMaps.size() > 0
		//
		/*
		List<Map> methodMaps = metaClass.getMethods()
			.stream()
			.sorted(new MetaMethodComparator())
			.map({ MetaMethod it ->
				Map<String, Object> m = new HashMap<>()
				m.put("name", it.getName())
				return m
			})
			.collect(Collectors.toList())
		assert methodMaps.size() > 0
		*/
		//
		Map<String, Object> mp = new HashMap<>()
		mp.put("metaMethods", metaMethodMaps);
		//mp.put("methods", methodMaps);
		
		return JsonOutput.prettyPrint(JsonOutput.toJson(mp))
	}
}
