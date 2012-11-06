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
	public Map<Long, Long> generateDraw(Map<Long, Set<Long>> _members)
			throws DrawFailureException {

		// TODO Would like minimise some of the creation of new ArrayLists
		// from the Sets.

		// Can't draw zero or single length
		if (_members == null || _members.size() < 2) {
			throw new DrawFailureException("Can't have less than two members.");
		}

		Map<Long, Long> finalResult = new HashMap<Long, Long>();

		// Shuffle the one input list
		List<Long> randomMembers = new ArrayList<Long>(_members.keySet());
		Collections.shuffle(randomMembers, new Random());

		// Sort based on number of restrictions.
		// Most restrictive first to minimise rollbacks.
		List<Long> sortedMembers = new ArrayList<Long>(_members.keySet());
		Collections.sort(sortedMembers, new RestrictionsComparator(_members));

		int rowIndex = 0;
		// If the index goes back less than 0 ... we won't find anything.
		while (rowIndex >= 0 && rowIndex < sortedMembers.size()) {
			Long from = sortedMembers.get(rowIndex);
			Long lastAssignment = finalResult.get(from);

			// If there is an existing assignment for a member, then
			// it's because we have done a rollback - so add this value to the
			// list of restrictions.
			if (lastAssignment != null) {
				_members.get(from).add(lastAssignment);
				finalResult.remove(from); // no use anymore
			}

			Long to = null;

			// Try to find an allowed match - pick from the randomised list.
			for (Long pick : randomMembers) {

				/*
				 * 1. Can't pick self 2. Can't be restricted 3. Can't pick if
				 * already picked
				 */
				if (!(pick.equals(from) || _members.get(from).contains(pick) || finalResult
						.containsValue(pick))) {
					to = pick;
					break;
				}
			}

			if (to == null) {
				// Go back a row and choose differently.
				rowIndex--;
			} else {
				// Will overwrite any existing assignment.
				finalResult.put(from, to);
				rowIndex++;
			}

		}

		if (finalResult.size() == _members.size()) {
			return finalResult;
		} else {
			throw new DrawFailureException();
		}
	}

	private class RestrictionsComparator implements Comparator<Long> {
		private Map<Long, Set<Long>> mRestrictionsMap;

		public RestrictionsComparator(Map<Long, Set<Long>> _restrictions) {
			mRestrictionsMap = _restrictions;
		}

		public int compare(Long l1, Long l2) {
			return mRestrictionsMap.get(l2).size()
					- mRestrictionsMap.get(l1).size();
		}
	}

}
