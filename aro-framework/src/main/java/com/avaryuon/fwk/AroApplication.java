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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.fwk.bean.BeanManager;
import com.avaryuon.fwk.config.ConfigManager;
import com.avaryuon.fwk.javafx.util.AlertUtils;
import com.avaryuon.fwk.util.RuntimeUtils;

/**
 * <b>Define an ARO application</b>
 * <p>
 * Extends this class and use static method "Application.launch()".
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public abstract class AroApplication extends Application {
	/* STATIC FIELDS ======================================================= */
	/* Singleton ----------------------------------------------------------- */
	private static AroApplication instance;

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( AroApplication.class );

	/* FIELDS ============================================================== */
	/* Singleton ----------------------------------------------------------- */
	private RandomAccessFile lockRAF;
	private FileLock lock;

	/* Properties ---------------------------------------------------------- */
	@Getter
	private AroTitle title;

	/* Java FX ------------------------------------------------------------- */
	@Getter(AccessLevel.PROTECTED)
	private Stage mainStage;

	/* Beans --------------------------------------------------------------- */
	private ConfigManager configMgr;

	/* CONSTRUCTORS ======================================================== */
	protected AroApplication() {
		super();

		/* Singleton */
		instance = this;
	}

	/* METHODS ============================================================= */
	/* Scene --------------------------------------------------------------- */
	public void loadScene( Scene scene ) {
		mainStage.setScene( scene );
	}

	/* Life-cycle ---------------------------------------------------------- */
	/* Initialization */
	@Override
	public final void init() throws Exception {
		LOGGER.info( "Initialize application..." );

		// Gets bean from manager (this is not instantiate via injection)
		BeanManager beanMgr = BeanManager.instance();
		configMgr = beanMgr.getBean( ConfigManager.class );

		// Initialize properties
		String titleBase = configMgr.getProperty( "app", "title" );
		title = new AroTitle( titleBase );

		onInit();
		LOGGER.info( "Application is initialized !" );
	}

	/**
	 * Called at the end of init(). Overrides this method for initialize your
	 * application.
	 */
	protected void onInit() throws Exception {
		// Nothing to do
	}

	/* Starting */
	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public final void start( Stage primaryStage ) throws Exception {
		LOGGER.info( "Start {}...", title );

		// Check if this application is not already started
		if( checkSingletonInstance() ) {
			// Prepare and show window
			mainStage = primaryStage;
			mainStage.setTitle( title.getValue() );
			title.getProperty().addListener(
					( observable, oldValue, newValue ) -> mainStage
							.setTitle( newValue ) );

			// Start and show
			onStart();
			mainStage.show();

			LOGGER.info( "{} is started !", title );
		}
	}

	/**
	 * Called at the end of start(), before mainStage.show(). Overrides this
	 * method for start your application.
	 */
	protected void onStart() throws Exception {
		// Nothing to do
	}

	/* Stopping */
	/**
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public final void stop() throws Exception {
		LOGGER.info( "Stop {}...", title );
		onStop();
	}

	/**
	 * Called at the start of stop(). Overrides this method for stop your
	 * application.
	 */
	protected void onStop() throws Exception {
		// Nothing to do
	}

	/* Singleton ----------------------------------------------------------- */
	/* Instance */
	@SuppressWarnings("unchecked")
	public static < A extends AroApplication > A instance() {
		return (A) instance;
	}

	/* Locking */
	private boolean checkSingletonInstance() {
		boolean success = false;

		// Checks not already started and lock file
		try {
			File lockFile = new File( "./.lock" );
			lockRAF = new RandomAccessFile( lockFile, "rw" );
			lock = lockRAF.getChannel().tryLock();
			success = lock != null;
		} catch( IOException ex ) {
			LOGGER.error( "Lock application failed.", ex );
		}

		// If success to lock, add auto unlocking, else display message
		if( success ) {
			RuntimeUtils.addShutdownHook( ( ) -> {
				try {
					lock.release();
					lockRAF.close();
				} catch( IOException ex ) {
					LOGGER.error( "Unlock application failed.", ex );
				}
				LOGGER.info( "{} is stopped.", title );
			} );
		} else {
			AlertUtils.showInfo( title.getBase(), null,
					"The application is already running." );
			LOGGER.info( "{} is stopped !", title );
		}

		return success;
	}
}
