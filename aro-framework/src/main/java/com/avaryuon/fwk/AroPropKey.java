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
package com.avaryuon.fwk;

import com.avaryuon.fwk.core.config.PropKey;

/**
 * Keys of ARO properties.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public enum AroPropKey implements PropKey {
	/* VALUES ============================================================== */
	NAME( "aro" ), TITLE( "ARO application" ),

	/* Icons --------------------------------------------------------------- */
	ICON_NAME, ICON_SIZES;

	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private String defaultValue;

	/* CONSTRUCTORS ======================================================== */
	private AroPropKey() {
		this( null );
	}

	private AroPropKey( String defaultValue ) {
		this.defaultValue = defaultValue;
	}

	/* METHODS ============================================================= */
	/* Overriding - PropKey.class ------------------------------------------ */
	@Override
	public String group() {
		return "app";
	}

	@Override
	public String defaultValue() {
		return defaultValue;
	}
}
