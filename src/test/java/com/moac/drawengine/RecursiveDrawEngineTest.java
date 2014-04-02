package com.moac.drawengine;

import org.junit.After;
import org.junit.Before;

/**
 *  Copyright 2014 Peter Tackage
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

/**
 * Empty child of AbstractDrawEngineTest - runs all the standard tests.
 *
 * @author peter
 */
public class RecursiveDrawEngineTest extends AbstractDrawEngineTest {

    @Before
    public void setUp() {
        engine = new RecursiveDrawEngine();
    }

    @After
    public void tearDown() {
        engine = null;
    }
}
