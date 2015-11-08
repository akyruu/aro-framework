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
package com.avaryuon.fwk.core.bean.context.cache;

/**
 * Cache view scoped implementation.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class ViewScopedCache extends SimpleScopedCache {
	/* STATIC FIELDS ======================================================= */
	/* Singleton ----------------------------------------------------------- */
	private static ViewScopedCache instance;

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Singleton ----------------------------------------------------------- */
	public static ViewScopedCache instance() {
		if( instance == null ) {
			instance = new ViewScopedCache();
		}
		return instance;
	}

}
