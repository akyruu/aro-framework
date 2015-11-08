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
package com.avaryuon.fwk.core.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.avaryuon.commons.xml.XmlUtils;
import com.avaryuon.fwk.AroApplication;
import com.avaryuon.fwk.core.bean.BeanManager;
import com.avaryuon.fwk.core.i18n.I18nManager;

/**
 * <b>Manager configuration files, properties and preferences in ARO
 * application</b>
 * <p>
 * Use META-INF/aro-config.xml for initialize configuration.
 * </p>
 * <p>
 * Lists of specific property group :
 * <ul>
 * <li>'system': call System.getProperty()</li>
 * <li>'app' : shortcut for 'application'</li>
 * </ul>
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Singleton
public class ConfigManager {
	/* STATIC FIELDS ======================================================= */
	/* Configuration ------------------------------------------------------- */
	private static final String CONFIG_FILE_NAME = "/META-INF/aro-config.xml";

	/* Properties ---------------------------------------------------------- */
	private static final String SYSTEM_GROUP = "system";
	private static final String APP_GROUP = "app";
	private static final String APPLICATION_GROUP = "application";

	/* Specific ------------------------------------------------------------ */
	public static final String BUNDLE_KEY_PREFIX = "%";

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ConfigManager.class );

	/* FIELDS ============================================================== */
	/* Properties ---------------------------------------------------------- */
	private Map< String, Properties > propertyGroups;

	/* Beans --------------------------------------------------------------- */
	@Inject
	private AroApplication app;
	@Inject
	private BeanManager beanMgr;

	/* CONSTRUCTORS ======================================================== */
	/* Initialization ------------------------------------------------------ */
	@PostConstruct
	void initialize() {
		LOGGER.info( "Initialize configuration manager..." );

		/* Properties */
		propertyGroups = new HashMap<>();

		InputStream configFileStream = app.getClass().getResourceAsStream(
				CONFIG_FILE_NAME );
		if( configFileStream != null ) {
			LOGGER.warn( "Config file found: " + CONFIG_FILE_NAME );
			try {
				XmlUtils.parseXmlFile( configFileStream, new ConfigHandler(
						propertyGroups ) );
			} catch( SAXException ex ) {
				LOGGER.warn( "Could not parse config file", ex );
			}
		} else {
			LOGGER.warn( "Config file not found: " + CONFIG_FILE_NAME );
		}

		LOGGER.info( "Configuration manager is initialized !" );
	}

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	/**
	 * Use lazy load otherwise throw circular dependencies.
	 * 
	 * @return Instance of I18nManager.
	 */
	private I18nManager getI18nMgr() {
		return beanMgr.getBean( I18nManager.class );
	}

	/* Properties ---------------------------------------------------------- */
	/**
	 * Gets property from configuration.
	 * <p>
	 * Call method getProperty( group, name, null ).
	 * </p>
	 * 
	 * @param group
	 *            Group of properties.
	 * @param name
	 *            Name of property.
	 * 
	 * @return A string or null if group or property not found.
	 */
	public String getProperty( String group, String name ) {
		return getProperty( group, name, null );
	}

	public String getProperty( PropKey propKey ) {
		return getProperty( propKey.group(), propKey.key(),
				propKey.defaultValue() );
	}

	/**
	 * Gets property from configuration.
	 * 
	 * @param group
	 *            Group of properties.
	 * @param name
	 *            Name of property.
	 * @param defaultValue
	 *            Value to return if property not found.
	 * 
	 * @return A string or defaultValue if group or property not found.
	 */
	public String getProperty( String group, String name, String defaultValue ) {
		String propertyValue = null;

		if( SYSTEM_GROUP.equals( group ) ) {
			propertyValue = System.getProperty( name );
		} else {
			String realGroup = APP_GROUP.equals( group ) ? APPLICATION_GROUP
					: group;
			Properties properties = propertyGroups.get( realGroup );
			propertyValue = (properties == null) ? defaultValue : properties
					.getProperty( name, defaultValue );
			if( (propertyValue != null)
					&& propertyValue.startsWith( BUNDLE_KEY_PREFIX ) ) {
				String msgKey = propertyValue.substring( BUNDLE_KEY_PREFIX
						.length() );
				propertyValue = getI18nMgr().getBundleMessage( msgKey );
			}
		}

		return propertyValue;
	}
}
