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
package com.avaryuon.fwk.core.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.CollectionUtils;
import com.avaryuon.commons.StringUtils;
import com.avaryuon.commons.io.file.FileContants;

/**
 * Internationalization control for build ZIP resource bundles.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class I18nControl extends Control {
	/* STATIC FIELDS ======================================================= */
	/* Control ------------------------------------------------------------- */
	public static final String FORMAT_ZIP = "java.zip";
	public static final String FORMAT_FOLDER = "java.folder";

	public static final List< String > FORMAT_DEFAULT = Collections
			.unmodifiableList( CollectionUtils.addFirstToCopy(
					Control.FORMAT_DEFAULT, FORMAT_ZIP, FORMAT_FOLDER ) );

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( I18nControl.class );

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Overriding - Control.class ------------------------------------------ */
	/**
	 * @see java.util.ResourceBundle.Control#getFormats(java.lang.String)
	 */
	@Override
	public List< String > getFormats( String baseName ) {
		if( baseName == null ) {
			throw new NullPointerException();
		}
		return FORMAT_DEFAULT;
	}

	/**
	 * @see java.util.ResourceBundle.Control#newBundle(java.lang.String,
	 *      java.util.Locale, java.lang.String, java.lang.ClassLoader, boolean)
	 */
	@Override
	public ResourceBundle newBundle( String baseName, Locale locale,
			String format, ClassLoader loader, boolean reload )
			throws IllegalAccessException, InstantiationException, IOException {
		// This code is inspired by super method newBundle()
		ResourceBundle bundle = null;
		if( format.equals( FORMAT_ZIP ) ) {
			bundle = newBundleZip( baseName, locale, loader, reload );
		} else if( format.equals( FORMAT_FOLDER ) ) {
			bundle = newBundleFolder( baseName, locale, loader );
		} else {
			bundle = super.newBundle( baseName, locale, format, loader, reload );
		}
		return bundle;
	}

	public ResourceBundle newBundleZip( String baseName, Locale locale,
			ClassLoader loader, boolean reload ) throws IOException {
		ResourceBundle bundle = null;

		String bundleName = toBundleName0( baseName, locale );
		if( StringUtils.isNotBlank( bundleName ) ) {
			String resourceName = toResourceName( bundleName,
					I18nConstants.BUNDLE_EXTENSION );
			if( resourceName == null ) {
				return null;
			}

			InputStream stream = null;
			try {
				stream = getInputStream( resourceName, loader, reload );
			} catch( PrivilegedActionException ex ) {
				LOGGER.trace( "Insufficient privileges", ex );
				throw (IOException) ex.getException();
			}
			if( stream != null ) {
				try {
					bundle = new ZipBundle( new ZipInputStream( stream ) );
				} finally {
					stream.close();
				}
			}
		}

		return bundle;
	}

	public ResourceBundle newBundleFolder( String baseName, Locale locale,
			ClassLoader loader ) throws IOException {
		ResourceBundle bundle = null;

		String folderName = toBundleName0( baseName, locale );
		if( StringUtils.isNotBlank( folderName ) ) {
			URL folderURL = loader.getResource( folderName
					+ FileContants.PATH_SEPARATOR );
			if( folderURL != null ) {
				// TODO
			}
		}

		return bundle;
	}

	/* Bundle -------------------------------------------------------------- */
	private String toBundleName0( String baseName, Locale locale ) {
		String bundleName = baseName;

		String localeName = locale.toString();
		if( !localeName.isEmpty() ) {
			if( bundleName.endsWith( FileContants.PATH_SEPARATOR ) ) {
				bundleName += localeName;
			} else {
				bundleName = localeName;
			}
		}

		return bundleName;
	}

	private InputStream getInputStream( String resourceName,
			ClassLoader loader, boolean reload )
			throws PrivilegedActionException {
		return AccessController
				.doPrivileged( new PrivilegedExceptionAction< InputStream >() {
					@Override
					public InputStream run() throws IOException {
						InputStream is = null;
						if( reload ) {
							URL url = loader.getResource( resourceName );
							if( url != null ) {
								URLConnection connection = url.openConnection();
								if( connection != null ) {
									// Disable caches to get fresh data
									connection.setUseCaches( false );
									is = connection.getInputStream();
								}
							}
						} else {
							is = loader.getResourceAsStream( resourceName );
						}
						return is;
					}
				} );
	}
}
