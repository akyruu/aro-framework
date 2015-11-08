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
package com.avaryuon.fwk.view;

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
import com.avaryuon.fwk.core.bean.BeanManager;
import com.avaryuon.fwk.core.i18n.I18nManager;
import com.avaryuon.fwk.javafx.JavaFXFileExtension;
import com.avaryuon.fwk.javafx.util.AroBuilderFactory;

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
	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ViewManager.class );

	/* FIELDS ============================================================== */
	/* Beans --------------------------------------------------------------- */
	@Inject
	private AroApplication app;
	@Inject
	private BeanManager beanMgr;
	@Inject
	private I18nManager i18nMgr;
	@Inject
	private AroBuilderFactory buildFct;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Loading ------------------------------------------------------------- */
	public < V extends View > V buildView( Class< V > viewClass ) {
		V view = null;

		FXMLLoader loader = new FXMLLoader();
		if( initLoader( loader, viewClass ) ) {
			// Build root
			Parent root = buildRoot( loader );
			if( root == null ) {
				return view;
			}

			// Build view
			try {
				view = viewClass.newInstance();
			} catch( InstantiationException | IllegalAccessException ex ) {
				LogUtils.logError(
						LOGGER,
						"View \"{}\" must be implements a public default constructor.",
						ex, viewClass.getCanonicalName() );
				return null;
			}

			view.setRoot( root );
			Object controller = loader.getController();
			if( controller != null ) {
				view.setController( controller );
				if( controller instanceof Controller ) {
					((Controller) controller).setView( view );
				}
			}
		}
		return view;
	}

	public void loadView( Class< ? extends View > viewClass ) {
		View view = buildView( viewClass );
		if( view != null ) {
			Scene scene = new Scene( view.getRoot() );
			view.setScene( scene );
			app.loadScene( scene );
		}
	}

	/* Initialization ------------------------------------------------------ */
	private boolean initLoader( FXMLLoader loader, Class< ? > viewClass ) {
		boolean success = true;

		loader.setBuilderFactory( buildFct );
		loader.setCharset( StandardCharsets.UTF_8 );
		loader.setResources( i18nMgr.getBundle() );

		String fxml = viewClass.getSimpleName()
				+ JavaFXFileExtension.FXML.extension();
		URL location = viewClass.getResource( fxml );
		if( location == null ) {
			LOGGER.error( "FXML file \"{}/{}\" is not found.", viewClass
					.getPackage().getName().replace( ".", "/" ), fxml );
			success = false;
		}
		loader.setLocation( location );

		Controller controller = buildController( viewClass );
		if( controller != null ) {
			loader.setController( controller );
		}

		return success;
	}

	/* Building ------------------------------------------------------------ */
	private Parent buildRoot( FXMLLoader loader ) {
		try {
			loader.load();
		} catch( IOException ex ) {
			LogUtils.logError( LOGGER, "Load of FXML \"{}\" failed.", ex,
					loader.getLocation() );
		}

		return loader.getRoot();
	}

	private Controller buildController( Class< ? > viewClass ) {
		// Find controller class
		Class< ? > ctrlClass = null;

		String ctrlClassSuffix = Controller.class.getSimpleName();
		String ctrlClassName = viewClass.getCanonicalName() + ctrlClassSuffix;
		try {
			ctrlClass = viewClass.getClassLoader().loadClass( ctrlClassName );
		} catch( ClassNotFoundException ex ) {
			LogUtils.logTrace( LOGGER, "Controller \"{}\" don't exists.", ex,
					ctrlClassName );
		}

		// Build controller
		Controller ctrl = null;
		if( ctrlClass != null ) {
			try {
				ctrl = (Controller) beanMgr.getBean( ctrlClass );
			} catch( ClassCastException ex ) {
				LogUtils.logError( LOGGER,
						"Controller \"{}\" must be extends \"{}\".", ex,
						ctrlClass.getCanonicalName(),
						Controller.class.getCanonicalName() );
			}
		}
		return ctrl;
	}
}
