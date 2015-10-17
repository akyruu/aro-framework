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

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains utility methods for files. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class FileUtils {
	/* STATIC FIELDS ======================================================= */
	/* Pattern ------------------------------------------------------------- */
	private static final Pattern PATTERN_ARCHIVE = Pattern
			.compile( "(.+)!(.+)" );

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( FileUtils.class );

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private FileUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Walking ------------------------------------------------------------- */
	/**
	 * Walks file tree.
	 * 
	 * @see java.nio.file.Files#walkFileTree;
	 * 
	 * @param startFolder
	 *            Starting folder.
	 * @param visitor
	 *            File visitor to invoke for each folder/file.
	 * @return A boolean : true if success, false otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             Root is null or isn't a folder.
	 */
	public static boolean walkFileTree( Path startFolder,
			FileVisitor< ? super Path > visitor ) {
		if( startFolder == null ) {
			throw new IllegalArgumentException( "Root path is required" );
		} else if( !Files.isDirectory( startFolder ) ) {
			throw new IllegalArgumentException( "Root path " + startFolder
					+ " isn't a folder" );
		}

		boolean success = false;
		try {
			String rootFolderName = startFolder.toString();
			Matcher matcher = PATTERN_ARCHIVE.matcher( rootFolderName );
			if( matcher.find() ) {
				Path archive = Paths.get( matcher.group( 1 ) );
				try( FileSystem fs = FileSystems.newFileSystem( archive, null ) ) {
					Files.walkFileTree( fs.getPath( matcher.group( 2 ) ),
							visitor );
				}
			} else {
				Files.walkFileTree( startFolder, visitor );
			}
			success = true;
		} catch( IOException ex ) {
			LOGGER.error( "An error occured during walk file tree", ex );
		}
		return success;
	}
}
