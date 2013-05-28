package com.moac.drawengine;

/**
 * Copyright 2011 Peter Tackage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public final class DrawEngineProvider {

    private DrawEngine mEngine = null;

    public DrawEngine getDrawEngine() throws InvalidDrawEngineException {
        return mEngine;
    }

    public void setDrawEngine(String classname) throws InvalidDrawEngineException {

        try {
            Class<? extends DrawEngine> newEngineClass;
            newEngineClass = Class.forName(classname).asSubclass(DrawEngine.class);
            mEngine = newEngineClass.newInstance();
        } catch(Exception e) {
            mEngine = null;
            throw new InvalidDrawEngineException("Error loading Draw Engine: " + classname + ": " + e.getMessage(), e);
        }
    }
}
