package com.moac.drawengine;

/**
 *    Copyright 2011 Peter Tackage
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BasicDrawEngine implements DrawEngine {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moac.drawengine.DrawEngine#generateDraw(java.
	 * util.Map)
	 */
	public Map<Long, Long> generateDraw(final Map<Long, Set<Long>> members)
			throws DrawFailureException {
		
		// TODO Would like minimise some of the creation of new ArrayLists
		// from the Sets. In fact, I would like to rewrite the whole thing..

		// Can't draw zero or single length
		if (members == null || members.size() < 2) {
			throw new DrawFailureException("Can't have less than two members.");
		}

		// Initialise to desired size (performance)
		Map<Long, Long> result = new HashMap<Long, Long>(members.size());

		// Shuffle the one input list
		List<Long> randomMembers = new ArrayList<Long>(members.keySet());
		Collections.shuffle(randomMembers, new Random());
				
		// Sort based on number of restrictions.
		// Most restrictive first to minimise rollbacks.
		List<Long> sortedMembers = new ArrayList<Long>(members.keySet());
		Collections.sort(sortedMembers, new RestrictionsComparator(members));
		
		Map<Long, Set<Long>> failedPaths = new HashMap<Long, Set<Long>>();

		int rowIndex = 0;
		// If the index goes back less than 0 ... we won't find anything.
		while (rowIndex >= 0 && rowIndex < sortedMembers.size()) {
			
			Long from = sortedMembers.get(rowIndex);
			Long to = null;
			
			// If there is an existing assignment for a member, then
			// it's because we have done a rollback to here - so add this value to the
			// list of failed paths for this node.
			Long lastfailedPath = result.remove(from);
			
			Set<Long> nodeFailedPaths = failedPaths.get(from);		
			if (lastfailedPath != null) {
				if (nodeFailedPaths == null)
				{
					nodeFailedPaths = new HashSet<Long>();
				}
				nodeFailedPaths.add(lastfailedPath);
				failedPaths.put(from, nodeFailedPaths);			
			}
			
			// Try to find an allowed match - pick from the randomised list.
			for (Long pick : randomMembers) {

				/*
				 * 1. Can't pick self 2. Can't be restricted 3. Can't pick if
				 * already picked 4.Can't pick a failed path for this node.
				 */
				if (!(pick.equals(from) || members.get(from).contains(pick) || result
						.containsValue(pick) || (nodeFailedPaths != null && nodeFailedPaths.contains(pick)))) {
					to = pick;
					break;
				}
			}

			if (to == null) {
				// Go back to previous node and choose differently.
				
				// Clear any failed paths from current node.
				if (nodeFailedPaths != null)
				{
					nodeFailedPaths.clear(); 
				}
				rowIndex--;
				
			} else {
				// Set path and visit next node.
				result.put(from, to);
				rowIndex++;
			}
			
		}

		if (result.size() == members.size()) {
			return result;
		} else {
			throw new DrawFailureException();
		}
	}

	/**
	 * Orders members in a descending order of restrictedness.
	 *
	 */
	private class RestrictionsComparator implements Comparator<Long> {
		private Map<Long, Set<Long>> mRestrictionsMap;

		public RestrictionsComparator(Map<Long, Set<Long>> restrictions) {
			mRestrictionsMap = restrictions;
		}

		public int compare(Long l1, Long l2) {
			return mRestrictionsMap.get(l2).size()
					- mRestrictionsMap.get(l1).size();
		}
	}

}
