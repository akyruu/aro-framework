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

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.avaryuon.fwk.AroApplication;

/**
 * <b>Manages dialog on ARO application.</b>
 * <p>
 * Rules for load :
 * <ul>
 * <li>Your FXML file name must be &lt;dialog_class_name&gt;.fxml</li>
 * <li>Your controller class name must be &lt;dialog_class_name&gt;Controller</li>
 * </ul>
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Singleton
public class DialogManager {
	/* STATIC FIELDS ======================================================= */
	private static final String NO_TITLE = "";
	private static final Modality NO_MODALITY = Modality.NONE;

	/* FIELDS ============================================================== */
	/* Beans --------------------------------------------------------------- */
	@Inject
	private AroApplication app;
	@Inject
	private ViewManager viewMgr;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Loading ------------------------------------------------------------- */
	public < D extends Dialog > D buildDialog( Class< D > dialogClass ) {
		return buildDialog( dialogClass, NO_TITLE );
	}

	public < D extends Dialog > D buildDialog( Class< D > dialogClass,
			String title ) {
		return buildDialog( dialogClass, title, NO_MODALITY );
	}

	public < D extends Dialog > D buildDialog( Class< D > dialogClass,
			String title, Modality modality ) {
		D dialog = viewMgr.buildView( dialogClass );

		Stage stage = new Stage();
		stage.setTitle( title );
		stage.initModality( modality );
		stage.initOwner( app.getMainStage() );
		dialog.setStage( stage );

		Scene scene = new Scene( dialog.getRoot() );
		stage.setScene( scene );
		dialog.setScene( scene );

		return dialog;
	}

	/* Opening ------------------------------------------------------------- */
	public void open( Class< ? extends Dialog > dialogClass ) {
		open( dialogClass, NO_TITLE );
	}

	public void open( Class< ? extends Dialog > dialogClass, String title ) {
		open( dialogClass, title, NO_MODALITY );
	}

	public void open( Class< ? extends Dialog > dialogClass, String title,
			Modality modality ) {
		Dialog dialog = buildDialog( dialogClass, title, modality );
		if( dialog != null ) {
			dialog.openAndWait();
		}
	}
}
