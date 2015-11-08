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
package com.avaryuon.fwk.core.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.io.file.FileContants;
import com.avaryuon.fwk.AroApplication;
import com.avaryuon.fwk.AroPropKey;
import com.avaryuon.fwk.core.config.ConfigManager;

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
	private static final String RESOURCES_FOLDER_PATH = FileContants.PATH_SEPARATOR
			+ RESOURCES_FOLDER_NAME;

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ResourceManager.class );

	private static final String LOG_RESOURCE_FOUND = "Resource found: {}";

	/* FIELDS ============================================================== */
	/* Resources ----------------------------------------------------------- */
	@Getter
	private ClassLoader classLoader;
	@Getter
	private Path tempFolderPath;

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

		List< URL > resourceURLs = new ArrayList<>();

		/* Paths */
		Path installResourceFolder = Paths.get( RESOURCES_FOLDER_NAME + "/" );
		if( isValidFolder( installResourceFolder ) ) {
			URL resourceURL = installResourceFolder.toUri().toURL();
			addResourceUrlIfNotNull( resourceURLs, resourceURL );
		}

		String appName = configMgr.getProperty( AroPropKey.NAME );
		if( appName != null ) {
			Path userResourceFolder = Paths.get(
					configMgr.getProperty( "system", "user.home" ), "."
							+ formatTitle( appName )
							+ FileContants.PATH_SEPARATOR );
			if( isValidFolder( userResourceFolder ) ) {
				URL resourceURL = userResourceFolder.toUri().toURL();
				addResourceUrlIfNotNull( resourceURLs, resourceURL );
			}
		}

		URL jarResourceURL = app.getClass().getResource( RESOURCES_FOLDER_PATH );
		addResourceUrlIfNotNull( resourceURLs, jarResourceURL );

		Enumeration< URL > en = app.getClass().getClassLoader()
				.getResources( RESOURCES_FOLDER_NAME );
		while( en.hasMoreElements() ) {
			URL resourceURL = en.nextElement();
			if( !resourceURL.equals( jarResourceURL ) ) {
				addResourceUrlIfNotNull( resourceURLs, resourceURL );
			}
		}

		/* Resources */
		if( resourceURLs.isEmpty() ) {
			LOGGER.info( "No resources found." );
		}
		classLoader = new URLClassLoader(
				resourceURLs.toArray( new URL[resourceURLs.size()] ) );

		/* Temporary */
		tempFolderPath = Files.createTempDirectory( appName );
		LOGGER.info( "Tempory path: {}", tempFolderPath );

		LOGGER.info( "Resources manager is initialized !" );
	}

	private void addResourceUrlIfNotNull( List< URL > resourceURLs,
			URL resourceURL ) throws MalformedURLException {
		if( resourceURL != null ) {
			// Format URL
			String externalForm = resourceURL.toExternalForm();
			URL formattedURL = externalForm
					.endsWith( FileContants.PATH_SEPARATOR ) ? resourceURL
					: new URL( externalForm + FileContants.PATH_SEPARATOR );

			// Add URL
			resourceURLs.add( formattedURL );
			LOGGER.info( LOG_RESOURCE_FOUND, formattedURL );
		}
	}

	/* METHODS ============================================================= */
	/* Resources ----------------------------------------------------------- */
	/* Getting */
	public URL getResource( String name ) {
		return classLoader.getResource( name );
	}

	public InputStream getResourceAsStream( String name ) {
		return classLoader.getResourceAsStream( name );
	}

	/* Temporary ----------------------------------------------------------- */
	public Path createTempFile( String fileName, FileAttribute< ? >... attrs )
			throws IOException {
		return Files.createTempFile( tempFolderPath, "~", fileName, attrs );
	}

	/* TOOLS =============================================================== */
	private boolean isValidFolder( Path dir ) {
		return Files.exists( dir ) && Files.isDirectory( dir );
	}

	private String formatTitle( String title ) {
		String formattedTitle = title.toLowerCase().replaceAll( "\\W", "" );
		formattedTitle = Normalizer.normalize( title, Normalizer.Form.NFD );
		formattedTitle = formattedTitle.replaceAll( "[^\\p{ASCII}]", "" );
		return formattedTitle;
	}
}
