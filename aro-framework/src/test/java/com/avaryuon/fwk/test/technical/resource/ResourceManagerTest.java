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
package com.avaryuon.fwk.test.technical.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.avaryuon.fwk.core.bean.BeanManager;
import com.avaryuon.fwk.core.resource.ResourceManager;
import com.avaryuon.fwk.test.FXTestCase;

/**
 * Tests for ResourceManager.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class ResourceManagerTest extends FXTestCase {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private ResourceManager resourceMgr;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Set-up -------------------------------------------------------------- */
	@Override
	public void setUpBeans() {
		resourceMgr = BeanManager.instance().getBean( ResourceManager.class );
	}

	/* Tests --------------------------------------------------------------- */
	public void testGetResource() {
		/* Existing file */
		URL existFileURL = resourceMgr.getResource( "file.txt" );
		assertNotNull( "Resource file.txt not exists", existFileURL );
		assertEquals( "Protocol is not file", "file",
				existFileURL.getProtocol() );
		assertTrue( existFileURL.getFile()
				+ " not ends with resources/file.txt", existFileURL.getFile()
				.endsWith( "resources/file.txt" ) );

		/* Not existing file */
		URL notExistfileURL = resourceMgr.getResource( "nofile.txt" );
		assertNull( "Resource nofile.txt exists", notExistfileURL );
	}

	public void testGetResourceAsStream() throws IOException {
		/* Existing file */
		InputStream existFileIS = resourceMgr.getResourceAsStream( "file.txt" );
		assertNotNull( "Resource file.txt not exists", existFileIS );

		BufferedReader reader = new BufferedReader( new InputStreamReader(
				existFileIS ) );
		String firstLine = reader.readLine();
		assertNotNull( firstLine );
		assertEquals( "test", firstLine );
		String lastLine = reader.readLine();
		assertNull( lastLine );

		/* Not existing file */
		InputStream notExistfileIS = resourceMgr
				.getResourceAsStream( "nofile.txt" );
		assertNull( "Resource nofile.txt exists", notExistfileIS );
	}

}
