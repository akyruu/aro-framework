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
package com.avaryuon.fwk.io.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.TestCase;

import com.avaryuon.commons.io.file.PathUtils;

/**
 * Test case for JavaFX tests.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class PathUtilsTest extends TestCase {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* Logging ------------------------------------------------------------- */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Tests --------------------------------------------------------------- */
	public void testGetPathFromString() {
		Path path = PathUtils.getPath( "test" );
		assertNotNull( "Path isn't valid", path );
		assertEquals( "Path is not \"test\"", Paths.get( "test" ), path );
	}

	public void testGetPathFromStringURL() {
		Path path = PathUtils.getPath( "file:test" );
		assertNotNull( "URL is not a valid URL file", path );
		assertEquals( "Path is not \"test\"", Paths.get( "test" ), path );

		path = PathUtils.getPath( "jar:file:test" );
		assertNotNull( "URL is not a valid URL file", path );
		assertEquals( "Path is not \"test\"", Paths.get( "test" ), path );

		path = PathUtils.getPath( "unknown:test" );
		assertNull( "Path from unknown protocol URL is valid", path );
	}

	public void testGetPathFromURL() throws MalformedURLException {
		URL url = new URL( "file:test" );
		Path path = PathUtils.getPath( url );
		assertNotNull( "URL is not a valid URL file", path );
		assertEquals( "Path is not \"test\"", Paths.get( "test" ), path );
	}

}
