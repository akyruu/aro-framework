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
package com.avaryuon.fwk.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.avaryuon.fwk.AroApplication;

/**
 * Tests for AroApplication.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class AroApplicationTest extends AroApplication {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private boolean initialized;

	/* CONSTRUCTORS ======================================================== */
	public AroApplicationTest() {
		initialized = false;
	}

	/* METHODS ============================================================= */
	/* Set-up -------------------------------------------------------------- */
	@Before
	public void setUp() throws Exception {
		if( !initialized ) {
			init();
			initialized = true;
		}
	}

	/* Tests --------------------------------------------------------------- */
	@Test
	public void testTitle() {
		String title = getTitle();
		assertNotNull( "Title is null", title );
		assertEquals( "ARO Framework Test", title );
	}

}
