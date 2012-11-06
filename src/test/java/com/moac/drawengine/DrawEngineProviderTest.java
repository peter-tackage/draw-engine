package com.moac.drawengine;

/**
 *  Copyright 2011 Peter Tackage
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * 
 */

import com.moac.drawengine.BasicDrawEngine;
import com.moac.drawengine.DrawEngineProvider;

import junit.framework.TestCase;


public class DrawEngineProviderTest extends TestCase {
	
	public void testSetValidDrawEngine() throws Exception
	{
		DrawEngineProvider instance = DrawEngineProvider.getInstance();	
		instance.setDrawEngine("com.moac.drawengine.BasicDrawEngine");
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
