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
package com.avaryuon.commons.io.file.archive;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains utility methods for ZIP archives. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class ZipUtils {
	/* STATIC FIELDS ======================================================= */
	private static final int DEFAULT_BUFFER_SIZE = 2048;

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ZipUtils.class );

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private ZipUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Streaming ----------------------------------------------------------- */
	public static Iterator< ZipEntry > getZipEntryIterator(
			final ZipInputStream zipStream ) {
		return new ZipEntryStreamIterator( zipStream );
	}

	public static InputStream getInputStream( ZipInputStream zipStream,
			ZipEntry zipEntry ) {
		// Checks parameters
		if( zipStream == null ) {
			throw new IllegalArgumentException( "ZIP input stream is required" );
		} else if( zipEntry == null ) {
			throw new IllegalArgumentException( "ZIP entry is required" );
		}

		// Build byte array
		ByteArrayOutputStream entryStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int len = 0;
		try {
			while( (len = zipStream.read( buffer )) > 0 ) {
				entryStream.write( buffer, 0, len );
			}
		} catch( IOException ex ) {
			LOGGER.error( "An error occured during read of ZIP stream.", ex );
		}

		// Build input stream
		byte[] data = entryStream.toByteArray();
		return new ByteArrayInputStream( data );
	}
}
