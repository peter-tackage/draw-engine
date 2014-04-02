package com.moac.drawengine;

/**
 *    Copyright 2014 Peter Tackage
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

import java.util.*;

/**
 * A DrawEngine implemented using recursion
 *
 * FIXME Performance is poor, probably due to high cost and frequency of "rollbacks"
 */
public class RecursiveDrawEngine implements DrawEngine {

    /*
     * (non-Javadoc)
     *
     * @see
     * com.moac.drawengine.DrawEngine#generateDraw(java.
     * util.Map)
     */
    public Map<Long, Long> generateDraw(final Map<Long, Set<Long>> members)
      throws DrawFailureException {

        // Can't draw null, zero or single length members sets
        if(members == null || members.size() < 2) {
            throw new DrawFailureException("Can't have less than two members.");
        }

        // Shuffle a list of members to randomize the result
        List<Long> randomMembers = new ArrayList<Long>(members.keySet());
        Collections.shuffle(randomMembers, new Random());

        return generateDrawImpl(members, randomMembers);
    }

    private Map<Long, Long> generateDrawImpl(final Map<Long, Set<Long>> members, List<Long> otherMembers)
      throws DrawFailureException {
        if(members.size() == 0 && otherMembers.size() == 0) return new HashMap<Long, Long>();

        for(Long self : members.keySet()) {
            Long pick = pick(self, members.get(self), otherMembers);
            if(pick != null) {
                // Add the pair to the result
                Map<Long, Long> result = new HashMap<Long, Long>();
                result.put(self, pick);

                // Remove this pairing from the draw and continue
                Map<Long, Set<Long>> membersSublist = new HashMap<Long, Set<Long>>(members);
                List<Long> otherMembersSublist = new ArrayList<Long>(otherMembers);
                membersSublist.remove(self);
                otherMembersSublist.remove(pick);
                try {
                    // Recursively draw the remaining members
                    result.putAll(generateDrawImpl(membersSublist, otherMembersSublist));
                    return result;
                } catch (DrawFailureException dfe)  {
                   // Explicitly ignore - this path is not possible. Move to next
                }
            }
        }

        // Could not create a result, throw control back to caller
        throw new DrawFailureException();
    }

    private static Long pick(Long self, Set<Long> restrictions, List<Long> others) {
        for(Long pick : others) {
            if(!(pick.equals(self) || restrictions.contains(pick))) {
                return pick;
            }
        }
        return null;
    }

}
