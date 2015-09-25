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
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.fwk.bean.BeanManager;
import com.avaryuon.fwk.config.ConfigManager;
import com.avaryuon.fwk.resource.ResourceManager;
import com.avaryuon.fwk.util.RuntimeUtils;
import com.avaryuon.fwk.util.fx.AlertUtils;

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

	/* Properties ---------------------------------------------------------- */
	private static final String DEFAULT_TITLE = "ARO application";

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory.getLogger( "ARO" );

	/* FIELDS ============================================================== */
	/* Singleton ----------------------------------------------------------- */
	private RandomAccessFile lockRAF;
	private FileLock lock;

	/* Properties ---------------------------------------------------------- */
	private String title;

	/* Java FX ------------------------------------------------------------- */
	@Getter(AccessLevel.PACKAGE)
	private Stage mainStage;

	/* Beans --------------------------------------------------------------- */
	private ConfigManager configMgr;
	private ResourceManager resourceMgr;

	/* CONSTRUCTORS ======================================================== */
	protected AroApplication() {
		super();

		/* Singleton */
		instance = this;
	}

	/* METHODS ============================================================= */
	/* Life-cycle ---------------------------------------------------------- */
	@Override
	public final void init() throws Exception {
		LOGGER.info( "Initialize ARO application" );

		// Gets bean from manager (this is not instantiate via injection)
		BeanManager beanMgr = BeanManager.instance();
		configMgr = beanMgr.getBean( ConfigManager.class );
		resourceMgr = beanMgr.getBean( ResourceManager.class );

		// Initialize properties
		title = configMgr.getProperty( "app", "title", DEFAULT_TITLE );
	}

	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public final void start( Stage primaryStage ) throws Exception {
		LOGGER.info( "Start {0}...", title );

		// Check if this application is not already started
		if( checkSingletonInstance() ) {
			// Prepare and show window
			mainStage = primaryStage;
			mainStage.setTitle( title );
			mainStage.show();

			LOGGER.info( "{0} is started !", title );
		}
	}

	/**
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public final void stop() throws Exception {
		LOGGER.info( "Stop {0}...", title );
	}

	/* Singleton ----------------------------------------------------------- */
	/* Instance */
	public static AroApplication instance() {
		return instance;
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
				LOGGER.info( "{0} is stopped.", title );
			} );
		} else {
			AlertUtils.showInfo( title, null,
					"The application is already running." );
			LOGGER.info( "{0} is stopped.", title );
		}

		return success;
	}
}
