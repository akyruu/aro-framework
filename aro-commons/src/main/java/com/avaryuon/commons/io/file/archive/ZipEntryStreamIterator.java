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

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZIP entry iterator from ZIP stream.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
final class ZipEntryStreamIterator implements Iterator< ZipEntry > {
	/* STATIC FIELDS ======================================================= */
	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ZipEntryStreamIterator.class );

	/* FIELDS ============================================================== */
	private ZipInputStream zipStream;
	private ZipEntry zipEntry;

	private boolean read;

	/* CONSTRUCTORS ======================================================== */
	public ZipEntryStreamIterator( ZipInputStream zipStream ) {
		this.zipStream = zipStream;
		read = true;
	}

	/* METHODS ============================================================= */
	/* Overriding - Iterator.class ----------------------------------------- */
	/* Check */
	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return readNext();
	}

	/* Next */
	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public ZipEntry next() {
		if( !hasNext() ) {
			throw new NoSuchElementException();
		}
		read = true;
		return zipEntry;

	}

	private boolean readNext() {
		if( read ) {
			zipEntry = null;
			try {
				zipEntry = zipStream.getNextEntry();
			} catch( IOException ex ) {
				LOGGER.error( "An error occured during reading", ex );
				throw new NoSuchElementException( ex.getMessage() );
			}
			read = false;
		}
		return zipEntry != null;
	}

	/* Remove */
	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new IllegalStateException();
	}
}
