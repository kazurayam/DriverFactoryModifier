package com.kazurayam.ks

import static org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class DriverFactoryModifierTest {
	
	@Test
	public void testSmoke() {
		boolean b = DriverFactoryModifier.runWith("Chrome")
		assertTrue(b)
	}
}
