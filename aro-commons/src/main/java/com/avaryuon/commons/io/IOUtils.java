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
package com.avaryuon.commons.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.LogUtils;

/**
 * Contains utility methods for Input/Output streams. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class IOUtils {
	/* STATIC FIELDS ======================================================= */
	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( IOUtils.class );

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private IOUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* From String --------------------------------------------------------- */
	/**
	 * Builds a new input stream from string. Return null if string is null.
	 * 
	 * @param string
	 *            String to write in stream.
	 * @return An InputStream or null.
	 */
	public static InputStream getInputStream( String string ) {
		return (string == null) ? null : new ByteArrayInputStream(
				string.getBytes() );
	}

	/**
	 * Builds a new output stream from string. Return null if string is null or
	 * an IOException generated.
	 * 
	 * @param string
	 *            String to write in stream.
	 * @return An OutputStream or null.
	 */
	public static OutputStream getOutputStream( String string ) {
		ByteArrayOutputStream os = null;
		if( string != null ) {
			os = new ByteArrayOutputStream();
			try {
				os.write( string.getBytes() );
			} catch( IOException ex ) {
				LogUtils.logError( LOGGER,
						"Failed to create OutputStream from string : {}", ex,
						string );
			}
		}
		return os;
	}

	/* To string ----------------------------------------------------------- */
	/**
	 * Reads input stream in a string.
	 * 
	 * @param stream
	 *            Stream to read.
	 * @return A String or null.
	 */
	public static String toString( InputStream stream ) {
		StringBuilder builder = new StringBuilder();

		if( stream != null ) {
			BufferedReader reader = new BufferedReader( new InputStreamReader(
					stream ) );

			String line;
			try {
				while( (line = reader.readLine()) != null ) {
					builder.append( line ).append( "\n" );
				}
			} catch( IOException ex ) {
				LOGGER.error( "Failed to read input stream", ex );
			}
		}

		return (builder == null) ? null : builder.toString();
	}
}
