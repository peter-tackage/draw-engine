package com.moac.android.opensecretsanta.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

/*
 * Tests suitable for all implementations of DrawEngine
 */
public abstract class DrawEngineTest extends TestCase {
	
	DrawEngine engine;
	
	/**
	 * 500 members, no restrictions. Should succeed.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testPossibleManyMembers() throws DrawFailureException, InstantiationException, IllegalAccessException
	{
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();

		List<Long> members = new ArrayList<Long>();
		for (int i =1; i <= 500; i++)
		{
			Long m = new Long(i);
			members.add(m);
			// Add empty restrictions.
			data.put(m, new HashSet<Long>());
		}

			Map<Long, Long> result =  engine.generateDraw(data);			
			assertEquals(data.keySet().size(), result.size());

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
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
				
		data.put(m1, new HashSet<Long>());	
		data.put(m2, new HashSet<Long>());		
		data.put(m3, new HashSet<Long>());		
		data.put(m4, new HashSet<Long>());		

		Map<Long, Long> result =  engine.generateDraw(data);
		assertEquals(data.keySet().size(), result.size());

	}
	
	public void testPossibleComplicatedRestrictions() throws DrawFailureException, InstantiationException, IllegalAccessException 	{
				
		Long m1 = Long.valueOf(1);
		Long m2 = Long.valueOf(2);
		Long m3 = Long.valueOf(3);
		Long m4 = Long.valueOf(4);
		Long m5 = Long.valueOf(5);
		Long m6 = Long.valueOf(6);		
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		// m1 can only give to 2,4
		{
			Set<Long> m1Rs = new HashSet<Long>();
			m1Rs.add(m3); // Restrict m1->m3
			m1Rs.add(m5); // Restrict m1->m5
			m1Rs.add(m6); // Restrict m1->m6
			data.put(m1, m1Rs);	
		}
		
		// m2 can only give to m3
		{
			Set<Long> m2Rs = new HashSet<Long>();
			m2Rs.add(m1); // Restrict m2->m1
			m2Rs.add(m4); // Restrict m2->m4
			m2Rs.add(m5); // Restrict m2->m5
			m2Rs.add(m6); // Restrict m2->m6
			data.put(m2, m2Rs);		

		}
		
		// m3 can only give to m4
		{
			Set<Long> m3Rs = new HashSet<Long>();
			m3Rs.add(m1); // Restrict m3->m1
			m3Rs.add(m2); // Restrict m3->m2
			m3Rs.add(m5); // Restrict m3->m5
			m3Rs.add(m6); // Restrict m3->m6
			data.put(m3, m3Rs);		
		}
		
		// m4 can only give to m5 and m1
		{
			Set<Long> m4Rs = new HashSet<Long>();
			m4Rs.add(m2); // Restrict m4->m2
			m4Rs.add(m3); // Restrict m4->m3
			m4Rs.add(m6); // Restrict m4->m6
			data.put(m4, m4Rs);		

		}
		
		// m5 can only give to m6
		{
			Set<Long> m5Rs = new HashSet<Long>();
			m5Rs.add(m1); // Restrict m5->m1
			m5Rs.add(m2); // Restrict m5->m2
			m5Rs.add(m3); // Restrict m5->m3
			m5Rs.add(m4); // Restrict m5->m4
			data.put(m5, m5Rs);		
		}	
		
		// m6 can only give to m5
		{
			Set<Long> m6Rs = new HashSet<Long>();
			m6Rs.add(m1); // Restrict m6->m1
			m6Rs.add(m2); // Restrict m6->m2
			m6Rs.add(m3); // Restrict m6->m3
			m6Rs.add(m4); // Restrict m6->m4
			data.put(m6, m6Rs);		
		}	

		Map<Long, Long> result = engine.generateDraw(data);
		assertEquals(data.keySet().size(), result.size());
	
	}
	
	/**
	 * Must have minimum two members
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testFailOnlyOneMember() throws InstantiationException, IllegalAccessException
	{
		
		Long m1 = new Long(1);
	
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		Set<Long> m1Rs = new HashSet<Long>();
		data.put(m1, m1Rs);

		try {
			Map<Long, Long> result =  engine.generateDraw(data);
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
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		// m1 can only give to 2,4
		{
			Set<Long> m1Rs = new HashSet<Long>();
			m1Rs.add(m3); // Restrict m1->m3
			m1Rs.add(m5); // Restrict m1->m5
			m1Rs.add(m6); // Restrict m1->m6
			data.put(m1, m1Rs);	
		}
		
		// m2 can only give to m3
		{
			Set<Long> m2Rs = new HashSet<Long>();
			m2Rs.add(m1); // Restrict m2->m1
			m2Rs.add(m4); // Restrict m2->m4
			m2Rs.add(m5); // Restrict m2->m5
			m2Rs.add(m6); // Restrict m2->m6
			data.put(m2, m2Rs);		

		}
		
		// m3 can only give to m4
		{
			Set<Long> m3Rs = new HashSet<Long>();
			m3Rs.add(m1); // Restrict m3->m1
			m3Rs.add(m2); // Restrict m3->m2
			m3Rs.add(m5); // Restrict m3->m5
			m3Rs.add(m6); // Restrict m3->m6
			data.put(m3, m3Rs);		
		}
		
		// m4 can only give to m5
		{
			Set<Long> m4Rs = new HashSet<Long>();
			m4Rs.add(m1); // Restrict m4->m1
			m4Rs.add(m2); // Restrict m4->m2
			m4Rs.add(m3); // Restrict m4->m3
			m4Rs.add(m6); // Restrict m4->m6
			data.put(m4, m4Rs);		

		}
		
		// m5 can only give to m6
		{
			Set<Long> m5Rs = new HashSet<Long>();
			m5Rs.add(m1); // Restrict m5->m1
			m5Rs.add(m2); // Restrict m5->m2
			m5Rs.add(m3); // Restrict m5->m3
			m5Rs.add(m4); // Restrict m5->m4
			data.put(m5, m5Rs);		
		}	
		
		// m6 can only give to m5
		{
			Set<Long> m6Rs = new HashSet<Long>();
			m6Rs.add(m1); // Restrict m6->m1
			m6Rs.add(m2); // Restrict m6->m2
			m6Rs.add(m3); // Restrict m6->m3
			m6Rs.add(m4); // Restrict m6->m4
			data.put(m6, m6Rs);		
		}	

		Map<Long, Long> result;
		try {
			result = engine.generateDraw(data);
			fail("Draw should fail as no one gives m1");
		} catch (DrawFailureException e) {
			assertTrue(true);
		}
	
	}
	
	/*
	 * Only a single path is possible m1->m2->m3->m4->m5->m6->m1
	 */
	public void testPossibleSinglePath() throws DrawFailureException, InstantiationException, IllegalAccessException 	{
				
		Long m1 = Long.valueOf(1);
		Long m2 = Long.valueOf(2);
		Long m3 = Long.valueOf(3);
		Long m4 = Long.valueOf(4);
		Long m5 = Long.valueOf(5);
		Long m6 = Long.valueOf(6);		
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		// m1 can only give to 2
		//
		{
			Set<Long> m1Rs = new HashSet<Long>();
			m1Rs.add(m3); // Restrict m1->m3
			m1Rs.add(m4); // Restrict m1->m4
			m1Rs.add(m5); // Restrict m1->m5
			m1Rs.add(m6); // Restrict m1->m6
			data.put(m1, m1Rs);	
		}
		
		// m2 can only give to m3
		{
			Set<Long> m2Rs = new HashSet<Long>();
			m2Rs.add(m1); // Restrict m2->m1
			m2Rs.add(m4); // Restrict m2->m4
			m2Rs.add(m5); // Restrict m2->m5
			m2Rs.add(m6); // Restrict m2->m6
			data.put(m2, m2Rs);		

		}
		
		// m3 can only give to m4
		{
			Set<Long> m3Rs = new HashSet<Long>();
			m3Rs.add(m1); // Restrict m3->m1
			m3Rs.add(m2); // Restrict m3->m2
			m3Rs.add(m5); // Restrict m3->m5
			m3Rs.add(m6); // Restrict m3->m6
			data.put(m3, m3Rs);		
		}
		
		// m4 can only give to m5
		{
			Set<Long> m4Rs = new HashSet<Long>();
			m4Rs.add(m1); // Restrict m4->m1
			m4Rs.add(m2); // Restrict m4->m2
			m4Rs.add(m3); // Restrict m4->m3
			m4Rs.add(m6); // Restrict m4->m6
			data.put(m4, m4Rs);		

		}
		
		// m5 can only give to m6
		{
			Set<Long> m5Rs = new HashSet<Long>();
			m5Rs.add(m1); // Restrict m5->m1
			m5Rs.add(m2); // Restrict m5->m2
			m5Rs.add(m3); // Restrict m5->m3
			m5Rs.add(m4); // Restrict m5->m4
			data.put(m5, m5Rs);		
		}	
		
		// m6 can only give to m1
		{
			Set<Long> m6Rs = new HashSet<Long>();
			m6Rs.add(m2); // Restrict m6->m2
			m6Rs.add(m3); // Restrict m6->m3
			m6Rs.add(m4); // Restrict m6->m4
			m6Rs.add(m5); // Restrict m6->m5

			data.put(m6, m6Rs);		
		}	

			Map<Long, Long> result = engine.generateDraw(data);
			assertTrue(true);
	
	}
	
