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
package com.avaryuon.fwk.util.fx;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Contains utility methods for Java FX alerts. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class AlertUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private AlertUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Showing ------------------------------------------------------------- */
	public static Optional< ButtonType > showInfo( String title,
			String headerText, String contentText ) {
		return show( AlertType.INFORMATION, title, headerText, contentText );
	}

	public static Optional< ButtonType > showWarn( String title,
			String headerText, String contentText ) {
		return show( AlertType.WARNING, title, headerText, contentText );
	}

	public static Optional< ButtonType > showError( String title,
			String headerText, String contentText ) {
		return show( AlertType.ERROR, title, headerText, contentText );
	}

	public static Optional< ButtonType > show( AlertType type, String title,
			String headerText, String contentText ) {
		Alert alert = new Alert( (type == null) ? AlertType.NONE : type );
		alert.setTitle( title );
		alert.setHeaderText( headerText );
		alert.setContentText( contentText );

		return alert.showAndWait();
	}
}
