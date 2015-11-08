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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.LogUtils;
import com.avaryuon.commons.StringUtils;
import com.avaryuon.fwk.core.resource.ResourceManager;

/**
 * Manages the localized messages.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Singleton
public class I18nManager {
	/* STATIC FIELDS ======================================================= */
	/* Base ---------------------------------------------------------------- */
	private static final String BASE_FOLDER_NAME = "i18n/";

	private static final Control DEFAULT_CONTROL = new I18nControl();

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( I18nManager.class );

	private static final String LOG_BUNDLE_NOT_FOUND = "The bundle '{}' is not found.";
	private static final String LOG_MESSAGE_NOT_FOUND = "The message '{}' in bundle '{}' is not found.";

	/* FIELDS ============================================================== */
	private Locale locale;
	private ZipBundle zipBundle;

	/* Beans --------------------------------------------------------------- */
	@Inject
	private ResourceManager resourceMgr;

	/* CONSTRUCTORS ======================================================== */
	@PostConstruct
	void initialize() {
		// Nothing to do
		setLocale( Locale.getDefault() );
	}

	/* METHODS ============================================================= */
	/* Locale -------------------------------------------------------------- */
	public Locale getLocale() {
		return locale;
	}

	public void setLocale( Locale newLocale ) {
		locale = newLocale;
		zipBundle = null;

		try {
			zipBundle = (ZipBundle) ResourceBundle.getBundle( BASE_FOLDER_NAME,
					locale, resourceMgr.getClassLoader(), DEFAULT_CONTROL );
		} catch( MissingResourceException ex ) {
			LogUtils.logTrace( LOGGER, "No ZIP bundle found for locale {}", ex,
					newLocale );
		}
	}

	/* Bundle -------------------------------------------------------------- */
	public ResourceBundle getBundle() {
		ResourceBundle bundle = null;
		try {
			return getBundleImpl( I18nConstants.DEFAULT_BUNDLE_NAME );
		} catch( MissingResourceException ex ) {
			LogUtils.logTrace( LOGGER, LOG_BUNDLE_NOT_FOUND, ex,
					I18nConstants.DEFAULT_BUNDLE_NAME );
		}
		return bundle;
	}

	public ResourceBundle getBundle( String bundleName ) {
		ResourceBundle bundle = null;
		try {
			return getBundleImpl( bundleName );
		} catch( MissingResourceException ex ) {
			LogUtils.logError( LOGGER, LOG_BUNDLE_NOT_FOUND, ex, bundleName );
		}
		return bundle;
	}

	private ResourceBundle getBundleImpl( String bundleName )
			throws MissingResourceException {
		ResourceBundle bundle = zipBundle;
		if( bundle == null ) {
			ClassLoader loader = resourceMgr.getClassLoader();
			bundle = ResourceBundle.getBundle( BASE_FOLDER_NAME + bundleName,
					locale, loader );
		}
		return bundle;
	}

	/* Message ------------------------------------------------------------- */
	public String getBundleMessage( String msgKey, Object... args ) {
		return getBundleMessage( I18nConstants.DEFAULT_BUNDLE_NAME, msgKey,
				args );
	}

	public String getBundleMessage( MsgKey msgKey, Object... args ) {
		return getBundleMessage( msgKey.bundleName(), msgKey.key(), args );
	}

	public String getBundleMessage( String bundleName, String msgKey,
			Object... args ) {
		String message = null;
		if( zipBundle != null ) {
			message = zipBundle.getString( bundleName, msgKey );
		}
		if( message == null ) {
			ResourceBundle bundle = getBundle( bundleName );
			if( bundle != null ) {
				try {
					message = bundle.getString( msgKey );
				} catch( MissingResourceException ex ) {
					LOGGER.trace( "Failed to find bundle message", ex );
					LOGGER.error( LOG_MESSAGE_NOT_FOUND, msgKey, bundleName );
				}
			} else {
				LOGGER.error( LOG_BUNDLE_NOT_FOUND, bundleName );
			}
		}
		return (message == null) ? "???" + msgKey + "???" : StringUtils.format(
				message, args );
	}

}
