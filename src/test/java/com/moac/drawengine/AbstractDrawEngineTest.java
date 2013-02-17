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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.moac.drawengine.DrawEngine;
import com.moac.drawengine.DrawFailureException;

import junit.framework.TestCase;

/*
 * Tests suitable for all implementations of DrawEngine
 * 
 * Extend and implement any specific tests if required. Otherwise just
 * extend as per @BasicDrawEngineTest
 * 
 */
public abstract class AbstractDrawEngineTest extends TestCase {

	// The DrawEngine under test.
	DrawEngine engine;

	/**
	 * 5000 members, no restrictions. Should succeed.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testPossibleManyMembers() throws DrawFailureException, InstantiationException, IllegalAccessException
	{

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		List<Long> members = new ArrayList<Long>();
		// 100 members
		for (int i =1; i <= 1000; i++)
		{
			Long m = new Long(i);
			members.add(m);
			// Add empty restrictions.
			input.put(m, new HashSet<Long>());
		}

		Map<Long, Long> result =  engine.generateDraw(input);			
		verifyResult(input, result);

	}


	/**
	 * Four members, no restrictions. Should succeed.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testPossibleSimple() throws DrawFailureException, InstantiationException, IllegalAccessException
	{

		Long m1 = Long.valueOf(1);
		Long m2 = Long.valueOf(2);
		Long m3 = Long.valueOf(3);
		Long m4 = Long.valueOf(4);

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		input.put(m1, new HashSet<Long>());	
		input.put(m2, new HashSet<Long>());		
		input.put(m3, new HashSet<Long>());		
		input.put(m4, new HashSet<Long>());		

		Map<Long, Long> result =  engine.generateDraw(input);
		verifyResult(input, result);

	}

	public void testPossibleComplicatedRestrictions() throws DrawFailureException, InstantiationException, IllegalAccessException 	{

		// TODO Control randomisation
		
		/* (0 to end) Rotate length, length times
		 *  A B C D
		 *  A C D B
		 *  A C B D
		 *  A D B C
		 *  A D C B
		 *  A B D C
		 *  
		 *  B C D A
		 *  
		 *  C D A B
		 *  
		 *  D A B C
		 *  
		 */

		
		for (int i=0; i<500; i++)
		{
			Long m1 = Long.valueOf(1);
			Long m2 = Long.valueOf(2);
			Long m3 = Long.valueOf(3);
			Long m4 = Long.valueOf(4);
			Long m5 = Long.valueOf(5);
			Long m6 = Long.valueOf(6);		

			SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();
			
			// m1 can only give to 2,4
			{
				Set<Long> m1Rs = new HashSet<Long>();
				m1Rs.add(m3); // Restrict m1->m3
				m1Rs.add(m5); // Restrict m1->m5
				m1Rs.add(m6); // Restrict m1->m6
				input.put(m1, m1Rs);	
			}

			// m2 can only give to m3
			{
				Set<Long> m2Rs = new HashSet<Long>();
				m2Rs.add(m1); // Restrict m2->m1
				m2Rs.add(m4); // Restrict m2->m4
				m2Rs.add(m5); // Restrict m2->m5
				m2Rs.add(m6); // Restrict m2->m6
				input.put(m2, m2Rs);		

			}

			// m3 can only give to m4
			{
				Set<Long> m3Rs = new HashSet<Long>();
				m3Rs.add(m1); // Restrict m3->m1
				m3Rs.add(m2); // Restrict m3->m2
				m3Rs.add(m5); // Restrict m3->m5
				m3Rs.add(m6); // Restrict m3->m6
				input.put(m3, m3Rs);		
			}

			// m4 can only give to m5 and m1
			{
				Set<Long> m4Rs = new HashSet<Long>();
				m4Rs.add(m2); // Restrict m4->m2
				m4Rs.add(m3); // Restrict m4->m3
				m4Rs.add(m6); // Restrict m4->m6
				input.put(m4, m4Rs);		

			}

			// m5 can only give to m6
			{
				Set<Long> m5Rs = new HashSet<Long>();
				m5Rs.add(m1); // Restrict m5->m1
				m5Rs.add(m2); // Restrict m5->m2
				m5Rs.add(m3); // Restrict m5->m3
				m5Rs.add(m4); // Restrict m5->m4
				input.put(m5, m5Rs);		
			}	

			// m6 can only give to m5
			{
				Set<Long> m6Rs = new HashSet<Long>();
				m6Rs.add(m1); // Restrict m6->m1
				m6Rs.add(m2); // Restrict m6->m2
				m6Rs.add(m3); // Restrict m6->m3
				m6Rs.add(m4); // Restrict m6->m4
				input.put(m6, m6Rs);		
			}	

			Map<Long, Long> result = engine.generateDraw(input);
			verifyResult(input, result);
		}
	}

	/**
	 * Must have minimum two members
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testFailOnlyOneMember() throws InstantiationException, IllegalAccessException
	{

		Long m1 = new Long(1);

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		Set<Long> m1Rs = new HashSet<Long>();
		input.put(m1, m1Rs);

		try {
			@SuppressWarnings("unused")
			Map<Long, Long> unused =  engine.generateDraw(input);
			fail("Should be impossible as only one member");			
		} catch (DrawFailureException e) {
			assertTrue(true);
		}
	}

	/**
	 * No-one gives to m1, so fails.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testImpossibleComplicatedRestrictions() throws InstantiationException, IllegalAccessException 	{

		Long m1 = Long.valueOf(1);
		Long m2 = Long.valueOf(2);
		Long m3 = Long.valueOf(3);
		Long m4 = Long.valueOf(4);
		Long m5 = Long.valueOf(5);
		Long m6 = Long.valueOf(6);		

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		// m1 can only give to 2,4
		{
			Set<Long> m1Rs = new HashSet<Long>();
			m1Rs.add(m3); // Restrict m1->m3
			m1Rs.add(m5); // Restrict m1->m5
			m1Rs.add(m6); // Restrict m1->m6
			input.put(m1, m1Rs);	
		}

		// m2 can only give to m3
		{
			Set<Long> m2Rs = new HashSet<Long>();
			m2Rs.add(m1); // Restrict m2->m1
			m2Rs.add(m4); // Restrict m2->m4
			m2Rs.add(m5); // Restrict m2->m5
			m2Rs.add(m6); // Restrict m2->m6
			input.put(m2, m2Rs);		

		}

		// m3 can only give to m4
		{
			Set<Long> m3Rs = new HashSet<Long>();
			m3Rs.add(m1); // Restrict m3->m1
			m3Rs.add(m2); // Restrict m3->m2
			m3Rs.add(m5); // Restrict m3->m5
			m3Rs.add(m6); // Restrict m3->m6
			input.put(m3, m3Rs);		
		}

		// m4 can only give to m5
		{
			Set<Long> m4Rs = new HashSet<Long>();
			m4Rs.add(m1); // Restrict m4->m1
			m4Rs.add(m2); // Restrict m4->m2
			m4Rs.add(m3); // Restrict m4->m3
			m4Rs.add(m6); // Restrict m4->m6
			input.put(m4, m4Rs);		

		}

		// m5 can only give to m6
		{
			Set<Long> m5Rs = new HashSet<Long>();
			m5Rs.add(m1); // Restrict m5->m1
			m5Rs.add(m2); // Restrict m5->m2
			m5Rs.add(m3); // Restrict m5->m3
			m5Rs.add(m4); // Restrict m5->m4
			input.put(m5, m5Rs);		
		}	

		// m6 can only give to m5
		{
			Set<Long> m6Rs = new HashSet<Long>();
			m6Rs.add(m1); // Restrict m6->m1
			m6Rs.add(m2); // Restrict m6->m2
			m6Rs.add(m3); // Restrict m6->m3
			m6Rs.add(m4); // Restrict m6->m4
			input.put(m6, m6Rs);		
		}	

		try {
			@SuppressWarnings("unused")
			Map<Long, Long> unused = engine.generateDraw(input);
			fail("Draw should fail as no one gives m1");
		} catch (DrawFailureException e) {
			assertTrue(true);
		}

	}

	/*
	 * Only a single path is possible m1->m2->m3->m4->m5->m6->m1
	 */
	public void testPossibleSinglePath() throws DrawFailureException, InstantiationException, IllegalAccessException 	{

		// TODO Test Randomisation
		for (int i=0; i<500.; i++)
		{
			Long m1 = Long.valueOf(1);
			Long m2 = Long.valueOf(2);
			Long m3 = Long.valueOf(3);
			Long m4 = Long.valueOf(4);
			Long m5 = Long.valueOf(5);
			Long m6 = Long.valueOf(6);		

			SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

			// m1 can only give to 2
			//
			{
				Set<Long> m1Rs = new HashSet<Long>();
				m1Rs.add(m3); // Restrict m1->m3
				m1Rs.add(m4); // Restrict m1->m4
				m1Rs.add(m5); // Restrict m1->m5
				m1Rs.add(m6); // Restrict m1->m6
				input.put(m1, m1Rs);	
			}

			// m2 can only give to m3
			{
				Set<Long> m2Rs = new HashSet<Long>();
				m2Rs.add(m1); // Restrict m2->m1
				m2Rs.add(m4); // Restrict m2->m4
				m2Rs.add(m5); // Restrict m2->m5
				m2Rs.add(m6); // Restrict m2->m6
				input.put(m2, m2Rs);		

			}

			// m3 can only give to m4
			{
				Set<Long> m3Rs = new HashSet<Long>();
				m3Rs.add(m1); // Restrict m3->m1
				m3Rs.add(m2); // Restrict m3->m2
				m3Rs.add(m5); // Restrict m3->m5
				m3Rs.add(m6); // Restrict m3->m6
				input.put(m3, m3Rs);		
			}

			// m4 can only give to m5
			{
				Set<Long> m4Rs = new HashSet<Long>();
				m4Rs.add(m1); // Restrict m4->m1
				m4Rs.add(m2); // Restrict m4->m2
				m4Rs.add(m3); // Restrict m4->m3
				m4Rs.add(m6); // Restrict m4->m6
				input.put(m4, m4Rs);		

			}

			// m5 can only give to m6
			{
				Set<Long> m5Rs = new HashSet<Long>();
				m5Rs.add(m1); // Restrict m5->m1
				m5Rs.add(m2); // Restrict m5->m2
				m5Rs.add(m3); // Restrict m5->m3
				m5Rs.add(m4); // Restrict m5->m4
				input.put(m5, m5Rs);		
			}	

			// m6 can only give to m1
			{
				Set<Long> m6Rs = new HashSet<Long>();
				m6Rs.add(m2); // Restrict m6->m2
				m6Rs.add(m3); // Restrict m6->m3
				m6Rs.add(m4); // Restrict m6->m4
				m6Rs.add(m5); // Restrict m6->m5

				input.put(m6, m6Rs);		
			}	

			Map<Long, Long> result = engine.generateDraw(input);
			verifyResult(input, result);
		}
	}

	public void testFailEmptyMembers() throws InstantiationException, IllegalAccessException
	{

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		try {
			@SuppressWarnings("unused")
			Map<Long, Long> unused =  engine.generateDraw(input);
			fail("Should be impossible as empty members");			
		} catch (DrawFailureException e) {
			assertTrue(true);
		}
	}

	public void testImpossiblePairNotSymmetrical() throws InstantiationException, IllegalAccessException
	{

		Long m1 = new Long(1);
		Long m2 = new Long(2);

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		Set<Long> m1Rs = new HashSet<Long>();
		m1Rs.add(m2); // Restrict m1->m2
		input.put(m1, m1Rs);
		input.put(m2, new HashSet<Long>());

		try {
			@SuppressWarnings("unused")
			Map<Long, Long> unused =  engine.generateDraw(input);
			fail("Should be impossible as one restricts the other");			
		} catch (DrawFailureException e) {
			assertTrue(true);
		}
	}

	public void testImpossibleSymmetrical() throws InstantiationException, IllegalAccessException
	{

		List<Long> members = new ArrayList<Long>();
		Long m1 = new Long(1);
		Long m2 = new Long(2);
		members.add(m1);
		members.add(m2);

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		Set<Long> m1Rs = new HashSet<Long>();
		m1Rs.add(m2); // Restrict m1->m2
		input.put(m1, m1Rs);

		Set<Long> m2Rs = new HashSet<Long>();
		m1Rs.add(m2); // Restrict m2->m1
		input.put(m2, m2Rs);

		try {
			@SuppressWarnings("unused")
			Map<Long, Long> unused =  engine.generateDraw(input);
			fail("Should be impossible as both restrict each other");
		} catch (DrawFailureException e) {
			assertTrue(true);
		}

	}

	/**
	 * It should ignore an attempt to self restrict. I.e. it shouldn't throw a DrawFailureException.
	 * 
	 * @throws DrawFailureException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testPossibleSelfRestrict() throws DrawFailureException, InstantiationException, IllegalAccessException
	{

		// Simple draw - two members only.
		Long m1 = Long.valueOf(1);
		Long m2 = Long.valueOf(2);

		SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

		Set<Long> m1Rs = new HashSet<Long>();
		m1Rs.add(m1); // Restrict m1->m1 (self)

		input.put(m1, m1Rs);	
		input.put(m2, new HashSet<Long>());		

		Map<Long, Long> result =  engine.generateDraw(input);
		verifyResult(input, result);

	}

	public void testImpossibleNullMembers() throws InstantiationException, IllegalAccessException 
	{

		try {
			@SuppressWarnings("unused")
			Map<Long, Long> unused =  engine.generateDraw(null);
			fail("Should be impossible as null members");
		} catch (DrawFailureException e) {
			assertTrue(true);
		}


	}
	/*
	 * Two married grandparents, two daughters and sons-in-law and two children for each daughter.
	 * 
	 *  I restricted spouses from giving to each other.
	 *  I also restricted the grand children from giving to their siblings or their own parents 
	 *  and the middle generation parents from giving to their own children. 
	 *  
	 *  Maybe a tough challenge, but everyone should have had at least six people they could give to.
	 */
	public void testPaulsScenario() throws InstantiationException, IllegalAccessException, DrawFailureException
	{

		// TODO Control randomisation
		for (int i=0; i<500; i++)
		{

			Long gp1 = Long.valueOf(1);
			Long gp2 = Long.valueOf(2);

			Long d1 = Long.valueOf(3);
			Long d2 = Long.valueOf(4);

			Long sil1 = Long.valueOf(5);
			Long sil2 = Long.valueOf(6);	

			Long c1 = Long.valueOf(7);	
			Long c2 = Long.valueOf(8);	
			Long c3 = Long.valueOf(9);	
			Long c4 = Long.valueOf(10);	

			SortedMap<Long, Set<Long>> input = new TreeMap<Long, Set<Long>>();

			// gp1 cannot give to gp2
			{
				Set<Long> gp1Rs = new HashSet<Long>();
				gp1Rs.add(gp2);
				input.put(gp1, gp1Rs);	
			}

			// gp2 cannot give to gp1
			{
				Set<Long> gp2Rs = new HashSet<Long>();
				gp2Rs.add(gp1);
				input.put(gp2, gp2Rs);	
			}

			// d1 can't give to sil1, c1 or c2
			{
				Set<Long> d1Rs = new HashSet<Long>();
				d1Rs.add(sil1);
				d1Rs.add(c1);
				d1Rs.add(c2);
				input.put(d1, d1Rs);	
			}

			// d2 can't give to sil2, c3 or c4
			{
				Set<Long> d2Rs = new HashSet<Long>();
				d2Rs.add(sil2);
				d2Rs.add(c3);
				d2Rs.add(c4);
				input.put(d2, d2Rs);	
			}

			// sil1 can't give to d1, c1 or c2
			{
				Set<Long> sil1Rs = new HashSet<Long>();
				sil1Rs.add(d1);
				sil1Rs.add(c1);
				sil1Rs.add(c2);
				input.put(sil1, sil1Rs);	
			}

			// sil2 can't give to d2, c3 or c4
			{
				Set<Long> sil2Rs = new HashSet<Long>();
				sil2Rs.add(d2);
				sil2Rs.add(c3);
				sil2Rs.add(c4);
				input.put(sil2, sil2Rs);	
			}

			// c1 can't give to d1,sil1 or c2
			{
				Set<Long> c1Rs = new HashSet<Long>();
				c1Rs.add(d1);
				c1Rs.add(sil1);
				c1Rs.add(c2);
				input.put(c1, c1Rs);	
			}

			// c2 can't give to d1,sil1 or c1
			{
				Set<Long> c2Rs = new HashSet<Long>();
				c2Rs.add(d1);
				c2Rs.add(sil1);
				c2Rs.add(c1);
				input.put(c2, c2Rs);	
			}

			// c3 can't give to d2,sil2 or c4
			{
				Set<Long> c3Rs = new HashSet<Long>();
				c3Rs.add(d2);
				c3Rs.add(sil2);
				c3Rs.add(c4);
				input.put(c3, c3Rs);	
			}

			// c4 can't give to d2,sil2 or c3
			{
				Set<Long> c4Rs = new HashSet<Long>();
				c4Rs.add(d2);
				c4Rs.add(sil2);
				c4Rs.add(c3);
				input.put(c4, c4Rs);	
			}

			// Sanity check the test member and restriction input
			assertEquals(10, input.size());
			assertEquals(1, input.get(gp1).size());
			assertEquals(1, input.get(gp2).size());
			assertEquals(3, input.get(d1).size());
			assertEquals(3, input.get(sil1).size());
			assertEquals(3, input.get(d2).size());
			assertEquals(3, input.get(sil2).size());
			assertEquals(3, input.get(c1).size());
			assertEquals(3, input.get(c2).size());
			assertEquals(3, input.get(c3).size());
			assertEquals(3, input.get(c4).size());

			Map<Long, Long> result =  engine.generateDraw(input);
			verifyResult(input, result);
		}
	}


	/**
	 * Verifies if a draw result was completed correctly.
	 * 
	 * @param input (members and their restrictions)
	 * @param result (assignments)
	 */
	public final void verifyResult(SortedMap<Long, Set<Long>> input, Map<Long, Long> result)
	{
		// Verify same number of givers as entrants
		assertEquals(input.keySet().size(), result.size()); 

		// Verify all givers assign to non-null
		assertFalse(result.containsValue(null));

		for (Entry<Long, Long> entry: result.entrySet())
		{
			// Assignment is one of the entrant members
			assertTrue(input.containsKey(entry.getValue()));

			// Giver is one of the entrant members
			assertTrue(input.containsKey(entry.getKey())); 

			// Assignment is NOT a restriction of the giver
			assertFalse(input.get(entry.getKey()).contains(entry.getValue()));
		}

	} 
}