	public void testFailEmptyMembers() throws InstantiationException, IllegalAccessException
	{
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		try {
			Map<Long, Long> result =  engine.generateDraw(data);
			fail("Should be impossible as empty members");			
		} catch (DrawFailureException e) {
			assertTrue(true);
		}
	}
	
	public void testImpossiblePairNotSymmetrical() throws InstantiationException, IllegalAccessException
	{
		
		Long m1 = new Long(1);
		Long m2 = new Long(2);
	
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		Set<Long> m1Rs = new HashSet<Long>();
		m1Rs.add(m2); // Restrict m1->m2
		data.put(m1, m1Rs);
		data.put(m2, new HashSet<Long>());

		try {
			Map<Long, Long> result =  engine.generateDraw(data);
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
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		Set<Long> m1Rs = new HashSet<Long>();
		m1Rs.add(m2); // Restrict m1->m2
		data.put(m1, m1Rs);
		
		Set<Long> m2Rs = new HashSet<Long>();
		m1Rs.add(m2); // Restrict m2->m1
		data.put(m2, m2Rs);

		try {
			Map<Long, Long> result =  engine.generateDraw(data);
			fail("Should be impossible as both restrict each other");
		} catch (DrawFailureException e) {
			assertTrue(true);
		}

	}
	
	/**
	 * It should ignore an attempt to self restrict.
	 * @throws DrawFailureException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testPossibleSelfRestrict() throws DrawFailureException, InstantiationException, IllegalAccessException
	{
		
		// Simple draw - two members only.
		Long m1 = Long.valueOf(1);
		Long m2 = Long.valueOf(2);
		
		Map<Long, Set<Long>> data = new HashMap<Long, Set<Long>>();
		
		Set<Long> m1Rs = new HashSet<Long>();
		m1Rs.add(m1); // Restrict m1->m1 (self)
		
		data.put(m1, m1Rs);	
		data.put(m2, new HashSet<Long>());		
	
		Map<Long, Long> result =  engine.generateDraw(data);
		assertEquals(data.keySet().size(), result.size());
	
	}
	
	public void testImpossibleNullMembers() throws InstantiationException, IllegalAccessException 
	{
			
		try {
			Map<Long, Long> result =  engine.generateDraw(null);
			fail("Should be impossible as null members");
		} catch (DrawFailureException e) {
			assertTrue(true);
		}

		
	}
}
