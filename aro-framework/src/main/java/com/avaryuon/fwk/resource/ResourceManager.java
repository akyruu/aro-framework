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
package com.avaryuon.fwk.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.fwk.AroApplication;
import com.avaryuon.fwk.config.ConfigManager;
import com.avaryuon.fwk.util.ArrayUtils;

/**
 * <b>Manages resources in ARO application</b>
 * <p>
 * Use three root directories :
 * <ul>
 * <li>&lt;install_path&gt;/resources</li>
 * <li>&lt;user_folder&gt;/.&lt;app_title&gt;</li>
 * <li>&lt;app_jar&gt;!/resources</li>
 * </ul>
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Singleton
public class ResourceManager {
	/* STATIC FIELDS ======================================================= */
	/* Paths --------------------------------------------------------------- */
	private static final String RESOURCES_FOLDER_NAME = "resources";

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ResourceManager.class );

	private static final String RESOURCE_FOUND_LOG = "Resource found: {}";

	/* FIELDS ============================================================== */
	/* Resources ----------------------------------------------------------- */
	private ClassLoader loader;

	/* Beans --------------------------------------------------------------- */
	@Inject
	private AroApplication app;

	@Inject
	private ConfigManager configMgr;

	/* CONSTRUCTORS ======================================================== */
	/* Initialization ------------------------------------------------------ */
	@PostConstruct
	void initialize() throws IOException {
		LOGGER.info( "Initialize resources manager..." );

		URL[] resourceURLs = new URL[0];

		/* Paths */
		Path installResourceFolder = Paths.get( RESOURCES_FOLDER_NAME + "/" );
		if( isValidFolder( installResourceFolder ) ) {
			URL resourceURL = format( installResourceFolder.toUri().toURL() );
			resourceURLs = ArrayUtils.push( resourceURLs, resourceURL );
			LOGGER.info( RESOURCE_FOUND_LOG, resourceURL );
		}

		String appTitle = configMgr.getProperty( "app", "title" );
		if( appTitle != null ) {
			Path userResourceFolder = Paths.get(
					configMgr.getProperty( "system", "user.home" ), "."
							+ appTitle.toLowerCase() + "/" );
			if( isValidFolder( userResourceFolder ) ) {
				URL resourceURL = userResourceFolder.toUri().toURL();
				resourceURLs = ArrayUtils.push( resourceURLs, resourceURL );
				LOGGER.info( RESOURCE_FOUND_LOG, resourceURL );
			}
		}

		URL jarResourceURL = app.getClass().getResource(
				"/" + RESOURCES_FOLDER_NAME );
		if( jarResourceURL != null ) {
			jarResourceURL = format( jarResourceURL );
			resourceURLs = ArrayUtils.push( resourceURLs, jarResourceURL );
			LOGGER.info( RESOURCE_FOUND_LOG, jarResourceURL );
		}

		/* Resources */
		if( resourceURLs.length == 0 ) {
			LOGGER.info( "No resources found." );
		}
		loader = new URLClassLoader( resourceURLs );

		LOGGER.info( "Resources manager is initialized !" );
	}

	/* METHODS ============================================================= */
	/* Resources ----------------------------------------------------------- */
	/* Getting */
	public URL getResource( String name ) {
		return loader.getResource( name );
	}

	public InputStream getResourceAsStream( String name ) {
		return loader.getResourceAsStream( name );
	}

	/* TOOLS =============================================================== */
	private boolean isValidFolder( Path dir ) {
		return Files.exists( dir ) && Files.isDirectory( dir );
	}

	private URL format( URL url ) throws MalformedURLException {
		String externalForm = url.toExternalForm();
		return externalForm.endsWith( "/" ) ? url
				: new URL( externalForm + "/" );
	}
}
