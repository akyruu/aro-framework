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
package com.avaryuon.commons.io.file;

import java.io.File;

/**
 * Constants of files. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class FileContants {
	/* STATIC FIELDS ======================================================= */
	public static final String ARCHIVE_SUBPATH_SEPARATOR = "!";
	public static final String EXTENSION_SEPARATOR = ".";
	public static final String PATH_SEPARATOR = "/";
	public static final String PLATERFORM_PATH_SEPARATOR = File.pathSeparator;

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private FileContants() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	// Nothing here
}
