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
package com.avaryuon.fwk;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.fwk.util.LogUtils;

/**
 * <b>Manages view on ARO application.</b>
 * <p>
 * Rules for load :
 * <ul>
 * <li>Your FXML file name must be &lt;view_class_name&gt;.fxml</li>
 * <li>Your controller class name must be &lt;view_class_name&gt;Controller</li>
 * </ul>
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Singleton
public class ViewManager {
	/* STATIC FIELDS ======================================================= */
	private static final String FXML_FILE_EXTENSION = ".fxml";

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ViewManager.class );

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Loading ------------------------------------------------------------- */
	public void loadView( Class< ? > viewClass ) {
		FXMLLoader loader = new FXMLLoader();
		if( initLoader( loader, viewClass ) ) {
			loadScene( loader );
		}
	}

	private boolean initLoader( FXMLLoader loader, Class< ? > viewClass ) {
		boolean success = true;

		loader.setCharset( StandardCharsets.UTF_8 );

		String fxml = viewClass.getSimpleName() + FXML_FILE_EXTENSION;
		URL location = viewClass.getResource( fxml );
		if( location == null ) {
			LOGGER.error( "FXML file \"{}/{}\" is not found.", viewClass
					.getPackage().getName().replace( ".", "/" ), fxml );
			success = false;
		}
		loader.setLocation( location );

		return success;
	}

	private void loadScene( FXMLLoader loader ) {
		try {
			loader.load();
		} catch( IOException ex ) {
			LogUtils.logError( LOGGER, "Load of FXML \"{}\" failed.", ex,
					loader.getLocation() );
		}

		Parent root = loader.getRoot();
		if( root != null ) {
			Scene scene = new Scene( root );
			AroApplication.instance().getMainStage().setScene( scene );
		}
	}
}
