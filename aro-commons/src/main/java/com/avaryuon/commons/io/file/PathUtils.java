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

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.StringUtils;
import com.avaryuon.commons.net.URLConstants;

/**
 * Contains utility methods for files. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class PathUtils {
	/* STATIC FIELDS ======================================================= */
	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( PathUtils.class );

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private PathUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Building ------------------------------------------------------------ */
	/**
	 * Builds path from string (string must be indicate an URL too).
	 * 
	 * @param pathStr
	 *            Path as string.
	 * @return Path builded or null if not valid.
	 */
	public static Path getPath( String pathStr ) {
		Path path = null;

		if( StringUtils.isNotBlank( pathStr ) ) {
			if( pathStr.matches( "(.{3,}:)+(.+)" ) ) {
				path = getPathFromStringURL( pathStr );
			} else {
				path = Paths.get( pathStr );
			}
		}

		return path;
	}

	/**
	 * Builds path from URL. Hierarchical URLs is supported.
	 * 
	 * @param url
	 * @return Path builded or null if not valid.
	 */
	public static Path getPath( URL url ) {
		Path path = null;

		// Build path from URL
		if( url != null ) {
			String urlStr = url.toExternalForm();
			path = getPathFromStringURL( urlStr );
		}

		// Check and return path
		if( path == null ) {
			LOGGER.error( "URL \"{}\" isn't a valid file protocol", url );
		}
		return path;
	}

	private static Path getPathFromStringURL( String urlStr ) {
		Path path = null;

		String urlPath = urlStr;
		int sep = -1;
		while( path == null
				&& (sep = urlPath.indexOf( URLConstants.PROTOCOL_SEPARATOR )) >= 0 ) {
			String protocol = urlPath.substring( 0, sep );
			urlPath = urlPath.substring( sep + 1 );

			if( URLConstants.PROTOCOL_FILE.equals( protocol ) ) {
				path = Paths.get( urlPath );
			}
		}

		return path;
	}

	/* File ---------------------------------------------------------------- */
	public static String getFileName( Path file ) {
		return file.getFileName().toString();
	}

	/* Extension ----------------------------------------------------------- */
	public static boolean hasExtension( Path file, String extension ) {
		String fileName = getFileName( file );
		return fileName.endsWith( FileContants.EXTENSION_SEPARATOR + extension );
	}

	public static String getExtension( Path file ) {
		String fileName = getFileName( file );
		int sep = fileName.lastIndexOf( FileContants.EXTENSION_SEPARATOR );
		return (sep < 0) ? null : fileName.substring( sep + 1 );
	}

}
