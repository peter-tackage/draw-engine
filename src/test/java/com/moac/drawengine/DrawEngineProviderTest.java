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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(JUnit4.class)
public class DrawEngineProviderTest {

    @Test
	public void setValidDrawEngine() throws Exception
	{
		DrawEngineProvider dep = new DrawEngineProvider();	
		dep.setDrawEngine("com.moac.drawengine.BasicDrawEngine");
		assertTrue(dep.getDrawEngine().getClass().equals(BasicDrawEngine.class));
	}
	
	public void testSetInvalidDrawEngine() {
		try
		{
			DrawEngineProvider dep = new DrawEngineProvider();	
			dep.setDrawEngine("NOT A VALID CLASSNAME");
			fail("Should have thrown a InvalidDrawEngineException to due invalid classname");
		} catch (InvalidDrawEngineException exp)
		{
			assertTrue(true);
		}
	}
}
