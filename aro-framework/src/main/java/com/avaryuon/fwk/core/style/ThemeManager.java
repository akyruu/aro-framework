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
package com.avaryuon.fwk.core.style;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.avaryuon.fwk.core.resource.ResourceManager;

/**
 * <b>Manages the themes in ARO application.</b>
 * <p>
 * Use the ResourceManager for found themes. Scan only folders "themes" directly
 * in resources folder.
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Singleton
public class ThemeManager {
	/* STATIC FIELDS ======================================================= */
	/* Base ---------------------------------------------------------------- */
	private static final String BASE_FOLDER_NAME = "themes/";
	
	/* Icon ---------------------------------------------------------------- */
	private static final String ICON_FOLDER_NAME = BASE_FOLDER_NAME + "icons/";
	private static final String[] ICON_EXTENSIONS = new String[] { "png" };

	/* FIELDS ============================================================== */
	/* Beans --------------------------------------------------------------- */
	@Inject
	private ResourceManager resourceMgr;

	/* CONSTRUCTORS ======================================================== */
	@PostConstruct
	void initialize() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Icon ---------------------------------------------------------------- */
	public URL getIconURL( String name, int size ) {
		URL icon = null;

		for( String iconExt : ICON_EXTENSIONS ) {
			icon = resourceMgr.getResource( ICON_FOLDER_NAME + size
					+ "/" + name + "." + iconExt );
			if( icon != null ) {
				break;
			}
		}

		return icon;
	}

}
