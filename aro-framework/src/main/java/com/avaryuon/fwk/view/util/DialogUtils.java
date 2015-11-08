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
package com.avaryuon.fwk.view.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Contains utility methods for Java FX dialogs. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class DialogUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private DialogUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Building ------------------------------------------------------------ */
	public static Node buildExpansion( Throwable t ) {
		GridPane expension = new GridPane();
		expension.setMaxWidth( Double.MAX_VALUE );
		expension.add( new Label( "Trace:" ), 0, 0 );

		StringWriter stackTrace = new StringWriter();
		t.printStackTrace( new PrintWriter( stackTrace ) );

		TextArea textArea = new TextArea( stackTrace.toString() );
		textArea.setEditable( false );
		textArea.setWrapText( true );
		textArea.setMaxWidth( Double.MAX_VALUE );
		textArea.setMaxHeight( Double.MAX_VALUE );
		GridPane.setVgrow( textArea, Priority.ALWAYS );
		GridPane.setHgrow( textArea, Priority.ALWAYS );
		expension.add( textArea, 0, 1 );
		return expension;
	}
}
