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
package com.avaryuon.fwk.test.technical;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import com.avaryuon.fwk.ViewManager;
import com.avaryuon.fwk.bean.BeanManager;
import com.avaryuon.fwk.test.FXTestCase;

/**
 * Tests for AroApplication.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class ViewManagerTest extends FXTestCase {
	/* TYPES =============================================================== */
	public static class Exists {
		// Nothing here
	}

	public static class NotExists {
		// Nothing here
	}

	public static class NoCompliance {
		// Nothing here
	}

	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private ViewManager viewMgr;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Set-up -------------------------------------------------------------- */
	@Override
	protected void setUpBeans() {
		viewMgr = BeanManager.instance().getBean( ViewManager.class );
	}

	/* Tests --------------------------------------------------------------- */
	public void testLoadExistingView() {
		runAfter( ( ) -> viewMgr.loadView( Exists.class ) );

		Node label = getApp().getFxer().getAt( "#testFXML" );
		assertNotNull( "Label with id \"testFXML\" is not found", label );
		assertTrue( "Object found is not a label", label instanceof Label );
		assertEquals( "test FXML", ((Label) label).getText() );
	}

	public void testLoadNonExistingView() {
		Parent root = getApp().getRoot();
		runAfter( ( ) -> viewMgr.loadView( NotExists.class ) );
		assertEquals( "Root changed after not exists FXML loading.", root,
				getApp().getRoot() );
	}

	public void testLoadNonComplianceView() {
		Parent root = getApp().getRoot();
		runAfter( ( ) -> viewMgr.loadView( NoCompliance.class ) );
		assertEquals( "Root changed after not exists FXML loading.", root,
				getApp().getRoot() );
	}
}
