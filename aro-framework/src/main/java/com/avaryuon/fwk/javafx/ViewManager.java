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
package com.avaryuon.fwk.javafx;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.LogUtils;
import com.avaryuon.fwk.AroApplication;
import com.avaryuon.fwk.core.i18n.I18nManager;
import com.avaryuon.fwk.javafx.fxml.AroBuilderFactory;

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
	/* Beans --------------------------------------------------------------- */
	@Inject
	private AroBuilderFactory buildFct;
	@Inject
	private I18nManager i18nMgr;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Loading ------------------------------------------------------------- */
	public < V extends View > V buildView( Class< V > viewClass ) {
		V view = null;

		FXMLLoader loader = new FXMLLoader();
		if( initLoader( loader, viewClass ) ) {
			Parent root = buildRoot( loader );
			if( root != null ) {
				try {
					view = viewClass.newInstance();
				} catch( InstantiationException | IllegalAccessException ex ) {
					LogUtils.logError(
							LOGGER,
							"View \"{}\" must be implements a public default constructor.",
							ex, viewClass );
				}
				view.setRoot( root );
				view.setController( loader.getController() );
			}
		}

		return view;
	}

	public void loadView( Class< ? extends View > viewClass ) {
		View view = buildView( viewClass );
		if( view != null ) {
			Scene scene = new Scene( view.getRoot() );
			AroApplication.instance().loadScene( scene );
		}
	}

	private boolean initLoader( FXMLLoader loader, Class< ? > viewClass ) {
		boolean success = true;

		loader.setBuilderFactory( buildFct );
		loader.setCharset( StandardCharsets.UTF_8 );
		loader.setResources( i18nMgr.getBundle() );

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

	private Parent buildRoot( FXMLLoader loader ) {
		try {
			loader.load();
		} catch( IOException ex ) {
			LogUtils.logError( LOGGER, "Load of FXML \"{}\" failed.", ex,
					loader.getLocation() );
		}

		return loader.getRoot();
	}
}
