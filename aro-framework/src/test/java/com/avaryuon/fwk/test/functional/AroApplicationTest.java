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
package com.avaryuon.fwk.test.functional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.avaryuon.fwk.test.FXTestCase;

/**
 * Tests for ARO Application.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class AroApplicationTest extends FXTestCase {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Set-up -------------------------------------------------------------- */
	@Override
	protected void setUpRoot() {
		VBox root = new VBox();

		Label label = new Label( "beforeClick" );
		label.setId( "testLabel" );
		root.getChildren().add( label );

		Button button = new Button( "TestClickButton" );
		button.setId( "testButton" );
		button.setOnAction( new EventHandler< ActionEvent >() {
			@Override
			public void handle( ActionEvent event ) {
				label.setText( "afterClick" );
			}
		} );
		root.getChildren().add( button );

		getApp().setRoot( root );
	}

	/* Tests --------------------------------------------------------------- */
	public void testClickButton() throws Exception {
		Node label = getApp().getFxer().getAt( "#testLabel" );
		assertNotNull( "Label with id \"testLabel\" is not found", label );
		assertTrue( "Object found is not a label", label instanceof Label );
		assertEquals( "beforeClick", ((Label) label).getText() );

		Node button = getApp().getFxer().getAt( "#testButton" );
		assertNotNull( "Button with id \"testButton\" is not found", button );
		assertTrue( "Object found is not a bouton", button instanceof Button );
		assertEquals( "TestClickButton", ((Button) button).getText() );

		getApp().getFxer().clickOn( button ).waitForFxEvents();
		pause();

		assertEquals( "afterClick", ((Label) label).getText() );
		pause();
	}
}
