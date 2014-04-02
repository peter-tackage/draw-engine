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

import com.moac.drawengine.util.TestDataUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.*;

/**
 * Tests suitable for all implementations of DrawEngine
 * 
 * Extend and implement any specific tests if required. Otherwise just
 * extend as per @BasicDrawEngineTest
 * 
 */
@RunWith(JUnit4.class)
public abstract class AbstractDrawEngineTest {

    // The DrawEngine under test.
    // This should be instantiated by the concrete implementations of this class.
    DrawEngine engine;

    /*
     * 1000 members, no restrictions. Should succeed.
     */
    @Test
    public void possibleManyMembers() throws DrawFailureException {
        Map<Long, Set<Long>> input = new HashMap<Long, Set<Long>>();
        for(int i = 1; i <= 1000; i++) {
            Long m = (long) i;
            // Add empty restrictions.
            input.put(m, new HashSet<Long>());
        }
        Map<Long, Long> result = engine.generateDraw(input);
        verifyResult(input, result);
    }

    /*
     * Four members, no restrictions. Should succeed.
     */
    @Test
    public void possibleSimple() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("possible_simple.txt");
        Map<Long, Long> result = engine.generateDraw(input);
        verifyResult(input, result);
    }

    @Test
    public void possibleComplexRestrictions() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("possible_complex.txt");
        // TODO Control randomisation
        for(int i = 0; i < 500; i++) {
            Map<Long, Long> result = engine.generateDraw(input);
            verifyResult(input, result);
        }
    }

    /*
     * Must have minimum two members
     */
    @Test(expected = DrawFailureException.class)
    public void failOnlyOneMember() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("impossible_single_member.txt");
        engine.generateDraw(input);
    }

    /*
     * A complex scenario: m1 is restricted by all - should be impossible
     */
    @Test(expected = DrawFailureException.class)
    public void impossibleComplexRestrictions() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("impossible_too_restricted_complex.txt");
        engine.generateDraw(input);
        fail("Draw should fail as no one gives m1");
    }

    /*
     * A simple scenario: m1 is restricted by all - should be impossible
     */
    @Test(expected = DrawFailureException.class)
    public void impossibleTooRestricted() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("impossible_too_restricted_simple.txt");
        engine.generateDraw(input);
        fail("Draw should fail as no one gives m1");
    }

    /*
     * Only a single path is possible m1->m2->m3->m4->m5->m6->m1
     */
    @Test
    public void possibleSinglePath() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("possible_single_path.txt");
        // TODO Test Randomisation
        for(int i = 0; i < 500.; i++) {
            Map<Long, Long> result = engine.generateDraw(input);
            verifyResult(input, result);
        }
    }

    @Test(expected = DrawFailureException.class)
    public void failEmptyMembers() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("impossible_zero_members.txt");
        engine.generateDraw(input);
        fail("Should be impossible as empty members");
    }

    @Test(expected = DrawFailureException.class)
    public void impossiblePairNotSymmetrical() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("impossible_not_symmetrical.txt");
        engine.generateDraw(input);
        fail("Should be impossible as one restricts the other");
    }

    @Test(expected = DrawFailureException.class)
    public void impossibleSymmetrical() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("impossible_symmetrical.txt");
        engine.generateDraw(input);
        fail("Should be impossible as both restrict each other");
    }

    /**
     * It should ignore an attempt to self restrict. I.e. it shouldn't throw a DrawFailureException.
     */
    @Test
    public void possibleSelfRestrict() throws DrawFailureException {
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("ignore_self_restrict.txt");
        Map<Long, Long> result = engine.generateDraw(input);
        verifyResult(input, result);
    }

    @Test(expected = DrawFailureException.class)
    public void impossibleNullMembers() throws DrawFailureException {
        engine.generateDraw(null);
        fail("Should be impossible as null members");
    }

    /*
     * Paul's scenario
     */
    @Test
    public void paulsScenario() throws DrawFailureException {
        // TODO Control randomisation
        Map<Long, Set<Long>> input = TestDataUtils.readTestDataFile("pauls_test.txt");

        // Sanity check the test member and restriction input
        assertEquals(10, input.size());
        assertEquals(1, input.get(1l).size());
        assertEquals(1, input.get(2l).size());
        assertEquals(3, input.get(3l).size());
        assertEquals(3, input.get(4l).size());
        assertEquals(3, input.get(5l).size());
        assertEquals(3, input.get(6l).size());
        assertEquals(3, input.get(7l).size());
        assertEquals(3, input.get(8l).size());
        assertEquals(3, input.get(9l).size());
        assertEquals(3, input.get(10l).size());

        for(int i = 0; i < 500; i++) {
            Map<Long, Long> result = engine.generateDraw(input);
            verifyResult(input, result);
        }
    }

    /*
     * Test that the DrawEngine is randomizing the result
     *
     * This is based on the assumption that many consecutive draws will not
     * return an identical result.
     */
    @Test
    public void isRandomized() throws DrawFailureException {
        final int MAX_DRAW_THRESHOLD = 50;
        Map<Long, Long> lastResult = null;
        for(int drawNumber = 0; drawNumber < MAX_DRAW_THRESHOLD ; drawNumber++) {
            Map<Long, Set<Long>> input = new HashMap<Long, Set<Long>>();
            // Add many members to make randomization more obvious
            for(int i = 1; i <= 100; i++) {
                Long m = (long) i;
                // Add empty restrictions.
                input.put(m, new HashSet<Long>());
            }
            Map<Long, Long> result = engine.generateDraw(Collections.unmodifiableMap(input));
            verifyResult(input, result);
            if(drawNumber != 0 && !isEqual(lastResult, result)){
                return; // Draws is different - no need to continue
            }
            lastResult = result;
        }
        fail(String.format("DrawEngine produced %d consecutive identical draws", MAX_DRAW_THRESHOLD));
    }

    /**
     * Verifies if a draw result was completed correctly.
     *
     * @param input  (members and their restrictions)
     * @param result (assignments)
     */
    public static void verifyResult(Map<Long, Set<Long>> input, Map<Long, Long> result) {
        // Verify same number of givers as entrants
        assertEquals(input.keySet().size(), result.size());

        // Verify all givers assign to non-null
        assertFalse(result.containsValue(null));

        for(Entry<Long, Long> entry : result.entrySet()) {
            // Assignment is one of the entrant members
            assertTrue(input.containsKey(entry.getValue()));

            // Giver is one of the entrant members
            assertTrue(input.containsKey(entry.getKey()));

            // Assignment is NOT a restriction of the giver
            assertFalse(input.get(entry.getKey()).contains(entry.getValue()));
        }
    }

    /**
     * Returns true if the given draw results are equal, false otherwise.
     *
     * @param draw1 (assignments for one draw)
     * @param draw2 (assignments for the other draw)
     */
    public static boolean isEqual(Map<Long,Long> draw1, Map<Long,Long> draw2) {
         if(draw1 == draw2) return true;
         if(draw1.size() != draw2.size()) return false;

         for(Entry<Long, Long> entry : draw1.entrySet()) {
           if(!entry.getValue().equals(draw2.get(entry.getKey()))) return false;
         }
         return true;
    }
}
