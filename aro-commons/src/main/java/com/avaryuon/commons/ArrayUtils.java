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

import java.util.Arrays;

/**
 * Contains utility methods for arrays. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class ArrayUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private ArrayUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/**
	 * Push an item in array.
	 * <p>
	 * Copy and resize, with one case, array in parameter and add item in last
	 * case.
	 * </p>
	 * 
	 * @param array
	 *            Array to add item.
	 * @param item
	 *            Item to push in array.
	 * 
	 * @return Resized array.
	 */
	public static < G, T extends G > G[] push( G[] array, T item ) {
		// Add case in array
		int index = array.length;
		G[] resizedArray = Arrays.copyOf( array, index + 1 );

		// Set item in last place and return resized array
		resizedArray[ index ] = item;
		return resizedArray;
	}
}
