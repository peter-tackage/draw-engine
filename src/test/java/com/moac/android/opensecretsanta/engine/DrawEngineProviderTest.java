package com.moac.android.opensecretsanta.engine;

import junit.framework.TestCase;

public class DrawEngineProviderTest extends TestCase {
	
	public void testSetValidDrawEngine() throws Exception
	{
		DrawEngineProvider instance = DrawEngineProvider.getInstance();	
		instance.setDrawEngine("com.moac.android.opensecretsanta.engine.BasicDrawEngine");
		assertTrue(instance.getDrawEngine().getClass().equals(BasicDrawEngine.class));
	}
	
	public void testSetInvalidDrawEngine() {
		try
		{
			DrawEngineProvider instance = DrawEngineProvider.getInstance();	
			instance.setDrawEngine("NOT A VALID CLASSNAME");
			fail("Should have thrown an Exception to due invalid classname");
		} catch (Exception exp)
		{
			assertTrue(true);
		}
	}
}
