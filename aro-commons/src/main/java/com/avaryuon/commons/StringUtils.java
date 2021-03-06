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

import java.text.MessageFormat;

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
	public static boolean isEmpty( String string ) {
		return (string == null) || string.isEmpty();
	}

	public static boolean isNotEmpty( String string ) {
		return !isEmpty( string );
	}

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

	/* Format -------------------------------------------------------------- */
	/**
	 * Format a non-null string (escape simple quote character).
	 * 
	 * @see java.text.MessageFormat#format.
	 * 
	 * @param pattern
	 *            String to format.
	 * @param args
	 *            Arguments for formats pattern.
	 * 
	 * @return String or null.
	 */
	public static String format( String pattern, Object... args ) {
		return (pattern == null) ? null : MessageFormat.format(
				pattern.replace( "'", "''" ), args );
	}

	/* Inserts ------------------------------------------------------------- */
	/**
	 * <b>Inserts string between from/to indexes.<b>
	 * <p>
	 * For example, insert("Hello world !", "me", 6, 11 ) will return
	 * "Hello me !".
	 * </p>
	 * 
	 * @param string
	 *            Original string.
	 * @param insert
	 *            String to insert.
	 * @param from
	 *            Insertion point index (use -1 for replace all the start).
	 * @param to
	 *            Last insertion point index (use -1 for replace all the end).
	 * 
	 * @return String or null if the original string is empty.
	 */
	public static String insert( String string, String insert, int from, int to ) {
		if( StringUtils.isEmpty( string ) ) {
			return string;
		}

		String result = "";
		if( (from >= 1) && (from < string.length()) ) {
			result += string.substring( 0, from );
		}
		if( insert != null ) {
			result += insert;
		}
		if( (to >= 1) && (to < string.length()) ) {
			result += string.substring( to );
		}
		return result;
	}
}
