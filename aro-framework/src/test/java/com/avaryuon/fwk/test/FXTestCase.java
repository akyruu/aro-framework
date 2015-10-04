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
package com.avaryuon.fwk.test;

import groovy.util.GroovyTestCase;
import javafx.application.Platform;
import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.athaydes.automaton.FXApp;
import com.avaryuon.fwk.TestApplication;

/**
 * Test case for JavaFX tests.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public abstract class FXTestCase extends GroovyTestCase {
	/* STATIC FIELDS ======================================================= */
	private static final long PAUSE = 2000L;

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( FXTestCase.class );

	/* FIELDS ============================================================== */
	@Getter
	private static TestApplication app;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Set-up -------------------------------------------------------------- */
	public void setUp() throws Exception {
		if( app == null ) {
			app = new TestApplication();
			app.init();

			Platform.setImplicitExit( false );
			FXApp.startApp( app );
			pause();

			runAfter(  ( ) -> setUpRoot() );
		}

		setUpBeans();
	}

	protected void setUpRoot() {
		// Nothing to do
	}

	protected void setUpBeans() {
		// Nothing to do
	}

	/* Tools --------------------------------------------------------------- */
	/* After --------------------------------------------------------------- */
	public void runAfter( Runnable runnable ) {
		Platform.runLater( runnable );
		pause();
	}

	public void runAfter( Runnable runnable, long time ) {
		Platform.runLater( runnable );
		pause( time );
	}

	/* Pause */
	public void pause() {
		pause( PAUSE );
	}

	public void pause( long time ) {
		try {
			Thread.sleep( PAUSE );
		} catch( InterruptedException ex ) {
			LOGGER.info( "Sleep is interrupted", ex );
		}
	}
}
