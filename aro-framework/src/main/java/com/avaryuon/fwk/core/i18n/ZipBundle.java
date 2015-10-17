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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.avaryuon.commons.io.file.archive.ZipUtils;

/**
 * <b>Localized bundle.</b>
 * <p>
 * Used by I18nManager.
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class ZipBundle extends ResourceBundle {
	/* STATIC FIELDS ======================================================= */
	private static final String BUNDLE_NAME_PATTERN = "(.+/)*(.+)\\.properties";

	/* FIELDS ============================================================== */
	private Map< String, Object > lookup;

	/* CONSTRUCTORS ======================================================== */
	public ZipBundle( ZipInputStream zipStream ) throws IOException {
		lookup = new HashMap<>();

		Iterator< ZipEntry > it = ZipUtils.getZipEntryIterator( zipStream );
		while( it.hasNext() ) {
			ZipEntry zipEntry = it.next();

			String bundleName = toBundleName( zipEntry.getName() );
			if( bundleName != null ) {
				InputStream zipEntryStream = ZipUtils.getInputStream(
						zipStream, zipEntry );
				Properties props = new Properties();
				props.load( zipEntryStream );
				for( Entry< Object, Object > propEntry : props.entrySet() ) {
					String propName = toPropertyName( bundleName,
							propEntry.getKey() );
					lookup.put( propName, propEntry.getValue() );
				}
			}
		}
	}

	/* METHODS ============================================================= */
	/* Bundle -------------------------------------------------------------- */
	private String toBundleName( String entryName ) {
		Pattern pattern = Pattern.compile( BUNDLE_NAME_PATTERN );
		Matcher matcher = pattern.matcher( entryName );
		return matcher.find() ? matcher.group( 2 ) : null;
	}

	private String toPropertyName( String bundleName, Object propName ) {
		return bundleName + I18nConstants.BUNDLE_MESSAGE_SEPARATOR + propName;
	}

	/* Object -------------------------------------------------------------- */
	public String getString( String bundleName, String key ) {
		return getString( toPropertyName( bundleName, key ) );
	}

	public String getStringArray( String bundleName, String key ) {
		return getString( toPropertyName( bundleName, key ) );
	}

	/* Overriding - ResourceBundle ----------------------------------------- */
	/**
	 * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
	 */
	@Override
	protected Object handleGetObject( String key ) {
		if( key == null ) {
			throw new NullPointerException();
		}

		String bundleKey = key
				.contains( I18nConstants.BUNDLE_MESSAGE_SEPARATOR ) ? key
				: I18nConstants.DEFAULT_BUNDLE_NAME
						+ I18nConstants.BUNDLE_MESSAGE_SEPARATOR + key;
		return lookup.get( bundleKey );
	}

	/**
	 * @see java.util.ResourceBundle#getKeys()
	 */
	@Override
	public Enumeration< String > getKeys() {
		return Collections.enumeration( lookup.keySet() );
	}

	/**
	 * @see java.util.ResourceBundle#handleKeySet()
	 */
	@Override
	protected Set< String > handleKeySet() {
		return lookup.keySet();
	}
}
