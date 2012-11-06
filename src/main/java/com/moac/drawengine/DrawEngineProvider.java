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

public final class DrawEngineProvider {
	
	private static DrawEngineProvider instance = null;
	
	private DrawEngine mEngine = null;
	
	// Private for singleton
	private DrawEngineProvider()
	{}
	
	public static DrawEngineProvider getInstance() {
		if (instance == null){
			instance = new DrawEngineProvider();
	}
		return instance;
	}
	
	public DrawEngine getDrawEngine() {
		return mEngine;
	}
		
	public void setDrawEngine(String classname) throws Exception {

		Class<? extends DrawEngine>  newEngineClass = 
			Class.forName(classname).asSubclass(DrawEngine.class);
		DrawEngine engine = (DrawEngine)newEngineClass.newInstance();
		DrawEngineProvider.getInstance().setDrawEngine(engine); 
	}	
	
	private void setDrawEngine(DrawEngine to)
	{
		mEngine = to;
	}
}
