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

import junit.framework.TestCase;

/**
 * Test case for PathUtils.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class StringUtilsTest extends TestCase {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Tests --------------------------------------------------------------- */
	public void testInsert() {
		assertEquals( "Hello me !",
				StringUtils.insert( "Hello world !", "me", 6, 11 ) );
		assertEquals( null, StringUtils.insert( null, "me", 6, 11 ) );
		assertEquals( "Hello  !",
				StringUtils.insert( "Hello world !", null, 6, 11 ) );
		assertEquals( "ame", StringUtils.insert( "ab", "me", 1, -1 ) );
		assertEquals( "meb", StringUtils.insert( "ab", "me", -1, 1 ) );
	}
}
