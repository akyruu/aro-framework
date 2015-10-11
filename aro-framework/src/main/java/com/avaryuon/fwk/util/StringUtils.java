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
package com.avaryuon.fwk.util;

/**
 * Contains utility methods for strings. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class StringUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private StringUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Validation ---------------------------------------------------------- */
	public static boolean isBlank( String string ) {
		return (string == null) || string.trim().isEmpty();
	}

	public static boolean isNotBlank( String string ) {
		return !isBlank( string );
	}

	/* Compare ------------------------------------------------------------- */
	public static boolean areEqual( String str1, String str2 ) {
		return (str1 == str2) || ((str1 != null) && str1.equals( str2 ));
	}

}
