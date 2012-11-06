package com.moac.android.opensecretsanta.engine;

/*
 * Where you go to find your draws.
 * 
 * Not actually an Android provider.
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
