package com.moac.drawengine;

import java.util.Map;
import java.util.Set;

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

public interface DrawEngine {

    /**
     * @param _members A mapping between the members in the draw and their associated restrictions.
     *                 Members with no restrictions will be mapped to an empty Set.
     * @return A mapping between the member and their assignment.
     *         A value should only be returned if the draw was successful.
     * @throws DrawFailureException Thrown if assignments are unable to be made for any reason - i.e. an unsuccessful draw.
     */
    public Map<Long, Long> generateDraw(Map<Long, Set<Long>> members) throws DrawFailureException;
}
