package com.kazurayam.ks

import groovy.lang.MetaMethod

public class MetaMethodComparator implements Comparator<MetaMethod> {
	
	@Override
	public int compare(MetaMethod o1, MetaMethod o2) {
		String n1 = o1.getName()
		String n2 = o2.getName()
		return n1.compareTo(n1)
	}
	
}
