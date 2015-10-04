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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import lombok.Getter;

import com.athaydes.automaton.FXer;

/**
 * Tests for AroApplication.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class TestApplication extends AroApplication {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	@Getter
	private FXer fxer;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	public Parent getRoot() {
		Scene scene = getScene();
		return (scene == null) ? null : scene.getRoot();
	}

	public void setRoot( Parent root ) {
		getMainStage().setScene( new Scene( root ) );
		fxer = FXer.getUserWith( root );
	}

	/* Life-cycle ---------------------------------------------------------- */
	@Override
	protected void onStart() throws Exception {
		setRoot( new VBox() );
	}
}
