/*
 * Copyright 2015 Akyruu (akyruu@hotmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.avaryuon.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains utility methods for collections. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class CollectionUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private CollectionUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/**
	 * Adds an item into a new list copied from parameter list in first place.
	 * 
	 * @param list
	 *            List to copied.
	 * @param item
	 *            Item to add in copied list.
	 * @return A copied list.
	 */
	@SuppressWarnings("unchecked")
	public static < S, T extends S > List< S > addFirstToCopy( List< S > list,
			T... items ) {
		List< S > copiedList = new ArrayList<>();
		copiedList.addAll( Arrays.asList( items ) );
		copiedList.addAll( list );
		return copiedList;
	}
}
