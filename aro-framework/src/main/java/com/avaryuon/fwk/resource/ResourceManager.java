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
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

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
	private static final String RESOURCES_FOLDER_NAME = "resources";

	/* FIELDS ============================================================== */
	/* Resources ----------------------------------------------------------- */
	private ClassLoader loader;

	/* Beans --------------------------------------------------------------- */
	@Inject
	private ConfigManager configMgr;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Resources ----------------------------------------------------------- */
	/* Getting */
	public URL getResource( String name ) {
		return loader.getResource( name );
	}

	public InputStream getResourceAsStream( String name ) {
		return loader.getResourceAsStream( name );
	}

	/* SINGLETON =========================================================== */
	@PostConstruct
	void initialize() throws IOException {
		URL[] resourceURLs = new URL[0];

		/* Paths */
		Path installResourceFolder = Paths.get( ".", RESOURCES_FOLDER_NAME );
		if( isValidFolder( installResourceFolder ) ) {
			ArrayUtils.push( resourceURLs, installResourceFolder.toUri()
					.toURL() );
		}

		String appTitle = configMgr.getProperty( "app", "title" );
		Path userResourceFolder = Paths.get( System.getProperty( "user.home" ),
				"." + appTitle );
		if( isValidFolder( userResourceFolder ) ) {
			ArrayUtils.push( resourceURLs, userResourceFolder.toUri().toURL() );
		}

		URLClassLoader sysLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		URL jarResourceURL = new URL( sysLoader.getURLs()[ 0 ]
				+ RESOURCES_FOLDER_NAME );
		ArrayUtils.push( resourceURLs, jarResourceURL );

		/* Resources */
		loader = new URLClassLoader( resourceURLs );
	}

	/* TOOLS =============================================================== */
	private boolean isValidFolder( Path dir ) {
		return Files.exists( dir ) && Files.isDirectory( dir );
	}
}
